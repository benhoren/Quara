package Quara;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String text = "hamburger";
		String topic = "america, USA, food, money";
		int  num = 10;
		int minViews = 1000;
		
		play(text, topic, minViews, num);
	}
	
	public static void play(String text, String topic, int minViews, int num){
		quaraSearch qs = new quaraSearch();
		qs.start(text, topics(topic), minViews, num);
	}
	
	private static String[] topics(String topic){
		String[] topics = topic.split(",");
		for(int i=0; i<topics.length; i++)
			topics[i] = topics[i].trim();
		return topics;
		
	}

}
