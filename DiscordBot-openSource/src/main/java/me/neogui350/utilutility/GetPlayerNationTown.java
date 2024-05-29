package me.neogui350.utilutility;

import me.neogui350.logger.PlayerLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static me.neogui350.Main.API_KEY;

/**
 * GetPlayerInfoToJson() - 유저 정보를 json파일에 저장하고 리턴값으로 돌려줌 - 제작중
 * GetPlayerUuidInfo() - 유저uuid로 정보를 json파일에 저장하고 리텃값으로 돌려줌 - 미제작
 * GetNationByName() - 유저 닉으로 현제 소속된 국가를 불러옴 - 국가 왕, 부왕, 이전 왕 또는 부왕 확인할수 있게 로그 생각중
 * GetNationUuidByName() - 유저 닉으로 현제 소속된 국가uuid를 불러옴 - 국가 uuid 확인하는 명령어 추가 에정
 * GetTownByName() - 유저 닉으로 현제 소속된 마을 이름을 불러옴 - 나중에 json 파일레 이전 국가 소속, 마을장, 부시장, 트러스트 확인할수 있게 로그 생각중
 * GetTownUuidByName() - 미제작 - 유저 닉으로 현제 소속된 마을uuid를 불러옴 - 마을 uuid 확인하는 명령어도 같이 추가
 */


public class GetPlayerNationTown {

    //시간
    static Date today = new Date();
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");

    //대충 유저 정보를 json파일로 만들어주는 코드인데 아직 다 못만듬
    public static boolean GetPlayerInfoToJson(String nationOrTown, String nameOrUuid, String name) throws IOException, ParseException {
        if (!(nationOrTown.equals("town")) || !(nationOrTown.equals("nation")) && !(nameOrUuid.equals("name")) || !(nameOrUuid.equals("uuid"))){
            return false;
        }

        String path = ".\\LOGS\\JSON\\JSONFile-playersINFO\\" + name + "\\" + name + "-" + format.format(today) + ".json";

        String a = "https://planetearth.kr/api/" + nationOrTown + ".php?key=" + API_KEY + "&" + nameOrUuid + "=" + name;
        URL url = new URL(a);
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine = br.readLine();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputLine);

        try {
            FileWriter file = new FileWriter(path);
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }


    //유저 닉으로 국가 불러오는 함수
    public static String GetNationByName(String name) throws IOException, ParseException {
        //유저 정보 저장 경로
        String path = ".\\LOGS\\JSON\\JSONFile-playersINFO\\" + name + "\\" + name + "-" + format.format(today) + ".json";

        //유저 정보를 json 파일로 만들어주는 함수, 참일경우 정보를 제대로 불러온거, 거짓일경우 정보를 제대로 못불러옴 = 플어 정보에 없거나 rate limit에 걸린거임, 이것도 로그로 출력해야됨
        if (!(new File(path).exists())){
            boolean TF = GetPlayerInfoToJson("nation", "name", name);

        }
        //밑에 코드를 위에 유저 정보를 json 파일로 만들어주는 함수로 옮겨야됨

        //try문, 예외처리가 필요한 부분임
        try{
            //마을 이름을 마을 이름 가져오는 함수에서 챙겨옴
            String town_name = GetTownByName(name);

            //URL to get JSON info
            String a = "https://planetearth.kr/api/town.php?key=" + API_KEY + "&name=" + town_name.replaceAll("\\\\", "");
            URL url = new URL(a);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = br.readLine();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputLine);

            if (jsonObject.get("status").equals("SUCCESS")){
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                if (!(jsonObject1.get("nation").equals(""))){
                    return jsonObject1.get("nation").toString().replaceAll("_", "\\_");
                }

//                PlayerLogger.PlayerMinecraftDataLogger("name", "", "" + jsonObject1.get("nation"), "" + jsonObject1.get("uuid"), town_name, "Not available yet", "Not available yet");
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return "";
    }


    public static String GetNationUuidByName(String name){

        try{

            //URL to get JSON info
            String a = "https://planetearth.kr/api/town.php?key=" + API_KEY + "&name=" + GetTownByName(name).replaceAll("\\\\", "");
            URL url = new URL(a);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = br.readLine();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputLine);

            if (jsonObject.get("status").equals("SUCCESS")){
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                if (!(jsonObject1.get("uuid").equals(""))){
                    return jsonObject1.get("uuid").toString().replaceAll("_", "\\_");
                }

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return "";
    }


    public static String GetTownByName(String name){

        try{
            //URL to get JSON info
            String a = "https://planetearth.kr/api/resident.php?key=" + API_KEY + "&name=" + name;
            URL url = new URL(a);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = br.readLine();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(inputLine);

            if (jsonObject.get("status").equals("SUCCESS")){
                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                return jsonObject1.get("town").toString().replaceAll("_", "\\\\_");

            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

}
