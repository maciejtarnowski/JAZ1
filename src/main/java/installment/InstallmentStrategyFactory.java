package installment;

public class InstallmentStrategyFactory {
	public InstallmentStrategy get(InstallmentType type) throws FactoryException {
		switch (type) {
			case FIXED:
				return new FixedInstallmentStrategy();
			case DECREASING:
				return new DecreasingInstallmentStrategy();
			default:
				throw new FactoryException("Invalid installment type");
		}
	}
}
