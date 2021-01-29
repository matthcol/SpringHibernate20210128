package javabase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class TestOptional {

	@Test
	void testEmptyOptional() {
		Optional<String> optString = Optional.empty();
		System.out.println(optString);
		assertTrue(optString.isEmpty());
		optString.ifPresent(System.out::println);
		Optional<String> res = optString.map(String::toUpperCase);
		System.out.println(res);
	}
	
	@Test
	void testNotEmptyOptional() {
		Optional<String> optString = Optional.of("Blade Runner");
		System.out.println(optString);
		assertTrue(optString.isPresent());
		optString.ifPresent(System.out::println);
		Optional<String> res = optString.map(String::toUpperCase);
		System.out.println(res);
	}

}
