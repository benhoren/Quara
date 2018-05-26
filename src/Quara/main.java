package Quara;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main {

	static final String fileName = "excelFile";
	static String folderName ="output";

	public static void main(String[] args) {

		String text = "travel";
		String topic = "";
		int  qnum = 10;
		int  anum = 100;       
		int minViews = 100;     //per answer
		int minUpvotes = 100;   //per answer
		int minFollows = 100; //per question
		
		

		start(text, topic, minFollows, minViews, minUpvotes, qnum, anum, "quara.java@gmail.com", "b1112222");
	}

	public static void start(String text, String topic, int minFollows, int minViews, int minUpvotes, int qnum, int anum, String mail, String password){

		mainScreen.addToLog("start..");
		
		if(text == null || text.isEmpty()){
			mainScreen.addToLog("error: invaild text.");
			return;
		}
		

//		intppt.mainth = Thread.currentThread();
//		intppt tr = new intppt(text, topic, minFollows,minViews,minUpvotes,qnum, anum, mail, password);
//		tr.start();

		play(text, topic, minFollows, minViews, minUpvotes, qnum, anum, mail, password);
	}


	/**
	 * 
	 * @param text text to search
	 * @param topic topics of the question
	 * @param minFollows minimum follows on the question
	 * @param minViews minimum views on answer
	 * @param minUpvote minimum upvotes on answer
	 * @param qnum number of questions
	 * @param anum number of answers per question
	 */
	public static void play(String text, String topic, int minFollows, int minViews, int minUpvote, int qnum, int anum, String mail, String password){

		System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
		
		
		quaraSearch qs = new quaraSearch(mail, password);
		ArrayList<String> links = null;
		try{
			links = qs.start(text, topics(topic),minFollows, qnum);
		}catch(Exception e){e.printStackTrace(); mainScreen.addToLog("");}
		System.out.println();

		if(links == null || links.size() == 0){
			System.err.println("NO RESULTS");
			 qs = new quaraSearch(mail, password);
			links = null;
			try{
				links = qs.start(text, topics(topic),minFollows, qnum);
			}catch(Exception e){e.printStackTrace(); mainScreen.addToLog("error");}
			System.out.println();

			
			if(links == null || links.size() == 0){
				System.err.println("NO RESULTS");
				return;
			}

			
		}

		mainScreen.addToLog(links.size()+" questions found");
		quoraQA qQA = new quoraQA();
		ArrayList<Question> questions = qQA.start(links, minViews, minUpvote, anum);

		readyFiles();

		Question.toExcel(questions, QuestionsSheet, AnswersSheet, ProfileSheet, CommentsSheet, true);

		//		for(int i=0; i<questions.size(); i++){
		//			Funcs.StringArrToLastRow(questions.get(i).toArray(), QuestionsSheet);
		//
		//			for(int j=0; j<questions.get(i).answers.size(); j++){
		//				Funcs.StringArrToLastRow(questions.get(i).answers.get(j).toArray(), AnswersSheet);
		//				try{
		//					for(int k=0; k<questions.get(i).answers.get(j).comments.size(); k++){
		//						Funcs.StringArrToLastRow(questions.get(i).answers.get(j).comments.get(k).toArr(), CommentsSheet);
		//				
		//					}
		//				}catch(Exception e){System.err.println(e);}
		//			}
		//	}


		closeWriters();
		
		mainScreen.addToLog("Done.");
		
	}

	private static String[] topics(String topic){
		String[] topics = topic.trim().split(",");
		for(int i=0; i<topics.length; i++)
			topics[i] = topics[i].trim();
		return topics;

	}


	private static void readyFiles(){
		boolean ok =false;
		int i=1;
		File directory = new File(folderName);
		if (! directory.exists()){
			directory.mkdir();
			ok=true;
		}
		while(!ok){
			directory = new File(folderName+"-"+i);
			if (! directory.exists()){
				directory.mkdir();
				ok=true;
				folderName = folderName+"-"+i;
			}
			i++;
		}

		startWriters();

		directory = new File(folderName+"/"+fileName+".xlsx");
		if (!directory.exists()){
			try {
				outputStream = new FileOutputStream(folderName+"/"+fileName+".xlsx");
			} catch (FileNotFoundException e) {}
		}

		else{
			ok = false;
			int j=1;
			while(!ok){
				directory = new File(folderName+"/"+fileName+"-"+j+".xlsx");
				if (!directory.exists()){
					try {
						outputStream = new FileOutputStream(folderName+"/"+fileName+"-"+j+".xlsx");
						ok =true;
					} catch (FileNotFoundException e) {}
				}
				j++;
			}
		}
	}

	static XSSFWorkbook workbook;
	static FileOutputStream outputStream;
	static XSSFSheet QuestionsSheet;
	static XSSFSheet AnswersSheet;
	static XSSFSheet ProfileSheet;
	static XSSFSheet CommentsSheet;

	public static void startWriters(){
		workbook = new XSSFWorkbook();

		QuestionsSheet = workbook.createSheet("Questions");
		AnswersSheet = workbook.createSheet("Answers");
		ProfileSheet = workbook.createSheet("Profiles");
		CommentsSheet = workbook.createSheet("Comments");

		//{serialNum+"", link, question, stringTags(), views+"", followers+"", answersNum+""};
		String[] ques={"num.","Link","Question","Tags","Views","Followers","Number of answers", "answers"};
		Funcs.StringArrToLastRow(ques, QuestionsSheet);

		//questionNum+"", serialNum+"", name, slogan, date, orgQuestion, body, views+"", upvote+""};
		String[] answ={"question number","num.","name","slogan","date",/*"original question",*/ "answer","views","upvotes", "comments"};
		Funcs.StringArrToLastRow(answ, AnswersSheet);


		//{""+questionNum, ""+answerNum, name, slogan, link, work, study, live, about, ""+views,
		//""+answers, ""+questions, ""+activity, ""+posts, ""+blogs, ""+followers, ""+following, ""+topics,""+ edits};
		String[] prfls={"question num","answer num.","link","name","slogan","work","live", "study","about","views"
				,"answers" ,"questions" /*,"activity"*/ ,"posts" ,"blogs" ,"followers" ,"following" ,"topics" ,"edits"};
		Funcs.StringArrToLastRow(prfls, ProfileSheet);

		//{questionNum+"", answerNum+"", serialNum+"", name, body};
		String[] cmmt={"question number","answer num.","num.","name","body"};
		Funcs.StringArrToLastRow(cmmt, CommentsSheet);
	}



	public static void closeWriters(){
		try {
			workbook.write(outputStream);
			outputStream.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}








}
