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
package org.anyframe.oden.bundle.core.config;

import java.util.ArrayList;
import java.util.List;

import org.anyframe.oden.bundle.common.FileUtil;
import org.anyframe.oden.bundle.common.Logger;
import org.anyframe.oden.bundle.common.OdenException;
import org.anyframe.oden.bundle.core.txmitter.TransmitterService;
import org.anyframe.oden.bundle.deploy.DeployerService;

/**
 * This represents agent element in the config.xml
 * 
 * @author Junghwan Hong
 */
public class AgentElement {
	private static final String DEFAULT_LOCATION = null;

	private static final String BACKUP_LOCATION = "\nbackup";

	private String name = "";

	private String host = "";

	private String port = "";

	private List<AgentLocation> locs = new ArrayList<AgentLocation>();

	private String agentHome = null;

	private TransmitterService txmitter;

	public AgentElement(TransmitterService txmitter) {
		this.txmitter = txmitter;
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

	private AgentLocation getDefaultLoc() {
		return getLoc(DEFAULT_LOCATION);
	}

	public String getDefaultLocValue() {
		AgentLocation l = getDefaultLoc();
		return l != null ? l.getValue() : null;
	}

	public void setDefaultLoc(String value) {
		addLoc(DEFAULT_LOCATION, value);
	}

	private AgentLocation getBackupLoc() {
		return getLoc(BACKUP_LOCATION);
	}

	public String getBackupLocValue() {
		AgentLocation l = getBackupLoc();
		return l != null ? l.getValue() : null;
	}

	public void setBackupLoc(String value) {
		addLoc(BACKUP_LOCATION, value);
	}

	public void addLoc(String name, String value) {
		locs.add(new AgentLocation(this, name, value));
	}

	public void removeLoc(String name) throws OdenException {
		if (name == null) {
			throw new OdenException("Can't remove default location");
		}

		for (AgentLocation loc : locs) {
			if (name.equals(loc.getName())) {
				loc.setAgent(null);
				locs.remove(loc);
			}
		}
	}

	/**
	 * 
	 * @param name
	 *            location variable name. null if it default-location
	 * @return
	 */
	private AgentLocation _getLoc(String name) {
		for (AgentLocation loc : locs) {
			if ((name == null && loc.getName() == null) /* default */
					|| (name != null && name.equals(loc.getName()))) {
				return loc;
			}
		}
		return null;
	}

	private AgentLocation getLoc(String name) {
		AgentLocation locsetting = _getLoc(name);
		if (locsetting == null) {
			return null;
		}
		if (FileUtil.isAbsolutePath(locsetting.getValue())) {
			return locsetting;
		}

		try {
			if (agentHome == null) {
				agentHome = getAgentHome();
			}

			String resolved = FileUtil.resolveDotNatationPath(agentHome + "/"
					+ locsetting.getValue());
			if (resolved == null) {
				throw new Exception("Illegal format: " + agentHome + "/"
						+ locsetting.getValue());
			}

			locs.remove(locsetting);
			AgentLocation result = new AgentLocation(this, name, resolved);
			locs.add(result);
			return result;
		} catch (Exception e) {
			Logger.error(e);
		}
		return null;
	}

	private String getAgentHome() throws Exception {
		DeployerService ds = txmitter.getDeployer(getAddr());
		if (ds == null) {
			throw new Exception("Access Fail: " + getAddr());
		}

		return ds.odenHome();
	}

	public String getLocValue(String name) {
		AgentLocation l = getLoc(name);
		return l != null ? l.getValue() : null;
	}

	/**
	 * Get agent's location names except default location(null).
	 * 
	 * @return
	 */
	public List<String> getLocNames() {
		List<String> names = new ArrayList<String>();
		for (AgentLocation loc : locs) {
			if (loc.getName() != DEFAULT_LOCATION
					&& loc.getName() != BACKUP_LOCATION) {
				names.add(loc.getName());
			}
		}
		return names;
	}

	public String getAddr() {
		String port = getPort().equals("") ? "" : ":" + getPort();
		return getHost() + port;
	}
}
