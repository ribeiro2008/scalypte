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

package calypte.server.util;


/**
 * 
 * @author Ribeiro
 *
 */
public class ArraySplit {

	public static final byte[] EMPTY = new byte[0];
	
	private byte[] data;
	
	private int len;
	
	private int start;
	
	private byte separator;
	
	public void setSeparator(byte value){
		separator = value;
	}
	
	public void setData(byte[] value){
		data  = value;
		len   = data.length;
		start = 0;
	}
	
	public void reset(){
		start = 0;
	}
	
	public int readNext(byte[] buf, int o, int l){
		try{
			int end = start;
			
			while(end<len && data[end++] != separator);
			
			int cp = end < len? end - start - 1 : end - start;
			
			ArraysUtil.arraycopy(data, start, buf, o, cp > l? l : cp);
			
			start  = end;
			
			return cp;
		}
		catch(Throwable e){
			return -1;
		}
	}

	public String readNextString(){
		try{
			int end = start;
			
			while(end<len && data[end++] != separator);
			
			int cp = end < len? end - start - 1 : end - start;
			
			String r = ArraysUtil.toString(data, start, cp);
			
			start  = end;

			return r;
		}
		catch(Throwable e){
			return null;
		}
	}

	public long readNextLong(){
		try{
			int end = start;
			
			while(end<len && data[end++] != separator);
			
			int cp = end < len? end - start - 1 : end - start;
			
			long r = ArraysUtil.toLong(data, start, cp);
			
			start  = end;

			return r;
		}
		catch(Throwable e){
			return 0;
		}
	}

	public int readNextInt(){
		try{
			int end = start;
			
			while(end<len && data[end++] != separator);
			
			int cp = end < len? end - start - 1 : end - start;
			
			int r = ArraysUtil.toInt(data, start, cp);
			
			start  = end;

			return r;
		}
		catch(Throwable e){
			return 0;
		}
	}
	
	public boolean readNextBoolean(){
		try{
			int end = start;
			
			while(end<len && data[end++] != separator);
			
			boolean r = data[start] != '0';
			
			start  = end;

			return r;
		}
		catch(Throwable e){
			return false;
		}
	}
	
}
