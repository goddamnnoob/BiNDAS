package com.example.das;

public interface MessageListener {
    void vehicleNo(String vno);
    void longitudeinfo(String longi);
    void latitudeinfo(String lati);
    void currentSpeed(String speed);
    void driverState(String state);
    void senderPhoneno(String phno);
    void alertTime(long time);
}