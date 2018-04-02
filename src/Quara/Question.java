package Quara;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Question extends Funcs implements excelData{

	static int counter = 1;
	
	String link;
	String question;
	int serialNum;
	int answersNum;
	int views;
	int followers;
	ArrayList<String> tags;
	ArrayList<Answer> answers;
	
	
	public Question(){
		this.serialNum = counter++;
		link="";
		question="";
		answersNum=-1;
		views=-1;
		followers=-1;
	}
	
	
	public Question(String link) {
		this.serialNum = counter++;
		this.link = link;
		link="";
		question="";
		answersNum=-1;
		views=-1;
		followers=-1;
	}


	public void addQuestionStat(String question, int views, int followers, int answerNum, ArrayList<String> tags){
		
		this.question = question;
		this.views = views;
		this.followers = followers;
		this.answersNum = answerNum;
		this.tags = tags;
	}
	
	
	
	public static void toExcel(ArrayList<Question> list, XSSFSheet q, XSSFSheet a, XSSFSheet p, XSSFSheet c){
		for(int i=0; i<list.size(); i++){
			list.get(i).toSheet(q);
			Answer.toExcel(list.get(i).answers, a, p, c);	
		}
	}


	@Override
	public void toSheet(XSSFSheet sheet) {
		String[] arr = toArray();
		StringArrToLastRow(arr, sheet);	
	}
	
	public String[] toArray(){
		if(question == null) question="";
		String[] arr = {serialNum+"",link, question, stringTags(), views+"", followers+"", answersNum+""};
		return arr;
		
	}


	private String stringTags() {
		String str = "";
		if(tags == null || tags.size() == 0) return "";
		for(String tag: tags)
			str+=tag+", ";
		str = str.substring(0,str.length()-2);
		return str;
	}
	
	
	
	
	
	public static void getQuestionStat(Question question){

		String head="";
		int answersNum=0, views=0, follows=0;
		ArrayList<String> tags=null;
		try{
			head = getQuestion();
		}catch(Exception e){e.printStackTrace();}
		try{
			answersNum = getAnswerNum();
		}catch(Exception e){e.printStackTrace();}
		try{
			views = getViews();
		}catch(Exception e){e.printStackTrace();}
		try{
			follows = getFollows();
		}catch(Exception e){e.printStackTrace();}
		try{
			tags = getTags();
		}catch(Exception e){e.printStackTrace();}


		System.out.println(head+" "+'\n'+views+" "+'\n'+follows+" "+'\n'+answersNum+" "+'\n'+tags.toString());
		System.out.println();
		question.addQuestionStat(head, views, follows, answersNum, tags);
	}

	private static int getFollows() {
		WebElement count = driver.findElement(By.xpath("//*[@action_click='QuestionFollow']//span[@class='bullet']/../*[contains(@id,'count')]"));
		String str = count.getText();
		
		int k =1;
		if(str.contains("k")){
			k=1000;
			str =str.trim().substring(0, str.length()-1);
		}
		int x =(int)(Double.parseDouble(str)*k);
		return x;
	}

	private static ArrayList<String> getTags() {
		ArrayList<String> tags = new ArrayList<String>();

		try{
			WebElement more = driver.findElement(By.xpath("//*[contains(@class,'TopicList')]//*[contains(@class,'view_more_topics_link')]/a"));
			more.click();
		}catch(Exception e){}
		sleep(1000);

		ArrayList<WebElement> items = (ArrayList<WebElement>)
				driver.findElements(By.xpath("//*[contains(@class,'TopicListItem')]//*[contains(@class,'TopicNameSpan')]"));

		for(WebElement item: items){
			tags.add(item.getText());
		}

		//remove duplicate
		for(int i=0; i<tags.size()-1; i++){
			for(int j=i+1; j<tags.size(); j++){
				if(i!=j){
					if(tags.get(i).equals(tags.get(j))){
						tags.remove(j);
						j--;
					}
				}
			}
		}

		return tags;
	}

	public static String getQuestion(){
		String str="";
		WebElement title = driver.findElement(By.xpath("//*[contains(@class,'QuestionArea')]//*[@class='rendered_qtext']"));
		str= title.getText();
		return str;
	}

	public static int getAnswerNum(){
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

	private static String countAnswers() {
		return "-1";
	}


	public static int getViews(){
		String str="";

		WebElement view = driver.findElement(By.xpath("//*[contains(@class,'ViewsRow')]"));
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
