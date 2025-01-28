package NegativeTesting;

import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class NegativeSetupIOS {
	public IOSDriver<IOSElement> driver;
	JavascriptExecutor jse = (JavascriptExecutor) driver;
	DesiredCapabilities capabilities;

	@BeforeTest
	@org.testng.annotations.Parameters(value = { "deviceIndex" })
	public void beforeTest(String deviceIndex) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resources/com/browserstack/parallel.conf.json"));
		JSONArray envs = (JSONArray) config.get("environments");

		capabilities = new DesiredCapabilities();

		Map<String, String> envCapabilities = (Map<String, String>) envs.get(Integer.parseInt(deviceIndex));
		Iterator it = envCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
		}

		Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
		it = commonCapabilities.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (capabilities.getCapability(pair.getKey().toString()) == null) {
				capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
			}
		}

		String username = System.getenv("BROWSERSTACK_USERNAME");
		if (username == null) {
			username = (String) config.get("username");
		}

		String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
		if (accessKey == null) {
			accessKey = (String) config.get("access_key");
		}

		String app = System.getenv("BROWSERSTACK_APP_ID");
		if (app != null && !app.isEmpty()) {
			capabilities.setCapability("app", app);
		}

		driver = new IOSDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"),
				capabilities);
	}

	@AfterTest
	public void afterTest() throws Exception {
		driver.quit();
	}

}
