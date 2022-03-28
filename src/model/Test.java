package model;
import java.util.Set;

public class Test {
	
	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public String getTestDesc() {
		return testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	public String getTestDiff() {
		return testDiff;
	}

	public void setTestDiff(String testDiff) {
		this.testDiff = testDiff;
	}

	public Set<TestCase> getTestcases() {
		return testcases;
	}

	public void setTestcases(Set<TestCase> testcases) {
		this.testcases = testcases;
	}

	private int testId;
	private String testTitle;
	private String testDesc;
	private String testDiff;
	private Set<TestCase> testcases;
	private TestCase publicTc;
	
	public TestCase getPublicTc() {
		return publicTc;
	}

	public Test(int testId, String testTitle, String testDesc, String testDiff, TestCase publicTc) {
		super();
		this.testId = testId;
		this.testTitle = testTitle;
		this.testDesc = testDesc;
		this.testDiff = testDiff;
		this.publicTc = publicTc;
	}

	public void setPublicTc(TestCase publicTc) {
		this.publicTc = publicTc;
	}
}
