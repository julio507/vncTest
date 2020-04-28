package clientservercaptura;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * RunnableCaptura
 */
public class RunnableCaptura implements Runnable {

    byte buffer[] = new byte[Util.BLOCK_X * Util.BLOCK_Y * 4 + 4 + 4]; // pegar R, G, B, e alfa para cada pixel + 4
    // pois quero informar o posX e + 4 posY
    // (posicão sorteada)

    // destinatário
    DatagramSocket senderSocket;
    BufferedImage bi;
    InetAddress ipDestino;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    double he;
    double we;

    double hs;
    double ws;

    public RunnableCaptura(double hs, double ws, double he, double we, BufferedImage bi, DatagramSocket senderSocket,
            InetAddress ipDestino) {
        this.bi = bi;
        this.ipDestino = ipDestino;
        this.senderSocket = senderSocket;

        this.he = he;
        this.we = we;

        this.hs = hs;
        this.he = he;
    }

    @Override
    public void run() {

        try {

            for (int i = (int) 0; i < d.getWidth()-Util.BLOCK_X; i += Util.BLOCK_X) {
                for (int k = (int) 0; k < d.getHeight()-Util.BLOCK_Y; k += Util.BLOCK_Y) {
                    
                    int posX = i;
                    int posY = k;

                    //System.out.println(posX);
                    //System.out.println(posY);
                    
                    int aux = 0;
                    for (int y = 0; y < Util.BLOCK_Y; y++) {
                        for (int x = 0; x < Util.BLOCK_X; x++) {

                            int cor = bi.getRGB(posX + x, posY + y); //ARGB

                            byte auxBuffer[] = Util.integerToBytes(cor);
                            for (int j = 0; j < auxBuffer.length; j++) {
                                buffer[aux++] = auxBuffer[j];
                            }
                        }
                    }

                    // bytes do posX
                    byte auxBufferPosX[] = Util.integerToBytes(posX);
                    for (int j = 0; j < auxBufferPosX.length; j++) {
                        buffer[aux++] = auxBufferPosX[j];
                    }

                    // bytes do posY
                    byte auxBufferPosY[] = Util.integerToBytes(posY);
                    for (int j = 0; j < auxBufferPosY.length; j++) {
                        buffer[aux++] = auxBufferPosY[j];
                    }

                    DatagramPacket enviaPacote = new DatagramPacket(buffer, buffer.length, ipDestino, Util.PORTA);
                    senderSocket.send(enviaPacote);

                    Thread.sleep(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
