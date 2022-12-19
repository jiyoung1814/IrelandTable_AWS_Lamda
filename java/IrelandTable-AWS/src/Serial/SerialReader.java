package Serial;

import Packet.DataPacket;

import java.io.InputStream;

public class SerialReader extends Thread {
    InputStream in;
    String savePacket;
    DataPacket dataPacket;

    public SerialReader(InputStream in, DataPacket dataPacket) {
        this.in = in;
        this.dataPacket = dataPacket;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);

                byte[] buffer = new byte[50];
                String packet ="";

                in.read(buffer);

                packet = byteArrayToString(buffer);
                System.out.println("raw Packet: "+packet);

                Parsing(packet);
            }
        } catch (Exception e) {
            System.out.println("read Error: "+e.getMessage());
        }
    }
    private String byteArrayToString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {// bytes에서 객체를 꺼내어 b에 저장 (bytes가 없어질 때 까지 반복)
            sb.append(String.format("%02X", b & 0xff));
//            System.out.println(b);
        }
        return sb.toString();
    }

    public void Parsing(String pack) {
        savePacket = "";
        int start = -1;
        int end = -1;

        //쓰레기 값을 제거한 순수 패킷 분류
        for (int i = 0; i < pack.length(); i++) {
            start = pack.indexOf("02", start);
            end = pack.lastIndexOf("03");

            if (start > end || start == -1 || end == -1) {
                continue;
            }
            pack = pack.substring(start, end + 2);
            System.out.println("pure packet" + pack);

            if (start < end) {
                if (pack.length() == 60) {
                    savePacket = pack;
                    dataPacket.DivisionPacket(savePacket);
                    break;
                }
            }
        }

    }
}