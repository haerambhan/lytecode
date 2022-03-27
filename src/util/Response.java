package util;

public enum Response {
	
	INTERNAL_ERROR(4001, "Server error occured"),
	USER_EXISTS(4002, "User already exists"),
	LOGIN_FAILED(4003, "Invalid Credentials"),
	SIGNUP_FAILED(4004, "Unable to create new user"),
	INVALID_INPUT(4005, "Input is not valid"),
	SUCCESS(2000, "Success");

	
	private final int responseCode;
	private final String responseMessage;
	
	Response(int responseCode, String responseMessage){
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
	

}
