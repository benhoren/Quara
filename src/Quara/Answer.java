package Quara;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Answer extends Funcs{

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


	public String[] toArray(){
		String[] arr={questionNum+"", serialNum+"", name, slogan, date, orgQuestion, body, views+"", upvote+""};

		return arr;
	}




	public static Answer getAnswer(WebElement ansElement, int serialQuestion) {
		String name ="", link = "", slogan="", date="";

		try{

			//div[contains(@id,'answer_content')]
			WebElement more = ansElement.findElement(By.xpath(".//*[@class='ui_qtext_more_link']"));
			moveTo2(driver,more);
			sleep(1500);
			more.click();
			sleep(1500);
			System.out.println("click (more)");
		}catch(Exception e){}

		try{
			WebElement more = ansElement.findElement(By.xpath(".//div[contains(@id,'answer_content')]"));
			moveTo2(driver,more);
			sleep(1500);
			more.click();
			sleep(1500);
			System.out.println("click body");
		}catch(Exception e){}

		try{
			WebElement header = ansElement.findElement(By.xpath(".//*[contains(@class,'AnswerHeader')]"));

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


		}catch(Exception e){e.printStackTrace(); return null;}

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
		if(name == null || name.trim().isEmpty() || name.equals("David Moore"))
			return null;

		ArrayList<Comment> cmmts = null;
		try{
			cmmts = getComments(ansElement);
		}catch(Exception e){e.printStackTrace();}

		Answer answer = new Answer(serialQuestion, name, slogan, link, body, date,
				organs, views, upvt, cmmts);

		return answer;
	}

	public static ArrayList<Comment> getComments(WebElement ansElement){


		try{

			WebElement section = ansElement.findElement(By.xpath(".//*[contains(@class,'threaded_comments')]"));
			moveTo2(driver, section);
			sleep(1500);

			try{
				WebElement prv = section.findElement(By.xpath(".//*[@class='toggle_link toggle_all']"));
				moveTo2(driver, prv);
				sleep(1500);
				prv.click();
				sleep(2500);
			}catch(Exception e){}

			try{
				WebElement all = section.findElement(By.xpath(".//*[@class='comments_preview_toggle']"));
				moveTo2(driver, all);
				sleep(1500);
				all.click();
				sleep(2500);
			}catch(Exception e){}


			int tries = 0;
			for(int i=0; i<1000; i++){
				try{
					WebElement morecmm = section.findElement(By.xpath(".//*[contains(@class,'comments_more_button')]"));
					moveTo2(driver, morecmm);
					sleep(1000);
					morecmm.click();
					sleep(2000);
				}catch(Exception e){tries++; if(tries==5) break;}
			}

			try{
				WebElement coll = section.findElement(By.xpath("//*[@class='collapsed_comments_link']"));
				moveTo2(driver, coll);
				sleep(1000);
				coll.click();
				sleep(2000);
			}catch(Exception e){}

			tries = 0;
			for(int i=0; i<1000; i++){
				try{
					WebElement showchild = section.findElement(By.xpath(".//*[@class='show_child_link']"));
					moveTo2(driver, showchild);
					sleep(1000);
					showchild.click();
					sleep(2000);
				}catch(Exception e){tries++; if(tries==5) break;}
			}


			tries = 0;
			for(int i=0; i<1000; i++){
				try{
					WebElement expand = section.findElement(By.xpath(".//*[@class='ui_qtext_more_link']"));
					moveTo2(driver, expand);
					sleep(1000);
					expand.click();
					sleep(2000);
				}catch(Exception e){tries++; if(tries==5) break;}
			}


		}catch(Exception e){e.printStackTrace(); return null;}

		return null;
	}




}
