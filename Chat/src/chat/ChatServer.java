package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	
	public ChatServer() {
		ServerSocket server;
		try {
			server = new ServerSocket(5000);
			while (true) {
				Socket socket = server.accept();
				new Thread(new EscutaCliente(socket)).start();
			}
		} catch (IOException e) {}
	}
	
	private class EscutaCliente implements Runnable {

		Scanner leitor;
		
		public EscutaCliente(Socket socket) {
			try {
				leitor = new Scanner(socket.getInputStream());
			} catch (Exception e) {}
		}
		
		@Override
		public void run() {
			try {
				String texto;
				while ((texto = leitor.nextLine()) != null) {
					System.out.println(texto);
				}
			} catch (Exception e) {}
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
}
