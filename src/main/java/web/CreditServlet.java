package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import credit.Calculator;
import credit.CalculatorException;
import credit.CreditParameters;
import credit.InputParameters;
import credit.Installment;
import installment.InstallmentType;

public class CreditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RequestInputParametersMapper inputMapper;
	private Calculator calculator;

	public CreditServlet(RequestInputParametersMapper inputMapper, Calculator calculator) {
		this.inputMapper = inputMapper;
		this.calculator = calculator;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			InputParameters inputParameters = inputMapper.map(request);
			CreditParameters credit = calculator.calculate(inputParameters);
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html;charset=UTF-8");
			writer.println("<!DOCTYPE html><html><head><meta name=\"charset\" content=\"utf-8\"><title>Kalkulator kredytowy</title></head><body><h1>Harmonogram splat kredytu</h1><ul>");
			writer.println("<li>Wnioskowana kwota: " + credit.getNetAmount() + "</li>");
			writer.println("<li>Ilosc rat: " + credit.getNumberOfInstallments() + "</li>");
			writer.println("<li>Oprocentowanie: " + BigDecimal.valueOf(credit.getInterestRate()).setScale(2, BigDecimal.ROUND_HALF_EVEN) + "%</li>");
			writer.println("<li>Oplata stala: " + credit.getFixedFee() + "</li>");
			writer.println("<li>Rodzaj rat: " + (credit.getInstallmentType() == InstallmentType.FIXED ? "Stale" : "Malejace") + "</li>");
			writer.println("</ul><h2>Laczna kwota do zaplaty: " + credit.getTotalPayableAmount() + "</h2>");
			
			writer.println("<table><thead><tr><th>Lp</th><th>Kapital</th><th>Odsetki</th><th>Oplata stala</th><th>Kwota raty</th></tr></thead><tbody>");
			for (Installment installment : credit.getInstallments()) {
				writer.println("<tr>");
                writer.println("<td>" + installment.getSequentialNumber() + "</td>");
                writer.println("<td>" + installment.getPrincipalAmount() + "</td>");
                writer.println("<td>" + installment.getInterestAmount() + "</td>");
                writer.println("<td>" + installment.getFixedFeeAmount() + "</td>");
                writer.println("<td>" + installment.getPayableAmount() + "</td>");
                writer.println("</tr>");
			}
			writer.println("</table></body></html>");
		} catch (CalculatorException | MapperException e) {
			response.getWriter().println(e.getMessage());
			response.sendError(500);
			return;
		}
	}
}
