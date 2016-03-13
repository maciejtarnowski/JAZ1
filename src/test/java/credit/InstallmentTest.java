package credit;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InstallmentTest {
	@Test
	public void itCalculatesPayableAmount() {
		Installment subject = new Installment(1, new Monetary("100", "PLN"), new Monetary("50", "PLN"), new Monetary("10", "PLN"));
		
		assertThat(subject.getPayableAmount().getValue()).isEqualByComparingTo(new BigDecimal("160"));
		assertThat(subject.getPayableAmount().getCurrency()).isEqualTo("PLN");
	}

}
