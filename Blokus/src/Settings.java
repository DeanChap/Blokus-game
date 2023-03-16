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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Settings extends JFrame implements ActionListener {

	private JPanel buttonPanel, topPanel, buttonTop, buttonBottom, humanPanel, cpuPanel, difficultyPanel, startPanel;
	private JLabel background, humanLabel, cpuLabel, difficultyLabel;
	private JButton start, easy, medium, hard, human1, human2, human3, human4, cpu1, cpu2, cpu3, exit;

	public Settings() 
	{
		super("Initiate");
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
		JLabel background = new JLabel(picIcon);
		topPanel.setBackground(Color.BLACK);
		topPanel.add(background, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(Color.BLACK);
		
		buttonTop = new JPanel();
		buttonTop.setLayout(new BorderLayout());
		buttonTop.setOpaque(false);
		buttonTop.setBackground(Color.BLACK);

		buttonBottom = new JPanel();
		buttonBottom.setLayout(new BorderLayout());
		buttonBottom.setOpaque(false);
		buttonBottom.setBackground(Color.BLACK);
		
		humanPanel = new JPanel();
		humanPanel.setLayout(new FlowLayout());
		humanPanel.setOpaque(false);
		humanPanel.setBackground(Color.BLACK);
		
		cpuPanel = new JPanel();
		cpuPanel.setLayout(new FlowLayout());
		cpuPanel.setOpaque(false);
		cpuPanel.setBackground(Color.BLACK);

		difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new FlowLayout());
		difficultyPanel.setOpaque(false);
		difficultyPanel.setBackground(Color.BLACK);

		startPanel = new JPanel();
		startPanel.setLayout(new FlowLayout());
		startPanel.setOpaque(false);
		startPanel.setBackground(Color.BLACK);

		humanLabel = new JLabel();
		humanLabel.setForeground(Color.WHITE);
		humanLabel.setText("Please enter number of human players:");
		
		cpuLabel = new JLabel();
		cpuLabel.setForeground(Color.WHITE);
		cpuLabel.setText("Please enter number of computer players:");

		difficultyLabel = new JLabel();
		difficultyLabel.setForeground(Color.WHITE);
		difficultyLabel.setText("Please choose level of difficulty");

		
		easy = new JButton("Easy");
		easy.setBackground(Color.WHITE);
		easy.addActionListener((ActionListener) this);

		medium = new JButton("Medium");
		medium.setBackground(Color.WHITE);
		medium.addActionListener(this);


		hard = new JButton("Hard");
		hard.setBackground(Color.WHITE);
		hard.addActionListener(this);
		
		human1 = new JButton("1");
		human1.setBackground(Color.WHITE);
		human1.addActionListener((ActionListener) this);

		human2 = new JButton("2");
		human2.setBackground(Color.WHITE);
		human2.addActionListener(this);


		human3 = new JButton("3");
		human3.setBackground(Color.WHITE);
		human3.addActionListener(this);
		
		human4 = new JButton("4");
		human4.setBackground(Color.WHITE);
		human4.addActionListener(this);

		cpu1 = new JButton("1");
		cpu1.setBackground(Color.WHITE);
		cpu1.addActionListener((ActionListener) this);

		cpu2 = new JButton("2");
		cpu2.setBackground(Color.WHITE);
		cpu2.addActionListener(this);


		cpu3 = new JButton("3");
		cpu3.setBackground(Color.WHITE);
		cpu3.addActionListener(this);
		
		start = new JButton("Start");
		start.setBackground(Color.WHITE);
		start.addActionListener(this);
		
		exit = new JButton("Exit");
		exit.setBackground(Color.WHITE);
		exit.addActionListener(this);
		
		humanPanel.add(humanLabel);
		humanPanel.add(human1);
		humanPanel.add(human2);
		humanPanel.add(human3);
		humanPanel.add(human4);

		cpuPanel.add(cpuLabel);
		cpuPanel.add(cpu1);
		cpuPanel.add(cpu2);
		cpuPanel.add(cpu3);
		
		difficultyPanel.add(difficultyLabel);
		difficultyPanel.add(easy);
		difficultyPanel.add(medium);
		difficultyPanel.add(hard);
		
		startPanel.add(start);
		startPanel.add(exit);
		
		buttonTop.add(humanPanel, BorderLayout.NORTH);
		buttonTop.add(cpuPanel, BorderLayout.SOUTH);
		
		buttonBottom.add(difficultyPanel, BorderLayout.NORTH);
		buttonBottom.add(startPanel, BorderLayout.SOUTH);
		
		buttonPanel.add(buttonTop, BorderLayout.NORTH);
		buttonPanel.add(buttonBottom, BorderLayout.SOUTH);
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	} 
	
	public void actionPerformed(ActionEvent e) {
		//get clicked object in gui
		Object clicked = e.getSource();
		
		if(clicked.equals(easy)) {
			JOptionPane.showMessageDialog(null, "Easy level chosen", "Level of Difficulty", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(medium)) {
			JOptionPane.showMessageDialog(null, "Medium level chosen", "Level of Difficulty", JOptionPane.INFORMATION_MESSAGE);

		} 
		
		if(clicked.equals(hard)) {
			JOptionPane.showMessageDialog(null, "Hard level chosen", "Level of Difficulty", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(clicked.equals(human1)) {
			JOptionPane.showMessageDialog(null, "1 human player chosen", "Number of Human Players", JOptionPane.INFORMATION_MESSAGE);

		} 
		
		if(clicked.equals(human2)) {
			JOptionPane.showMessageDialog(null, "2 human players chosen", "Number of Human Players", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(human3)) {
			JOptionPane.showMessageDialog(null, "3 human players chosen", "Number of Human Players", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(human4)) {
			JOptionPane.showMessageDialog(null, "4 human players chosen", "Number of Human Players", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(cpu1)) {
			JOptionPane.showMessageDialog(null, "1 computer player chosen", "Number of Computer Players", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(cpu2)) {
			JOptionPane.showMessageDialog(null, "2 computer players chosen", "Number of Computer Players", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(cpu3)) {
			JOptionPane.showMessageDialog(null, "3 computer players chosen", "Number of Computer Players", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(start)) {
			new Blokus();
			setVisible(false);
		}
		
		if(clicked.equals(exit)) {
			setVisible(false);
		}

	}
}
