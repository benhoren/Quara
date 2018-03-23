package Quara;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class quoraQA extends Funcs{

	WebDriver driver;
	String url;
	
	final int maxSearch = 10000;
	

	public void start(WebDriver driver, int minViews, int num){
		this.driver = driver;
	
	}
	
	public void scanAnswers(int minViews, int num){
		
		ArrayList <WebElement> results = (ArrayList <WebElement>)
				driver.findElements(By.xpath("//*[@class='pagedlist_item']"));
		
		int i=0;
		int r = 0;
		int found = 0;
		int tries = 0;
		while(found < num){
			if(r >= maxSearch)
				break;
			else r++;
			
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
				
				
				
			}
			
		
		}
		
	}

	public String getQuestion(){
		String str="";

		WebElement title = driver.findElement(By.className("rendered_qtext"));
		str= title.getText();

		//		rendered_qtext
		return str;
	}

	public int getAnswerNum(){
		String str="";

		WebElement title = driver.findElement(By.className("answer_count"));
		str= title.getText();

		str = str.split(" ")[0];

		if(str.indexOf("+")!=-1)
			str=countAnswers();
		
		int num =0;
		try{
			num  = Integer.parseInt(str);
		}catch(Exception e){e.printStackTrace(); num = -1; }

		return num;
	}

	private String countAnswers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public int getViews(){
		String str="";

		WebElement view = driver.findElement(By.xpath("//*[@id='AtzOtt']//*[@class='ViewsRow HighlightRow']"));
		str= view.getText();
		
		str = str.split(" ")[0];

		str = str.replace(','+"", "");
		int num = 0;
		try{
			num = Integer.parseInt(str);
		}catch(Exception e){e.printStackTrace(); num =-1;}

		return num;
	}
	
	
	




}
