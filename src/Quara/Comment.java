package Quara;

import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Comment extends Funcs implements excelData{

	static int counter = 1;

	int questionNum;
	int answerNum;
	int serialNum;
	String name;
	String body;

	public Comment(String name2, String body2) {
		questionNum = 0;
		answerNum = 0;
		serialNum = counter++;
		name = name2;
		body = body2;
	}



	public static void fixSerialNums(ArrayList<Comment> comments,
			int serialNumQ, int serialNumA) {

		if(comments ==null)
			return ;

		for(int i=0; i<comments.size(); i++){
			if(comments.get(i) == null){
				comments.remove(i);
				i--;
			}
			else{
				comments.get(i).questionNum = serialNumQ;
				comments.get(i).answerNum = serialNumA;
			}

		}


	}

	public static void toExcel(ArrayList<Comment> comments, XSSFSheet c) {
		if(comments == null)
			return;

		for(int i=0; i<comments.size(); i++){
			comments.get(i).toSheet(c);
		}
	}

	@Override
	public void toSheet(XSSFSheet sheet) {
		String[] arr = toArray();
		StringArrToLastRow(arr, sheet);	
	}


	@Override
	public String[] toArray(){
		String[] arr = {questionNum+"", answerNum+"", serialNum+"", name, body};
		return arr;
	}



	public static ArrayList<Comment> getComments(WebElement ansElement){

		ArrayList<Comment> cmmts = new ArrayList<Comment>();

		try{

			WebElement section = ansElement.findElement(By.xpath(".//*[contains(@class,'threaded_comments')]"));
			moveTo2(driver, section);
			sleep(1500);

			System.out.println("here");

			try{
				WebElement prv = section.findElement(By.xpath(".//*[@class='comment_snippet']"));
				moveTo2(driver, prv);
				sleep(1500);
				//				prv.click();
				clickInvisible(driver, prv);
				sleep(2500);

				System.err.println("prev");
			}catch(Exception e){System.err.println("prev fail ");}

			try{
				WebElement all = section.findElement(By.xpath(".//*[@class='toggle_link toggle_all']"));
				moveTo(driver, all);
				sleep(1500);
				clickInvisible(driver, all);
				//				all.click();
				sleep(2500);

				System.err.println("all");
			}catch(Exception e){System.err.println("no all ");}



			int tries = 0;
			for(int i=0; i<1000; i++){
				try{
					WebElement morecmm = section.findElement(By.xpath(".//*[contains(@class,'comments_more_button')]"));
					if(morecmm.getAttribute("class").contains("hidden"))
						break;

					moveTo2(driver, morecmm);
					sleep(1000);
					//					morecmm.click();
					clickInvisible(driver, morecmm);
					sleep(2000);

					System.err.println("more1");
				}catch(Exception e){tries++; if(tries==5) break;}
			}
			tries = 0;
		
			if(section == null)
				System.err.println("IS NULL!!");

			try{
				WebElement coll = section.findElement(By.xpath(".//*[contains(@class,'collapsed_comments_link')]"));
				moveTo2(driver, coll);
				sleep(1000);
				clickInvisible(driver, coll);
				//				coll.click();
				sleep(2000);

				System.err.println("collapse");
			}catch(Exception e){System.err.println("collapse fail");}

			tries = 0;
			int size = 0;
			for(int i=0; i<10; i++){
				System.out.println("round");
				try{
					
					ArrayList<WebElement>showchild = 
							(ArrayList<WebElement>) section.findElements(By.xpath(".//*[@class='show_child_link']"));
					if(size == showchild.size())
						break;
					size = showchild.size();
					for(WebElement child : showchild){
						moveTo(driver, child);
						sleep(1000);
						//					showchild.click();
						clickInvisible(driver, child);
						sleep(2000);
					}

					System.err.println("child");
				}catch(Exception e){tries++; if(tries==5) break;}
			}


			tries = 0;
			for(int i=0; i<1000; i++){
				try{
					WebElement expand = section.findElement(By.xpath(".//*[@class='ui_qtext_more_link']"));
					moveTo2(driver, expand);
					sleep(1000);
					expand.click();
					clickInvisible(driver, expand);
					sleep(2000);

					System.err.println("morecomm");
				}catch(Exception e){tries++; if(tries==5) break;}
			}


			try{
				getComments(cmmts, section);
			}catch(Exception e){e.printStackTrace(); return cmmts;}
		}catch(Exception e){e.printStackTrace(); return null;}

		return cmmts;
	}









	private static void getComments(ArrayList<Comment> comments, WebElement section) {

		System.out.println("COMMENTS");		
		Comment.counter = 1;

		try{
			ArrayList<WebElement> cmmts = (ArrayList<WebElement>) 
					section.findElements(By.xpath(".//*[contains(@id,'container_all')]//*[contains(@class,'comment_list_level_0')]//*[@class='comment_contents']"));

			System.out.println("web comment "+cmmts.size());

			Comment c=null;
			String name, body;
			for(int i=0; i<cmmts.size(); i++){
				name=""; body="";
				try{
					WebElement author = cmmts.get(i).findElement(By.className("author_name"));
					WebElement content = cmmts.get(i).findElement(By.xpath(".//span[@class='ui_qtext_rendered_qtext']"));
					clickInvisible(driver, content);
					sleep(2000);
					try{
						content = cmmts.get(i).findElement(By.xpath(".//*[contains(@id,'expanded_content')]"));
					}catch(Exception e){
						content = cmmts.get(i).findElements(By.xpath(".//div")).get(0);
						String id = content.getAttribute("id");
						WebElement we = driver.findElement(By.xpath("//*[@id='"+id+"']"));
						content = we.findElement(By.xpath(".//*[contains(@class,'expanded_content')]"));
					}

					name = author.getText();
					body += content.getText();
					
					c = new Comment(name, body);
					comments.add(c);
				}catch(Exception e){e.printStackTrace();}
			}

		}catch(Exception e){e.printStackTrace();}

	}




}
