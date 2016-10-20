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
package anyframe.oden.bundle.core.config;

import java.util.ArrayList;
import java.util.List;

import anyframe.oden.bundle.common.OdenException;

/**
 * This represents agent element in the config.xml
 * 
 * @author joon1k
 *
 */
public class AgentElement {

	private String name = "";
	
	private String host = "";
	
	private String port = "";
	
	private List<AgentLocation> locs = new ArrayList<AgentLocation>();
	
	public AgentElement(){
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public AgentLocation getDefaultLoc() {
		return getLoc(null);
	}

	public void setDefaultLoc(String value) {
		addLoc(null, value);
	}

	public void addLoc(String name, String value){
		locs.add(new AgentLocation(this, name, value));
	}
	
	public void removeLoc(String name) throws OdenException{
		if(name == null)	
			throw new OdenException("Can't remove default location");
		
		for(AgentLocation loc : locs){
			if(name.equals(loc.getName())){
				loc.setAgent(null);
				locs.remove(loc);
			}
		}
	}
	
	/**
	 * 
	 * @param name location variable name that can be null.
	 * @return
	 */
	public AgentLocation getLoc(String name){
		for(AgentLocation loc : locs){
			if(name == null){
				if(loc.getName() == null) return loc;
			}else {
				if(name.equals(loc.getName())) return loc;
			}
		}
		return null;
	}
	
	/**
	 * Get agent's location names except default location(null). 
	 * 
	 * @return
	 */
	public List<String> getLocNames(){
		List<String> names = new ArrayList<String>();
		for(AgentLocation loc : locs){
			if(loc.getName() != null)
				names.add(loc.getName());
		}
		return names;
	}
	
	public String getAddr(){
		String port = getPort().equals("") ? "" :  ":" + getPort();
		return getHost() + port;
	}
}
