package Quara;

import java.io.FileWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;




import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;



/**
 * @author Ben Horn
 * @since 3/2018
 *
 */
public abstract class Funcs {


	/**
	 *  the visibility of the webdriver window. by WindowState enum.
	 */
	static WindowState window = WindowState.visible;
	final static String url ="https://www.quora.com/";
	static WebDriver driver;

	/**
	 * start webDriver with url in this.window of visability
	 * @param url first url
	 * @return started driver
	 */
	public WebDriver startWebDriver(String url){
		
		
		
		if(this.window == WindowState.visible){
			return startVisibleWebDriver(url);
		}
		if(this.window == WindowState.Background){
			return startDistanceWebDriver(url);
		}
		if(this.window == WindowState.Invisible){
			return startHeadlessWebDriver(url);
		}
		return null;
	}


	/**
	 * this function open and start new visable WebDriver
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	private WebDriver startVisibleWebDriver(String url){
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//		options.addArguments("--headless");
		options.addArguments("--start-maximized");
		options.addArguments("--mute-audio");

		driver = new ChromeDriver(options);
		try{
			//			driver.manage().window().setPosition(new Point(-2000, 0));
		}
		catch(Exception e){System.err.println(e);}


		if(url.isEmpty() || url.equals(""))
			return driver;
		driver.get(url);
		return driver;
	}



	/**
	 * this function open and start new invisible WebDriver
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	private WebDriver startHeadlessWebDriver(String url){

		for(int i=0; i<3; i++){
			try{
				WebDriver driver;
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--start-maximized");
				options.addArguments("--mute-audio");
				driver = new ChromeDriver(options);

				if(url.isEmpty() || url.equals(""))
					return driver;
				driver.get(url);
				return driver;

			}catch(Exception e){e.printStackTrace();}
		}
		return null;

	}




	/**
	 * this function open and start new WebDriver in background
	 * @param url -first url
	 * @return new webDriver open with param url
	 */
	private WebDriver startDistanceWebDriver(String url){
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//		options.addArguments("--headless");
		options.addArguments("--start-maximized");
		options.addArguments("--mute-audio");
		driver = new ChromeDriver(options);
		try{
			driver.manage().window().setPosition(new Point(-2000, 0));
		}
		catch(Exception e){System.err.println(e);}


		if(url.isEmpty() || url.equals(""))
			return driver;
		driver.get(url);
		return driver;
	}

	/**
	 * use to click on invisible elements, using javaScript. not recommended but usualy working 
	 * @param driver -webfriver
	 * @param element -WebElement to click. 
	 */
	public static void clickInvisible(WebDriver driver, WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
	}

	/**
	 * send keys to invisible elements. not recommended but working
	 * @param driver -webfriver
	 * @param element -WebElement field. 
	 */
	public void sendKeysInvisible(WebDriver driver, WebElement element){
		RemoteWebDriver r=(RemoteWebDriver) driver;
		r.executeScript("arguments[0].value='admin'",element);

	}





	/**
	 * move to element in page using Actions. 
	 * @param driver
	 * @param web element
	 */
	public static boolean moveTo(WebDriver driver, WebElement we){
		Actions actions = new Actions(driver);
		try{
			actions.moveToElement(we).perform();
		}
		catch (IndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	/**
	 * move to element in page using javaScript. 
	 * @param driver
	 * @param web element
	 * @return false if faild.
	 */
	public static boolean moveTo2(WebDriver driver, WebElement we){
		try{
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].scrollIntoView()", we); 
		}catch(Exception e) {return false;}
		return true;
	}


	/**
	 * use to sign into a website for full access.
	 * can be overriden.
	 */
	public void signIn() {}



	/**
	 * this finction write a String arr to the last row in sheet
	 * @param arr
	 * @param sheet
	 */
	public static void StringArrToLastRow(String[] arr, XSSFSheet sheet) {
		if(arr == null) return;

		for(int i=0; i<arr.length; i++){
			if(arr[i] == null)
				arr[i]="";
		}

		Row row = sheet.getRow(sheet.getLastRowNum());
		if(row==null)
			row = sheet.createRow(sheet.getLastRowNum());
		else row = sheet.createRow(sheet.getLastRowNum()+1);

		for(int i=0 ;i<arr.length; i++){
			if(arr[i].length()>30000){
				expand(arr);
				break;
			}
		}

		int i=0;
		Cell cell;
		for(i=0; i<arr.length; i++){
			cell = row.createCell(i);
			cell.setCellValue(arr[i]);
		}
	}

	/**
	 * expand big arr cells to next cells
	 * @param arr array with Strings.
	 */
	private static void expand(String[] arr){
		String tmp = "";
		for(int i=0; i<arr.length-1; i++){
			if(arr[i].length()>30000){
				tmp = arr[i].substring(30000, arr[i].length());
				arr[i] = arr[i].substring(0, 30000);
				arr[i+1] = tmp;
			}
		}
	}


	/**
	 * thread sleep for mil milliseconds
	 * @param mil
	 */
	public static void sleep(int mil){
		try {
			Thread.sleep(mil);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param monthStr month in String
	 * @return month in int (1/2/3/4..12)
	 */
	public static int monthToInt(String month){
		String monthStr = "";
		int c ='\0';
		for(int i=0;i<month.length(); i++){
			c= month.charAt(i);
			if(c>=97 && c<=122)
				c=c-32;
			monthStr =monthStr + (char) c;
		}
		if (monthStr.contains("JAN"))
			return 1;
		if (monthStr.contains("FEB"))
			return 2;
		if (monthStr.contains("MAR"))
			return 3;
		if (monthStr.contains("APR"))
			return 4;
		if (monthStr.contains("MAY"))
			return 5;
		if (monthStr.contains("JUN"))
			return 6;
		if (monthStr.contains("JUL"))
			return 7;
		if (monthStr.contains("AUG"))
			return 8;
		if (monthStr.contains("SEP"))
			return 9;
		if (monthStr.contains("OCT"))
			return 10;
		if (monthStr.contains("NOV"))
			return 11;
		if (monthStr.contains("DEC"))
			return 12;


		if (monthStr.contains("ינו"))
			return 1;
		if (monthStr.contains("פבר"))
			return 2;
		if (monthStr.contains("מרץ"))
			return 3;
		if (monthStr.contains("אפר"))
			return 4;
		if (monthStr.contains("מאי"))
			return 5;
		if (monthStr.contains("יונ"))
			return 6;
		if (monthStr.contains("יול"))
			return 7;
		if (monthStr.contains("אוג"))
			return 8;
		if (monthStr.contains("ספט"))
			return 9;
		if (monthStr.contains("אוק"))
			return 10;
		if (monthStr.contains("נוב"))
			return 11;
		if (monthStr.contains("דצ"))
			return 12;

		return 0;

	}


	/**
	 * apeand long text in file, seperate by ',' (next cell) 
	 * @param writer
	 * @param text
	 */
	public static void apeandComments(FileWriter writer, String text){
		try {
			if(text.length()>32000){
				int size=0;
				int i=1;
				while(size+30000<text.length()){
					writer.append(text.substring(size, 30000*i)+',');
					i++;
					size+=30000;
				}
				writer.append(text.substring(size, text.length())+',');
			}
			else
				writer.append(text+',');


		} catch (IOException e) {

			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @param date String that present date with format dd.MM.yyy
	 * @return date in Date type.
	 */
	public static Date stringToDate(String date){
		if(date == null || date.isEmpty()) return null;

		Date dt=null ; 
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		try {
			dt = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dt;
	}

	/**
	 * 
	 * @param date
	 * @return String that present the date in dd.MM.yyyy format 
	 */
	public static String dateToString(Date date){
		if(date == null) return "";

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		String reportDate = df.format(date);

		return reportDate;
	}

	/**
	 * 
	 * @param date Date 
	 * @param days how many days to add
	 * @return update date.
	 */
	public static String addDays(String date , int days){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {e.printStackTrace();}
		c.add(Calendar.DATE, days);  // number of days to add
		date = sdf.format(c.getTime());  // dt is now the new date
		return date;
	}
	
	public static WebDriver killDriver(WebDriver driver){
		System.out.println("turn off");
		try{
			driver.close();
			driver.quit();
		}catch(Exception e){}

		try {
			Runtime.
			getRuntime().
			exec("taskkill /im chromedriver.exe /f");
		} catch (IOException e) {}
		sleep(2000);

		driver = null;
		return driver;
	}


	/**
	 * 
	 * @return today date in format dd.MM.yyyy
	 */
	public static String todayString(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	
	/**
	 * replase " with ' ' (space)
	 * @param text
	 * @return
	 */
	public String SearchField(String text) {
		String newStr = text;
		if(text.indexOf('"'+"")!=-1){
			newStr=text.replace('"', ' ');
		}
		return newStr;
	}
	

	/**
	 * remove ',' and '\n' from text, before insert to csv file
	 * @param text
	 * @return
	 */
	public static String clean(String text){
		String str="";
		str=text.replaceAll("," , "/");
		str = str.replaceAll("\n", "	");
		str = str.replaceAll("\r", "	");
		str = str.replace('\n','	');
		str = str.replace(',', '/');
		str = str.replace('\r', '	');

		return str;
	}





}
