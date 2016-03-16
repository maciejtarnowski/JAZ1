package installment;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import credit.Installment;
import credit.Monetary;

@RunWith(JUnit4.class)
public class FixedInstallmentStrategyTest {
	private FixedInstallmentStrategy subject;
	
	@Before
	public void setUp() {
		subject = new FixedInstallmentStrategy();
	}
	
	@After
	public void tearDown() {
		subject = null;
	}
	
	@Test
	public void itThrowsInstallmentStrategyExceptionIfCreditAmountIsNull() {
		assertThatExceptionOfType(InstallmentStrategyException.class).isThrownBy(() -> {
			subject.calculate(null, new Monetary("100", "PLN"), 12, 5.0);
		});
	}
	
	@Test
	public void itThrowsInstallmentStrategyExceptionIfFixedFeeIsNull() {
		assertThatExceptionOfType(InstallmentStrategyException.class).isThrownBy(() -> {
			subject.calculate(new Monetary("1000", "PLN"), null, 12, 5.0);
		});
	}
	
	@Test
	public void itThrowsInstallmentStrategyExceptionIfNumberOfInstallmentsIsLessThanOne() {
		assertThatExceptionOfType(InstallmentStrategyException.class).isThrownBy(() -> {
			subject.calculate(new Monetary("1000", "PLN"), new Monetary("100", "PLN"), 0, 5.0);
		});
	}
	
	@Test
	public void itThrowsInstallmentStrategyExceptionIfInterestRateIsLessThanZero() {
		assertThatExceptionOfType(InstallmentStrategyException.class).isThrownBy(() -> {
			subject.calculate(new Monetary("1000", "PLN"), new Monetary("100", "PLN"), 12, -1.0);
		});
	}
	
	@Test
	public void itCalculatesInstallmentsBasedOnInputParameters() throws InstallmentStrategyException {
		List<Installment> result = subject.calculate(new Monetary("1000", "PLN"), new Monetary("100", "PLN"), 12, 5.0);
		
		assertThat(result).hasSize(12);
		assertInstallmentAmounts(result.get(0), 1, "94.17");
		assertInstallmentAmounts(result.get(11), 12, "94.17");
	}
	
	private void assertInstallmentAmounts(Installment installment, Integer number, String total) {
		assertThat(installment.getSequentialNumber()).isEqualTo(number);
		assertThat(installment.getPayableAmount().getValue()).isEqualByComparingTo(new BigDecimal(total));
		assertThat(installment.getInterestAmount().getValue()).isEqualByComparingTo(new BigDecimal("0"));
		assertThat(installment.getFixedFeeAmount().getValue()).isEqualByComparingTo(new BigDecimal("0"));
	}
}
