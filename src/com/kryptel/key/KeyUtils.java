/*******************************************************************************

  Product:       Kryptel/Java
  File:          KeyUtils.java

  Copyright (c) 2018 Inv Softworks LLC,    http://www.kryptel.com

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.

*******************************************************************************/


package com.kryptel.key;


import java.util.Arrays;

import com.kryptel.IKryptelComponent;
import com.kryptel.cipher.IBlockCipherParams;
import com.kryptel.hash_function.IHashFunction;
import com.kryptel.hash_function.IHashFunctionParams;


public final class KeyUtils {

	//
	// Verificators
	//
	
	private final static byte filler[] = {
			(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07, (byte)0x08, (byte)0x09, (byte)0x0A, (byte)0x0B, (byte)0x0C, (byte)0x0D, (byte)0x0E, (byte)0x0F,
			(byte)0x10, (byte)0x11, (byte)0x12, (byte)0x13, (byte)0x14, (byte)0x15, (byte)0x16, (byte)0x17, (byte)0x18, (byte)0x19, (byte)0x1A, (byte)0x1B, (byte)0x1C, (byte)0x1D, (byte)0x1E, (byte)0x1F,
			(byte)0x20, (byte)0x21, (byte)0x22, (byte)0x23, (byte)0x24, (byte)0x25, (byte)0x26, (byte)0x27, (byte)0x28, (byte)0x29, (byte)0x2A, (byte)0x2B, (byte)0x2C, (byte)0x2D, (byte)0x2E, (byte)0x2F,
			(byte)0x30, (byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34, (byte)0x35, (byte)0x36, (byte)0x37, (byte)0x38, (byte)0x39, (byte)0x3A, (byte)0x3B, (byte)0x3C, (byte)0x3D, (byte)0x3E, (byte)0x3F,
			(byte)0x40, (byte)0x41, (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x48, (byte)0x49, (byte)0x4A, (byte)0x4B, (byte)0x4C, (byte)0x4D, (byte)0x4E, (byte)0x4F,
			(byte)0x50, (byte)0x51, (byte)0x52, (byte)0x53, (byte)0x54, (byte)0x55, (byte)0x56, (byte)0x57, (byte)0x58, (byte)0x59, (byte)0x5A, (byte)0x5B, (byte)0x5C, (byte)0x5D, (byte)0x5E, (byte)0x5F,
			(byte)0x60, (byte)0x61, (byte)0x62, (byte)0x63, (byte)0x64, (byte)0x65, (byte)0x66, (byte)0x67, (byte)0x68, (byte)0x69, (byte)0x6A, (byte)0x6B, (byte)0x6C, (byte)0x6D, (byte)0x6E, (byte)0x6F,
			(byte)0x70, (byte)0x71, (byte)0x72, (byte)0x73, (byte)0x74, (byte)0x75, (byte)0x76, (byte)0x77, (byte)0x78, (byte)0x79, (byte)0x7A, (byte)0x7B, (byte)0x7C, (byte)0x7D, (byte)0x7E, (byte)0x7F,
			(byte)0x80, (byte)0x81, (byte)0x82, (byte)0x83, (byte)0x84, (byte)0x85, (byte)0x86, (byte)0x87, (byte)0x88, (byte)0x89, (byte)0x8A, (byte)0x8B, (byte)0x8C, (byte)0x8D, (byte)0x8E, (byte)0x8F,
			(byte)0x90, (byte)0x91, (byte)0x92, (byte)0x93, (byte)0x94, (byte)0x95, (byte)0x96, (byte)0x97, (byte)0x98, (byte)0x99, (byte)0x9A, (byte)0x9B, (byte)0x9C, (byte)0x9D, (byte)0x9E, (byte)0x9F,
			(byte)0xA0, (byte)0xA1, (byte)0xA2, (byte)0xA3, (byte)0xA4, (byte)0xA5, (byte)0xA6, (byte)0xA7, (byte)0xA8, (byte)0xA9, (byte)0xAA, (byte)0xAB, (byte)0xAC, (byte)0xAD, (byte)0xAE, (byte)0xAF,
			(byte)0xB0, (byte)0xB1, (byte)0xB2, (byte)0xB3, (byte)0xB4, (byte)0xB5, (byte)0xB6, (byte)0xB7, (byte)0xB8, (byte)0xB9, (byte)0xBA, (byte)0xBB, (byte)0xBC, (byte)0xBD, (byte)0xBE, (byte)0xBF,
			(byte)0xC0, (byte)0xC1, (byte)0xC2, (byte)0xC3, (byte)0xC4, (byte)0xC5, (byte)0xC6, (byte)0xC7, (byte)0xC8, (byte)0xC9, (byte)0xCA, (byte)0xCB, (byte)0xCC, (byte)0xCD, (byte)0xCE, (byte)0xCF,
			(byte)0xD0, (byte)0xD1, (byte)0xD2, (byte)0xD3, (byte)0xD4, (byte)0xD5, (byte)0xD6, (byte)0xD7, (byte)0xD8, (byte)0xD9, (byte)0xDA, (byte)0xDB, (byte)0xDC, (byte)0xDD, (byte)0xDE, (byte)0xDF,
			(byte)0xE0, (byte)0xE1, (byte)0xE2, (byte)0xE3, (byte)0xE4, (byte)0xE5, (byte)0xE6, (byte)0xE7, (byte)0xE8, (byte)0xE9, (byte)0xEA, (byte)0xEB, (byte)0xEC, (byte)0xED, (byte)0xEE, (byte)0xEF,
			(byte)0xF0, (byte)0xF1, (byte)0xF2, (byte)0xF3, (byte)0xF4, (byte)0xF5, (byte)0xF6, (byte)0xF7, (byte)0xF8, (byte)0xF9, (byte)0xFA, (byte)0xFB, (byte)0xFC, (byte)0xFD, (byte)0xFE, (byte)0xFF };

	
	public static byte[] ComputeVerificator(IHashFunction hashFunc, int hashSize, byte salt[], byte[] key, int keySize) throws Exception {
		hashFunc.Init();
		hashFunc.Hash(salt, 0, salt.length);
		assert key.length >= keySize;
		hashFunc.Hash(key, 0, keySize);
		byte[] verificator = hashFunc.Done();
		
		for (int i = 1; i <= 256; i++) {
			hashFunc.Init();
			hashFunc.Hash(filler, 0, i);
			hashFunc.Hash(verificator, 0, hashSize);
			verificator = hashFunc.Done();
		}
		
		return verificator;
	}

	public static byte[] ComputePasswordVerificator(IHashFunction hashFunc, int hashSize, byte salt[], String password) throws Exception {
		byte[] byteSeq = password.getBytes("UnicodeLittleUnmarked");
		return ComputeVerificator(hashFunc, hashSize, salt, byteSeq, byteSeq.length);
	}

	public static byte[] ComputeBinaryKeyVerificator(IHashFunction hashFunc, int hashSize, byte salt[], byte[] binaryKey) throws Exception {
		return ComputeVerificator(hashFunc, hashSize, salt, binaryKey, binaryKey.length);
	}
	
	
	//
	// Key derivation
	//
	
	public static byte[] DeriveEncryptionKey(byte[] baseKey, int cipherKeySize) {
		byte key[] = new byte [cipherKeySize];
		System.arraycopy(baseKey, 0, key, 0, Math.min(cipherKeySize, baseKey.length));
		if (cipherKeySize > baseKey.length) Arrays.fill(key, baseKey.length, cipherKeySize, (byte)0);
		return key;
	}


	public static byte[] DeriveHmacKey(byte[] baseKey) {
		byte key[] = new byte [baseKey.length];
		for (int i = 0, j = baseKey.length; i < baseKey.length; ) key[i++] = (byte)(~baseKey[--j]);
		return key;
	}
	
	
	public static byte[] DeriveInitVector(byte[] baseKey, int blockSize) {
		int len = Math.min(baseKey.length, blockSize);
		byte iv[] = new byte [blockSize];
		for (int i = 0, j = baseKey.length; i < len; ) iv[i++] = (byte)(~baseKey[--j]);
		if (blockSize > baseKey.length) Arrays.fill(iv, baseKey.length, blockSize, (byte)0xA5);
		return iv;
	}


	//
	// Misc key function
	//
	
	public static KeyBlock.ComponentDescriptor FillComponentDescriptor(	IKryptelComponent cipherComp,
																																			IBlockCipherParams cipherParams,
																																			IKryptelComponent hashFuncComp,
																																			IHashFunctionParams hashFuncParams) throws Exception {
		KeyBlock.ComponentDescriptor desc = new KeyBlock.ComponentDescriptor();
		desc.cipher = cipherComp.ComponentID();
		desc.cipherBlockSize = cipherParams.GetBlockSize();
		desc.cipherKeySize = cipherParams.GetKeySize();
		desc.cipherRounds = cipherParams.GetRounds();
		desc.cipherScheme = cipherParams.GetScheme();
		desc.hashFunction = hashFuncComp.ComponentID();
		desc.hashSize = hashFuncParams.GetHashSize();
		desc.hashPasses = hashFuncParams.GetPasses();
		desc.hashScheme = hashFuncParams.GetScheme();
		return desc;
	}
}
