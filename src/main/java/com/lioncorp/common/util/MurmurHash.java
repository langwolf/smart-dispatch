package com.lioncorp.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * @author bjssgong
 * byte[] mm3_le = Hashing.murmur3_128().hashString("abc", UTF_8).asBytes();
 * byte[] mm3_be = Bytes.toArray(Lists.reverse(Bytes.asList(mm3_le)));
 * assertEquals("79267961763742113019008347020647561319",
 *   new BigInteger(mm3_be).toString());
 */
public class MurmurHash {

	private static final HashFunction MURMUR3_32 = Hashing.murmur3_32();

	public static long hash(String input) {
		return (long)(murmurHash32(input) & 0xFFFFFFFFL) ;
	}
	
	public static int murmurHash32(String input) {
		return murmurHash32(input, StandardCharsets.UTF_8);
	}
	
	public static int murmurHash32(String input, Charset charset) {
		return MURMUR3_32.newHasher().putString(input, charset).hash().hashCode();
	}

	public static void main(String[] args) throws Exception {
		long t = hash("9IG74V5H00963VRO_video_VD3GSLR2O");
		System.out.println(t);
//		long test = (long) t % 8;
//		System.out.println(test);
	}
}
