package cn.edu.uestc.MyQQ;

import java.io.*;
import java.net.*;

/**
 * 
 * use multiple threads to simultaneously receive responses
 * from multiple clients
 * @author mohanyi
 *
 */
public class TCPServerThread extends Thread {
	/*
	 * the thread-related Socket
	 */
	String clientSentence = null; // store sentence from client
	String capticalizedSentence = null; // store translated sentence from client
	Socket connectionSocket = null; // store the client socket
	
	/* constructor */
	public TCPServerThread(Socket socket){
		this.connectionSocket = socket;
	}
	
	/*
	 * The operation performed by the thread
	 * in response to the client's request
	 */
	public void run(){
		BufferedReader inFromClient = null; // store info from client
		DataOutputStream outToClient = null; // store info to server
		
		try {
			
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));
			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			
			// in order to distinction, turn all characters to uppercase characters 
			capticalizedSentence = clientSentence.toUpperCase();
			MySQLConnect.storeMessage(capticalizedSentence);
			outToClient.writeBytes(MySQLConnect.getMessage() + '\n');
			outToClient.writeBytes("You are connected to Server. \n");
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			// close the related resources
			try {
				if(inFromClient != null)
					inFromClient.close();
				if(outToClient != null)
					outToClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
