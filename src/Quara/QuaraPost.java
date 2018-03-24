package Quara;

import java.util.ArrayList;

public class QuaraPost {
	static int counter = 1;
	
	int serial;
	String question;
	String qalink;
	String tags;
	String by;
	String prlink;
	Profile profile;
	String answer;
	int upvotes;
	int downvotes;
	int views;
	ArrayList<Comment> comments;
	
	public QuaraPost(String question, String qalink, String by, String prlink, String answer,
		int up, int down, int view){
		serial = counter++;
		this.question = question;
		
	}
	
	
}
