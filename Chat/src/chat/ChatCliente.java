package chat;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JTextField textoParaEnviar;
	Socket socket;
	PrintWriter escritor;
	String nome;
	JTextArea textoRecebido;
	Scanner leitor;
	
	private class EscutaServidor implements Runnable {
		
		@Override
		public void run() {
			try {
			String texto;
			while ((texto = leitor.nextLine()) != null) {
				textoRecebido.append(texto + "\n");
			}
			} catch (Exception e) {}
		}
	}
	
	
	public ChatCliente(String nome) {
		super("Chat : " + nome);
		this.nome = nome;
		
		Font fonte = new Font("Serif", Font.PLAIN, 26);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setFont(fonte);
		JButton botao = new JButton("Enviar");
		botao.setFont(fonte);
		botao.addActionListener(new EnviarListener());
		Container envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, textoParaEnviar);
		envio.add(BorderLayout.EAST, botao);
		
		textoRecebido = new JTextArea();
		textoRecebido.setFont(fonte);
		JScrollPane scroll = new JScrollPane(textoRecebido);

		getContentPane().add(BorderLayout.CENTER, scroll);
		getContentPane().add(BorderLayout.SOUTH, envio);
		
		configurarRede();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
	}

	private class EnviarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			escritor.println(nome + " : " + textoParaEnviar.getText());
			escritor.flush();
			textoParaEnviar.setText("");
			textoParaEnviar.requestFocus();
		}
	}
	
	private void configurarRede() {
		try {
			socket = new Socket("25.219.143.73", 5000);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream());
			new Thread(new EscutaServidor()).start();
		} catch (Exception e) {}
	}
	
	public static void main(String[] args) {
		new ChatCliente(JOptionPane.showInputDialog(null, "Digite seu nome:"));
//		Enumeration nis = null;
//		try {
//			nis = NetworkInterface.getNetworkInterfaces();
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//				
//		while (nis.hasMoreElements()) {  
//			NetworkInterface ni = (NetworkInterface) nis.nextElement();  
//			Enumeration ias = ni.getInetAddresses();
//					
//			while (ias.hasMoreElements()) {  
//				InetAddress ia = (InetAddress) ias.nextElement();
//				if (!ni.getName().equals("lo"))
//					System.out.println(ia.getHostAddress());   
//			}  
//		}
		
		// Ele retorna.
		//		fe80:0:0:0:213:d4ff:fe7f:4d9d%2 
		//		111.222.333.444
	}
}