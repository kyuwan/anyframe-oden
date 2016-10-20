/* 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package anyframe.oden.eclipse.core.utils;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
	public final static String KNOWN_EXCEPTION = "KnownException";	
	public final static String UNKNOWN_EXCEPTION = "UnknownException";
	
	private static String jsonizedException(String key, Exception e) {
		JSONArray jarr = new JSONArray();
		try {
			jarr.put(new JSONObject().put(key, e.getMessage()) );
		} catch (JSONException e1) {
			try {
				jarr.put(new JSONObject().put(KNOWN_EXCEPTION, 
						JSONException.class.toString()) );
			} catch (JSONException e2) {
			}
		}
		return jarr.toString();
	}
	
	public static String jsonizedUnknowException(Exception e) {
		return jsonizedException(UNKNOWN_EXCEPTION, e);
	}
	
	public static String jsonizedKnownException(Exception e){
		return jsonizedException(KNOWN_EXCEPTION, e);
	}
	
	public static String jsonizedException(Exception e) {
		
		return jsonizedUnknowException(e);
	}
	
	public static String toString(JSONArray jArr) {
		StringBuffer buf = new StringBuffer();
		try{
			for(int i=0; i<jArr.length(); i++){
				Object o = jArr.get(i);
				if(o instanceof JSONArray) {
					buf.append(toStringWithoutNewLine((JSONArray)o));
				}else if(o instanceof JSONObject) {
					buf.append(toString((JSONObject)o));
				}else {
					buf.append(o.toString());
				}
				if(i < jArr.length())
					buf.append("\n");
			}
		}catch(JSONException e) {
			jArr.toString();
		}
		return buf.toString();
	}
	
	private static String toStringWithoutNewLine(JSONArray jArr) {
		StringBuffer buf = new StringBuffer();
		try{
			for(int i=0; i<jArr.length(); i++){
				Object o = jArr.get(i);
				if(o instanceof JSONArray) {
					buf.append(toStringWithoutNewLine((JSONArray)o));
				}else if(o instanceof JSONObject) {
					buf.append(toString((JSONObject)o));
				}else {
					buf.append(o.toString());
				}
				if(i < jArr.length())
					buf.append(", ");
			}
		}catch(JSONException e) {
			return jArr.toString();
		}
		return buf.toString();
	}
	
	private static String toString(JSONObject jObj) {
		StringBuffer buf = new StringBuffer();
		try{
			for(Iterator<String> i = jObj.keys();i.hasNext();){
				String key = i.next();
				buf.append(key).append(" = ").append(jObj.get(key));
				if(i.hasNext())
					buf.append(", ");
			}
		}catch(JSONException e){
			return jObj.toString();
		}
		return buf.toString();
	}
}