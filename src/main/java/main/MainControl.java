package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import socket.SocketManager;

/***
 * ×Ü¿ØÖÆÊÒ
 */
public class MainControl {

	private static boolean isWorking = false;
	private static ServerSocket serverSocket;
	
	private static SocketManager socketMgr = SocketManager.getInstance();
	
	public static void start(int port) throws IOException {
		
		if(!isWorking) {
			isWorking = true;
			serverSocket = new ServerSocket(port);
			System.out.println("ServerSocket start!!");
			while(isWorking) {
				Socket socket = serverSocket.accept();
				socketMgr.addSocket(socket);
			}
		}
		
	}
	
}
