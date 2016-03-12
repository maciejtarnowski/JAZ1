package credit;

public class Installment {
	private Integer sequentialNumber;
	private Monetary payableAmount;
	
	public Installment(Integer sequentialNumber, Monetary payableAmount) {
		this.sequentialNumber = sequentialNumber;
		this.payableAmount = payableAmount;
	}

	public Integer getSequentialNumber() {
		return sequentialNumber;
	}
	
	public Monetary getPayableAmount() {
		return payableAmount;
	}
}
