package Quara;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class quaraSearch extends Funcs{

	WebDriver driver;
	final static String mail = "quara.java@gmail.com";
	final static String password = "b1112222";

	public void start(String textToSearch, String[] topics,int minViews, int num){
		login();
		search(textToSearch);
		selectTopics(topics);
		
		quoraQA qa = new quoraQA();
		qa.start(driver, minViews, num);
	}
	
	

	/**
	 * log in to the account
	 */
	public void login(){
		driver = startWebDriver("https://www.quora.com/");
		WebElement flds = driver.findElement(By.xpath("//*[@class='form_inputs']"));

		WebElement email = flds.findElement(By.xpath(".//input[@name='email']"));
		email.click();
		email.clear();
		email.sendKeys(mail);

		WebElement pass = flds.findElement(By.xpath(".//input[@name='password']"));
		pass.click();
		pass.clear();
		pass.sendKeys(password);
		sleep(1000);


		WebElement sumbit = flds.findElement(By.xpath(".//input[contains(@class,'submit_button')]"));
		sumbit.click();
		sleep(5000);
		System.out.println("log");
	}



	/**
	 * 
	 * @param textToSearch
	 * @param topic
	 * @return
	 */
	public boolean search(String textToSearch){
		WebElement field = driver.findElement(By.xpath("//textarea[@class='selector_input text']"));
		field.click();
		field.clear();
		field.sendKeys(textToSearch);
		sleep(2000);

		field.sendKeys(Keys.RETURN);
		//		WebElement search = driver.findElement(By.xpath("//*[@class='results_wrapper']//*[@class='selector_result search']"));
		//		search.click();
		sleep(3000);

		WebElement qa = driver.findElement(By.xpath("//*[@data-value='answer']"));
		qa.click();

		return true;
	}


	/**
	 * add topic filter to search bar
	 * @param topics 
	 */
	public void selectTopics(String[] topics){

		for(int i=0; i<topics.length; i++){

			WebElement topicf = driver.findElement(By.xpath("//*[contains(@class,'TopicSelector')]//input[@class='selector_input text']"));
			topicf.click();
			topicf.clear();
			topicf.sendKeys(topics[i]);

			sleep(2500);
			try{
				WebElement first = driver.findElement(By.xpath("//li[@class='selector_result topic_alias']"));
				first.click();
			}catch(Exception e){System.err.println("topic "+topics[i]+" faild");}
		}
	}




}
