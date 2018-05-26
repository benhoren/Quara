package Quara;

public class intppt extends Thread{
	
	public static Thread mainth;
	public static boolean flag = false;
	
	public intppt(String text, String topic, int minFollows, int minViews,
			int minUpvotes, int qnum, int anum, String mail, String password) {
		this.text = text;
		this.topic= topic;
		this.minFollows = minFollows;
		this.minViews = minViews;
		this.minUpvotes = minUpvotes;
		this.qnum = qnum;
		this.anum = anum;
		this.mail = mail;
		this.password = password;
				
		
	}
	String text; String topic; int minFollows; int minViews;
	int minUpvotes; int qnum; int anum; String mail; String password;
	
	

	public void run(){
		System.out.println("start tr1");
		
		try {
			Thread.currentThread();
			Thread.sleep(30*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!flag){
			System.out.println("kill");
			mainth.stop();
			
			//public static void start(String text, String topic, int minFollows, int minViews, int minUpvotes, int qnum, int anum)
			Main.start(text, topic, minFollows, minUpvotes, minViews, qnum ,anum, mail, password);
		}
		System.out.println("exit");
		
		
		
	}

}
