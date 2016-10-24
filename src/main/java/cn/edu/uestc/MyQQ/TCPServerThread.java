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
		OutputStream outToClient = null; // store info to server
		
		try {
			
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));
//			outToClient = new DataOutputStream(
//					connectionSocket.getOutputStream());
			outToClient = connectionSocket.getOutputStream();
			clientSentence = inFromClient.readLine();
			
			// in order to distinction, turn all characters to uppercase characters 
			capticalizedSentence = clientSentence;
			MySQLConnect.storeMessage(capticalizedSentence);
			outToClient.write((MySQLConnect.getMessage() + '\n').getBytes());
			outToClient.write("You are connected to Server. \n".getBytes());
		
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
