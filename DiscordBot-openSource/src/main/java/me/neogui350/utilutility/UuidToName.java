package me.neogui350.utilutility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class UuidToName {

    /**
     * 모장 API
     * 분당 200회 근처
     * **/
    public static Object uuidToName(String uuid) throws IOException, ParseException {
        if (Objects.equals(uuid, "")){
            System.out.println("[me/neogui350/utilutility/UuidToName.java] UUID IS EMPTY!! Return \"[ERROR]\" for Object. ");
            return "*[ERROR]*";
        }

        String a = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid;
        URL url = new URL(a);
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;
        FileWriter file = new FileWriter(Paths.jsonFileUuid(uuid));
        while ((inputLine = br.readLine()) != null && !(inputLine.equals(" "))){
            try {
                file.write(inputLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.close();
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(Paths.jsonFileUuid(uuid));
        JSONObject jsonObjectS = (JSONObject) parser.parse(reader);
        System.out.println("[me/neogui350/utilutility/UuidToName.java] uuid = " + uuid + ", name = " + jsonObjectS.get("name"));
        return jsonObjectS.get("name");
    }
}
