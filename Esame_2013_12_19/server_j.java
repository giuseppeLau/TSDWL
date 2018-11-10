/* 
 * File:   server_j.java
 * Author: Giuseppe
 *
 */

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class server_j {

	public static void main(String[] args) {
	
		ServerSocket serv_sock = null;
		Socket sock = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader in = null;
		BufferedWriter bw = null;
		PrintWriter out = null;

		try {
			serv_sock = new ServerSocket(7777);
			ArrayList<String> lista = new ArrayList<String>(10);
			int i = 0;

			System.out.println("Avvio del server..");
			while(true){
				System.out.println("Server in attesa di connessioni (CTLR+C per terminare).");
				sock = serv_sock.accept();
				System.out.println("Connessione accettata.");

				isr = new InputStreamReader(sock.getInputStream());
				in = new BufferedReader(isr);
				osw = new OutputStreamWriter(sock.getOutputStream());
				bw = new BufferedWriter(osw);
				out = new PrintWriter(bw, true);

				String str = in.readLine();

				if (str.equals("LIST")){
					for(int j = 0; j < lista.size(); j++){
						out.println(lista.get(j));
					}
				} else if(lista.size() >= 10){
					Random rand = new Random();
					int n = rand.nextInt(10);
					lista.set(n, str);
					out.println("Piena.");
				} else if(lista.contains(str)){
					out.println("Presente.");
				} else if(lista.size() < 10){
					lista.add(str);
					out.println("Inserita.");
				}
				in.close();
				out.close();
				sock.close();
				System.out.println("SERVER > Connessione terminata.");
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Exception.");
		} finally {
			try{
				if(in != null) in.close();
				if(out != null) out.close();
				if(serv_sock != null) serv_sock.close();
				if(sock != null) sock.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception.");
			}
		}

	}

}
