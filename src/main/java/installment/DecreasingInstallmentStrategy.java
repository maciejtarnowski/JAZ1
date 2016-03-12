package installment;

import java.math.BigDecimal;

import credit.Installment;
import credit.Monetary;

public class DecreasingInstallmentStrategy implements InstallmentStrategy {

	@Override
	public Installment calculate(Integer installmentNumber, Monetary grossAmount, Integer numberOfInstallments, Double interestRate) throws InstallmentStrategyException {
		if (installmentNumber < 1 || grossAmount == null || numberOfInstallments < 1 || interestRate < 0.0) {
			throw new InstallmentStrategyException("Invalid input data");
		}
		return new Installment(installmentNumber, calculateInstallment(installmentNumber, grossAmount, numberOfInstallments, interestRate));
	}

	/**
	 * http://www.matematykafinansowa.pl/jak-wyliczyc-rate-malejaca-kredytu/
	 */
	private Monetary calculateInstallment(Integer installmentNumber, Monetary grossAmount, Integer numberOfInstallments, Double interestRate) {
		Double creditAmount = grossAmount.getValue().doubleValue();
		Double monthlyInterest = interestRate / 12;
		
		Double amount = (creditAmount / numberOfInstallments) * (1 + (numberOfInstallments - installmentNumber + 1) * monthlyInterest);
		
		return new Monetary(BigDecimal.valueOf(amount), grossAmount.getCurrency());
	}
}
