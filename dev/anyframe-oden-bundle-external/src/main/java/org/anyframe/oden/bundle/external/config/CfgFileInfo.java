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
package org.anyframe.oden.bundle.external.config;

import java.io.Serializable;
import java.util.List;

import org.anyframe.oden.bundle.common.Utils;

/**
 * This is CfgFileInfo Class
 * 
 * @author Junghwan Hong
 */
public class CfgFileInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String ciId;

	String exeDir;

	boolean delete;

	List<CfgTarget> targets;

	public CfgFileInfo(String ciId, String exeDir, boolean delete,
			List<CfgTarget> targets) {
		this.ciId = ciId;
		this.exeDir = exeDir;
		this.delete = delete;
		this.targets = targets;

	}

	public String getCiId() {
		return ciId;
	}

	public String getExeDir() {
		return exeDir;
	}

	public boolean isDelete() {
		return delete;
	}

	public List<CfgTarget> getTargets() {
		return targets;
	}

	@Override
	public int hashCode() {
		return Utils.hashCode(ciId, exeDir, targets);
	}
}
