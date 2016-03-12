package installment;

import credit.Installment;
import credit.Monetary;

public interface InstallmentStrategy {
	public static final int TYPE_FIXED = 1;
	public static final int TYPE_DECREASING = 2;
	
	public Installment calculate(Integer installmentNumber, Monetary grossAmount, Integer numberOfInstallments, Double interestRate) throws InstallmentStrategyException;
}
