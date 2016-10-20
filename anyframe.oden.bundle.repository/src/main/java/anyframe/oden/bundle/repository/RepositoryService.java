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
package anyframe.oden.bundle.repository;

import java.util.List;

import anyframe.oden.bundle.common.FatInputStream;
import anyframe.oden.bundle.common.FileInfo;
import anyframe.oden.bundle.common.OdenException;

/**
 * Oden Service to access some file repositories like local filesystem or ftp.
 * 
 * @author joon1k
 *
 */
public interface RepositoryService {
	public boolean matchedURI(String[] args);
	
	public String getProtocol();

	/**
	 * 
	 * @param repoArgs
	 * @param string2 
	 * @param string 
	 * @param regex should be finished with file or wildcard file or **
	 * @param isRecentOnly
	 * @return
	 * @throws OdenException
	 */
	public List<String> resolveFileRegex(String[] args, 
			List<String> includes, List<String> excludes) 
			throws OdenException;

	public FatInputStream resolve(String[] args, String file) throws OdenException;

	/**
	 * return required arguments to access repository service.
	 * 커맨드라인에서 usage 로 사용 될 것임. 계정관련 인자는 사용법에서 제외
	 * @return
	 */
	public String getUsage();

	public List<FileInfo> getFileList(String[] args) throws OdenException;
	
}
