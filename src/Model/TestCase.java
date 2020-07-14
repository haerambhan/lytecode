package Model;

public class TestCase {
	
	public TestCase(int testCaseId, String input, String output) {
		super();
		this.testCaseId = testCaseId;
		this.input = input;
		this.output = output;
	}
	private int testCaseId;
	private String input;
	private String output;
	
	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + testCaseId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase other = (TestCase) obj;
		if (testCaseId != other.testCaseId)
			return false;
		return true;
	}

}
