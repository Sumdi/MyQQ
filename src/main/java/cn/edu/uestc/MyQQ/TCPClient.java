package cn.edu.uestc.MyQQ;

import java.io.*;
import java.net.*;

/**
 * 
 * TCP client
 * to input a message and get it back in UpperCase
 * @author mohanyi
 *
 */
public class TCPClient {
	public static void main(String[] args) {
		String sentence;
		String modifiedSentence;
		String currentUser = "test";
		String respondSentence;
		
		BufferedReader inFromUser = new BufferedReader(
				new InputStreamReader(System.in));
		
		while (true) {
			try {
				// 1. Create a client Socket, specify the server address and port
				Socket clientSocket = new Socket("localhost", 8888);
				// 2. Get the output stream to send information to the server
				OutputStream outToServer = clientSocket.getOutputStream();
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				sentence = inFromUser.readLine();
				outToServer.write((sentence + '\n').getBytes());
				modifiedSentence = inFromServer.readLine();
				respondSentence = inFromServer.readLine();
				System.out.println(modifiedSentence);
				System.out.println(respondSentence);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

}
