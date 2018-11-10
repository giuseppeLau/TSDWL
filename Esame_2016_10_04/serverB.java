/* 
 * File:   serverB.java
 * Author: Giuseppe
 *
 */

import java.io.*;
import java.net.*;


public class serverB {

	public static void main(String[] args) {
		
		ServerSocket serv_sock = null;
		Socket sock = null;
		BufferedReader in = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;

		try {

			serv_sock = new ServerSocket(3333);

			System.out.println("Server Avviato.");
			while(true){
				System.out.println("In Attesa di Connessione.");
				sock = serv_sock.accept();
				System.out.println("Connessione Accettata.");

				isr = new InputStreamReader(sock.getInputStream());
				in = new BufferedReader(isr);
				osw = new OutputStreamWriter(sock.getOutputStream());
				bw = new BufferedWriter(osw);
				out = new PrintWriter(bw, true);

				String str = in.readLine();

				int x = 0;

				for(int i = 0; i < str.length(); i++){ if(str.charAt(i) == 'V' || str.charAt(i) == 'F') x++; } 

				if(x == str.length()) { 

					System.out.println(str);
					out.println("Message from Server: " + str);
					
				} else {

					System.out.println("String not accepted.");
				} 

				in.close();
				out.close();
				sock.close();
				System.out.println("Connessione Terminata.");
			}
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception.");
		} finally {
			try {
				if(in != null) in.close();
				if(out != null) out.close();
				if(serv_sock != null) serv_sock.close();
				if(sock != null) sock.close();
			} catch (Exception e){
				e.printStackTrace();
				System.out.println("Exception.");
			}
		}
	}	
}
