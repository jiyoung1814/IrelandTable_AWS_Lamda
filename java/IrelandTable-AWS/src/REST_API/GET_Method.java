package REST_API;

import Packet.DataPacket;
import Serial.Serial;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class GET_Method extends Thread{
    String path;
    Serial serial;
    DataPacket dataPacket;

    //http 통신을 하기위한 객체 선언 실시
    URL url = null;
    HttpURLConnection conn = null;

    //http 통신 요청 후 응답 받은 데이터를 담기 위한 변수
    String responseData = "";
    BufferedReader br = null;
    StringBuffer sb = null;

    //메소드 호출 결과값을 반환하기 위한 변수
    String returnData = "";

    static String[] lastedControl = new String[7];
    static boolean isFirstGet = true;
    String[] controlData = new String[7];

    public GET_Method(String path, Serial serial, DataPacket dataPacket){

        this.path = (path + "control").trim();
        this.serial = serial;
        this.dataPacket = dataPacket;

    }

    public void run() {
        while(true) {
            try {
                //파라미터로 들어온 url을 사용해 connection 실시
                url = new URL(path);
                conn = (HttpURLConnection) url.openConnection();

                //http 요청에 필요한 타입 정의 실시
                conn.setRequestMethod("GET");

                //http 요청 실시
                conn.connect();

                //http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                sb = new StringBuffer();
                while ((responseData = br.readLine()) != null) {
                    sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
                }

                //메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
                returnData = sb.toString();

                //http 요청 응답 코드 확인 실시
                String responseCode = String.valueOf(conn.getResponseCode());
                System.out.println("http 응답 코드 : " + responseCode);
                System.out.println("http 응답 데이터 : " + returnData);

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(returnData);
                JSONObject jsonObj = (JSONObject) obj;


                controlData[0] = String.valueOf(jsonObj.get("datetime"));
                controlData[1] = String.valueOf(jsonObj.get("redState"));
                controlData[2] = String.valueOf(jsonObj.get("redRange"));
                controlData[3] = String.valueOf(jsonObj.get("blueState"));
                controlData[4] = String.valueOf(jsonObj.get("blueRange"));
                controlData[5] = String.valueOf(jsonObj.get("fanState"));
                controlData[6] = String.valueOf(jsonObj.get("fanRange"));

                int idx = 0; //if redState = 1, redRange = 2 ...
                if (isFirstGet) {
                    for (int i = 0; i < lastedControl.length; i++) {
                        lastedControl[i] = controlData[i];
                        isFirstGet = false;
                    }
                    System.out.println("first");
                } else {
//                    System.out.println(lastedControl[1]+","+lastedControl[2]+","+lastedControl[3]+","+lastedControl[4]+","+lastedControl[5]+","+lastedControl[6]);
                    System.out.println(lastedControl[0] + "==" + controlData[0]);
                    if (!lastedControl[0].equals(controlData[0])) {
                        System.out.println("dif date");
                        for (int i = 0; i < lastedControl.length; i++) {
                            if (!lastedControl[i].equals(controlData[i])) {
                                idx = i;
                            }
                            lastedControl[i] = controlData[i];
                        }
                        System.out.println(idx + ": "+ controlData[idx]);
                    sendToIreland(idx, controlData[idx]);

                    } else {
                        System.out.println("same date");
                    }
                }

                Thread.sleep(1500);


            } catch (Exception e) {
                System.out.println("getMethod Error" + e.getMessage());
            } finally {
                //http 요청 및 응답 완료 후 BufferedReader 닫기
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (Exception e) {
                    System.out.println("BufferedReader close Error" + e.getMessage());
                }
            }
        }
    }

    public void sendToIreland(int idx, String v) {

        if(idx %2 != 0){  //state
            int i = Math.floorDiv(idx,2) + 1;
            String hex = "0"+v;
            System.out.println("control state: "+ idx+" =>"+ hex);
            serial.sw.write_byte("0201FF4CFF0" + (i) + "FF" + hex + "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03");
        }
        else{// range
            int value = Integer.parseInt(v);
            idx = Math.floorDiv(idx,2);
            String hex = String.format("%02X", value);
            System.out.println("control range: "+ idx+" => " + "dec: "+value+", hex : " + hex);
            serial.sw.write_byte(
                    "0201FF50FF0" + (idx) + "FF0000640000" + hex + "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03"); // Duty

        }
    }
}


