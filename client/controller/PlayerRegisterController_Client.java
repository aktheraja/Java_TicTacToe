package exer5.client.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import exer5.client.SocketCommands;
import exer5.client.view.PlayerRegisterView;
import exer5.client.controller.MainController_Client;
public class PlayerRegisterController_Client extends WindowAdapter implements ActionListener, SocketCommands {

	protected PlayerRegisterView prWindow;
	protected MainController_Client mainWindowController;
	
	//INITIALIZER METHODS.
	/**
	 * Initializes the player registration window with the already instantiated 
	 * player registration window.
	 * @param prFrame
	 */
	public PlayerRegisterController_Client(PlayerRegisterView prFrame)
	{
		this.prWindow = prFrame;
	}
	
	/**
	 * Initializes the controller main window with the already instantiated
	 * main window controller.
	 * @param mainWindowController
	 */
	public void linkToMainController(MainController_Client mainWindowController)
	{
		this.mainWindowController=mainWindowController;
		mainWindowController.mainWindow.setEnabled(false); //make mainframe unclickable.
	}
	
	// EVENT-HANDLING METHODS.
	
	@Override
	public void windowClosing(WindowEvent event)
	{
		mainWindowController.mainWindow.setEnabled(true); //restore clickableness of mainframe.
		prWindow.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource()==prWindow.btnDoneRegisteringPlayer)
		{
			try
			{
				String playerType = prWindow.cbxPlayerType.getSelectedItem().toString();				
				String playerUsername = prWindow.playerInputField.getText();
				String s = prWindow.cbxPlayerMark.getSelectedItem().toString();
				String playerMark = String.valueOf(s.charAt(s.length() - 1)); //just get last character of the string.
				
				if (!playerUsername.equals(""))
				{
					if(playerMark.equals("X"))
					{
						mainWindowController.outputToGame.println(CHECK_XPLAYER_REGISTERED);
						String xPlayerStatus=mainWindowController.inputFromGame.readLine();
						
						if(!Boolean.parseBoolean(xPlayerStatus))
						{
							createPlayer(playerType, playerUsername, playerMark);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The Player Mark X has been registered.");
						}	
					}
					
					if(playerMark.equals("O"))
					{
						mainWindowController.outputToGame.println(CHECK_OPLAYER_REGISTERED);
						String oPlayerStatus=mainWindowController.inputFromGame.readLine();
						
						if(!Boolean.parseBoolean(oPlayerStatus))
						{
							createPlayer(playerType, playerUsername, playerMark);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "The Player Mark O has been registered.");
						}
					}
				}
				else
				{			
					JOptionPane.showMessageDialog(null, "Please enter a username.");
				}
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, "An error occured. Please try again");
			}
		}
	}	
	
	/**
	 * creates the player in the game (in the server side).
	 * @param playerType
	 * @param playerUsername
	 * @param playerMark
	 */
	private void createPlayer(String playerType, String playerUsername, String playerMark)
	{
		//create the players.
		String messageToGame = CREATE_PLAYER + "$$" + 
				playerUsername + "$$" + playerType + "$$" + playerMark;
		
		mainWindowController.outputToGame.println(messageToGame);
		
		mainWindowController.mainWindow.setEnabled(true); //restore clickableness of mainframe.
		
		mainWindowController.mainWindow.setTitle("Player " + playerMark + " Window");
		mainWindowController.mainWindow.setMarkOfMainWindow(playerMark);
		prWindow.dispose();
	}
}
