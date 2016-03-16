package installment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import credit.Installment;
import credit.Monetary;

public class FixedInstallmentStrategy implements InstallmentStrategy {

	@Override
	public List<Installment> calculate(Monetary creditAmount, Monetary fixedFee, Integer numberOfInstallments, Double interestRate) throws InstallmentStrategyException {
		if (creditAmount == null || fixedFee == null || numberOfInstallments < 1 || interestRate < 0.0) {
			throw new InstallmentStrategyException("Invalid input data");
		}
		
		List<Installment> installments = new ArrayList<Installment>();
		
		for (int installmentNumber = 1; installmentNumber <= numberOfInstallments; installmentNumber++) {
			installments.add(
				new Installment(
					installmentNumber,
					calculateAmount(creditAmount, fixedFee, numberOfInstallments, interestRate),
					new Monetary("0", "PLN"),
					new Monetary("0", "PLN")
				)
			);
		}
		
		return installments;
	}

	/**
	 * http://www.matematykafinansowa.pl/jak-wyliczyc-rate-rowna-kredytu/
	 */
	private Monetary calculateAmount(Monetary creditAmount, Monetary fixedFee, Integer numberOfInstallments, Double interestRate) {
		BigDecimal creditValue = creditAmount.getValue().add(fixedFee.getValue());
		Double monthlyInterestPower = monthlyInterestToPowerOfTotalInstallments(interestRate, numberOfInstallments);
		
		BigDecimal amount = creditValue.multiply(BigDecimal.valueOf(monthlyInterestPower)).multiply(BigDecimal.valueOf((monthlyInterest(interestRate) - 1) / (monthlyInterestPower - 1)));
		
		return new Monetary(amount, creditAmount.getCurrency());
	}
	
	private Double monthlyInterest(Double interestRate) {
		return 1 + interestRate / 100 / 12;
	}
	
	private Double monthlyInterestToPowerOfTotalInstallments(Double interestRate, Integer numberOfInstallments) {
		return Math.pow(monthlyInterest(interestRate), numberOfInstallments);
	}
}
