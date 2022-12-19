package Serial;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter extends Thread {
    OutputStream out;

    public SerialWriter(OutputStream out) {
        this.out = out;
    }

    public void write_byte(String str) {
        byte[] packet = hexStringToByteArray(str);
        try {
            out.write(packet);
        } catch (IOException e) {
            System.out.println("writeError: "+e.getMessage());
        }
        System.out.println("Send Packet : " + str);
    }

    private byte[] hexStringToByteArray(String str) {
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }
}