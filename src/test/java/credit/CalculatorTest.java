package credit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import installment.FactoryException;
import installment.InstallmentStrategy;
import installment.InstallmentStrategyException;
import installment.InstallmentStrategyFactory;
import installment.InstallmentType;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {
	private Calculator subject;
	
	@Mock
	private InstallmentStrategyFactory strategyFactory;
	
	@Mock
	private InstallmentStrategy strategy;
	
	@Before
	public void setUp() {
		subject = new Calculator(strategyFactory);
	}
	
	@After
	public void tearDown() {
		subject = null;
	}
	
	@Test
	public void itThrowsCalculatorExceptionIfStrategyFactoryFailsToInstantiateStrategy() throws FactoryException {
		when(strategyFactory.get(InstallmentType.DECREASING)).thenThrow(new FactoryException("Invalid installment type"));
		
		assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
			subject.calculate(getInput("1000", 12, 5.0, "100", 2));
		});
	}
	
	@Test
	public void itThrowsCalculatorExceptionIfCalculationFailed() throws Exception {
		when(strategyFactory.get(InstallmentType.DECREASING)).thenReturn(strategy);
		when(strategy.calculate(any(Monetary.class), any(Monetary.class), eq(12), eq(5.0))).thenThrow(new InstallmentStrategyException("Invalid input data"));
		
		assertThatExceptionOfType(CalculatorException.class).isThrownBy(() -> {
			subject.calculate(getInput("1000", 12, 5.0, "100", 2));
		});
	}
	
	@Test
	public void itReturnsCreditParametersObjectWithInputAndInstallments() throws Exception {
		when(strategyFactory.get(InstallmentType.DECREASING)).thenReturn(strategy);
		when(strategy.calculate(any(Monetary.class), any(Monetary.class), eq(12), eq(5.0))).thenReturn(new ArrayList<Installment>());
		
		InputParameters input = getInput("1000", 12, 5.0, "100", 2);
		CreditParameters result = subject.calculate(input);
		assertThat(result).isInstanceOf(CreditParameters.class);
		assertThat(result.getInstallments()).hasSize(0);
		assertThat(result.getGrossAmount().getValue()).isEqualByComparingTo(new BigDecimal("1100"));
		assertThat(result.getInterestRate()).isEqualByComparingTo(5.0);
		assertThat(result.getNumberOfInstallments()).isEqualByComparingTo(12);
		
		verify(strategy).calculate(any(Monetary.class), any(Monetary.class), eq(12), eq(5.0));
	}
	
	private InputParameters getInput(String amount, Integer numberOfInstallments, Double interestRate, String fixedFee, Integer installmentsType) {
		return new InputParameters(new Monetary(amount, "PLN"), numberOfInstallments, interestRate, new Monetary(fixedFee, "PLN"), installmentsType);
	}
}
