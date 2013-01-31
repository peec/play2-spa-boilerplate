package utils;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class RandomGenerator {

	static private SecureRandom random = new SecureRandom();

	
	static public String nextSessionId() {
		return nextSessionId(32);
	}
	
	static public String nextSessionId(int length) {
		return new BigInteger(130, random).toString(length);
	}
}
