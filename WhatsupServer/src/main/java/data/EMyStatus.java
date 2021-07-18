package data;

public enum EMyStatus {

	OK, COMM_FAILURE, LOGIC_FAILURE;

	private String message;

	private EMyStatus() {
		this.message = "";
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
