package credit;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import installment.InstallmentStrategy;

@RunWith(JUnit4.class)
public class CreditParametersTest {
	private InputParameters inputParameters;
	
	@Before
	public void setUp() {
		inputParameters = new InputParameters(new Monetary("1000", "PLN"), 5, 5.0, new Monetary("100", "PLN"), InstallmentStrategy.TYPE_DECREASING);
	}

	@After
	public void tearDown() {
		inputParameters = null;
	}

	@Test
	public void itCalculatesGrossAmount() {
		CreditParameters subject = new CreditParameters(inputParameters, new ArrayList<Installment>());
		
		assertThat(subject.getGrossAmount().getValue()).isEqualByComparingTo(new BigDecimal("1100"));
	}
	
	@Test
	public void itReturnsNumberOfInstallmentsFromInputParameters() {
		CreditParameters subject = new CreditParameters(inputParameters, new ArrayList<Installment>());
		
		assertThat(subject.getNumberOfInstallments()).isEqualTo(5);
	}
	
	@Test
	public void itReturnsInterestRateFromInputParameters() {
		CreditParameters subject = new CreditParameters(inputParameters, new ArrayList<Installment>());
		
		assertThat(subject.getInterestRate()).isEqualTo(5.0);
	}
	
	@Test
	public void itReturnsTotalPayableAmount() {
		List<Installment> installments = new ArrayList<Installment>();
		installments.add(getInstallment(1));
		installments.add(getInstallment(2));
		installments.add(getInstallment(3));
		CreditParameters subject = new CreditParameters(inputParameters, installments);
		
		assertThat(subject.getTotalPayableAmount().getValue()).isEqualByComparingTo(new BigDecimal("666"));
	}
	
	private Installment getInstallment(Integer multiplier) {
		return new Installment(multiplier, new Monetary(BigDecimal.valueOf(100 * multiplier), "PLN"), new Monetary(BigDecimal.valueOf(10 * multiplier), "PLN"), new Monetary(BigDecimal.valueOf(1 * multiplier), "PLN"));
	}
}
