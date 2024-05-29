package me.neogui350.utilutility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static me.neogui350.Main.API_KEY;

/**
 * 국가권한 불러오는건데 rate limit에 걸려서 현제 사용 불가
 * 국가나 마을 엔드포인트에 유저 정보들 순서대로 전부 불러오는거 아닌이상 사용하기는 힘듦
 */

public class GetPlayerNationRanks {
    public static String NationRanks(String playerNames) throws IOException, ParseException {
            String[] names = playerNames.split(",");
            for (int i = 0; i < names.length; i++ ){
                String a = "https://planetearth.kr/api/resident.php?key=" + API_KEY + "&name=" + names[i];
                URL url = new URL(a);
                URLConnection conn = url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                while ((inputLine = br.readLine()) != null){
                    JSONObject jsonObjectF = (JSONObject) new JSONParser().parse(inputLine);

                    JSONArray jsonArray = (JSONArray) jsonObjectF.get("data");
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                    return jsonObject.get("nation-ranks").toString();



                }
            }
        return null;
    }
}
