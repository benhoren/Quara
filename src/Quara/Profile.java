package Quara;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Profile extends Funcs implements excelData{
	int questionNum;
	int answerNum;
	
	String name;
	String slogan;
	String link;
	String work;
	String live;
	String study;
	String about;
	int views;
	int answers;
	int questions;
	int activity;
	int posts;
	int blogs;
	int followers;
	int following;
	int topics;
	int edits;


	/**
	 * @param name
	 * @param slogan
	 * @param link
	 * @param work
	 * @param live
	 * @param study
	 * @param about
	 * @param views
	 * @param answers
	 * @param questions
	 * @param activity
	 * @param posts
	 * @param blogs
	 * @param followers
	 * @param following
	 * @param topics
	 * @param edits
	 */
	public Profile(String name,String slogan, String link, String work,
			String live, String study, String about,int views, int answers,
			int questions, int activity, int posts, int blogs, int followers,
			int following, int topics, int edits) {
		super();
		this.name = name;
		this.slogan = slogan;
		this.link = link;
		this.work = work;
		this.live = live;
		this.study = study;
		this.about = about;
		this.views = views;
		this.answers = answers;
		this.questions = questions;
		this.activity = activity;
		this.posts = posts;
		this.blogs = blogs;
		this.followers = followers;
		this.following = following;
		this.topics = topics;
		this.edits = edits;
	}



	@Override
	public String[] toArray() {
		// TODO Auto-generated method stub
		String[] arr = {""+questionNum, ""+answerNum, link, name, slogan, work, study, live, about, ""+views,
				""+answers, ""+questions, ""+activity, ""+posts, ""+blogs, ""+followers, ""+following, ""+topics,""+ edits};
		return arr;
	}


	@Override
	public void toSheet(XSSFSheet sheet) {
		String[] arr = toArray();
		StringArrToLastRow(arr, sheet);
	}




	/**
	 * get all the info from profile
	 * @param link to profile
	 * @return profile in Profile type
	 */
	public static Profile getProfile(String link){
		Profile p = null;
		driver.get(link);
		String name="", slogan="", work="", school="", live="", desc="";
		int views=0;

		//name
		try{
			WebElement nm = driver.findElement(By.xpath("//*[@class='header_content']//*[@class='user']"));
			name = nm.getText();
		}catch(Exception e){System.err.println("name");}

		//slogan
		try{
			WebElement slgn = driver.findElement(By.xpath("//*[@class='header_content']//*[contains(@class,'IdentityCredential')]"));
			slogan = slgn.getText();
		}catch(Exception e){System.err.println("slogan");}



		WebElement about = driver.findElement(By.xpath("//*[@class='AboutSection']/*[@class='contents']"));

		//work
		try{
			WebElement wrk = about.findElement(By.xpath(".//*[contains(@class,'WorkCredentialListItem')]//*[contains(@class,'UserCredential')]"));
			work = wrk.getText();
		}catch(Exception e){System.err.println("work");}

		//school
		try{
			WebElement scl = about.findElement(By.xpath(".//*[contains(@class,'SchoolCredentialListItem')]//*[contains(@class,'UserCredential')]"));
			school = scl.getText();
		}catch(Exception e){System.err.println("school");}

		//live
		try{
			WebElement lv = about.findElement(By.xpath(".//*[contains(@class,'LocationCredentialListItem')]//*[contains(@class,'UserCredential')]"));
			live = lv.getText();
		}catch(Exception e){System.err.println("live");}

		//views
		try{
			WebElement lv = about.findElement(By.xpath(".//*[contains(@class,'AnswerViewsAboutListItem')]/*[@class='main_text']"));
			String view = lv.getText();
			view = view.split(" ")[0];
			int letter=1;
			if(view.contains("k")){
				view = view.substring(0, view.length()-1);
				letter = 1000;
			}
			if(view.contains("m")){
				view = view.substring(0, view.length()-1);
				letter = 1000000;
			}

			double d=0;
			try{
				System.out.println("view: "+view);
				d= Double.parseDouble(view);
				System.out.println("view: "+d);
			}catch(Exception e){e.printStackTrace();}

			views =(int) (d*letter);
		}catch(Exception e){System.err.println("views");}


		//about
		try{
			try{
				WebElement more = driver.findElement(By.xpath("//*[contains(@class,'UserDescriptionExpandable')]//a[@class='ui_qtext_more_link']"));
				more.click();
			}catch(Exception e){}
			WebElement abt = driver.findElement(By.xpath("//*[contains(@class,'UserDescriptionExpandable')]//*[@class='ui_qtext_expanded']"));
			clickInvisible(driver, abt);
			desc = abt.getText();
		}catch(Exception e){System.err.println("about");}

		int answers=0, questions=0, activity=0, posts=0, blogs=0, followers=0, following=0, topics=0, edits=0;

		WebElement stat = driver.findElement(By.xpath("//*[contains(@class,'ProfileNavList')]"));
		String numof ="";

		//answers
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'AnswersNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				answers = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("answers");}

		//questions
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'QuestionsNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				questions = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("questions");}

		//activity
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'ActivityNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				activity = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("activity");}

		//posts
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'PostsNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				posts = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("posts");}

		//blogs
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'BlogsNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				blogs = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("blogs");}

		//followers
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'FollowersNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				followers = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("followers");}

		//following
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'FollowingNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				following = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("following");}

		//topics
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'TopicsNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				topics = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("topics");}

		//edits
		try{
			WebElement item = stat.findElement(By.xpath(".//*[contains(@class,'OperationsNavItem')]//span"));
			numof = item.getText();
			try{
				if(numof.indexOf(",")!=-1)
					numof = numof.replaceAll(",", "");
				edits = Integer.parseInt(numof);
			}catch(Exception e){e.printStackTrace();}
		}catch(Exception e){System.err.println("edits");}


		p = new Profile(name,slogan, link, work, school, live, desc, views,
				answers, questions, activity, posts, blogs, followers, following, topics, edits);

		return p;
	}
















}
