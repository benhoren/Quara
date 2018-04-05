package Quara;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class quoraQA extends Funcs{


	public ArrayList<Question> start(ArrayList<String> links, int minViews, int minUpvote, int num){
		if(links == null)
			return null;
		
		ArrayList<Question> questions = new ArrayList<Question>();
		Question q;
		for(String link: links){
			q = questionHandle(link, num, minViews, minUpvote);

			if(q!=null)
				questions.add(q);
		}
		
		getProfiles(questions);
		
		return questions;
	}

	private void getProfiles(ArrayList<Question> questions) {
		String link; Profile p;
		for(int i=0; i<questions.size(); i++){
			for(int j=0; j<questions.get(i).answersNum; j++){
				link = "";
				p=null;
				link = questions.get(i).answers.get(j).link;
				
				try{
					p = Profile.getProfile(link);
				}catch(Exception e){e.printStackTrace();}
				
				if(p!=null){
					p.questionNum = questions.get(i).answers.get(j).questionNum;
					p.answerNum = questions.get(i).answers.get(j).serialNum;
					questions.get(i).answers.get(j).profile = p;
					System.out.println(Arrays.toString(p.toArray()));
				}	
				else System.out.println("NULL");
			}
		}
		
	}

	public Question questionHandle(String link,int num, int minViews, int minUpvote){
		Question question = new Question(link);
		driver.navigate().to(link);
		Question.getQuestionStat(question);

		getQuestionAnswers(question,num, minViews, minUpvote);

		return question;
	}


	final int maxSearch = 10000;
	public void getQuestionAnswers(Question question, int num, int minViews, int minUpvotes){
		ArrayList<Answer> Answers = new ArrayList<Answer>();

		ArrayList <WebElement> results = (ArrayList <WebElement>)
				driver.findElements(By.xpath("//*[@class='paged_list_wrapper']/*[@class='pagedlist_item']/div/div"));

		sleep(3000);
		int i=0;
		int r = 0;
		int found = 0;
		int tries = 0;
		int answerCount = 0;
		Answer answer = null;
		while(found < num){
			if(r>=maxSearch)
				break;
			r++;
			answer = null;


			if(i >= results.size()){
				int size = results.size();
				moveTo2(driver, results.get(size-1));
				try{
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					jse.executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
				}catch(Exception e) {}

				sleep(5000);
				results = (ArrayList <WebElement>)
						driver.findElements(By.xpath("//*[@class='paged_list_wrapper']/*[@class='pagedlist_item']/div/div"));

				if(size == results.size())
					tries++;
				else tries = 0;

				if(tries > 10){
					System.err.println("end of results");
					break;
				}
			}

			if(i<results.size()){
				boolean addAns = false;
				answer = Answer.getAnswer(results.get(i), question.serialNum);

				if(answer!=null){
					answerCount++;
					if(answer.upvote >= minUpvotes && answer.views >= minViews)
						addAns = true;
				}
				i++;

				if(addAns){
					found++;
					answer.serialNum = found;
					Comment.fixSerialNums(answer.comments,question.serialNum,answer.serialNum);
					System.out.println("Question "+question.serialNum+" answer "+found);
					Answers.add(answer);
				}
			}	
		}
		question.answersNum = found;
		question.answers = Answers;
	}














}
