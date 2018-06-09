package com.example.jklee.netproject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class Location {

    private static double latitude;
    private static double longitude;
    private static String address ;
    private static GpsInfo gps ;

    public static void setLocation(double latitude, double longitude) {
        Location.latitude = latitude;
        Location.longitude = longitude;
    }

    public static void setAddress(String address)
    {
        Location.address = address;
    }

    public static double getLatitude() {
        return Location.latitude;
    }

    public static double getLongitude() {
        return Location.longitude;
    }

    public static String getAddress(){
        return Location.address;
    }

    public  static void getLocation(Context c ) {
        gps = new GpsInfo(c);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation())
         Location.setLocation(gps.getLatitude(), gps.getLongitude());
        else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }

        Geocoder geocoder = new Geocoder(c);
        List<Address> list = null;
        Location.setAddress("");
        try {
            list = geocoder.getFromLocation(Location.getLatitude(), Location.getLongitude(), 1);
            if (list != null) {
                if (list.size() == 0) {
                    Toast.makeText(c, "주소가 확인되지 않습니다.", Toast.LENGTH_SHORT);
                } else {

                    if (!list.get(0).getCountryCode().toString().equals("KR")) {
                        Toast.makeText(c, "해외는 지원하지 않습니다.", Toast.LENGTH_SHORT);
                    } else {

                        int i, j = 0;
                        Log.d("address", list.get(0).getAddressLine(0).toString());

                        //주소 중 ~~시 까지 제끼기
                        for (i = 0; i < list.get(0).getAddressLine(0).toString().length(); i++) {
                            if (list.get(0).getAddressLine(0).charAt(i) == ' ') {
                                j++;
                                if (j >= 2)
                                    break;
                            }
                        }

                        //첫번째 주소 저장
                        for (j = 0; j < list.get(0).getAddressLine(0).toString().length(); j++) {
                            Location.setAddress(Location.getAddress().concat(String.valueOf(list.get(0).getAddressLine(0).charAt(++i))));
                            if (list.get(0).getAddressLine(0).charAt(i + 1) == ' ')
                                break;
                        }

                        Location.setAddress(Location.getAddress().concat(","));
                        //두번째 주소 저장
                       for (j = 0; j < list.get(0).getAddressLine(0).toString().length(); j++) {
                            Location.setAddress(Location.getAddress().concat(String.valueOf(list.get(0).getAddressLine(0).charAt(++i))));
                            if (list.get(0).getAddressLine(0).charAt(i + 1) == ' ')
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GEOCODER", "서버에서 주소변환시 에러발생");
        }
    }

}