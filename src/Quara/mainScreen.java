package Quara;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class mainScreen {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private  JTextField textField_6;

	private static JButton startbtn = null;
	private static JTextArea log  = null;

	public static void addToLog(String message){
		if(log != null){

			String past = log.getText();
			String nm="";
			if(past == null || past.isEmpty())
				nm = message;
			else nm = message + '\n' + past;

			log.setText(nm);

			if(message.contains("Done"))
				startbtn.setEnabled(true);	

			if(message.contains("error"))
				startbtn.setEnabled(true);	
		}
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainScreen window = new mainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 685, 415);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(90dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(99dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(100dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(31dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));

		JTextPane txtpnText = new JTextPane();
		txtpnText.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnText.setEditable(false);
		txtpnText.setText("text");
		frame.getContentPane().add(txtpnText, "2, 2, right, center");

		textField = new JTextField();
		frame.getContentPane().add(textField, "4, 2, 3, 1, fill, default");
		textField.setColumns(10);

		JTextPane txtpnNumberOfQuestions = new JTextPane();
		txtpnNumberOfQuestions.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnNumberOfQuestions.setEditable(false);
		txtpnNumberOfQuestions.setText("number of questions");
		frame.getContentPane().add(txtpnNumberOfQuestions, "2, 6, right, center");

		textField_1 = new JTextField();
		frame.getContentPane().add(textField_1, "4, 6, fill, default");
		textField_1.setColumns(10);

		JTextPane txtpnMinimumFollows = new JTextPane();
		txtpnMinimumFollows.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnMinimumFollows.setEditable(false);
		txtpnMinimumFollows.setText("minimum follows");
		frame.getContentPane().add(txtpnMinimumFollows, "6, 6, right, center");

		textField_3 = new JTextField();
		frame.getContentPane().add(textField_3, "8, 6, fill, default");
		textField_3.setColumns(10);

		JTextPane txtpnNumberOfAnswers = new JTextPane();
		txtpnNumberOfAnswers.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnNumberOfAnswers.setEditable(false);
		txtpnNumberOfAnswers.setText("number of answers");
		frame.getContentPane().add(txtpnNumberOfAnswers, "2, 8, right, center");

		textField_2 = new JTextField();
		frame.getContentPane().add(textField_2, "4, 8, fill, default");
		textField_2.setColumns(10);

		JTextPane txtpnMinimumUpvotes = new JTextPane();
		txtpnMinimumUpvotes.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnMinimumUpvotes.setEditable(false);
		txtpnMinimumUpvotes.setText("minimum upvotes");
		frame.getContentPane().add(txtpnMinimumUpvotes, "6, 8, right, center");

		textField_4 = new JTextField();
		frame.getContentPane().add(textField_4, "8, 8, fill, default");
		textField_4.setColumns(10);

		JTextPane txtpnMinimumViews = new JTextPane();
		txtpnMinimumViews.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnMinimumViews.setEditable(false);
		txtpnMinimumViews.setText("minimum views");
		frame.getContentPane().add(txtpnMinimumViews, "6, 10, right, center");

		textField_5 = new JTextField();
		frame.getContentPane().add(textField_5, "8, 10, fill, default");
		textField_5.setColumns(10);

		JTextPane txtpnTopics = new JTextPane();
		txtpnTopics.setFont(new Font("Arial", Font.PLAIN, 13));
		txtpnTopics.setEditable(false);
		txtpnTopics.setText("topics");
		frame.getContentPane().add(txtpnTopics, "2, 4, right, center");

		textField_6 = new JTextField();
		frame.getContentPane().add(textField_6, "4, 4, 3, 1, fill, default");
		textField_6.setColumns(10);

		startbtn = new JButton("start");
		startbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt="", topics="", mfll, mvws, mupv, nques, nansw;
				txt = textField.getText();
				nques = textField_1.getText();
				nansw = textField_2.getText();
				mfll = textField_3.getText();
				mupv = textField_4.getText();
				mvws = textField_5.getText();
				topics = textField_6.getText();

				int ques, ans, fll, upv, vws;
				try{
					if(nques == null || nques.isEmpty())
						nques = "1";
					ques = Integer.parseInt(nques);
				}catch(Exception e){addToLog("Error: invaild num. of questions"); return;}
				
				try{
					if(nansw == null || nansw.isEmpty())
						nansw = "1";
					ans = Integer.parseInt(nansw);
				}catch(Exception e){addToLog("Error: invaild num. of answers"); return;}
				
				try{
					if(mfll == null || mfll.isEmpty())
						mfll = "0";
					fll = Integer.parseInt(mfll);
				}catch(Exception e){addToLog("Error: invaild num. of follows"); return;}
				
				try{
					if(mupv == null || mupv.isEmpty())
						mupv = "0";
					upv = Integer.parseInt(mupv);
				}catch(Exception e){addToLog("Error: invaild num. of upvotese"); return;}
				
				try{
					if(mvws == null || mvws.isEmpty())
						mvws = "0";
					vws = Integer.parseInt(mvws);
				}catch(Exception e){addToLog("Error: invaild num. of views"); return;}
				
				Main.start(txt, topics, fll, vws, upv, ques, ans);

			}
		});

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "4, 12, 3, 9, fill, fill");

		log = new JTextArea();
		scrollPane.setViewportView(log);
		frame.getContentPane().add(startbtn, "8, 12");

		JTextPane textPane = new JTextPane();
		frame.getContentPane().add(textPane, "24, 20, fill, fill");
	}


}
