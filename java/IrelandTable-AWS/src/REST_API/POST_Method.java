package REST_API;

import Callback.LedFanCallback;
import Callback.LedFanValueCallBack;
import Callback.SensingDataCallback;
import Packet.DataPacket;
import Serial.Serial;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class POST_Method extends Thread{
    String path;
    Serial serial;
    DataPacket dataPacket;

    URL url = null;
    HttpURLConnection conn = null;

    //http 통신 요청 후 응답 받은 데이터를 담기 위한 변수
    String responseData = "";
    BufferedReader br = null;
    StringBuffer sb = null;

    //메소드 호출 결과값을 반환하기 위한 변수
    String returnData = "";
    public POST_Method(String path, Serial serial, DataPacket dataPacket){
        this.path = path+"board";
        this.serial = serial;
        this.dataPacket = dataPacket;
    }

    public void run(){
        try{
            while(true){

                Thread.sleep(30000);
                serial.sw.write_byte("0202FF53FF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03");
                SensingData_Callback();
//                if(!isSensing){
//                    String body = "{\"redState\":"+0+", "
//                            + "\"redRange\":"+1+", "
//                            + "\"blueState\":"+0+", "
//                            + "\"blueRange\":"+0+", "
//                            + "\"fanState\":"+1+", "
//                            + "\"fanRange\":"+1
//                            + "}";
//                }

//                    Thread.sleep(30000);
//                    serial.sw.write_byte("0202FF53FF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03");
//                    SensingData_Callback();

//                else{
//                    Thread.sleep(1500);
//                    serial.sw.write_byte("0201FF53FF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03"); //LED on/off check
//                    LedFanCallback();
////                    Thread.sleep(1000);
////                    serial.sw.write_byte("0201FF73FF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03"); //LED value check
////                    LedFanValueCallBack();
//                }
            }
        }catch (Exception e){
            System.out.println("post_method Error: "+e.getMessage());
        }
    }

//    private void LedFanCallback(){
//        dataPacket.setLedFanCallback(new LedFanCallback() {
//
//            @Override
//            public void set_switch(boolean red, boolean blue, boolean fan) {
//
//                state[0] = red;
//                state[1] = blue;
//                state[2] = fan;
//
//                System.out.println(state[0]);
//                System.out.println(state[1]);
//                System.out.println(state[2]);
//
//                int[] state_int = {0,0,0};
//
//                for(int i=0;i<state.length;i++){
//                    if(state[i]) state_int[i] = 1;
//                    else state_int[i] = 0;
//                }
//
//
//                String body = "{\"redState\":"+state_int[0]+", "
//                        + "\"redRange\":"+0+", "
//                        + "\"blueState\":"+state_int+", "
//                        + "\"blueRange\":"+1+", "
//                        + "\"fanState\":"+state_int[2]+", "
//                        + "\"fanRange\":"+2
//                        + "}";
//
//                System.out.println(body);
//
//                Post(body);
//
//
//
//
////                serial.sw.write_byte("0201FF73FF00FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF03");
////                LedFanValueCallBack();
//
//            }
//        });
//    }

//    private void LedFanValueCallBack(){
//        dataPacket.setLedFanValueCallBack(new LedFanValueCallBack() {
//            @Override
//            public void set_power(int red, int blue, int fan) {
//
//                value[0] = red;
//                value[1] = blue;
//                value[2] = fan;
//
//                System.out.println(red);
//                System.out.println(blue);
//                System.out.println(fan);
//
//            }
//        });
//
//    }

    private void Post(String body){
        try{
            //파라미터로 들어온 url을 사용해 connection 실시
            path = path.trim();
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();

            //http 요청에 필요한 타입 정의 실시
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            conn.setDoInput(true); // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            os.write(body); //euc-kr
            os.flush();
            os.close();

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
            System.out.println("http 응답 코드 : "+responseCode);
            System.out.println("http 응답 데이터 : "+returnData);


        }catch(Exception e){
            System.out.println("postMethod Error: "+e.getMessage());
        }
        finally {
            //http 요청 및 응답 완료 후 BufferedReader 닫기
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                System.out.println("BufferedReader close Error"+e.getMessage());
            }
        }
    }

    public void SensingData_Callback(){
        dataPacket.setSensingDataCallback(new SensingDataCallback() {
            @Override
            public void set_sensing_data(String time, String temp, String humi, String co2, String illum, String gas) {
                System.out.println("time: "+time);
                System.out.println("temp: "+temp+ " ℃ ");
                System.out.println("humi: "+humi+ " %");
                System.out.println("Co2: "+co2+ " ppm");
                System.out.println("illum: "+illum+ "[lx]");
                System.out.println("gas: "+gas);

                LocalDateTime now = LocalDateTime.now();
                String datetime = now.format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss"));

                String body = "{\"datetime\":\""+datetime+"\", "
                        + "\"temperature\":"+temp+", "
                        + "\"humidity\":"+humi+", "
                        + "\"illumination\":"+illum.substring(1)+", "
                        + "\"co2\":"+co2+", "
                        + "\"gas\":"+gas
                        + "}";

                System.out.println(body);

                Post(body);

//                try{
//                    //파라미터로 들어온 url을 사용해 connection 실시
//                    path = path.trim();
//                    url = new URL(path);
//                    conn = (HttpURLConnection) url.openConnection();
//
//                    //http 요청에 필요한 타입 정의 실시
//                    conn.setRequestMethod("POST");
//                    conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
//                    conn.setDoInput(true); // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
//                    BufferedWriter os = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//                    os.write(body); //euc-kr
//                    os.flush();
//                    os.close();
//
//                    //http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
//                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                    sb = new StringBuffer();
//                    while ((responseData = br.readLine()) != null) {
//                        sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
//                    }
//
//                    //메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
//                    returnData = sb.toString();
//
//                    //http 요청 응답 코드 확인 실시
//                    String responseCode = String.valueOf(conn.getResponseCode());
//                    System.out.println("http 응답 코드 : "+responseCode);
//                    System.out.println("http 응답 데이터 : "+returnData);
//
//
//                }catch(Exception e){
//                    System.out.println("postMethod Error: "+e.getMessage());
//                }
//                finally {
//                    //http 요청 및 응답 완료 후 BufferedReader 닫기
//                    try {
//                        if (br != null) {
//                            br.close();
//                        }
//                    } catch (Exception e) {
//                        System.out.println("BufferedReader close Error"+e.getMessage());
//                    }
//                }

            }
        });
    }
}
