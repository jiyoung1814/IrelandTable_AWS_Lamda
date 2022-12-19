package REST_API;

import Packet.DataPacket;
import Serial.Serial;

public class API {
    static String path = "https://9aleo7ldsd.execute-api.ap-northeast-2.amazonaws.com/2022-09-29/";
    DataPacket dataPacket;
    Serial serial;

    POST_Method post_method;
    GET_Method get_method;

    public API(DataPacket dataPacket, Serial serial){
        this.dataPacket = dataPacket;
        this.serial = serial;

        get_method = new GET_Method(path, serial,dataPacket);
        post_method = new POST_Method(path,serial,dataPacket);

//        SerialConnect();
        post_method.start();
        get_method.start();

//        getTableData();
    }


//    private void SerialConnect(){
//        try {
//            serial.connect("COM10");
//            System.out.println("Serial Connect");
//        }catch (Exception e){
//            System.out.println("Serial connect Error: "+e.getMessage());
//        }
//
//    }

//    private void getTableData(){
//
//    }
}


