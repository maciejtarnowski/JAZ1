package installment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import credit.Installment;
import credit.Monetary;

public class DecreasingInstallmentStrategy implements InstallmentStrategy {

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
					calculatePrincipalAmount(creditAmount, numberOfInstallments),
					calculateInterestAmount(installmentNumber, creditAmount, fixedFee, numberOfInstallments, interestRate),
					calculateFixedFeeAmount(fixedFee, numberOfInstallments)
				)
			);
		}
		
		return installments;
	}

	private Monetary calculatePrincipalAmount(Monetary creditAmount, Integer numberOfInstallments) {
		BigDecimal creditValue = creditAmount.getValue();
		
		return new Monetary(creditValue.divide(BigDecimal.valueOf(numberOfInstallments), 2, BigDecimal.ROUND_HALF_EVEN), creditAmount.getCurrency());
	}
	
	private Monetary calculateInstallmentAmount(Integer installmentNumber, Monetary creditAmount, Monetary fixedFee, Integer numberOfInstallments, Double interestRate) {
		BigDecimal monthlyInterest = BigDecimal.valueOf(interestRate / 100 / 12);
		BigDecimal creditValue = creditAmount.getValue().add(fixedFee.getValue());
		
		BigDecimal principal = creditValue.divide(BigDecimal.valueOf(numberOfInstallments), 2, BigDecimal.ROUND_HALF_EVEN);
		BigDecimal interestMultiplier = BigDecimal.valueOf(numberOfInstallments - installmentNumber + 1).multiply(monthlyInterest).add(BigDecimal.valueOf(1));
		
		BigDecimal amount = principal.multiply(interestMultiplier).subtract(calculateFixedFeeAmount(fixedFee, numberOfInstallments).getValue());
		
		return new Monetary(amount, creditAmount.getCurrency());
	}
	
	private Monetary calculateInterestAmount(Integer installmentNumber, Monetary creditAmount, Monetary fixedFee, Integer numberOfInstallments, Double interestRate) {
		return new Monetary(calculateInstallmentAmount(installmentNumber, creditAmount, fixedFee, numberOfInstallments, interestRate).getValue().subtract(calculatePrincipalAmount(creditAmount, numberOfInstallments).getValue()), creditAmount.getCurrency());
	}
	
	private Monetary calculateFixedFeeAmount(Monetary fixedFee, Integer numberOfInstallments) {
		BigDecimal fixedFeeValue = fixedFee.getValue();
		return new Monetary(fixedFeeValue.divide(BigDecimal.valueOf(numberOfInstallments), 2, BigDecimal.ROUND_HALF_EVEN), fixedFee.getCurrency());
	}
}
