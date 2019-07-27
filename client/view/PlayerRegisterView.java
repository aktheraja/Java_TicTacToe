package exer5.client.view;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exer5.client.Constants;
import exer5.client.controller.PlayerRegisterController_Client;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class PlayerRegisterView extends JFrame implements Constants {
	
	public JPanel contentPane;
	public JLabel lblUsername, lblPlayerMark, lblPlayerType;
	public JTextField playerInputField;
	public JButton btnDoneRegisteringPlayer;
	public JComboBox<String> cbxPlayerType, cbxPlayerMark;
	
	/**
	 * Create the frame.
	 */
	public PlayerRegisterView() 
	{	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // custom close operation.
		setBounds(100, 100, 672, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		playerInputField = new JTextField();
		playerInputField.setBounds(385, 52, 206, 20);
		contentPane.add(playerInputField);
		playerInputField.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
		lblUsername.setBounds(440, 11, 79, 20);
		contentPane.add(lblUsername);
		
		btnDoneRegisteringPlayer = new JButton("Done");
		btnDoneRegisteringPlayer.setBounds(258, 95, 172, 23);
		contentPane.add(btnDoneRegisteringPlayer);
		
		cbxPlayerType = new JComboBox<String>();
		cbxPlayerType.setBounds(196, 52, 158, 20);
		contentPane.add(cbxPlayerType);
		cbxPlayerType.addItem(HUMAN_PLAYER);
		cbxPlayerType.addItem(RANDOM_PLAYER);
		cbxPlayerType.addItem(BLOCKING_PLAYER);
		cbxPlayerType.addItem(SMART_PLAYER);
		
		cbxPlayerMark = new JComboBox<String>();
		cbxPlayerMark.setBounds(28, 52, 158, 20);
		contentPane.add(cbxPlayerMark);
		cbxPlayerMark.addItem("Player "+String.valueOf(LETTER_O));
		cbxPlayerMark.addItem("Player "+String.valueOf(LETTER_X));
		
		lblPlayerMark = new JLabel("Player Mark Type");
		lblPlayerMark.setFont(new Font("Arial", Font.BOLD, 14));
		lblPlayerMark.setBounds(46, 21, 158, 20);
		contentPane.add(lblPlayerMark);
		
		lblPlayerType = new JLabel("Player Type");
		lblPlayerType.setFont(new Font("Arial", Font.BOLD, 14));
		lblPlayerType.setBounds(229, 21, 100, 20);
		contentPane.add(lblPlayerType);
		
		this.setVisible(true);
	}
	
	public void linkAllSourcesToController(PlayerRegisterController_Client prController)
	{
		btnDoneRegisteringPlayer.addActionListener(prController);
		this.addWindowListener(prController);
	}
}
