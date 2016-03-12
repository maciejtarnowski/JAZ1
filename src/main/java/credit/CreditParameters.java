package credit;

import java.math.BigDecimal;
import java.util.List;

public class CreditParameters {
	private InputParameters inputParameters;
	private List<Installment> installments;
	
	public CreditParameters(InputParameters inputParameters, List<Installment> installments) {
		this.inputParameters = inputParameters;
		this.installments = installments;
	}
	
	public Monetary getGrossAmount() {
		return new Monetary(
			inputParameters.getAmount().getValue().add(inputParameters.getFixedFee().getValue()),
			inputParameters.getAmount().getCurrency()
		);
	}
	
	public Integer getNumberOfInstallments() {
		return inputParameters.getNumberOfInstallments();
	}
	
	public Monetary getTotalPayableAmount() {
		return new Monetary(
			installments.stream()
				.map(Installment::getPayableAmount)
				.map(Monetary::getValue)
				.reduce(BigDecimal.ZERO, BigDecimal::add),
			inputParameters.getAmount().getCurrency()
		);
	}
	
	public Double getInterestRate() {
		return inputParameters.getInterestRate();
	}
	
	public List<Installment> getInstallments() {
		return installments;
	}
}
