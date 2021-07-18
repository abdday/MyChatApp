package data;

import java.util.Random;
import java.util.UUID;

public class IdsGenerator {

	public IdsGenerator() {
	}

	public String generateGameId() {
		return getRandomString(5, 5);
	}

	public static String generateId() {
		return UUID.randomUUID().toString();
	}

	// to create random strings
	private String getRandomString(int minLen, int maxLen) {
		Random rand = new Random();
		char[] alphabet = getCharSet();
		StringBuilder out = new StringBuilder();
		int len = rand.nextInt((maxLen - minLen) + 1) + minLen;
		while (out.length() < len) {
			int idx = Math.abs((rand.nextInt() % alphabet.length));
			out.append(alphabet[idx]);
		}
		return out.toString();
	}

	private char[] getCharSet() {
		StringBuffer b = new StringBuffer(128);
		for (int i = 48; i <= 57; i++)
			b.append((char) i); // 0-9
		for (int i = 65; i <= 90; i++)
			b.append((char) i); // A-Z
		for (int i = 97; i <= 122; i++)
			b.append((char) i); // a-z
		return b.toString().toCharArray();
	}

}
