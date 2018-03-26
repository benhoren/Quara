package Quara;

import java.util.ArrayList;

public class Comment {
	
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
	
	public String[] toArr(){
		String[] arr = {questionNum+"", answerNum+"", serialNum+"", name, body};
		return arr;
		
	}

	public static void fixSerialNums(ArrayList<Comment> comments,
			int serialNumQ, int serialNumA) {
		
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
	
}
