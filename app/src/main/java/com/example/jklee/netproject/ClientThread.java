package com.example.jklee.netproject;

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
    }

    protected String getStationName() { return stationName; }
    protected String getPm10() { return pm10; }
    protected String getPm25() { return pm25; }
    protected String getTemp() { return temp; }
    protected String getTempRange() { return tempRange; }

    public void run() {
        try {
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

            String lat = "37.23421"; // Get Location Here (lat)
            String lon = "128.12381"; // Get Location Here (lon)

            /* 데이터 전송 준비 */
            ByteBuffer sendByteBuffer = null;
            sendByteBuffer = ByteBuffer.allocate(lat.length() + lon.length());
            sendByteBuffer.put(lat.getBytes());
            sendByteBuffer.put(lon.getBytes());
            /*
            sendByteBuffer.put(x.getBytes()); // 바이트를 꼭 채워야 함 (특정 바이트 수대로)
            sendByteBuffer.put(y.getBytes()); // 프로토콜 정해졌을 시 재구현
            // 만약 10bytes 전송인데 x가 7바이트라면...
            sendByteBuffer.put(new byte[10 - x.getBytes().length]);
            */

            /* 데이터 전송 */
            os.write(sendByteBuffer.array());
            os.flush();

            /* 데이터 수신 준비 */


            byte[] buff = new byte[64];

            int read = 0;
            /* 데이터 수신 */
            while (true) {
                if (this.socket == null) {
                    break;
                }
                read = bis.read(buff, 0, 64);

                if (read < 0) {
                    break;
                }

                else if(read != 64) {
                    read += bis.read(buff, read, 64-read);
                }

                else if(read == 64) {
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
            System.arraycopy(buff, 52, pm25Arr,0,4);
            System.arraycopy(buff, 56, tempArr,0,4);
            System.arraycopy(buff, 60, tempRangeArr,0,4);

            stationName = new String(stationNameArr);
            pm10 = new String(pm10Arr);
            pm25 = new String(pm25Arr);
            temp = new String(tempArr);
            tempRange = new String(tempRange);

        } catch(IOException e) {

        } finally {
            try {
                socket.close();
            }
            catch(IOException e) {

            }
        }
    }
}
