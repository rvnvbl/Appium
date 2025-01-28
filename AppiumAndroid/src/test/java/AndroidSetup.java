import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class AndroidSetup {
   public AndroidDriver<AndroidElement> driver = null;
   JavascriptExecutor jse = (JavascriptExecutor) driver;

   @BeforeClass
   @org.testng.annotations.Parameters(value = {"deviceIndex"})
   public void beforeTest(String deviceIndex) throws Exception {
      JSONParser parser = new JSONParser();
      JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/com/browserstack/parallel.conf.json"));
      JSONArray envs = (JSONArray) config.get("environments");

      DesiredCapabilities capabilities = new DesiredCapabilities();

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
      HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
      browserstackOptions.put("enableCameraImageInjection", "true");
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

      driver = new AndroidDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
   }

   // Method for display the message on Browserstack
   public static void markTestStatus(String status, String reason, WebDriver driver) {
      final JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status + "\", \"reason\": \""
            + reason + "\"}}");
   }

   @AfterClass
   public void afterTest() {
      if (driver != null) {
         driver.quit();
      }
   }

}
