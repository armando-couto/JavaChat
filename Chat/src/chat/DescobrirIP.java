package chat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DescobrirIP {

	public static void main(String[] args) {
		Enumeration nis = null;
		try {
			nis = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
				
		while (nis.hasMoreElements()) {  
			NetworkInterface ni = (NetworkInterface) nis.nextElement();  
			Enumeration ias = ni.getInetAddresses();
					
			while (ias.hasMoreElements()) {  
				InetAddress ia = (InetAddress) ias.nextElement();
				if (!ni.getName().equals("lo"))
					System.out.println(ia.getHostAddress());   
			}  
		}
		
		// Ele retorna.
		//		fe80:0:0:0:213:d4ff:fe7f:4d9d%2 
		//		111.222.333.444
	}
}
