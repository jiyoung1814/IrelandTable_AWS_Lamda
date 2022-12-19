package Packet;

import Callback.AlarmCallback;
import Callback.LedFanCallback;
import Callback.LedFanValueCallBack;
import Callback.SensingDataCallback;

public class DataPacket {
    String time, temp, humi, co2, illum, gas;
    int Red, Blue, Fan;
    boolean RedOn, BlueOn, FanOn;
    String[] RedTime, BlueTime, FanTime;
    String[] sensingData;

    SensingDataCallback sensingData_callback;
    LedFanCallback ledFan_callback;
    LedFanValueCallBack ledFanValue_callback;
    AlarmCallback alarm_callback;

    public DataPacket(){}

    public void setSensingDataCallback(SensingDataCallback sc) {
        sensingData_callback = sc;
    }

    public void setLedFanCallback(LedFanCallback lfc) {
        ledFan_callback = lfc;
    }

    public void setLedFanValueCallBack(LedFanValueCallBack lfvc){ledFanValue_callback = lfvc;}

    public void setAlarmCallback(AlarmCallback ac) {
        alarm_callback = ac;
    }

    public void DivisionPacket(String packet) {
        System.out.println("received data: " + packet);
        String key = packet.substring(0, 8);

        if (key.equals("0202FF53")) {
            SensorBoard(packet);
        }

        else if (key.equals("0201FF53")) {
            LedOnOff(packet);
        }

        else if (key.equals("0201FF73")) {
            LedData(packet);
        }

        else if (key.equals("0201FF52")) {
            AlarmData(packet);
        }
    }

    public void SensorBoard(String packet){
        System.out.println("센서보드 패킷  " + packet);
        sensingData = new String[6];
        time = packet.substring(14, 16) + ":" + packet.substring(16, 18);
        temp = packet.substring(21, 22) + packet.substring(23, 24) + "." + packet.substring(25, 26) + " ℃ ";
        humi = packet.substring(29, 30) + packet.substring(31, 32) + "." + packet.substring(33, 34) + " %";
        co2 = packet.substring(37, 38);
        if (!co2.equals("0")) {
            co2 += packet.substring(39, 40) + packet.substring(41, 42) + packet.substring(42, 43) + " ppm";
        } else {
            co2 = packet.substring(39, 40) + packet.substring(41, 42) + packet.substring(42, 43) + " ppm";
        }

        illum = packet.substring(47, 48);
        if (!illum.equals("0")) {
            illum += packet.substring(49, 50) + packet.substring(51, 52) + packet.substring(53, 54) + " [lx]";
        } else {
            illum = packet.substring(49, 50) + packet.substring(51, 52) + packet.substring(53, 54) + " [lx]";
        }

        gas = packet.substring(55, 56);
        if (!gas.equals("0")) {
            gas += packet.substring(56, 58);
        } else {
            gas = packet.substring(56, 58);
        }
        sensingData[0] = time;
        sensingData[1] = temp.substring(0,temp.length()-2);
        sensingData[2] = humi.substring(0,humi.length()-2);
        sensingData[3] = co2.substring(0, co2.length()-4);
        sensingData[4] = illum.substring(0,illum.length()-5);
        sensingData[5] = gas;

//        sensingData_callback.set_sensing_data(time, temp, humi, co2, illum, gas);
        sensingData_callback.set_sensing_data(sensingData[0], sensingData[1], sensingData[2], sensingData[3], sensingData[4], sensingData[5]);
    }

    public String[] getSensingData() {
        return sensingData;
    }

    public void LedOnOff(String packet) {
        System.out.println("Led On/Off 패킷 : " + packet);

        if (packet.substring(16, 18).equals("01")) {
            System.out.println("Red 켜짐");
            RedOn = true;
        } else {
            System.out.println("Red 꺼짐");
            RedOn = false;
        }

        if (packet.substring(18, 20).equals("01")) {
            System.out.println("Blue 켜짐");
            BlueOn = true;
        } else {
            System.out.println("Blue 꺼짐");
            BlueOn = false;
        }

        if (packet.substring(20, 22).equals("01")) {
            System.out.println("Fan 켜짐");
            FanOn = true;
        } else {
            System.out.println("Fan 꺼짐");
            FanOn = false;
        }

        ledFan_callback.set_switch(RedOn, BlueOn, FanOn);
    }

    /* LED Data 상태 체크 */
    public void LedData(String packet) {
        System.out.println("Led Data 패킷 : " + packet);

        Red = Integer.parseInt(packet.substring(10, 12), 16);
        Blue = Integer.parseInt(packet.substring(24, 26), 16);
        Fan = Integer.parseInt(packet.substring(38, 40), 16);

        ledFanValue_callback.set_power(Red, Blue, Fan);
    }

    /* Alarm Time Data 상태 체크 */
    public void AlarmData(String packet) {
        System.out.println("Alarm Time Data 패킷 : " + packet);
        RedTime = new String[4]; // On_H, On_M, Off_H, Off_M;
        BlueTime = new String[4];
        FanTime = new String[4];
//
        for (int i = 0, t = 0; i < RedTime.length; i++, t += 2) {

            RedTime[i] = packet.substring(10 + t, 12 + t);
            BlueTime[i] = packet.substring(20 + t, 22 + t);
            FanTime[i] = packet.substring(30 + t, 32 + t);

            alarm_callback.set_alarm(RedTime,BlueTime,FanTime);
        }

        System.out.println(RedTime[0]+" : "+RedTime[1]+ " ~ "+RedTime[2]+" : " + RedTime[3]);
        System.out.println(BlueTime[0]+" : "+BlueTime[1]+ " ~ "+BlueTime[2]+" : " + BlueTime[3]);
        System.out.println(FanTime[0]+" : "+FanTime[1]+ " ~ "+FanTime[2]+" : " + FanTime[3]);
    }
}


