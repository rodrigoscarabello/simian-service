package br.com.mercadolivre.simianservice.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DnaHelper {

	private DnaHelper() {}

	private static final Pattern VALID_CHARACTERS = Pattern.compile("^[atcgATCG]*$");
	private static final Pattern SIMIAN_PATTER = Pattern.compile("(\\w)\\1{3}");
	private static final int LENGTH = 1000;

	public static boolean isValidDna(String[] dna) {
		List<String> dnaList = Arrays.asList(dna);
		List<List<String>> subLists = new ArrayList<>();
		CompletionService<Boolean> completionService = new ExecutorCompletionService<>(Executors.newCachedThreadPool(Thread::new));

		final int dnaSize = dnaList.size();

		if(dnaSize == 0)
			return false;

		int qtdSublist = 0;
		int indexStart = 0;
		int indexFinal = LENGTH;

		while(indexStart < dnaSize) {
			if(indexFinal > dnaSize)
				indexFinal = dnaSize;

			subLists.add(dnaList.subList(indexStart, indexFinal));
			indexStart = indexFinal + 1;
			indexFinal += LENGTH;
			qtdSublist++;
		}

		subLists.forEach(list -> completionService.submit(() -> list.parallelStream()
				.allMatch(DnaHelper::containsOnlyValidCharacter)));

		completionService.submit(() -> dnaList.parallelStream()
				.allMatch(sequence -> DnaHelper.isSquareMatrix(sequence, dnaSize)));

		try {
			for(int i = 0; i < qtdSublist + 1; i++) {
				Future<Boolean> f = completionService.take();
				if(!f.get())
					return false;
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static long analyzeDnaInLine(List<String> dnaList) {
		return dnaList.parallelStream().filter(DnaHelper::containsSimianPattern).count();
	}

	public static long analyzeDnaInColumns(List<String> dnaList) {
		return analyzeDnaInLine(IntStream.range(0, dnaList.size())
				.parallel().boxed()
				.map(x -> getSequenceInColumn(x, dnaList))
				.collect(toList()));
	}

	public static long analyzeDnaInDiagonalLeftRight(List<String> dnaList) {
		return analyzeDnaInLine(IntStream.range(0-dnaList.size(), dnaList.size())
				.parallel().boxed()
				.map(x -> getSequenceInDiagonalLeftRight(x, dnaList))
				.filter(sequence -> sequence.length() >= 4)
				.collect(toList()));
	}

	public static long analyzeDnaInDiagonalRightLeft(List<String> dnaList) {
		return analyzeDnaInLine(IntStream.range(0, dnaList.size()*2)
				.parallel().boxed()
				.map(x -> getSequenceInDiagonalRightLeft(x, dnaList))
				.filter(sequence -> sequence.length() >= 4)
				.collect(toList()));
	}

	private static boolean isSquareMatrix(String sequence, int size) {
		return sequence.length() == size;
	}

	private static boolean containsOnlyValidCharacter(String sequence) {
		return VALID_CHARACTERS.matcher(sequence).find();
	}

	private static String getSequenceInColumn(int x, List<String> dnaList) {
		return dnaList.stream().map(sequence -> sequence.substring(x, x + 1)).collect(joining());
	}

	private static String getSequenceInDiagonalLeftRight(int x, List<String> dnaList) {
		AtomicInteger index = new AtomicInteger(x);
		return dnaList.stream().map(sequence -> {
			String character = "";
			if(index.get() >= 0 && index.get() < sequence.length()) {
				character = sequence.substring(index.get(), index.get() + 1);
			}
			index.getAndIncrement();
			return character;
		}).collect(joining());
	}

	private static String getSequenceInDiagonalRightLeft(int x, List<String> dnaList) {
		AtomicInteger index = new AtomicInteger(x);
		return dnaList.stream().map(sequence -> {
			String character = "";
			if(index.get() >= 0 && index.get() < sequence.length()) {
				character = sequence.substring(index.get(), index.get() + 1);
			}
			index.getAndDecrement();
			return character;
		}).collect(joining());
	}

	private static boolean containsSimianPattern(String sequence) {
		return SIMIAN_PATTER.matcher(sequence).find();
	}

}
