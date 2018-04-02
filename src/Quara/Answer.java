package Quara;

import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Answer extends Funcs implements excelData{

	int serialNum = 0;
	int questionNum = 0;
	String name ="";
	String slogan ="";
	String link ="";
	Profile profile;
	String body ="";
	String date ="";
	String orgQuestion ="";
	int views =0;
	int upvote =0;
	ArrayList<Comment> comments;



	/**
	 * @param questionNum
	 * @param name
	 * @param slogan
	 * @param body
	 * @param date
	 * @param orgQuestion
	 * @param views
	 * @param upvote
	 * @param comments
	 */
	public Answer(int questionNum, String name, String slogan, String link, String body,
			String date, String orgQuestion, int views, int upvote,ArrayList<Comment> comments) {
		super();
		this.questionNum = questionNum;
		this.name = name;
		this.slogan = slogan;
		this.link = link;
		this.body = body;
		this.date = date;
		this.orgQuestion = orgQuestion;
		this.views = views;
		this.upvote = upvote;
		this.comments = comments;
	}
	
	
	public static void toExcel(ArrayList<Answer> answerslist, XSSFSheet a, XSSFSheet p, XSSFSheet c) {
		for(int i=0; i<answerslist.size(); i++){
			answerslist.get(i).toSheet(a);
			Comment.toExcel(answerslist.get(i).comments, c);
		}
		
	}
	
	@Override
	public String[] toArray(){
		String[] arr={questionNum+"", serialNum+"", name, slogan, date, orgQuestion, body, views+"", upvote+""};

		return arr;
	}


	@Override
	public void toSheet(XSSFSheet sheet) {
		String[] arr = toArray();
		StringArrToLastRow(arr, sheet);	
	}



	public static Answer getAnswer(WebElement ansElement, int serialQuestion) {
		String name ="", link = "", slogan="", date="";

		System.out.println("get ANSwer");
		
		try{
			WebElement more = ansElement.findElement(By.xpath(".//*[contains(@class,'ui_qtext_truncated')]"));
			moveTo2(driver,more);
			sleep(1500);
			more.click();
			sleep(1500);
			System.out.println("click expan");

			try{
				WebElement closePhoto = driver.findElement(By.xpath("//*[@class='photo_modal_close active']"));
				closePhoto.click();
				System.out.println("closephoto");
			}catch(Exception e){}

		}catch(Exception e){}


		try{
			WebElement more = ansElement.findElement(By.xpath(".//*[@class='ui_qtext_more_link']"));
			moveTo2(driver,more);
			sleep(1500);
			more.click();
			sleep(1500);
			System.out.println("click (more)");
		}catch(Exception e){}

		try{
			WebElement header = ansElement.findElement(By.xpath(".//*[contains(@class,'ContentHeader')]"));

			try{
				WebElement user = header.findElement(By.xpath(".//*[@class='feed_item_answer_user']//*[@class='user']"));
				name = user.getText();
				link = user.getAttribute("href");
			}catch(Exception e){}

			try{
				WebElement desc = header.findElement(By.xpath(".//*[contains(@class,'NameCredential')]"));
				slogan = desc.getText();
			}catch(Exception e){}

			try{
				WebElement dt = header.findElement(By.xpath(".//*[@class='answer_permalink']"));
				date = dt.getText();

				try{//Answered May 12, 2014

					String[] arr = date.trim().split(" ");
					if(arr.length>3){
						arr[1] = monthToInt(arr[1])+"";
						arr[2] = arr[2].substring(0, arr[2].length()-1);

						date = arr[2]+"."+arr[1]+"."+arr[3];
					}

					if(arr.length == 3){//Answered Mar 5
						arr[1] = monthToInt(arr[1])+"";

						date = arr[2]+"."+arr[1]+"."+"2018";

						if(arr[1].equals("0"))
							date = todayString();
					}
				}catch(Exception e){e.printStackTrace();}
			}catch(Exception e){e.printStackTrace();}


		}catch(Exception e){System.err.println("no header " +e);
			 return null;}

		String organs ="";
		try{
			WebElement qus = ansElement.findElement(By.xpath(".//*[@class='OriginallyAnsweredBanner']//*[@class='rendered_qtext']"));
			organs=  qus.getText();
		}catch(Exception e){}

		String body="";
		try{
			WebElement bd = ansElement.findElement(By.xpath(".//*[@class='ui_qtext_expanded']"));
			body=  bd.getText();

		}catch(Exception e){e.printStackTrace();}


		int views = 0;
		try{
			WebElement vs = ansElement.findElement(By.xpath(".//*[@class='meta_num']"));
			String vis=  vs.getText();

			int k =1;
			if(vis.contains("k")){
				k = 1000;
				vis = vis.trim().substring(0, vis.length()-1);
			}
			views = (int)( Double.parseDouble(vis)*k);

		}catch(Exception e){e.printStackTrace();}

		int upvt = 0;
		try{
			WebElement fl = ansElement.findElement(By.xpath(".//*[@action_click='AnswerUpvote']//*[contains(@id,'count')]"));
			String fll=  fl.getText();

			if(fll != null && !fll.isEmpty()){
				fll = fll.substring(2, fll.length());

				int k =1;

				if(fll.contains("k")){
					k = 1000;
					fll = fll.substring(0, fll.length()-1);
				}
				upvt = (int) (Double.parseDouble(fll)*k);
			}
		}catch(Exception e){e.printStackTrace();}

		if(body == null || body.trim().isEmpty())
			return null;
//		if(name == null || name.trim().isEmpty() || name.equals("David Moore"))
//			return null;

		ArrayList<Comment> cmmts = null;
		try{
			System.out.println("COMM");
			cmmts = Comment.getComments(ansElement);

			if(cmmts!=null)
				System.out.println(cmmts.size());
			System.out.println("null");
		}catch(Exception e){e.printStackTrace();}

		Answer answer = new Answer(serialQuestion, name, slogan, link, body, date,
				organs, views, upvt, cmmts);

		return answer;
	}

	


	




}
