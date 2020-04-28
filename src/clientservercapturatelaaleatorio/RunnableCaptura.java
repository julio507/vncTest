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

    public RunnableCaptura(DatagramSocket senderSocket, InetAddress ipDestino) {
        this.ipDestino = ipDestino;
        this.senderSocket = senderSocket;
    }

    @Override
    public void run() {

        try {
            Robot robot = new Robot();

            while (true) {
                // capturei
                // a tela
                // toda

                BufferedImage bi = robot.createScreenCapture(new Rectangle(Util.RESOLUCAO_X, Util.RESOLUCAO_Y));

                for (int i = 0; i < 10; i++) {
                    for (int k = 0; k < 10; k++) {

                        int posX = (int) (Math.random() * (Util.RESOLUCAO_X - Util.BLOCK_X)); // poder sortear
                        // um local da
                        // tela sem sair
                        // dela
                        int posY = (int) (Math.random() * (Util.RESOLUCAO_Y - Util.BLOCK_Y)); // poder sortear
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
