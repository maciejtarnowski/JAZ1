package credit;

import java.util.ArrayList;
import java.util.List;

import installment.FactoryException;
import installment.InstallmentStrategy;
import installment.InstallmentStrategyException;
import installment.InstallmentStrategyFactory;

public class Calculator {
	private InstallmentStrategyFactory strategyFactory;
	
	public CreditParameters calculate(InputParameters inputParameters) throws CalculatorException {
		return new CreditParameters(inputParameters, calculateInstallments(inputParameters));
	}
	
	private List<Installment> calculateInstallments(InputParameters inputParameters) throws CalculatorException {
		InstallmentStrategy installmentStrategy = getInstallmentStrategy(inputParameters.getInstallmentsType());
		
		try {
			return installmentStrategy.calculate(inputParameters.getAmount(), inputParameters.getFixedFee(), inputParameters.getNumberOfInstallments(), inputParameters.getInterestRate());
		} catch (InstallmentStrategyException ex) {
			throw getCalculatorException(ex.getMessage());
		}
	}
	
	private InstallmentStrategy getInstallmentStrategy(Integer installmentType) throws CalculatorException {
		try {
			return strategyFactory.get(installmentType);
		} catch (FactoryException ex) {
			throw getCalculatorException(ex.getMessage());
		}
	}
	
	private CalculatorException getCalculatorException(String message) {
		return new CalculatorException("Could not calculate credit: " + message);
	}
}
