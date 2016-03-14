package web;

import javax.servlet.http.HttpServletRequest;

import credit.InputParameters;
import credit.Monetary;

public class RequestInputParametersMapper {
	public InputParameters map(HttpServletRequest request) throws MapperException {
		validateInput(request);
		
		return new InputParameters(
			new Monetary(request.getParameter("amount"), "PLN"),
			Integer.valueOf(request.getParameter("installments")),
			Double.valueOf(request.getParameter("interest_rate")),
			new Monetary(request.getParameter("fixed_fee"), "PLN"),
			Integer.valueOf(request.getParameter("installment_type"))
		);
	}
	
	private void validateInput(HttpServletRequest request) throws MapperException {
		if (request.getParameter("amount") == null || request.getParameter("installments") == null || request.getParameter("interest_rate") == null || request.getParameter("fixed_fee") == null || request.getParameter("installment_type") == null) {
			throw new MapperException("Invalid request");
		}
	}
}
