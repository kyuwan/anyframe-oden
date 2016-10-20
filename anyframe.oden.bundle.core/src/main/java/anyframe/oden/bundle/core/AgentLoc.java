/*
 * Copyright 2009 SAMSUNG SDS Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package anyframe.oden.bundle.core;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import anyframe.oden.bundle.common.FileUtil;
import anyframe.oden.bundle.common.JSONizable;
import anyframe.oden.bundle.common.OdenException;
import anyframe.oden.bundle.core.config.AgentElement;
import anyframe.oden.bundle.core.config.AgentLocation;
import anyframe.oden.bundle.core.config.OdenConfigService;

/**
 * This represents agent's location.
 * 
 * @author joon1k
 *
 */
public class AgentLoc implements JSONizable, Serializable{
	private static final long serialVersionUID = -5238945691781751043L; 

	private final static char AGENT_SEP = ':';
	
	private final static char PATH_SEP = '/';
	
	private String agentName;
	
	private String agentAddr;
	
	private String location;
		
	/**
	 * 
	 * @param s <agent> or <agent>/<path> or <agent>/$<variable>/<path>
	 */
	public AgentLoc(String args, OdenConfigService configsvc) throws OdenException {
		agentName = agentName(args);
		
		AgentElement agentinfo = null;
		agentinfo = configsvc.getAgent(agentName);
		if(agentinfo == null)
			throw new OdenException("Fail to get agent information: " + agentName);
		
		agentAddr = agentinfo.getAddr();
		
		location = resolveLocation(args, agentinfo);
	}
	
	public AgentLoc(String agentName, String agentAddr, String location){
		this.agentName = agentName;
		this.agentAddr = agentAddr;
		this.location = location;
	}

	private String agentName(String args) {
		int idx = args.indexOf(AGENT_SEP);
		if(idx == -1)
			return args;
		return args.substring(0, idx);
	}
	
	/**
	 * <agent-name>:<$<location-var>[/<path> | ~[/<path>] | <absolute-path>]>
	 * 
	 * @param args
	 * @param info
	 * @return
	 * @throws OdenException
	 */
	private String resolveLocation(String args, AgentElement info) throws OdenException{
		String path = secondToken(args, AGENT_SEP);
		if(path == null)
			throw new OdenException("Invalid format: " + args);
		
		String parent = null;
		String child = null;
		if(path.startsWith("$")){
			parent = resolveLocationVariable(firstToken(path.substring(1), PATH_SEP), info);
			child = secondToken(path, PATH_SEP);
		}else if(path.startsWith("~")){
			parent = resolveLocationVariable(null, info);
			child = secondToken(path, PATH_SEP);
		}else {
			parent = null;
			child = path;
		}
		return FileUtil.combinePath(parent, child);
	}

	
	/**
	 * get first token when separate var with sep.
	 * return value doesn't include sep.
	 * 
	 * @param var
	 * @param sep
	 * @return
	 */
	private String firstToken(String var, char sep) {
		if(var == null || var.length() == 0)
			return null;
		
		int idx = var.indexOf(sep);
		if(idx == -1) 
			return var;
		return var.substring(0, idx);
	}
	
	/**
	 * get second token when parse var with sep.
	 * return value doesn't include sep.
	 * 
	 * @param var
	 * @param sep
	 * @return
	 */
	private String secondToken(String var, char sep) {
		int idx = var.indexOf(sep);
		if(idx == -1 || var.length() == idx+1) 
			return null;
		return var.substring(idx+1);
	}
	
	private String secondTokenStartWithSEP(String var, char sep) {
		int idx = var.indexOf(sep);
		if(idx == -1 || var.length() == idx) 
			return null;
		return var.substring(idx);
	}

//	private String resolveLocation(String args, AgentElement info) throws OdenException{
//		int idx = args.indexOf(AGENT_SEP);
//		if(idx == -1 || args.length() == idx+1) { // <agent-name> or <agent-name>:
//			throw new OdenException("Invalid format: " + args);
//		}
//		
//		String var = args.substring(idx+1);	// not start with ':'
//		if(!var.startsWith("$")) {		// <agent-name>:<path>
//			return FileUtil.combinePath(resolveLocationVariable(null, info), var);
//		}
//		
//		var = var.substring(1);		// not start with '$'
//		idx = var.indexOf(SEPARATOR);
//		if(idx == -1) {	// <agent-name>/$<var>
//			return resolveLocationVariable(var, info);
//		}
//		
//		if(var.length() == idx+1) {		// $<var>/
//			return resolveLocationVariable(var.substring(0, idx+1), info);
//		}
//		
//		return FileUtil.combinePath(resolveLocationVariable(var.substring(0, idx+1), info),		// <agent-name>/$<var>/.. 
//				var.substring(idx+1));		
//	}

	private String resolveLocationVariable(String var, AgentElement info) throws OdenException {
		AgentLocation loc = info.getLoc(var);
		if(loc == null)
			throw new OdenException("Couldn't find a location variable: " + var);
		return loc.getValue();
	}
	
	public String agentName() {
		return agentName;
	}

	public String agentAddr() {
		return agentAddr;
	}

	public String location() {
		return location;
	}

	public Object jsonize() {
		try {
			return new JSONObject().put("name", agentName)
					.put("addr", agentAddr).put("loc", location);
		} catch (JSONException e) {
		}
		return new JSONObject();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AgentLoc){
			AgentLoc ra = (AgentLoc)obj;
			if(equals(agentAddr, ra.agentAddr()) && 
					equals(agentName, ra.agentName()) && 
					equals(location, ra.location()))
				return true;
		}
		return false;
	}
	
	private boolean equals(String s0, String s1){
		return (s0 == null && s1 == null) ||
			(s0 != null && s0.equals(s1));
	}
	
}
