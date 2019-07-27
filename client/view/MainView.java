package exer5.client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exer5.client.controller.MainController_Client;

import javax.swing.JButton;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import java.awt.Font;

public class MainView extends JFrame {
	
	public String markOfMainWindow;

	public JPanel mainpane;
	public JTextField usernameDisplay, currentPlayerDisplay;
	public JTextArea mainDisplay;
		
	public GameplayButton btnZeroZero, btnZeroOne, btnZeroTwo,
	btnOneZero, btnOneOne, btnOneTwo,
	btnTwoZero, btnTwoOne, btnTwoTwo;
	
	public JButton btnRegisterPlayer,btnRestartGame, btnStartGame;
	
	JLabel lblCurrentPlayer,lblUsername;

	/**
	 * Create the frame.
	 */
	public MainView() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 328);
		
		mainpane = new JPanel();
		mainpane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainpane);
		mainpane.setLayout(null);
		
		btnZeroZero = new GameplayButton("",0,0);
		btnZeroZero.setBounds(10, 11, 46, 34);
		mainpane.add(btnZeroZero);
		
		btnZeroOne = new GameplayButton("",0,1);
		btnZeroOne.setBounds(76, 11, 46, 34);
		mainpane.add(btnZeroOne);
		
		btnZeroTwo = new GameplayButton("",0,2);
		btnZeroTwo.setBounds(142, 11, 46, 34);
		mainpane.add(btnZeroTwo);
		
		btnOneZero = new GameplayButton("",1,0);
		btnOneZero.setBounds(10, 56, 46, 34);
		mainpane.add(btnOneZero);
		
		btnOneOne = new GameplayButton("",1,1);
		btnOneOne.setBounds(76, 56, 46, 34);
		mainpane.add(btnOneOne);
		
		btnOneTwo = new GameplayButton("",1,2);
		btnOneTwo.setBounds(142, 56, 46, 34);
		mainpane.add(btnOneTwo);
		
		btnTwoZero = new GameplayButton("",2,0);
		btnTwoZero.setBounds(10, 101, 46, 34);
		mainpane.add(btnTwoZero);
		
		btnTwoOne = new GameplayButton("",2,1);
		btnTwoOne.setBounds(76, 101, 46, 34);
		mainpane.add(btnTwoOne);
		
		btnTwoTwo = new GameplayButton("",2,2);
		btnTwoTwo.setBounds(142, 101, 46, 34);
		mainpane.add(btnTwoTwo);
		
		lblCurrentPlayer = new JLabel("Mark Type:");
		lblCurrentPlayer.setBounds(10, 210, 90, 14);
		mainpane.add(lblCurrentPlayer);
		
		currentPlayerDisplay = new JTextField();
		currentPlayerDisplay.setEditable(false);
		currentPlayerDisplay.setBounds(119, 207, 86, 20);
		mainpane.add(currentPlayerDisplay);
		currentPlayerDisplay.setColumns(10);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 181, 76, 14);
		mainpane.add(lblUsername);
		
		usernameDisplay = new JTextField();
		usernameDisplay.setEditable(false);
		usernameDisplay.setBounds(119, 178, 178, 20);
		mainpane.add(usernameDisplay);
		usernameDisplay.setColumns(10);
		
		btnRegisterPlayer = new JButton("Register players");
		btnRegisterPlayer.setBounds(229, 255, 178, 23);
		mainpane.add(btnRegisterPlayer);
		
		btnRestartGame = new JButton("Restart the game");
		btnRestartGame.setBounds(27, 255, 136, 23);
		mainpane.add(btnRestartGame);
		
		btnStartGame = new JButton("START GAME");
		btnStartGame.setBounds(307, 146, 117, 92);
		mainpane.add(btnStartGame);
		
		mainDisplay = new JTextArea();
		mainDisplay.setEditable(false);
		mainDisplay.setLineWrap(true);
		mainDisplay.setBounds(246, 11, 178, 124);
		mainpane.add(mainDisplay);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(3, 146, 287, 21);
		mainpane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(3, 238, 287, 17);
		mainpane.add(separator_1);
		
		JLabel lblCurrentPlayer_1 = new JLabel("Current Player");
		lblCurrentPlayer_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCurrentPlayer_1.setBounds(43, 153, 189, 20);
		mainpane.add(lblCurrentPlayer_1);
	
		this.setVisible(true);
	}
	
	public void linkAllSourcesToListener(MainController_Client mainframeListener)
	{
		btnZeroZero.addActionListener(mainframeListener);
		btnZeroOne.addActionListener(mainframeListener);
		btnZeroTwo.addActionListener(mainframeListener);
		btnOneZero.addActionListener(mainframeListener);
		btnOneOne.addActionListener(mainframeListener);
		btnOneTwo.addActionListener(mainframeListener);
		btnTwoZero.addActionListener(mainframeListener);
		btnTwoOne.addActionListener(mainframeListener);
		btnTwoTwo.addActionListener(mainframeListener);
		
		btnRegisterPlayer.addActionListener(mainframeListener);
		btnRestartGame.addActionListener(mainframeListener);
		btnStartGame.addActionListener(mainframeListener);
	}
	
	// GETTERs and SETTERs.
	
	public String getMarkOfMainWindow() 
	{
		return markOfMainWindow;
	}

	public void setMarkOfMainWindow(String playerMark) 
	{
		this.markOfMainWindow = playerMark;
	}
}
