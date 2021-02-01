package javabase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class TestStreams {

	@Test
	void testMapForEach() {
		List<String> cities = List.of("Toulouse", "Pau", "Nantes", "Nîmes", "Saint-Petersbourg");
		cities.stream()
			//.map(c -> c.toUpperCase())  // String -> String
			.map(String::toUpperCase)  // String -> String (ref object method from class)
			//.forEach(c -> System.out.println(c));
			.forEach(System.out::println); // String -> void (ref object method from object)
	}
	
	@Test
	void testMapFilterCollectToList() {
		List<String> cities = List.of(" Toulouse", " Pau ", "nantes ", " Nîmes", "SAint-Petersbourg");
		var res = cities.parallelStream() //.stream()
			.map(String::trim)
			.map(String::toLowerCase)
			.filter(c -> c.startsWith("n"))  // String -> boolean
			.collect(Collectors.toList());
		// collecte necessite :
		//	-> 1 accumulateur initial
		//  -> 1 opération d'accumulation pour chaque donnéedu stream
		//  -> 1 opération de finalisation (accumulateur -> ?)
		//  -> 1 opération de combinaison d'accumulateur (travail en //)
		System.out.println(res);
	}
	
	@Test
	void testMapFilterCollectToStats() {
		List<String> cities = List.of(" Toulouse", " Pau ", "nantes ", " Nîmes", "SAint-Petersbourg");
		var res = cities.parallelStream() //.stream()
			.map(String::trim)
			.map(String::toLowerCase)
			.filter(c -> c.startsWith("n"))  // String -> boolean
			.mapToInt(String::length)  // c -> c.length()
			// => IntStream with numeric API :
			// sum/count as int result (0 if empty stream)
			// average, min, max => OptionalInt or OptionalDouble to deal with empty streams
				// .sum(); // 11
				// .average(); // OptionalDouble[5.5]
				// .count(); // 2 (always a result, 0 if empty stream)
				// .min(); //  OptionalInt[5]
				//  .max(); // OptionalInt[6]
				 .summaryStatistics(); // IntSummaryStatistics{count=2, sum=11, min=5, average=5,500000, max=6}
		System.out.println(res);
	}
	

}
