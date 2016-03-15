package web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import credit.InputParameters;

@RunWith(MockitoJUnitRunner.class)
public class RequestInputParametersMapperTest {
	private RequestInputParametersMapper subject;
	
	@Before
	public void setUp() {
		subject = new RequestInputParametersMapper();
	}
	
	@After
	public void tearDown() {
		subject = null;
	}
	
	@Mock
	private HttpServletRequest request;
	
	@Test
	public void itMapsRequestParametersToInputParameters() throws MapperException {
		stubRequest("1000", "12", "5.5", "100", "1");
		
		InputParameters result = subject.map(request);
		assertThat(result).isInstanceOf(InputParameters.class);
		assertThat(result.getAmount().getValue()).isEqualByComparingTo(new BigDecimal("1000"));
		assertThat(result.getNumberOfInstallments()).isEqualTo(12);
		assertThat(result.getInterestRate()).isEqualByComparingTo(5.5);
		assertThat(result.getFixedFee().getValue()).isEqualByComparingTo(new BigDecimal("100"));
		assertThat(result.getInstallmentsType()).isEqualTo(1);
	}
	
	@Test
	public void itThrowsMapperExceptionIfAnyOfParametersIsNull() throws MapperException {
		stubRequest(null, "12", "5.0", "100", "2");
		
		assertThatExceptionOfType(MapperException.class).isThrownBy(() -> {
			subject.map(request);
		});
	}
	
	@Test
	public void itThrowsMapperExceptionIfAnyOfParametersIsNotNumeric() throws MapperException {
		stubRequest("abc", "12", "def", "200", "2");
		
		assertThatExceptionOfType(MapperException.class).isThrownBy(() -> {
			subject.map(request);
		});
	}
	
	@Test
	public void itThrowsMapperExceptionIfInstallmentTypeIsOutOfRange() throws MapperException {
		stubRequest("1000", "12", "5.5", "100", "1234");
		
		assertThatExceptionOfType(MapperException.class).isThrownBy(() -> {
			subject.map(request);
		});
	}
	
	private void stubRequest(String amount, String installments, String interestRate, String fixedFee, String installmentType) {
		when(request.getParameter("amount")).thenReturn(amount);
		when(request.getParameter("installments")).thenReturn(installments);
		when(request.getParameter("interest_rate")).thenReturn(interestRate);
		when(request.getParameter("fixed_fee")).thenReturn(fixedFee);
		when(request.getParameter("installment_type")).thenReturn(installmentType);
	}
}
