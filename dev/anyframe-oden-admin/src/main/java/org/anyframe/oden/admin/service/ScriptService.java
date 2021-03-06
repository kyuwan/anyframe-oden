/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.anyframe.oden.admin.service;

import java.util.List;

import org.anyframe.oden.admin.domain.Command;
import org.anyframe.pagination.Page;
import org.json.JSONObject;

/**
 * This is ScriptService Interface
 * 
 * @author Sujeong Lee
 */
public interface ScriptService {

	/**
	 * 
	 * @param domain
	 * @throws Exception
	 */
	public Page findListByPk(String domain, String opt) throws Exception;

	public Page findListByPk(String domain, String opt, List<JSONObject> objectArray) throws Exception;
	
	public String run(String[] param, String name, String script)
			throws Exception;

	public List<Command> getCommandList(String jobName) throws Exception;

}