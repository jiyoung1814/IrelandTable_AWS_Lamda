import Packet.DataPacket;
import REST_API.API;
import Serial.Serial;
import TCP.TCP_Server;

public class Manage {
    API api;
    Serial serial;
    TCP_Server tcp_server;
    DataPacket dataPacket;

    public Manage(){
//        tcp_server = new TCP_Server();
//        tcp_server.start();

        dataPacket = new DataPacket();
        serial = new Serial(dataPacket);

        try {
            serial.connect("COM10");
            System.out.println("Serial Connect");
        }catch (Exception e){
            System.out.println("Serial connect Error: "+e.getMessage());
        }



        api = new API(dataPacket, serial);
    }
}
