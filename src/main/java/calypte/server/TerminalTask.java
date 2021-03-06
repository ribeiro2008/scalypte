/*
 * Calypte http://calypte.uoutec.com.br/
 * Copyright (C) 2018 UoUTec. (calypte@uoutec.com.br)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package calypte.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import calypte.Cache;
import calypte.server.io.StreamFactory;
import calypte.server.terminalinfolisteners.AutoCommitListener;

/**
 *
 * @author Ribeiro
 */
class TerminalTask implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(TerminalTask.class);
	
    private final Terminal terminal;
    
    private final TerminalFactory factory;
    
    private final TerminalVars terminalVars;
    
    private Cache cache;
    
    private Socket socket;
    
    private int readBufferSize;
    
    private int writeBufferSize;
    
    private StreamFactory streamFactory;
    
    public TerminalTask(Terminal terminal, Cache cache, 
            Socket socket,
            StreamFactory streamFactory,
            int readBufferSize, int writeBufferSize, 
            TerminalFactory factory,
            TerminalVars terminalVars){
        this.terminal            = terminal;
        this.factory             = factory;
        this.cache               = cache;
        this.socket              = socket;
        this.readBufferSize      = readBufferSize;
        this.writeBufferSize     = writeBufferSize;
        this.terminalVars        = terminalVars;
        this.streamFactory       = streamFactory;
    }
    
    public void run() {
        try{
            updateInfo();
            this.terminal.init(this.socket, this.cache, 
                    this.streamFactory,
                    this.readBufferSize, this.writeBufferSize, this.createTerminalVars());
            this.terminal.execute();
        }
        catch(Throwable e){
        	logger.error("terminal error", e);
        }
        finally{
            try{
                terminal.destroy();
                factory.release(this.terminal);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
            	updateInfo();
            }
        }
    }
    
    private TerminalVars createTerminalVars(){
    	TerminalVars lti = new TerminalVars(this.terminalVars, defaultTerminalInfoValues);
    	
    	lti.setListener(ServerConstants.AUTO_COMMIT, new AutoCommitListener(this.terminal));
    	return lti;
    }
    
    private void updateInfo(){
        synchronized(TerminalTask.class){
            this.terminalVars.put("curr_connections",  this.factory.getCurrentInstances());
            this.terminalVars.put("total_connections", this.factory.getCountInstances());
        }
    }
    
	private static final Map<String, Object> defaultTerminalInfoValues = new HashMap<String, Object>();

	static{
		defaultTerminalInfoValues.put(ServerConstants.AUTO_COMMIT, true);
	}
    
}
