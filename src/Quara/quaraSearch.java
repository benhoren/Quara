package Quara;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class quaraSearch extends Funcs{



	final int maxSearch = 10000;
	static String mail = "quara.java@gmail.com";
	static String password = "b1112222";

	public quaraSearch(String mail2, String password2) {

		if(!mail2.isEmpty() && !password2.isEmpty()){
			mail = mail2;
			password = password2;
		}
	}


	public ArrayList<String> start(String textToSearch, String[] topics,int minFollows, int num){
		driver = startWebDriver(url);

		if(login())
			System.out.println("login");
		else{
			mainScreen.addToLog("Error: can't log in.");
			return null;
		}
		try{
		if(!search(textToSearch)) return null;
		}catch(Exception e){e.printStackTrace(); return null;}
		selectTopics(topics);
		ArrayList<String> qlist = questionsList(num, minFollows);
		System.out.println("found: "+qlist.size());

		return qlist;
	}


	/**
	 * log in to the account
	 */
	public boolean login(){
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
		
		try{
			flds.findElement(By.xpath(".//input[contains(@class,'submit_button')]"));
		}catch(Exception e){return true;}
		
		return false;
	}


	/**
	 * 
	 * @param textToSearch
	 * @param topic
	 * @return
	 */
	public boolean search(String textToSearch){

		boolean flag = false;


		//		for(int i=0; i<5; i++){
		try{
			System.out.println("nav");
			driver.navigate().to(url);
			flag = true;
		}	catch(Exception e){System.err.println("WEBDRIVER FAILD");}


		while(!flag){
			try{
				driver = killDriver(driver);
				sleep(2000);
				driver = startWebDriver(url);
				login();
				sleep(2000);
				WebElement field = driver.findElement(By.xpath("//textarea[@class='selector_input text']"));

				flag = true;
			}catch(Exception e){System.err.println("driver error "); }
		}

		intppt.flag = true;
		System.out.println("nav end");

		WebElement field = driver.findElement(By.xpath("//textarea[@class='selector_input text']"));
		field.click();
		field.clear();
		field.sendKeys(textToSearch);
		sleep(5000);

		field.sendKeys(Keys.RETURN);
		//		WebElement search = driver.findElement(By.xpath("//*[@class='results_wrapper']//*[@class='selector_result search']"));
		//		search.click();
		sleep(3000);

		try{
			WebElement qa = driver.findElement(By.xpath("//*[@data-value='question']"));
			clickInvisible(driver, qa);
			//			qa.click();
		}catch(Exception e){
			field = driver.findElement(By.xpath("//textarea[@class='selector_input text']"));
			field.click();
			field.clear();
			field.sendKeys(textToSearch);
			sleep(5000);

			driver.findElement(By.xpath("//*[@class='selector_result search']")).click();
			//			field.sendKeys(Keys.RETURN);
			sleep(3000);
			WebElement qa = driver.findElement(By.xpath("//*[@data-value='question']"));
			qa.click();
			sleep(3000);
		}


		return true;
	}


	/**
	 * add topic filter to search bar
	 * @param topics 
	 */
	public void selectTopics(String[] topics){

		for(int i=0; i<topics.length; i++){

			for(int j=0; j<7; j++){ //three times every topic.
				if(topics[i] == null || topics[i].trim().isEmpty())
					continue;

				WebElement topicf = driver.findElement(By.xpath("//*[contains(@class,'TopicSelector')]//input[@class='selector_input text']"));
				topicf.click();
				topicf.clear();
				topicf.sendKeys(topics[i]);

				sleep(2500);
				try{
					WebElement first = driver.findElement(By.xpath("//li[@class='selector_result topic_alias']"));
					//					first.click();
					clickInvisible(driver, first);
				}catch(Exception e){System.err.println("topic "+topics[i]+" faild");}
			}
		}
	}


	/**
	 * get list of the questions.
	 * @param num number of questions required
	 * @param minFollows minimum followers to the question
	 * @return
	 */
	public ArrayList<String> questionsList(int num, int minFollows){
		ArrayList<String> questions = new ArrayList<String>();

		ArrayList <WebElement> results = (ArrayList <WebElement>)
				driver.findElements(By.xpath("//*[@class='pagedlist_item']"));

		int i=0;
		int r = 0;
		int found = 0;
		int tries = 0;
		String link=""; 
		int follow=0;
		while(found < num){
			if(r >= maxSearch)
				break;
			else r++;

			link="";follow=0;

			if(i >= results.size()){
				int size = results.size();
				moveTo2(driver, results.get(size-1));
				sleep(2000);
				results = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='pagedlist_item']"));

				if(size == results.size())
					tries++;
				else tries = 0;

				if(tries > 10){
					System.err.println("end of results");
					break;
				}
			}

			if(i<results.size()){

				try{
					WebElement ttl = results.get(i).findElement(By.xpath(".//*[@class='question_link']"));
					link = ttl.getAttribute("href");

					WebElement count = null;
					try{
						count = results.get(i).findElement(By.xpath(".//*[@class='bullet']/../span[2]"));
					}catch(Exception e2){
						count = results.get(i).findElement(By.xpath(".//*[@class='count']/span"));
					}

					try{
						String c = count.getText();
						int k=1;
						if(c.contains("k")){
							c = c.substring(0, c.length()-1);
							k=1000;
						}
						follow = (int)(Double.parseDouble(c)*k);

					}catch(NumberFormatException e2){}

				}catch(Exception e){e.printStackTrace();}
				System.out.println(follow+": "+link);

				if(follow >= minFollows){
					questions.add(link);
					found++;
					System.out.println("Question "+found+"/"+num);
					mainScreen.addToLog("Question "+found+"/"+num);
				} else System.out.println("no");
				i++;
			}	
		}

		return questions;
	}




}
