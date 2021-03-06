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
package org.anyframe.oden.bundle.deploy;

import java.io.Serializable;

/**
 * This is ByteArray class.
 * 
 * @author Junghwan Hong
 */
public class ByteArray implements Serializable {
	private static final long serialVersionUID = -7999398494995182535L;

	private byte[] bytes;

	public ByteArray(byte[] bytes) {
		this.bytes = bytes;
	}

	public ByteArray(byte[] bytes, int sz) {
		byte[] buf = new byte[sz];
		System.arraycopy(bytes, 0, buf, 0, sz);
		this.bytes = buf;
	}

	public byte[] getBytes() {
		return bytes;
	}
}
