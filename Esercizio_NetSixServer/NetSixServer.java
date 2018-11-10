/* 
 * File:   NetSixServer.java
 * Author: Giuseppe
 *
 */

import java.io.*;
import java.net.*;

public class NetSixServer {

	public static void main(String[] args) {

		ServerSocket serv_sock = null;
		Socket client_sock = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		PrintWriter out = null; 
		showList sl = new showList();

		try {

			serv_sock = new ServerSocket(3333);
			System.out.println("Server started.");
			
			while(true){	
				
				client_sock = serv_sock.accept();
				System.out.println("Connection accepted.");

				isr = new InputStreamReader(client_sock.getInputStream());
				in = new BufferedReader(isr);
				osw = new OutputStreamWriter(client_sock.getOutputStream());
				bw = new BufferedWriter(osw);
				out = new PrintWriter(bw, true);

				try {

					String client_message = in.readLine();
					String[] splitted_string = client_message.split(",");
					String showName = splitted_string[0];
					String epNumber = splitted_string[1];
					int ep_number = Integer.parseInt(epNumber);

					if(sl.isAvailable(showName, ep_number) == true){
						out.println("Episode " + ep_number + " of " + showName + " is available.");
					} else {
						out.println("Episode " + ep_number + " of " + showName + " is not available.");
					}

				} catch(Exception e) {
					System.out.println("Bad request.");
					out.println("Usage: NameOfTvShow,EpisodeNumber.");
				}

				in.close();
				out.close();
				client_sock.close();
				System.out.println("Connection closed.");
			}

		} catch (Exception e) {

			System.out.println("Exception");
			e.printStackTrace();

		} finally {

			try {
				
				if(in != null) in.close();
				if(out != null) out.close();
				if(serv_sock != null) isr.close();
				if(client_sock != null) osw.close();
			
			} catch (Exception e) {

				System.out.println("Exception.");
				e.printStackTrace();

			}
		}
	}
}
