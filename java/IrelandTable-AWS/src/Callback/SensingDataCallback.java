package Callback;

public interface SensingDataCallback {
    public void set_sensing_data(String time, String temp, String humi, String co2, String illum, String gas);
}
