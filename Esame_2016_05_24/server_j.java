/* 
 * File:   server_j.java
 * Author: Giuseppe
 *
 */

import java.net.*;
import java.io.*;

public class server_j {

	public static int cubo(int n ){
		return n*n*n;
	}

	public static void main(String[] args) {
	
		ServerSocket serv_sock = null;
		Socket sock = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader in = null;
		BufferedWriter bw = null;
		PrintWriter out = null;

		try {
			serv_sock = new ServerSocket(3333);

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

				int x = 0;

				for(int i = 0; i < str.length(); i++){
					if(Character.isDigit(str.charAt(i))) x++;
				}

				if(x == str.length()){
					int result = Integer.parseInt(str);
					int c = cubo(result);
					out.println("Il cubo del numero e': " + c);
				} else {
					out.println("La stringa non e' composta solo da numeri.");
				}

				in.close();
				out.close();
				sock.close();
				System.out.println("Connessione terminata.");
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
