package installment;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InstallmentStrategyFactoryTest {
	private InstallmentStrategyFactory subject;
	
	@Before
	public void setUp() {
		subject = new InstallmentStrategyFactory();
	}
	
	@After
	public void tearDown() {
		subject = null;
	}
	
	@Test
	public void itReturnsFixedInstallmentStrategy() throws FactoryException {
		assertThat(subject.get(InstallmentStrategy.TYPE_FIXED)).isInstanceOf(FixedInstallmentStrategy.class);
	}

	@Test
	public void itReturnsDecreasingInstallmentStrategy() throws FactoryException {
		assertThat(subject.get(InstallmentStrategy.TYPE_DECREASING)).isInstanceOf(DecreasingInstallmentStrategy.class);
	}
	
	@Test
	public void itThrowsFactoryExceptionOnUnknownTypes() {
		assertThatExceptionOfType(FactoryException.class).isThrownBy(() -> { subject.get(123); });
	}
}
