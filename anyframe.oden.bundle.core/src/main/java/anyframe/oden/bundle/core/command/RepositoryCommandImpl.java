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
package anyframe.oden.bundle.core.command;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ungoverned.osgi.service.shell.Command;

import anyframe.oden.bundle.common.DateUtil;
import anyframe.oden.bundle.common.JSONUtil;
import anyframe.oden.bundle.common.FileInfo;
import anyframe.oden.bundle.common.OdenException;
import anyframe.oden.bundle.core.DelegateService;

/**
 * JSON 으로 output을 출력하는 명령어 구현
 * shell이 아닌 http등과 통신시 사용하기 위해 만들어 짐
 * 
 * @author joon1k
 *
 */
public class RepositoryCommandImpl implements Command {
	public final static String[] REPO_OPT = {"repo", "r"};
	
	public final static String[] ACCOUNT_OPT = {"account", "a"};
	
	public final static String PROTOCOL_ACTION = "protocol";
	
	private DelegateService delegateService;
	
	protected void setDelegateService(DelegateService ds){
		this.delegateService = ds;
	}
	
	protected void unsetDelegateService(DelegateService ds){
		this.delegateService = null;
	}
	
	/**
	 * list: ["ftp", "fs", ...] 과 같은 형태의 리턴<br/>
	 * show: [{"name": "file1", "type": "file"}, {"name": "dir1", "type": "dir"}, ...] 형태의 리턴<br/> 
	 */
	public void execute(String line, PrintStream out, PrintStream err) {
		try{
			JSONArray ja = new JSONArray();
			
			Cmd cmd = new Cmd(line);
			String action = cmd.getAction();
			if(PROTOCOL_ACTION.equals(action)){	
				ja = getRepositoryProtocols();
			}else if(Cmd.SHOW_ACTION.equals(action)){
				String[] repoArgs = cmd.getOptionArgArray(REPO_OPT);
				if(repoArgs == null)
					throw new OdenException("Couldn't execute command.");
				ja = getRepositoryStructure(repoArgs);
			}else if(action.length() == 0){
				out.println(getFullUsage());
			}else {
				throw new OdenException("Couldn't execute specified action: " + action);
			}
			
			out.println(ja.toString());
		}catch(OdenException e){
			err.println(JSONUtil.jsonizedException(e));
		}catch(Exception e){
			err.println(JSONUtil.jsonizedException(e));
		}
	}

	private JSONArray getRepositoryStructure(String[] repoArgs) throws OdenException {
		List<FileInfo> files = delegateService.getFilesFromRepo(repoArgs);
		return jsonizedFileList(files);
	}

	private JSONArray jsonizedFileList(List<FileInfo> files) throws OdenException {
		final String NAME = "name";
		final String TYPE = "type";
		final String DIR_TYPE = "dir";
		final String FILE_TYPE = "file";
		final String DATE = "date";
		
		JSONArray array = new JSONArray();
		for(FileInfo file : files){
			JSONObject json = new JSONObject();
			try {
				json.put(NAME, file.getPath());
				json.put(TYPE, file.isDir() ? DIR_TYPE : FILE_TYPE);
				json.put(DATE, DateUtil.toStringDate(file.lastModified()));
			} catch (JSONException e) {
				throw new OdenException("Fail to jsonized: " + file.toString());
			}
			array.put(json);
		}
		return array;
	}

	private JSONArray getRepositoryProtocols() {
		List<String> types = delegateService.getRepositoryProtocols();
		return new JSONArray(types);
	}

	public String getName() {
		return "repository";
	}

	public String getUsage() {
		return getName() + " " + Cmd.HELP_ACTION;
	}
	
	public String getFullUsage() throws OdenException {
		return getName() + " " + PROTOCOL_ACTION + "\n" +
				getName() + " " + Cmd.SHOW_ACTION + 
				"\n\t-r[epo] " + getRepositoryUsages();
	}
	
	private String getRepositoryUsages() throws OdenException {
		StringBuffer usages = new StringBuffer();
		for(Iterator<String> it = delegateService.getRepositoryUsages().iterator(); it.hasNext();) {
			usages.append("[" + it.next() + "]");
			if(it.hasNext())
				usages.append(" | ");
		}
		if(usages.length() == 0)
			throw new OdenException("Couldn't find any repository services.");
		return usages.toString();
	}

	public String getShortDescription() {
		return "access to the repository";
	}

}
