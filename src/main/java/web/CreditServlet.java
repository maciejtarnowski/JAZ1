package web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.jknack.handlebars.Handlebars;

import credit.Calculator;
import credit.CalculatorException;
import credit.CreditParameters;
import credit.InputParameters;

public class CreditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestInputParametersMapper inputMapper;
	private Calculator calculator;
	private Handlebars tplEngine;

	public CreditServlet(RequestInputParametersMapper inputMapper, Calculator calculator, Handlebars tplEngine) {
		this.inputMapper = inputMapper;
		this.calculator = calculator;
		this.tplEngine = tplEngine;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			InputParameters inputParameters = inputMapper.map(request);
			CreditParameters credit = calculator.calculate(inputParameters);
			response.getWriter().println(credit);
		} catch (CalculatorException | MapperException e) {
			response.getWriter().println(e.getMessage());
			response.sendError(500);
			return;
		}
	}
}
