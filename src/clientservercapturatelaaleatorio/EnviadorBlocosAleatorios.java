package clientservercapturatelaaleatorio;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Alexandre Sturmer Wolf
 */
public class EnviadorBlocosAleatorios {



    public static void main(String[] args) {
        // buffer para armazenar o bloco da tela

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double h = d.getHeight();
        double w = d.getWidth();

        try {

            DatagramSocket senderSocket = new DatagramSocket();
            InetAddress ipDestino = InetAddress.getByName("127.0.0.1");

            Robot robot = new Robot();

            
            new Thread( new RunnableCaptura( senderSocket, ipDestino ) ).start();
            new Thread( new RunnableCaptura(   senderSocket, ipDestino ) ).start();
            new Thread( new RunnableCaptura(   senderSocket, ipDestino ) ).start();
            new Thread( new RunnableCaptura(  senderSocket, ipDestino ) ).start();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}