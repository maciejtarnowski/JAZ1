package installment;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InstallmentTypeTest {
	@Test
	public void itHasFixedOnZeroIndex() {
		assertThat(InstallmentType.fromOrdinal(0)).isEqualByComparingTo(InstallmentType.FIXED);
	}
	
	@Test
	public void itHasDecreasingOnFirstIndex() {
		assertThat(InstallmentType.fromOrdinal(1)).isEqualByComparingTo(InstallmentType.DECREASING);
	}
}
