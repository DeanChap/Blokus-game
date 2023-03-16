import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mainmenu extends JFrame implements ActionListener {

	private JPanel buttonPanel,topPanel;
	private JLabel background, header;
	private JButton start, resume, instructions;

	public Mainmenu() 
	{
		super("Let's Blokus!");
		setSize(500,600);
		getContentPane().setLayout(new BorderLayout());
		initiate();
			
		//House Keeping and Behavior
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	public void initiate() {
		
		topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());
		URL pic = Blokus.class.getResource("Data/Blokus.png");
		ImageIcon picIcon = new ImageIcon(pic);
		JLabel picLabel = new JLabel(picIcon);
		topPanel.setBackground(Color.BLACK);
		topPanel.add(picLabel, BorderLayout.CENTER);

		URL backgroundPic = Blokus.class.getResource("Data/blocks.png");
		ImageIcon backgroundIcon = new ImageIcon(backgroundPic);
		background = new JLabel(backgroundIcon);
		background.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setOpaque(false);
		
		start = new JButton("Start New Game");
		start.setBackground(Color.WHITE);
		start.addActionListener((ActionListener) this);

		resume = new JButton("Resume Previous Game");
		resume.setBackground(Color.WHITE);
		resume.addActionListener(this);


		instructions = new JButton("Game instructions");
		instructions.setBackground(Color.WHITE);
		instructions.addActionListener(this);


		buttonPanel.add(start);
		buttonPanel.add(resume);
		buttonPanel.add(instructions);
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		background.add(buttonPanel, BorderLayout.CENTER);
		getContentPane().add(background);
		pack();
		setVisible(true);
	} 
	
	public void actionPerformed(ActionEvent e) {
		//get clicked object in gui
		Object clicked = e.getSource();
		
		if(clicked.equals(start)) {
			new Settings();
		} 
		
		if(clicked.equals(resume)) {
			Blokus.load();
		} 

		if(clicked.equals(instructions)) {
			if (Desktop.isDesktopSupported()) {
			    try {
			        ClassLoader classLoader = getClass().getClassLoader();
			        File myFile = new File(classLoader.getResource("Data/Instructions.pdf").getFile());
			        Desktop.getDesktop().open(myFile);
			    }
			    
			    catch (IOException ex) {
			    //Control the exception
			    }
			}
		} 

	}
	

	public static void main(String [] args) 
	{
		new Mainmenu();
	}
}
