package installment;

public enum InstallmentType {
	FIXED,
	DECREASING;
	
	private static InstallmentType[] allValues = values();
    public static InstallmentType fromOrdinal(Integer ordinal) {
    	return allValues[ordinal];
    }
}
