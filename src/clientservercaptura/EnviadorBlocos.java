package clientservercaptura;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class EnviadorBlocos {



    public static void main(String[] args) {
        // buffer para armazenar o bloco da tela

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double h = d.getHeight();
        double w = d.getWidth();

        try {

            DatagramSocket senderSocket = new DatagramSocket();
            InetAddress ipDestino = InetAddress.getByName("127.0.0.1");

            Robot robot = new Robot();

            while( true )
            {
                BufferedImage bi = robot.createScreenCapture(new Rectangle(Util.RESOLUCAO_X, Util.RESOLUCAO_Y));

                double ht = h / 4;
                double wt = w / 4;
                
                new Thread( new RunnableCaptura( 0, 0, wt, ht, bi, senderSocket, ipDestino ) ).start();
                new Thread( new RunnableCaptura( wt+1, ht+1, wt*2, ht*2, bi, senderSocket, ipDestino ) ).start();
                new Thread( new RunnableCaptura( (wt*2)+1, (ht*2)+1, (wt*3), ht*3, bi, senderSocket, ipDestino ) ).start();
                new Thread( new RunnableCaptura( wt*3+1, (ht*3)+1, w, h, bi, senderSocket, ipDestino ) ).start();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}