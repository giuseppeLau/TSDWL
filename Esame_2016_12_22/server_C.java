import java.net.*;
import java.io.*;

public class server_C {

	public static String inverti(String s) {

		return s;

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
				System.out.println("Connection Accepted");

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