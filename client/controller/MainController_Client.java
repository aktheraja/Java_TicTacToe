package exer5.client.controller;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import exer5.client.SocketCommands;
import exer5.client.controller.PlayerRegisterController_Client;
import exer5.client.view.GameplayButton;
import exer5.client.view.MainView;
import exer5.client.view.PlayerRegisterView;

public class MainController_Client implements ActionListener, SocketCommands, Runnable{

	// ATTRIBUTES.
	
	public MainView mainWindow;
	
	public BufferedReader inputFromGame;
	public BufferedReader broadcastFromGame;
	public PrintWriter outputToGame;


	//INITIALIZER METHODS.
	/**
	 * Instantiate the controller for the main window of the GUI.
	 * @param mainWindow
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public MainController_Client(MainView mainWindow, Socket socketForGame) throws IOException, InterruptedException
	{
		
		inputFromGame = new BufferedReader(new InputStreamReader(socketForGame.getInputStream()));
		outputToGame = new PrintWriter(socketForGame.getOutputStream(), true);
		this.mainWindow = mainWindow;
		
		System.out.print(new Timestamp(System.currentTimeMillis()));
		System.out.println(" Connection requested.");
	}
	
	//OPERATIONAL METHODS.
	
	/**
	 * Establish connection to the broadcaster in the server side (from the game controller).
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void connectToGameBroadcaster(Socket socketForBroadcastFromGame) throws IOException, InterruptedException 
	{
		broadcastFromGame =  new BufferedReader(new InputStreamReader(socketForBroadcastFromGame.getInputStream()));
		System.out.print(new Timestamp(System.currentTimeMillis()));
		System.out.println(" Client connected to broadcaster");
	}
	
	// THREAD METHODS.
	
	/**
	 * THis run method will run in a thread that will allow it to perpetually 
	 * listen to the broadcasts from the game controller (in the server side).
	 */
	@Override
	public void run() 
	{
		try 
		{
			while(true)
			{
				if (this.broadcastFromGame!=null && this.broadcastFromGame.ready())
				{
					this.listenToBraodcastFromGame();
				}
			} 

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

	// HANDLING OF BROADCASTS FROM GAME CONTROLLER TO ALL CLIENTS.

	/**
	 * Handles all broadcasts from the game controller.
	 * @throws IOException
	 */
	synchronized public void listenToBraodcastFromGame() throws IOException
	{
		String broadcastedMsgFromGame;
		if (broadcastFromGame.ready())
		{
			broadcastedMsgFromGame = broadcastFromGame.readLine();
//			System.out.println(broadcastedMsgFromGame + " Line 71" );

			if (broadcastedMsgFromGame.contains(BROADCAST__DISPLAY_PLAY))
			{
				String [] s = broadcastedMsgFromGame.split(Pattern.quote("$$"));

				int row = Integer.parseInt(s[1]);
				int col = Integer.parseInt(s[2]);
				String playerMark= s[3];

				if (row==0)
				{
					if(col==0)
					{
						mainWindow.btnZeroZero.setText(playerMark);
					}
					if(col==1)
					{
						mainWindow.btnZeroOne.setText(playerMark);
					}
					if(col==2)
					{
						mainWindow.btnZeroTwo.setText(playerMark);
					}
				}
				else if (row==1)
				{
					if(col==0)
					{
						mainWindow.btnOneZero.setText(playerMark);
					}
					if(col==1)
					{
						mainWindow.btnOneOne.setText(playerMark);
					}
					if(col==2)
					{
						mainWindow.btnOneTwo.setText(playerMark);
					}
				}
				else if(row==2)
				{
					if(col==0)
					{
						mainWindow.btnTwoZero.setText(playerMark);
					}
					if(col==1)
					{
						mainWindow.btnTwoOne.setText(playerMark);
					}
					if(col==2)
					{
						mainWindow.btnTwoTwo.setText(playerMark);
					}
				}
			}

			if (broadcastedMsgFromGame.contains(BROADCAST__DISPLAY_CURRENT_PLAYER))
			{
				String [] s = broadcastedMsgFromGame.split(Pattern.quote("$$"));
				String playerName=s[1];
				String playerMark=s[2];
				mainWindow.usernameDisplay.setText(playerName);
				mainWindow.currentPlayerDisplay.setText(playerMark+" player");
			}
			
			if(broadcastedMsgFromGame.contains(BROADCAST__CLEAR_EVERYTHING))
			{
				this.clearAllCells();
				this.clearCurrentPlayerDisplay();
				this.clearUsernameDisplay();
				this.clearMainDisplay();
			}
			
			if(broadcastedMsgFromGame.contains(BROADCAST__GAME_RESULT))
			{
				String [] s = broadcastedMsgFromGame.split(Pattern.quote("$$"));
				
				String gameResult = s[1].replaceAll(Pattern.quote("**"),"\n");
				mainWindow.mainDisplay.setText(gameResult);
			}
		}
	}

	// EVENT-HANDLING METHODS.
	/**
	 * Handles all GUI events.
	 */

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (event.getSource()==mainWindow.btnZeroZero ||
				event.getSource()==mainWindow.btnZeroOne ||
				event.getSource()==mainWindow.btnZeroTwo ||
				event.getSource()==mainWindow.btnOneZero ||
				event.getSource()==mainWindow.btnOneOne ||
				event.getSource()==mainWindow.btnOneTwo ||
				event.getSource()==mainWindow.btnTwoZero ||
				event.getSource()==mainWindow.btnTwoOne ||
				event.getSource()==mainWindow.btnTwoTwo)
		{
			try
			{
				this.registerClickOnGameBoard(event);
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, "An error occured. Please try again.");
			}
		}

		if (event.getSource()==mainWindow.btnStartGame)
		{
			try
			{
				outputToGame.println(CHECK_REG_PLAYERS);
				String regStatus = inputFromGame.readLine();

				if(Boolean.parseBoolean(regStatus)==true)
				{
					outputToGame.println(SET_MATCHUP);
					nextTurn();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please register players before stating a game.");
				}

			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, "An error occured. Please try again.");
			}
		}

		if (event.getSource()==mainWindow.btnRegisterPlayer)
		{
			try
			{
				PlayerRegisterView prFrame = new PlayerRegisterView();
				PlayerRegisterController_Client prController = new PlayerRegisterController_Client(prFrame);

				prFrame.linkAllSourcesToController(prController);
				prController.linkToMainController(this);
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, "An error occured. Please try again.");
			}
		}

		if (event.getSource()==mainWindow.btnRestartGame)
		{
			try
			{
				this.clearAllCells();
				this.clearCurrentPlayerDisplay();
				this.clearUsernameDisplay();
				this.clearMainDisplay();
				outputToGame.println(START_NEW_GAME);
				outputToGame.println(BROADCAST__CLEAR_EVERYTHING);
			}
			catch (Exception err)
			{
				JOptionPane.showMessageDialog(null, "An error occured. Please try again.");
			}
		}
	}


	//OPERATIONAL METHODS.

	/**
	 *  Registers all clicks on the game board in the GUI, and sends the info to the game (in server side).
	 *  
	 * @param event
	 * @throws IOException
	 */

	public void registerClickOnGameBoard(ActionEvent event) throws IOException
	{
		outputToGame.println(CHECK_REG_PLAYERS);
		String regStatus = inputFromGame.readLine();

		if(Boolean.parseBoolean(regStatus)==true)
		{
			outputToGame.println(CHECK_MATCHUP_IS_SET);
			String matchupStatus = inputFromGame.readLine();
			if(Boolean.parseBoolean(matchupStatus)==true)
			{
				
				outputToGame.println(GET_MARK_OF_CURRENT_PLAYER);
				String playerMark=inputFromGame.readLine();
				if(mainWindow.getMarkOfMainWindow().equals(playerMark))
				{
					GameplayButton buttonClicked = (GameplayButton) ((JButton) event.getSource());
		
					String rowOfCellClicked = Integer.toString(buttonClicked.getRow());
					String colOfCellClicked = Integer.toString(buttonClicked.getCol());
		
					String messageToGame = CHECK_WHETHER_CELL_PLAYED + "$$" + 
							rowOfCellClicked + "$$" + colOfCellClicked;
					outputToGame.println(messageToGame);
					String cellStatus=inputFromGame.readLine();
		
					if (Boolean.parseBoolean(cellStatus)==false) 
					{
						messageToGame = PLAY_CELL + "$$" + 
								rowOfCellClicked + "$$" + colOfCellClicked;
						outputToGame.println(messageToGame);
		
						messageToGame = CHECK_WHETHER_CELL_PLAYED + "$$" + 
								rowOfCellClicked + "$$" + colOfCellClicked;
						outputToGame.println(messageToGame);
						cellStatus=inputFromGame.readLine();
		
						if (Boolean.parseBoolean(cellStatus)==true)
						{
							outputToGame.println(GET_MARK_OF_CURRENT_PLAYER);
							playerMark=inputFromGame.readLine();
								
							buttonClicked.setText(playerMark);
		
							messageToGame = BROADCAST__DISPLAY_PLAY + "$$" + 
									rowOfCellClicked + "$$" + colOfCellClicked + "$$"+playerMark;
							outputToGame.println(messageToGame);
						}
						nextTurn();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "That cell has been played. Please try another cell.");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You are not the current player");
				}	
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please start the game before playing.");
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please register players before starting a game.");
		}
	}

	/**
	 * Takes care of all operations needed to prepare the game and GUI for the 
	 * next turn after a move has been played or the game has been started.
	 * @throws IOException
	 */
	synchronized public void nextTurn() throws IOException
	{
		outputToGame.println(CHECK_REG_PLAYERS);

		String regStatus = inputFromGame.readLine();
//		System.out.println(regStatus+" Line 281");
		if(Boolean.parseBoolean(regStatus)==true)
		{
			outputToGame.println(CHECK_IF_TO_CONTINUE);
			String contStatus=inputFromGame.readLine();
			if (Boolean.parseBoolean(contStatus)==true)
			{
				outputToGame.println(CHECK_IF_ANY_MOVE);
				String anyMoveYet=inputFromGame.readLine();
				if(Boolean.parseBoolean(anyMoveYet)==true) //if a move has been made (i.e. not the first move of the game).
				{
					outputToGame.println(SET_OPP_OF_CURRENT_PLAYER_AS_CURRENT);
				}

				outputToGame.println(GET_MARK_OF_CURRENT_PLAYER);
				String playerMark=inputFromGame.readLine();
				mainWindow.currentPlayerDisplay.setText(playerMark+" player");

				outputToGame.println(GET_NAME_OF_CURRENT_PLAYER);
				String playerName=inputFromGame.readLine();
				mainWindow.usernameDisplay.setText(playerName);

				String messageToGame = BROADCAST__DISPLAY_CURRENT_PLAYER + "$$" + 
						playerName	+ "$$" + playerMark;
				outputToGame.println(messageToGame);
			}
			else
			{
				outputToGame.println(BROADCAST__GAME_RESULT);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please register players before starting a game.");
		}
	}

	/**
	 * Clears all cells on the game board in the GUI only.
	 */
	synchronized private void clearAllCells()
	{
		//reset all buttons to blank.

		Component [] allComps = ((Component [])mainWindow.mainpane.getComponents());

		for(int i=0;i<allComps.length;++i)
		{	
			Component comp = allComps[i];
			if(comp instanceof GameplayButton)
			{
				GameplayButton cell = (GameplayButton) comp;
				cell.setText("");
			}
		}
	}
	
	/**
	 * clear the current player display.
	 */
	synchronized public void clearCurrentPlayerDisplay()
	{
		mainWindow.currentPlayerDisplay.setText("");
	}

	synchronized public void clearUsernameDisplay()
	{
		mainWindow.usernameDisplay.setText("");
	}

	synchronized public void clearMainDisplay()
	{
		mainWindow.mainDisplay.setText("");
	}
}
