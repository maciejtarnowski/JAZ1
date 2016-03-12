package installment;

public class InstallmentStrategyFactory {
	public InstallmentStrategy get(Integer type) throws FactoryException {
		switch (type) {
			case InstallmentStrategy.TYPE_FIXED:
				return new FixedInstallmentStrategy();
			case InstallmentStrategy.TYPE_DECREASING:
				return new DecreasingInstallmentStrategy();
			default:
				throw new FactoryException("Invalid installment type");
		}
	}
}
