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

public class Options extends JFrame implements ActionListener {

	private JPanel buttonPanel, topPanel, buttonTop, buttonBottom, colorPanel, choicePanel, savePanel, exitPanel;
	private JLabel background, colorLabel;
	private JButton exit, standard, alternative, save;
	private static boolean isColorDef;

	public Options() 
	{
		super("Options");
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
		
		colorPanel = new JPanel();
		colorPanel.setLayout(new FlowLayout());
		colorPanel.setOpaque(false);
		colorPanel.setBackground(Color.BLACK);
		
		choicePanel = new JPanel();
		choicePanel.setLayout(new FlowLayout());
		choicePanel.setOpaque(false);
		choicePanel.setBackground(Color.BLACK);

		savePanel = new JPanel();
		savePanel.setLayout(new FlowLayout());
		savePanel.setOpaque(false);
		savePanel.setBackground(Color.BLACK);

		exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout());
		exitPanel.setOpaque(false);
		exitPanel.setBackground(Color.BLACK);

		colorLabel = new JLabel();
		colorLabel.setForeground(Color.WHITE);
		colorLabel.setText("Please choose color options");
			      
		standard = new JButton("Standard");
		standard.setBackground(Color.WHITE);
		standard.addActionListener((ActionListener) this);

		alternative = new JButton("Alternative");
		alternative.setBackground(Color.WHITE);
		alternative.addActionListener(this);

		save = new JButton("Save game");
		save.setBackground(Color.WHITE);
		save.addActionListener(this);
		
		exit = new JButton("Exit");
		exit.setBackground(Color.WHITE);
		exit.addActionListener(this);
		
		colorPanel.add(colorLabel);
		
		choicePanel.add(standard);
		choicePanel.add(alternative);
		
		savePanel.add(save);
		
		exitPanel.add(exit);
		
		buttonTop.add(colorPanel, BorderLayout.NORTH);
		buttonTop.add(choicePanel, BorderLayout.SOUTH);
		
		buttonBottom.add(savePanel, BorderLayout.NORTH);
		buttonBottom.add(exitPanel, BorderLayout.SOUTH);
		
		buttonPanel.add(buttonTop, BorderLayout.NORTH);
		buttonPanel.add(buttonBottom, BorderLayout.SOUTH);
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	} 
	
	public static boolean getColorDef() {
		return isColorDef;
	}
	
	public void setColorDef() {
		isColorDef = true;
	}
	public void actionPerformed(ActionEvent e) {
		//get clicked object in gui
		Object clicked = e.getSource();
		
		if(clicked.equals(standard)) {
			isColorDef = false;
			JOptionPane.showMessageDialog(null, "Standard colors chosen", "Color Options", JOptionPane.INFORMATION_MESSAGE);

		} 
		
		if(clicked.equals(alternative)) {
			setColorDef();
			JOptionPane.showMessageDialog(null, "Alternative colors chosen", "Color Options", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(save)) {
			new Blokus().save();
			JOptionPane.showMessageDialog(null, "Game saved", "Options", JOptionPane.INFORMATION_MESSAGE);
		} 
		
		if(clicked.equals(exit)) {
			setVisible(false);
		}
	}
}

