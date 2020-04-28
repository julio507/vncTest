package clientservercaptura;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.ByteBuffer;


public class Util {
    public static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int RESOLUCAO_X = (int) d.getWidth(); // resolucao da tela em x
    public static final int RESOLUCAO_Y = (int) d.getHeight(); // resolucao da tela em y

    public static final int BLOCK_X = 120; // em pixels
    public static final int BLOCK_Y = 120; // em pixels

    public static final int PORTA = 5020; // porta a qual serão enviados os dados

    
    public static byte[] integerToBytes(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    // mesmo que o anterior, porem usando bytes (na unha)
    public static byte[] integerToBytesNail(int value) {
        return new byte[]{
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value};
    }

    public static int bytesToInteger(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }

     // mesmo que o anterior, porem usando bytes (na unha)
    public static int bytesToIntegerNail(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24)
                | ((bytes[1] & 0xFF) << 16)
                | ((bytes[2] & 0xFF) << 8)
                | ((bytes[3] & 0xFF) << 0);
    }
    
    public static double myRound(double val) {
    if (val < 0) {
        return Math.ceil(val);
    }
    return Math.floor(val);
}
}