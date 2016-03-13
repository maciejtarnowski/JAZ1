package installment;

import java.math.BigDecimal;

import credit.Installment;
import credit.Monetary;

public class FixedInstallmentStrategy implements InstallmentStrategy {

	@Override
	public List<Installment> calculate(Monetary creditAmount, Monetary fixedFee, Integer numberOfInstallments, Double interestRate) throws InstallmentStrategyException {
		if (creditAmount == null || numberOfInstallments < 1 || interestRate < 0.0) {
			throw new InstallmentStrategyException("Invalid input data");
		}
		return new Installment(installmentNumber, calculateAmount(creditAmount, numberOfInstallments, interestRate));
	}

	/**
	 * http://www.matematykafinansowa.pl/jak-wyliczyc-rate-rowna-kredytu/
	 */
	private Monetary calculateAmount(Monetary grossAmount, Integer numberOfInstallments, Double interestRate) {
		Double creditAmount = grossAmount.getValue().doubleValue();
		Double monthlyInterestPower = monthlyInterestToPowerOfTotalInstallments(interestRate, numberOfInstallments);
		
		Double amount = creditAmount * monthlyInterestPower * ((monthlyInterest(interestRate) - 1) / (monthlyInterestPower - 1));
		
		return new Monetary(BigDecimal.valueOf(amount), grossAmount.getCurrency());
	}
	
	private Double monthlyInterest(Double interestRate) {
		return 1 + interestRate / 12;
	}
	
	private Double monthlyInterestToPowerOfTotalInstallments(Double interestRate, Integer numberOfInstallments) {
		return Math.pow(monthlyInterest(interestRate), numberOfInstallments);
	}
}
