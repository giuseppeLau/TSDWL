import java.net.*;
import java.io.*;

public class server_D {

	public static String inverti(String s) {

		StringBuilder nuova = new StringBuilder(s);

		for(int i = 0, j = s.length()-1; i < s.length()/2; i++, j--){

			char primo = s.charAt(i);
			nuova.setCharAt(j, primo);
			
			char ultimo = s.charAt(j);
			nuova.setCharAt(i, ultimo);

		}

		return nuova.toString();
	}

	public static void main(String[] args) {

			ServerSocket serv_sock = null;
			Socket client_sock = null;
			InputStreamReader isr = null;
			BufferedReader in = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			PrintWriter out = null;
		
		try {

			serv_sock = new ServerSocket(3333);
			System.out.println("Listening...");

			while(true){

				client_sock = serv_sock.accept();
				System.out.println("Connection Accepted.");

				isr = new InputStreamReader(client_sock.getInputStream());
				osw = new OutputStreamWriter(client_sock.getOutputStream());
				in = new BufferedReader(isr);
				bw = new BufferedWriter(osw);
				out = new PrintWriter(bw, true);

				String client_message = in.readLine();

				String stringa = inverti(client_message);
				System.out.println(stringa);
				out.println(stringa);

				in.close();
				out.close();
				client_sock.close();
				System.out.println("Connection closed.");
			}

		} catch (Exception e){
			System.out.println("Exception.");
			e.printStackTrace();
		} finally {

			try{

				if(serv_sock != null ) {serv_sock.close();}
				if(client_sock != null){client_sock.close();}
				if(in != null){ in.close();}
				if(out != null){out.close();}

			} catch(Exception e){
				System.out.println("Exception.");
				e.printStackTrace();
			}
		}
	}
}