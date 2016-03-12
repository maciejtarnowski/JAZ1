package credit;

import java.math.BigDecimal;

public class Monetary {
	private String currency;
	private BigDecimal value;
	
	public Monetary(String value, String currency) {
		this.value = new BigDecimal(value);
		this.currency = currency;
	}
	
	public Monetary(BigDecimal value, String currency) {
		this.value = value;
		this.currency = currency;
	}
	
	public String toString() {
		return getValue() + " " + getCurrency();
	}
	
	public String getCurrency() {
		return currency.toUpperCase();
	}
	
	public BigDecimal getValue() {
		return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	}
}
