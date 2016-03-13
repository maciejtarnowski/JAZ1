package credit;

public class Installment {
	private Integer sequentialNumber;
	private Monetary principalAmount;
	private Monetary interestAmount;
	private Monetary fixedFeeAmount;
	
	public Installment(Integer sequentialNumber, Monetary principalAmount, Monetary interestAmount, Monetary fixedFeeAmount) {
		this.sequentialNumber = sequentialNumber;
		this.principalAmount = principalAmount;
		this.interestAmount = interestAmount;
		this.fixedFeeAmount = fixedFeeAmount;
	}

	public Integer getSequentialNumber() {
		return sequentialNumber;
	}
	
	public Monetary getPrincipalAmount() {
		return principalAmount;
	}

	public Monetary getInterestAmount() {
		return interestAmount;
	}

	public Monetary getFixedFeeAmount() {
		return fixedFeeAmount;
	}

	public Monetary getPayableAmount() {
		return new Monetary(principalAmount.getValue().add(interestAmount.getValue()).add(fixedFeeAmount.getValue()), principalAmount.getCurrency());
	}
}
