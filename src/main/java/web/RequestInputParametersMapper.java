package web;

import javax.servlet.http.HttpServletRequest;

import credit.InputParameters;
import credit.Monetary;

public class RequestInputParametersMapper {
	public InputParameters map(HttpServletRequest request) throws MapperException {
		validateInputCompleteness(request);
		validateInputParametersType(request);
		validateTypeInRange(request);
		
		return new InputParameters(
			new Monetary(request.getParameter("amount"), "PLN"),
			Integer.valueOf(request.getParameter("installments")),
			Double.valueOf(request.getParameter("interest_rate")),
			new Monetary(request.getParameter("fixed_fee"), "PLN"),
			Integer.valueOf(request.getParameter("installment_type"))
		);
	}
	
	private void validateInputCompleteness(HttpServletRequest request) throws MapperException {
		if (request.getParameter("amount") == null
				|| request.getParameter("installments") == null
				|| request.getParameter("interest_rate") == null
				|| request.getParameter("fixed_fee") == null
				|| request.getParameter("installment_type") == null) {
			throw new MapperException("Invalid request");
		}
	}
	
	private void validateInputParametersType(HttpServletRequest request) throws MapperException {
		if (!isDouble(request.getParameter("amount"))
				|| !isInteger(request.getParameter("installments"))
				|| !isDouble(request.getParameter("interest_rate"))
				|| !isDouble(request.getParameter("fixed_fee"))
				|| !isInteger(request.getParameter("installment_type"))) {
			throw new MapperException("Invalid parameters");
		}
	}
	
	private void validateTypeInRange(HttpServletRequest request) throws MapperException {
		Integer type = Integer.valueOf(request.getParameter("installment_type"));
		if (type != 1 && type != 2) {
			throw new MapperException("Invalid installment type");
		}
	}
	
	private boolean isDouble(String value) {
		try {
			Double.valueOf(value);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	private boolean isInteger(String value) {
		try {
			Integer.valueOf(value, 10);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
