package clientservercapturatelaaleatorio;

import java.awt.Rectangle;
import java.awt.Robot;
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

            // capturei
            // a tela
            // toda

            for (int i = (int) hs; i < he; i++) {
                for (int k = (int) ws; j < we; j++) {

                    int posX = i; // poder sortear
                                                                                          // um local da
                                                                                          // tela sem sair
                                                                                          // dela
                    int posY = k; // poder sortear
                                                                                          // um local da
                                                                                          // tela sem sair
                                                                                          // dela

                    // arqui poderia ser lancado uma Thread
                    int aux = 0;
                    for (int y = 0; y < Util.BLOCK_Y; y++) {
                        for (int x = 0; x < Util.BLOCK_X; x++) {

                            int cor = bi.getRGB(posX + x, posY + y); // ARGB

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

                    Thread.sleep(10);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
