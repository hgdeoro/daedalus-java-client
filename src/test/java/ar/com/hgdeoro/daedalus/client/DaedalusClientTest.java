package ar.com.hgdeoro.daedalus.client;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DaedalusClientTest extends TestCase {

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DaedalusClientTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DaedalusClientTest.class);
	}

	/**
	 * Simple test of Daedalus client
	 * 
	 * @throws Exception
	 */
	public void testSendMessage() throws Exception {
		boolean sentOk;
		sentOk = new DaedalusClient().sendMessage(
				"An INFO message from the Java Daedalus client", Severity.INFO,
				"testhost", "testapp");
		Assert.assertTrue(sentOk);

		sentOk = new DaedalusClient(null, -1, "testhost", "testapp")
				.sendMessage("A WARN message from the Java Daedalus client",
						Severity.WARN, null, null);
		Assert.assertTrue(sentOk);
	}
}
