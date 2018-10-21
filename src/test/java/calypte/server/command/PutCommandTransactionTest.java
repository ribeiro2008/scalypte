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

package calypte.server.command;

import java.awt.EventQueue;
import java.io.IOException;

import calypte.Configuration;
import calypte.server.CalypteServer;
import calypte.server.ClientHelper;
import junit.framework.TestCase;

public class PutCommandTransactionTest extends TestCase{

	private CalypteServer server;
	
	private ClientHelper client;
	
	@Override
	public void setUp() throws Exception{
		Configuration config = new Configuration();
		config.setProperty("transaction_support", "true");
		this.server = new CalypteServer(config);
		EventQueue.invokeLater(new Runnable(){

			public void run() {
				try{
					server.start();
				}
				catch(Throwable e){
					e.printStackTrace();
				}
				
			}
			
		});

		Thread.sleep(1000);
		
		client = new ClientHelper();
		
	}

	@Override
	public void tearDown() throws Exception{
		this.server.stop();
		this.server = null;
		this.client.close();
		this.client = null;
		System.gc();
	}
	
	/* not exist  */
	
	public void testput_keyN_valueE_not_exist() throws IOException {
		client.testRequest(
				">>put key 0 0 0 0",
				"<<ERROR 1004: Bad command syntax error!"
		);
	}

	public void testput_key1_valueE_not_exist() throws IOException {
		client.testRequest(
				">>put k 0 0 0 0",
				"<<ERROR 1004: Bad command syntax error!"
		);
	}

	public void testput_keyN_valueN_not_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_valueN_not_exist
		);
	}
	
	public void testput_keyN_value1_not_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_value1_not_exist
		);
	}

	public void testput_key1_valueN_not_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_valueN_not_exist
		);
	}
	
	public void testput_key1_value1_not_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_value1_not_exist
		);
	}

	/* exist */
	
	public void testput_keyN_valueN_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_valueN_not_exist,
				CommandHelper.put_keyN_valueN_exist
		);
	}
	
	public void testput_keyN_value1_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_value1_not_exist,
				CommandHelper.put_keyN_value1_exist
		);
	}

	public void testput_key1_valueN_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_valueN_not_exist,
				CommandHelper.put_key1_valueN_exist
		);
	}
	
	public void testput_key1_value1_exist() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_value1_not_exist,
				CommandHelper.put_key1_value1_exist
		);
	}

	/* flush */

	public void testput_keyN_valueN_flush() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_valueN_not_exist,
				CommandHelper.put_keyN_valueN_exist,
				CommandHelper.flush,
				CommandHelper.put_keyN_valueN_not_exist
		);
	}
	
	public void testput_keyN_value1_flush() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_value1_not_exist,
				CommandHelper.put_keyN_value1_exist,
				CommandHelper.flush,
				CommandHelper.put_keyN_value1_not_exist
		);
	}

	public void testput_key1_valueN_flush() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_valueN_not_exist,
				CommandHelper.put_key1_valueN_exist,
				CommandHelper.flush,
				CommandHelper.put_key1_valueN_not_exist
		);
	}
	
	public void testput_key1_value1_flush() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_value1_not_exist,
				CommandHelper.put_key1_value1_exist,
				CommandHelper.flush,
				CommandHelper.put_key1_value1_not_exist
		);
	}
	
	/* remove */

	public void testput_keyN_valueN_remove() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_valueN_not_exist,
				CommandHelper.put_keyN_valueN_exist,
				CommandHelper.remove_keyN_valueN_exist,
				CommandHelper.put_keyN_valueN_not_exist
		);
	}
	
	public void testput_keyN_value1_remove() throws IOException {
		client.testRequest(
				CommandHelper.put_keyN_value1_not_exist,
				CommandHelper.put_keyN_value1_exist,
				CommandHelper.remove_keyN_value1_exist,
				CommandHelper.put_keyN_value1_not_exist
		);
	}

	public void testput_key1_valueN_remove() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_valueN_not_exist,
				CommandHelper.put_key1_valueN_exist,
				CommandHelper.remove_key1_valueN_exist,
				CommandHelper.put_key1_valueN_not_exist
		);
	}
	
	public void testput_key1_value1_remove() throws IOException {
		client.testRequest(
				CommandHelper.put_key1_value1_not_exist,
				CommandHelper.put_key1_value1_exist,
				CommandHelper.remove_key1_value1_exist,
				CommandHelper.put_key1_value1_not_exist
		);
	}
	
	/*  */
	
	public void testTimeToLiveNeg() throws IOException {
		client.testRequest(
				">>put key -1 0 5 0",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}

	public void testTimeToIdleNeg() throws IOException {
		client.testRequest(
				">>put key 0 -1 5 0",
				">>teste",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}

	public void testSizeNeg() throws IOException {
		client.testRequest(
				">>put key 0 0 -1 0",
				">>teste",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}

	public void testTimeToLiveChar() throws IOException {
		client.testRequest(
				">>put key a 0 5 0",
				">>teste",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}

	public void testTimeToIdleChar() throws IOException {
		client.testRequest(
				">>put key 0 a 5 0",
				">>teste",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}

	public void testSizeChar() throws IOException {
		client.testRequest(
				">>put key 0 0 a 0",
				">>teste",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}

	public void testParamsError() throws IOException {
		client.testRequest(
				">>put key 0 0 0 0 0",
				"<<ERROR 1004: Bad command syntax error!"
			);
	}
	
}
