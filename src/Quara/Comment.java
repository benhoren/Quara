package Quara;

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
	
}
