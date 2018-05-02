import java.io.FileInputStream;
import org.openqa.selenium.Keys;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestThemTings {
	
	ExtentReports report;
	ExtentTest eTest;
	FileInputStream file;
	XSSFWorkbook workbook;
	
	ChromeDriver driver;
	
	@Before
	public void startThemTings() {
		
		System.setProperty("webdriver.chrome.driver", "C:/Users/Admin/Downloads/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void readFromFile() {
		
		report = new ExtentReports("C:\\Users\\Admin\\myMadReport.html", true);
		
		eTest = report.startTest("Verify application Title and all that");
		eTest.log(LogStatus.INFO, "Test started"); // add a note to the test
		eTest.log(LogStatus.PASS, "verify something here"); // report the test as a pass

	    try {
	       file = new FileInputStream (Constants.dataPath + Constants.testFile);
	    } catch (FileNotFoundException e) {}
	    
	    try {
	        workbook = new XSSFWorkbook(file);
	    } catch (IOException e) {}
	    
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    
	    if(sheet.getPhysicalNumberOfRows() != 0) {
	    
	    nextCellOutput(sheet);
	    }
	    
		else {
    		endGame();
    	}
	}
	public void nextCellOutput(XSSFSheet sheet) {
		
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			
		   	Cell cellUser = sheet.getRow(i).getCell(0);
		   	Cell cellPass = sheet.getRow(i).getCell(1);
		    
		    String inputCell = (cellUser.getStringCellValue());
		    String inputPass = (cellPass.getStringCellValue());
		    
		    browser();
		    createAccount(inputCell, inputPass); 	
    	}
	}

	public void browser() {
		
		 driver.get("http://www.thedemosite.co.uk/");
		    driver.manage().window().maximize();
	}
	public void createAccount(String inputCell, String inputPass) {
		
		WebElement addUser = driver.findElement(By.xpath("/html/body/div/center/table/tbody/tr[2]/td/div/center/table/tbody/tr/td[2]/p/small/a[3]"));
		addUser.click();
		
		WebElement userField = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[1]/td[2]/p/input"));
		userField.click();
		userField.sendKeys(inputCell);
		
		userField.sendKeys(Keys.TAB);
		
		WebElement passField = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[2]/td[2]/p/input"));
		passField.sendKeys(inputPass);
		
		
		WebElement saveButton = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/div/center/table/tbody/tr/td[1]/div/center/table/tbody/tr[3]/td[2]/p/input"));
		saveButton.click();
		
	}
	
	@After 
	public void endGame() {
		
		report.endTest(eTest);
		report.flush();
		//driver.quit();
	}
}

