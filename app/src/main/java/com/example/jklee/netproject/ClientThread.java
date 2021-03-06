package com.example.jklee.netproject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientThread extends Thread {
    private Socket socket = null;
    private InetSocketAddress isa = null;
    private OutputStream os = null;
    private InputStream is = null;
    private BufferedInputStream bis = null;

    private String lat = null;
    private String lon = null;

    private String stationName = "";
    private String pm10 = "";
    private String pm25 = "";
    private String temp = "";
    private String tempRange = "";

    private String serverIP;
    private int serverPort;

    ClientThread(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.lat = String.format("%.4f", Location.getLatitude());
        this.lon = String.format("%.4f", Location.getLongitude());
        Log.d("loca", this.lat +" / " + this.lon);
    }

    ClientThread(String serverIP, int serverPort, String lat, String lon) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.lat = lat;
        this.lon = lon;
    }

    protected String getStationName() {
        return stationName;
    }

    protected String getPm10() {
        return pm10;
    }

    protected String getPm25() {
        return pm25;
    }

    protected String getTemp() {
        return temp;
    }

    protected String getTempRange() {
        return tempRange;
    }

    public void run() {
        try {
            Log.d("ERROR!", "전송 준비전");
            isa = new InetSocketAddress(serverIP, serverPort);
            socket = new Socket();
            socket.setReuseAddress(true);
            socket.connect(isa);
            // Timeout : 5sec
            socket.setSoTimeout(50000);
            socket.setSoLinger(true, 0);

            os = socket.getOutputStream();
            is = socket.getInputStream();
            bis = new BufferedInputStream(is);

            Log.d("ERROR!", "전송 준비전2");

            String sendLoc = lat + ", " + lon;

            // 데이터 전송 준비
            ByteBuffer sendByteBuffer = null;
            sendByteBuffer = ByteBuffer.allocate(32);

            sendByteBuffer.put(sendLoc.getBytes());
            if (sendLoc.length() < 32)
                sendByteBuffer.put(new byte[32 - sendLoc.getBytes().length]);

            Log.d("ERROR!", "전송 전");

            // 데이터 전송
            os.write(sendByteBuffer.array());
            os.flush();

            /// 데이터 수신 준비

            byte[] buff = new byte[64];
            int read = 0;

            // 데이터 수신
            while (true) {
                if (this.socket == null) {
                    break;
                }
                read = bis.read(buff, 0, 64);

                if (read < 0) {
                    break;
                } else if (read != 64) {
                    read += bis.read(buff, read, 64 - read);
                } else if (read == 64) {
                    break;
                }
            }
            // stationName
            byte[] stationNameArr = new byte[48];
            byte[] pm10Arr = new byte[4];
            byte[] pm25Arr = new byte[4];
            byte[] tempArr = new byte[4];
            byte[] tempRangeArr = new byte[4];

            System.arraycopy(buff, 0, stationNameArr, 0, 48);
            System.arraycopy(buff, 48, pm10Arr, 0, 4);
            System.arraycopy(buff, 52, pm25Arr, 0, 4);
            System.arraycopy(buff, 56, tempArr, 0, 4);
            System.arraycopy(buff, 60, tempRangeArr, 0, 4);

            stationName = new String(stationNameArr);
            stationName = stationName.replaceAll(" ", "");
            pm10 = new String(pm10Arr);
            pm10 = pm10.replaceAll(" ", "");
            pm25 = new String(pm25Arr);
            pm25 = pm25.replaceAll(" ", "");
            temp = new String(tempArr);
            tempRange = new String(tempRange);

            Weather.setPm10(Integer.parseInt(pm10));
            Weather.setPm25(Integer.parseInt(pm25));

            Log.d("SocketTest", "stationName : " + stationName + ", pm10 : " + Weather.getPm10() + ", pm2.5 : " + Weather.getPm25());


            MainActivity.setMainBgColor();

            socket.close();
            return;

        } catch (IOException e) {
            Log.d("ERROR!", e.toString());
        } catch (Exception e) {
            Log.d("ERROR!", e.toString());
        }

    }
}
