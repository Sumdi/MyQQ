package cn.edu.uestc.MyQQ;

import java.io.*;
import java.net.*;

/**
 * 
 * TCP server
 * @author mohanyi
 *
 */
public class TCPServer {

	public static void main(String[] args) {
		
		try {
			/*
			 * create a ServerSocket
			 * specifies the port to bind to
			 * listen the port
			 */
			ServerSocket welcomeSocket = new ServerSocket(8888);
			System.out.println("***The server is about to start, waiting for the client to connect***");
			Socket socket = null;
			int count = 0; // Record the number of clients
			
			/* The loop listener waits for a connection from the client */
			while(true) {
				/* 
				 * call accept () method to start listening
				 * waiting for the client connection
				 */
				
				// Will be in a blocked state, waiting to listen to the client
				socket = welcomeSocket.accept(); 
				
				// create a new thread
				TCPServerThread serverThread = new TCPServerThread(socket);
				// start the thread
				serverThread.start();
				count++;// counts the number of clients
				System.out.println("NUMBER OF CLIENTS: " + count);
				InetAddress address = socket.getInetAddress(); // get the client IP address
				System.out.println("CURRENT CLIENT'S IP ADDRESS: " + address.getHostAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
