package web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import credit.Calculator;
import installment.InstallmentStrategyFactory;

@WebListener
public class Dispatcher implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext();
        context.addServlet("creditServlet", new CreditServlet(new RequestInputParametersMapper(), getCalculator())).addMapping("/credit");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
	}
	
	private Calculator getCalculator() {
		return new Calculator(new InstallmentStrategyFactory());
	}
}
