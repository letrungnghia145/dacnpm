package com.app.helper;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {

	public static String randomUserCode() {
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		int s1 = random.nextInt(1000);
		int s2 = random.nextInt(1000);
		int s3 = random.nextInt(1000);
		return builder.append(s1).append(s2).append(s3).toString();
	}

	public static String randomUUIDCode() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String generateValidationCode() {
		Random random = new Random();
		String f1 = String.format("%03d", random.nextInt(999));
		String f2 = String.format("%03d", random.nextInt(999));
		return f1.concat(f2);
	}
}