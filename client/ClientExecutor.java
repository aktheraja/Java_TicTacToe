package exer5.client;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;

import exer5.client.view.MainView;
import exer5.client.controller.MainController_Client;

public class ClientExecutor {
	
	public Socket socketForGame, socketForBroadcastFromGame;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater
		(
			new Runnable() 
			{
				public void run() 
				{
					try 
					{	
						
						ClientExecutor clientRunner= new ClientExecutor();

						MainView mainWindow = new MainView(); // GUI is created.
						
						MainController_Client mainWindowController = 
								new MainController_Client(mainWindow, clientRunner.socketForGame); //Controller is created.
						
						mainWindow.linkAllSourcesToListener(mainWindowController); //GUI sources are linked to Controller.
						
						mainWindowController.connectToGameBroadcaster(clientRunner.socketForBroadcastFromGame);
												
						Thread threadToListenToGameBroadcast = new Thread(mainWindowController);
						// This thread is just to run the method that perpetually listens for broadcasts from the game.
						
						threadToListenToGameBroadcast.start();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		);
	}
	
	public ClientExecutor() throws IOException, InterruptedException 
	{
		socketForGame = new Socket("10.13.151.189", 7876);
		Thread.sleep(500);
		socketForBroadcastFromGame = new Socket("10.13.151.189", 7876);
	}
}
