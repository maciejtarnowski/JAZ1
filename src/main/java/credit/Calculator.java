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
		List<Installment> installments = new ArrayList<Installment>();
		InstallmentStrategy installmentStrategy = getInstallmentStrategy(inputParameters.getInstallmentsType());
		Monetary grossAmount = new Monetary(inputParameters.getAmount().getValue().add(inputParameters.getFixedFee().getValue()), inputParameters.getAmount().getCurrency());
		
		for (int installmentNumber = 1; installmentNumber <= inputParameters.getNumberOfInstallments(); installmentNumber++) {
			try {
				installments.add(installmentStrategy.calculate(installmentNumber, grossAmount, inputParameters.getNumberOfInstallments(), inputParameters.getInterestRate()));
			} catch (InstallmentStrategyException ex) {
				throw getCalculatorException(ex.getMessage());
			}
		}
		
		return installments;
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
