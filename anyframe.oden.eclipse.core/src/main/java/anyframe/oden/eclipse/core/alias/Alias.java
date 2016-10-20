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
package anyframe.oden.eclipse.core.alias;

/**
 * A super class for Server and Repository.
 * 
 * @author RHIE Jihwan
 * @version 1.0.0
 * @since 1.0.0 M3
 *
 */
public class Alias {

	static final String NICKNAME = "nickname";
	static final String URL = "url";
	static final String HAS_NO_USER_NAME = "has-no-user-name";
	static final String USER = "user";
	static final String PASSWORD = "password";

	protected String nickname;
	protected String url;
	protected boolean hasNoUserName;
	protected String user;
	protected String password;

	public void remove() {

	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the hasNoUserName
	 */
	public boolean isHasNoUserName() {
		return hasNoUserName;
	}

	/**
	 * @param hasNoUserName the hasNoUserName to set
	 */
	public void setHasNoUserName(boolean hasNoUserName) {
		if (this.hasNoUserName == hasNoUserName)
			return;
		this.hasNoUserName = hasNoUserName;
		if (hasNoUserName) {
			user = "anonymous";
			password = "";
		}
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
