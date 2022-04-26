package project3maven;

import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.shredzone.commons.suncalc.MoonPosition;
import org.shredzone.commons.suncalc.SunPosition;

public class Project3Test {
	
	public static WebDriver driver;

	private static void sleepFor(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.out.println("Got interrupted.");
		}
	}
	
	//Function for testing North Pole Distance
	private static double haversine(double lat1, double lon1, double lat2, double lon2) {
		// distance between latitudes and longitudes
		double dLat = (lat2 - lat1) *
				Math.PI / 180.0;
		double dLon = (lon2 - lon1) *
				Math.PI / 180.0;
		// convert to radians
		lat1 = (lat1) * Math.PI / 180.0;
		lat2 = (lat2) * Math.PI / 180.0;

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) +
				Math.pow(Math.sin(dLon / 2), 2) *
				Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}
	
	private static double moonCoreDist(double lat, double lng) {
		SunPosition.Parameters sunParam = SunPosition.compute()
		        .at(lat, lng)      						// current position
		        .on(java.time.LocalDateTime.now().getYear(),			//current date and time
		        		java.time.LocalDateTime.now().getMonthValue(),
		        		java.time.LocalDateTime.now().getDayOfMonth(),
		        		java.time.LocalDateTime.now().getHour(),
		        		java.time.LocalDateTime.now().getMinute(),
		        		java.time.LocalDateTime.now().getSecond());   			

		MoonPosition.Parameters moonParam = MoonPosition.compute()
		        .sameLocationAs(sunParam)
		        .sameTimeAs(sunParam);
		
		MoonPosition moon = moonParam.execute();
		return moon.getDistance();
	}
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\ULAS\\Downloads\\chromedriver_win32\\chromedriver.exe");

		driver = new ChromeDriver();
		//driver.manage().deleteAllCookies();
		driver.navigate().to("http://localhost:5500/src/main/index.html"); // open URL

		sleepFor(1000);
		
		// MAP CONTROL TESTS
		WebElement zoomIn;
		WebElement zoomOut;
		
		WebElement fullScreenMap;
		
		zoomIn = driver.findElement(By.xpath("//*[@id=\"map\"]/div/div/div[13]/div/div[3]/div/button[1]"));
		zoomOut = driver.findElement(By.xpath("//*[@id=\"map\"]/div/div/div[13]/div/div[3]/div/button[2]"));
		
		fullScreenMap = driver.findElement(By.xpath("//*[@id=\"map\"]/div/div/div[8]/button"));
		
		sleepFor(1000);
		
		//fullscreen
		fullScreenMap.click();
		sleepFor(1500);
		fullScreenMap.click();
		sleepFor(1500);
		
		//Zoom in and out
		zoomIn.click();
		sleepFor(1500);
		zoomOut.click();
		sleepFor(1500);
		
		// PAGE NAVIGATION
		WebElement homeTab;
		WebElement countryTab;
		WebElement northPoleTab;
		WebElement moonCoreTab;
		
		homeTab = driver.findElement(By.xpath("/html/body/div[1]/div/a[2]"));
		countryTab = driver.findElement(By.xpath("/html/body/div[1]/div/a[3]"));
		northPoleTab = driver.findElement(By.xpath("/html/body/div[1]/div/a[4]"));
		moonCoreTab = driver.findElement(By.xpath("/html/body/div[1]/div/a[5]"));
		
		countryTab.click();
		sleepFor(1500);
		northPoleTab.click();
		sleepFor(1500);
		moonCoreTab.click();
		sleepFor(1500);
		homeTab.click();
		sleepFor(1500);
		
		//FIND COUNTRY
		WebElement coordinateField;
		WebElement findCountryBtn;
		WebElement countryResult;

		coordinateField = driver.findElement(By.xpath("//*[@id=\"myText\"]"));
		findCountryBtn = driver.findElement(By.xpath("//*[@id=\"results\"]/div[1]/button"));
		countryResult = driver.findElement(By.xpath("//*[@id=\"result_country\"]")); 
		
		countryTab.click();
		sleepFor(1500);
		
		//valid and invalid coordinate inputs
		findCountryBtn.click(); //no coordinates given
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		if (countryResult.getText().isEmpty())
			System.out.println("Empty inputs for finding country test succeeded.");
		else
			System.out.println("Empty inputs for finding country test failed.");
		sleepFor(500);
		
		coordinateField.sendKeys("abc, def"); //invalid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		if (countryResult.getText().isEmpty())
			System.out.println("Invalid inputs for finding country test 1 succeeded.");
		else
			System.out.println("Invalid inputs for finding country test 1 failed.");
		sleepFor(500);
		
		coordinateField.clear();
		coordinateField.sendKeys("xya/0t9ru.p"); //invalid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		if (countryResult.getText().isEmpty())
			System.out.println("Invalid inputs for finding country test 2 succeeded.");
		else
			System.out.println("Invalid inputs for finding country test 2 failed.");
		sleepFor(500);
		
		coordinateField.clear();
		coordinateField.sendKeys("180 190"); //invalid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		if (countryResult.getText().isEmpty())
			System.out.println("Invalid inputs for finding country test 3 succeeded.");
		else
			System.out.println("Invalid inputs for finding country test 3 failed.");
		sleepFor(500);
		
		coordinateField.clear();
		coordinateField.sendKeys("1620.19, 19500.28"); //invalid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		if (countryResult.getText().isEmpty())
			System.out.println("Invalid inputs for finding country test 4 succeeded.");
		else
			System.out.println("Invalid inputs for finding country test 4 failed.");
		sleepFor(500);
		
		coordinateField.clear();
		coordinateField.sendKeys("39.86883, 32.75162"); //valid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		if (countryResult.getText().equals("Turkey"))
			System.out.println("Valid inputs for finding country test 1 succeeded.");
		else
			System.out.println("Valid inputs for finding country test 1 failed.");
		sleepFor(500);
		
		coordinateField.clear();
		coordinateField.sendKeys("20.195134281975815, 76.1819519536014"); //valid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		if (countryResult.getText().equals("India"))
			System.out.println("Valid inputs for finding country test 2 succeeded.");
		else
			System.out.println("Valid inputs for finding country test 2 failed.");
		sleepFor(500);
		
		coordinateField.clear();
		coordinateField.sendKeys("-32.8, 21.3838"); //valid inputs
		sleepFor(1000);
		findCountryBtn.click();
		sleepFor(1000);
		if (countryResult.getText().equals("South Africa"))
			System.out.println("Valid inputs for finding country test 3 succeeded.");
		else
			System.out.println("Valid inputs for finding country test 3 failed.");
		sleepFor(500);
		
		//FIND DISTANCE TO NORTH POLE
		WebElement deviceLocation;
		WebElement northPoleResult;

		deviceLocation = driver.findElement(By.xpath("//*[@id=\"info\"]"));
		northPoleResult = driver.findElement(By.xpath("//*[@id=\"result_northpole\"]"));
		
		double deviceLat = Double.parseDouble(deviceLocation.getText().substring(10, 18));
		double deviceLong = Double.parseDouble(deviceLocation.getText().substring(31, 39));
				
		northPoleTab.click();
		sleepFor(1500);
		
		String haversResult = String.format(Locale.US, "%.2f", haversine(deviceLat, deviceLong, 90, 135 ));
		if (northPoleResult.getText().equals(haversResult))
			System.out.println("Distance to north pole test succeeded.");
		else
			System.out.println("Distance to north pole test failed.");
		
		//FIND DISTANCE TO MOON'S CORE
		WebElement moonCoreField;
		WebElement moonCoreBtn;
		WebElement moonCoreResult;
		
		moonCoreField = driver.findElement(By.xpath("//*[@id=\"mooncor\"]"));
		moonCoreBtn = driver.findElement(By.xpath("/html/body/div[2]/main/div[8]/div/button"));
		moonCoreResult = driver.findElement(By.xpath("//*[@id=\"result_mooncore\"]"));
		
		moonCoreTab.click();
		sleepFor(500);
		
		System.out.println(moonCoreDist(deviceLat, deviceLong));
		System.out.println(moonCoreResult.getText());
		
		// valid and invalid coordinate inputs
		moonCoreBtn.click(); // no coordinates given
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		System.out.print("Result of moon core distance from empty inputs: ");
		System.out.println(moonCoreResult.getText());
		sleepFor(500);

		moonCoreField.sendKeys("abc, def"); // invalid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		System.out.print("Result 1 of moon core distance from invalid inputs: ");
		System.out.println(moonCoreResult.getText());
		sleepFor(500);

		moonCoreField.clear();
		moonCoreField.sendKeys("xya/0t9ru.p"); // invalid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		System.out.print("Result 2 of moon core distance from invalid inputs: ");
		System.out.println(moonCoreResult.getText());
		sleepFor(500);

		moonCoreField.clear();
		moonCoreField.sendKeys("180 190"); // invalid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		System.out.print("Result 3 of moon core distance from invalid inputs: ");
		System.out.println(moonCoreResult.getText());
		sleepFor(500);

		moonCoreField.clear();
		moonCoreField.sendKeys("1620.19, 19500.28"); // invalid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		driver.switchTo().alert().accept();
		sleepFor(500);
		System.out.print("Result 4 of moon core distance from invalid inputs: ");
		System.out.println(moonCoreResult.getText());
		sleepFor(500);

		System.out.println();
		moonCoreField.clear();
		moonCoreField.sendKeys("39.86883, 32.75162"); // valid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		System.out.print("Result 1 of moon core distance from valid inputs: ");
		System.out.println(moonCoreResult.getText());
		System.out.print("Expected Result: ");
		System.out.println(moonCoreDist(39.86883, 32.75162));
		sleepFor(500);

		moonCoreField.clear();
		moonCoreField.sendKeys("20.195134281975815, 76.1819519536014"); // valid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		System.out.print("Result 2 of moon core distance from valid inputs: ");
		System.out.println(moonCoreResult.getText());
		System.out.print("Expected Result: ");
		System.out.println(moonCoreDist(20.195134281975815, 76.1819519536014));
		sleepFor(500);

		moonCoreField.clear();
		moonCoreField.sendKeys("-32.8, 21.3838"); // valid inputs
		sleepFor(1000);
		moonCoreBtn.click();
		sleepFor(1000);
		System.out.print("Result 1 of moon core distance from valid inputs: ");
		System.out.println(moonCoreResult.getText());
		System.out.print("Expected Result: ");
		System.out.println(moonCoreDist(-32.8, 21.3838));
		sleepFor(500);
		
		//close browser
		driver.close();
		driver.quit();
	}
}