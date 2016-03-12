package credit;

public class InputParameters {
	private Monetary amount;
	private Integer numberOfInstallments;
	private Double interestRate;
	private Monetary fixedFee;
	private Integer installmentsType;
	
	public InputParameters(Monetary amount, Integer numberOfInstallments, Double interestRate, Monetary fixedFee, Integer installmentsType) {
		this.amount = amount;
		this.numberOfInstallments = numberOfInstallments;
		this.interestRate = interestRate;
		this.fixedFee = fixedFee;
		this.installmentsType = installmentsType;
	}

	public Monetary getAmount() {
		return amount;
	}
	
	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}
	
	public Double getInterestRate() {
		return interestRate;
	}
	
	public Monetary getFixedFee() {
		return fixedFee;
	}
	
	public Integer getInstallmentsType() {
		return installmentsType;
	}
}
