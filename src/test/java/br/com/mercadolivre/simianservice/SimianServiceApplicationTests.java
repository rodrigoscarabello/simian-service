package br.com.mercadolivre.simianservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimianServiceApplicationTests {

	public static final String HTTP_LOCALHOST = "http://localhost:";
	public static final String[] SIMIAN_SEQUENCE_1 = {"CTGTGA", "CTAAAA", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	public static final String[] HUMAN_SEQUENCE_1 = {"GTGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTAA", "TCACTG"};
	public static final String[] SIMIAN_SEQUENCE_2 = {"CTGATA", "CTATGC", "TATAGT", "AGAGAG", "TCCCTA", "TCACTG"};
	public static final String[] SIMIAN_SEQUENCE_3 = {"CTGAGA", "CTATGC", "TATTGT", "TGAGGG", "TCCCTA", "TCACTG"};
	public static final String[] HUMAN_SEQUENCE_2 = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
	public static final String[] HUMAN_SEQUENCE_3 = {"ATGCGA", "CATTGC", "TTATTT", "AGACGG", "GCGTCA", "TGACTG"};
	public static final String[] HUMAN_SEQUENCE_4 = {"ATGCGA", "CAGTGC", "TTATAT", "GGACGG", "GCGTCA", "TCACTG"};
	public static final String[] INVALID_CHARACTERS = {"ATDDGA", "CAGTGC", "TTATRT", "QGACGG", "GCGTCA", "TCACTG"};
	public static final String[] INVALID_SQUARE_MATRIX = {"ATGCGAA", "CAGTGC", "TTATTT", "AGACGGTT", "GCGTCA", "TCACTG"};

	@Test
	public void contextLoads() {
	}

}
