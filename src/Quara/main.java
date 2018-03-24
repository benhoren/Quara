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
		// TODO Auto-generated method stub

		String text = "computer";
		String topic = "";
		int  qnum = 3;
		int  anum = 3;
		int minViews = 10;
		int minUpvotes = 0;
		int minFollows = 0;

		play(text, topic,minFollows, minViews, minUpvotes, qnum, anum);
	}


	public static void play(String text, String topic, int minFollows, int minViews, int minUpvote, int qnum, int anum){

		readyFiles();
		
		quaraSearch qs = new quaraSearch();
		ArrayList<String> links =qs.start(text, topics(topic),minFollows, qnum);
		System.out.println();

		quoraQA qQA = new quoraQA();
		ArrayList<Question> questions = qQA.start(links, minViews, minUpvote, anum);
		
		for(int i=0; i<questions.size(); i++){
			Funcs.StringArrToLastRow(questions.get(i).toArray(), QuestionsSheet);
			for(int j=0; j<questions.get(i).answers.size(); j++){
				Funcs.StringArrToLastRow(questions.get(i).answers.get(j).toArray(), AnswersSheet);
			}
		}


		closeWriters();
	}

	private static String[] topics(String topic){
		String[] topics = topic.split(",");
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

	public static void startWriters(){
		workbook = new XSSFWorkbook();
		//{serialNum+"", link, question, stringTags(), views+"", followers+"", answersNum+""};

		QuestionsSheet = workbook.createSheet("Questions");
		 AnswersSheet = workbook.createSheet("Answers");

		String[] ques={"num.","Link","Question","Tags","Views","Followers","Number of answers"};
		Funcs.StringArrToLastRow(ques, QuestionsSheet);

		//questionNum+"", serialNum+"", name, slogan, date, orgQuestion, body, views+"", upvote+""};
		String[] answ={"question number","num.","name","slogan","date","original question", "answer","views","upvotes"};
		Funcs.StringArrToLastRow(answ, AnswersSheet);
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
