package com.example.jklee.netproject;

import java.util.ArrayList;
import java.util.List;

public class Recommend
{

    public static List<String> recommendation = new ArrayList<String>();

    public static void setRecommend(Weather weather)
    {
        int temperature = weather.getTemperature();
        int dust = weather.getDustDensity();

        if (temperature < 4)
            recommendation.add("\"날이 매우 춥습니다. 두꺼운 패딩점퍼에 목도리는 어떠신가요?\"");
        else if (temperature < 8)
            recommendation.add("\"날이 춥습니다. 코트나 가죽 자켓은 어떠세요?\"");
        else if (temperature < 11)
            recommendation.add("\"추위를 느낄 수 있으니 옷을 여러벌 껴입는 건 어떨까요?\"");
        else if (temperature < 16)
            recommendation.add("\"날이 쌀쌀하네요. 외투를 꼭 챙겨주세요\"");
        else if (temperature < 19)
            recommendation.add("\"날이 따듯하네요! 가볍게 맨투맨에 청바지 어떠세요?\"");
        else if (temperature < 22)
            recommendation.add("\"날이 따듯하네요! 얇은 긴팔 티셔츠를 입는건 어떠세요?\"");
        else if (temperature < 27)
            recommendation.add("\"대체적으로 따듯하나 조금 더울 수 있어요. 반팔 티셔츠를 입는 건 어떨까요?\"");
        else
            recommendation.add("\"날이 무덥습니다. 시원한 옷차림으로 입으시는건 어떠세요?\"");

        if (temperature > 27 && weather.getWeather().equals("sunny")) {
            recommendation.add("\"햇빛이 강렬합니다. 선글라스를 착용하는건 어떨까요?\"");
            MainActivity.sunglassesOn();
        } else
            MainActivity.sunglassesOff();

        if (weather.getWeather().equals("rainy")) {
            recommendation.add("\"비가 옵니다. 우산을 챙겨주세요 :)\"");
            MainActivity.umbrellaOn();
        } else
            MainActivity.umbrellaOff();

        if (dust > 80) {
            recommendation.add("\"미세먼지로부터 보호할 마스크를 챙겨주세요 !\"");
            recommendation.add("\"실외활동을 자제해주세요.\"");
            MainActivity.maskOn();
            if (dust > 120)
                recommendation.add("\"미세먼지가 심하네요. 실내에서 즐길 수 있는 운동을 해보세요.\"");
            if (dust > 200)
                recommendation.add("\"미세먼지 때문에 밖에 나갈 수 없겠어요. 탁구나 볼링 한판 어떠세요?\"");
        } else
            MainActivity.maskOff();
    }

    public static int getSize()
    {
        return recommendation.size();
    }

    public static String getString(int i)
    {
        return recommendation.get(i);
    }

    public static void resetRecommend()
    {
        recommendation.clear();
    }
}
