package br.com.mercadolivre.simianservice.helper;

import br.com.mercadolivre.simianservice.SimianServiceApplicationTests;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DnaHelperTest {

	@Test
	public void isValidDna() {
		assertThat(DnaHelper.isValidDna(SimianServiceApplicationTests.SIMIAN_SEQUENCE_1)).isTrue();
		assertThat(DnaHelper.isValidDna(SimianServiceApplicationTests.HUMAN_SEQUENCE_2)).isTrue();
		assertThat(DnaHelper.isValidDna(SimianServiceApplicationTests.INVALID_CHARACTERS)).isFalse();
		assertThat(DnaHelper.isValidDna(SimianServiceApplicationTests.INVALID_SQUARE_MATRIX)).isFalse();
	}

	@Test
	public void analyzeDnaInLine() {
		assertThat(DnaHelper.analyzeDnaInLine(Arrays.asList("CGGGGA", "CTAAAT", "TATTGT", "AGAGGG", "CACCTA", "TCACTG"))).isEqualTo(1L);
		assertThat(DnaHelper.analyzeDnaInLine(Arrays.asList(SimianServiceApplicationTests.SIMIAN_SEQUENCE_1))).isEqualTo(2L);
		assertThat(DnaHelper.analyzeDnaInLine(Arrays.asList("CGGGGA", "CTAAAA", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"))).isEqualTo(3L);
		assertThat(DnaHelper.analyzeDnaInLine(Arrays.asList("GGGGGG", "CTAAAA", "TATTTT", "AGAGGG", "CCCCTA", "TCACTG"))).isEqualTo(4L);
		assertThat(DnaHelper.analyzeDnaInLine(Arrays.asList("GGGGGG", "CTAAAA", "TATTTT", "AGAGGG", "CCCCTA", "TCCCCC"))).isEqualTo(5L);
		assertThat(DnaHelper.analyzeDnaInLine(Arrays.asList("GGGGGG", "CTAAAA", "TATTTT", "AGGGGG", "CCCCTA", "TCCCCC"))).isEqualTo(6L);
	}

	@Test
	public void analyzeDnaInColumns() {
		assertThat(DnaHelper.analyzeDnaInColumns(Arrays.asList("CTGTGA", "CTAAGA", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"))).isEqualTo(1L);
		assertThat(DnaHelper.analyzeDnaInColumns(Arrays.asList("CTGTGA", "CTAAGA", "TAAAGT", "AGAGGG", "CCACTA", "TCACTG"))).isEqualTo(2L);
		assertThat(DnaHelper.analyzeDnaInColumns(Arrays.asList("CTGTGA", "CTAAGA", "TAAAGT", "TGAGGG", "TCACTA", "TCACTG"))).isEqualTo(3L);
		assertThat(DnaHelper.analyzeDnaInColumns(Arrays.asList("CTGTGA", "CTAAGA", "TAAAGA", "TGAGGA", "TCACTA", "TCACTG"))).isEqualTo(4L);
		assertThat(DnaHelper.analyzeDnaInColumns(Arrays.asList("CTGTGA", "CTAAGA", "TCAAGA", "TCAGGA", "TCACTA", "TCACTG"))).isEqualTo(5L);
		assertThat(DnaHelper.analyzeDnaInColumns(Arrays.asList("CTGTGA", "CTATGA", "TCATGA", "TCATGA", "TCACTA", "TCACTG"))).isEqualTo(6L);
	}

	@Test
	public void analyzeDnaInDiagonalLeftRight() {
		assertThat(DnaHelper.analyzeDnaInDiagonalLeftRight(Arrays.asList("CTGTGA", "CTAGAA", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"))).isEqualTo(1L);
		assertThat(DnaHelper.analyzeDnaInDiagonalLeftRight(Arrays.asList("CTGTGA", "CTTGAA", "TATTGT", "AGAGTG", "CCCCTA", "TCACTG"))).isEqualTo(2L);
		assertThat(DnaHelper.analyzeDnaInDiagonalLeftRight(Arrays.asList("CTGTGA", "CTTGAA", "TAGTGT", "AGAGTG", "CCCCGA", "TCACTG"))).isEqualTo(3L);
		assertThat(DnaHelper.analyzeDnaInDiagonalLeftRight(Arrays.asList("CTGTGA", "ATTGAA", "TAGTGT", "AGAGTG", "CCCAGA", "TCACTG"))).isEqualTo(4L);
		assertThat(DnaHelper.analyzeDnaInDiagonalLeftRight(Arrays.asList("CTGTGA", "ATTGAA", "CAGTGT", "ACAGTG", "CCCAGA", "TCACTG"))).isEqualTo(5L);
	}

	@Test
	public void analyzeDnaInDiagonalRightLeft() {
		assertThat(DnaHelper.analyzeDnaInDiagonalRightLeft(Arrays.asList("CTGAGA", "CTAAAA", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"))).isEqualTo(1L);
		assertThat(DnaHelper.analyzeDnaInDiagonalRightLeft(Arrays.asList("CTGAGA", "CTAAAG", "TATTGT", "AGAGGG", "CCGCTA", "TCACTG"))).isEqualTo(2L);
		assertThat(DnaHelper.analyzeDnaInDiagonalRightLeft(Arrays.asList("CTGACA", "CTACAG", "TACTGT", "ACAGGG", "CCGCTA", "TCACTG"))).isEqualTo(3L);
		assertThat(DnaHelper.analyzeDnaInDiagonalRightLeft(Arrays.asList("CTGACA", "CTACAG", "TACAGT", "ACAGGG", "CCGCTA", "TCACTG"))).isEqualTo(4L);
		assertThat(DnaHelper.analyzeDnaInDiagonalRightLeft(Arrays.asList("CTGACA", "CTACAG", "TACAGA", "ACAGAG", "CCGATA", "TCACTG"))).isEqualTo(5L);
	}
}