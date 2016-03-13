package installment;

import java.util.List;

import credit.Installment;
import credit.Monetary;

public interface InstallmentStrategy {
	public static final int TYPE_FIXED = 1;
	public static final int TYPE_DECREASING = 2;
	
	public List<Installment> calculate(Monetary creditAmount, Monetary fixedFee, Integer numberOfInstallments, Double interestRate) throws InstallmentStrategyException;
}
