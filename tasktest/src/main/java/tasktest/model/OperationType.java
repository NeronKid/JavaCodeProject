package tasktest.model;

public enum OperationType {
	DEPOSIT,
	WITHDRAW;
	
	public static OperationType of (String volume) {
		switch (volume) {
			case "DEPOSIT": {
				return OperationType.DEPOSIT;
			}
			case "WITHDRAW": {
				return OperationType.WITHDRAW;
			}
			default: {
				return null;
			}
		}
	}
}
