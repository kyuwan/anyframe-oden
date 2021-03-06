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
package org.anyframe.oden.bundle.hessiansvr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.anyframe.oden.bundle.common.FileUtil;

/**
 * Work about Copy, Undo, Remove files.
 * 
 * @author Junghwan Hong
 */
@SuppressWarnings("PMD")
public class DeployerUtils {
	private static int MAX_UNDO;

	/**
	 * copy filePath's file to destPath
	 * 
	 * @param parentPath
	 * @param filePath
	 * @param destPath
	 * @throws IOException
	 */
	public static void copy(String parentPath, String filePath, String destPath)
			throws IOException {
		File destFile = new File(destPath, filePath);
		boolean created = false;
		try {
			if (!destFile.exists()) {
				FileUtil.mkdirs(destFile);
				created = true;
			}
			// FileUtil.copy(new File(parentPath, filePath), destFile);
			FileUtil.touchcopy(new File(parentPath, filePath), destFile);
		} catch (IOException e) {
			if (destFile != null && created) {
				destFile.delete();
			}
			throw e;
		}
	}

	/**
	 * backup file to the backPath for undo function
	 * 
	 * @param parentPath
	 * @param filePath
	 * @param bakPath
	 *            null if no backup
	 * @param bakDir
	 *            Max Counts
	 * @return is backuped?
	 * @throws IOException
	 */
	public static boolean undoBackup(String parentPath, String filePath,
			String bakPath, int backupcnt) throws IOException {
		MAX_UNDO = backupcnt;

		if (bakPath == null) {
			return new File(parentPath, filePath).exists();
		}

		if (!new File(parentPath, filePath).exists()
				|| !new File(bakPath).isAbsolute()) {
			return false;
		}

		removeOldUndos(new File(bakPath).getParent());
		copy(parentPath, filePath, bakPath);
		return true;
	}

	/**
	 * remove old backups for undo
	 * 
	 * @param bakDir
	 */
	@SuppressWarnings("PMD")
	public static void removeOldUndos(String bakDir) {
		File bak = new File(bakDir);
		if (!bak.exists() || !bak.isDirectory())
			return;

		List<Long> list = new ArrayList<Long>();
		File[] fs = bak.listFiles();
		for (File f : fs) {
			if (f.isDirectory()) {
				try {
					long date = Long.parseLong(f.getName());
					list.add(date);
				} catch (Exception e) {
					// ignore this file
				}
			}
		}
		if (list.size() > MAX_UNDO) {
			Collections.sort(list, Collections.reverseOrder());
			for (int i = MAX_UNDO; i < list.size(); i++)
				FileUtil.removeDir(new File(bakDir, String.valueOf(list.get(i))));
		}
	}
}
