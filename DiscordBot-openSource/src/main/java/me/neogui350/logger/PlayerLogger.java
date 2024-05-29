package me.neogui350.logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * {
 *   "name":"playerName",
 *   "uuid":"playerUuid",
 *   "updated-server":"serverID",
 *   "data":[
 *     {
 *         "updated-date":"date",
 *         "nation-name": "",
 *         "nation-uuid": "",
 *         "town-name": "",
 *         "town-uuid": "",
 *         "friends": [{"name1":"uuid1", "name2":"uuid2"}]
 *     }
 *   ],
 *   "old-data":[
 *     {
 *         "last-updated-date":"lastDate",
 *         "last-name": "",
 *         "last-nations-name": "",
 *         "last-nations-uuid": "",
 *         "last-town-name": "",
 *         "last-town-uuid": "",
 *         "was-friends": [{"name1":"uuid1", "name2":"uuid2"}]
 *     }
 *   ]
 * }
 */


public class PlayerLogger {
    private static String path = ".\\LOGS\\JSONLOGS\\";
    public static void PlayerMinecraftDataLogger(String player, String uuid, String nations, String nations_uuid, String town,String town_uuid, String friends) throws IOException, ParseException {
        String pathPlayerLogger = path + "\\PlayersINFOLogs\\" + uuid + ".json";

        JSONObject jsonObjectWrite = new JSONObject();
        JSONObject jsonArrayWrite = new JSONObject();
        JSONObject jsonArrayOldWrite = new JSONObject();

        File f = new File(pathPlayerLogger);
        if(f.exists() && !f.isDirectory()) {
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(pathPlayerLogger);
            JSONObject jsonObjectRead = (JSONObject) parser.parse(reader);

            jsonArrayWrite.put("last-name", jsonObjectRead.get("name"));
            jsonArrayWrite.put("last-nations-name", jsonObjectRead.get("nations-name"));
            jsonArrayWrite.put("last-nations-uuid", jsonObjectRead.get("nations-uuid"));
            jsonArrayWrite.put("last-town-name", jsonObjectRead.get("town-name"));
            jsonArrayWrite.put("last-town-uuid", jsonObjectRead.get("town-uuid"));
        }

        jsonObjectWrite.put("name", player);
        jsonObjectWrite.put("uuid", uuid);
        jsonObjectWrite.put("nation-name", nations);
        jsonObjectWrite.put("nation-uuid", nations_uuid);
        jsonObjectWrite.put("town-name", town);
        jsonObjectWrite.put("town-uuid", town_uuid);
        jsonObjectWrite.put("friends", friends);
        jsonObjectWrite.put("last-info", jsonArrayWrite);

        try{
            FileWriter file = new FileWriter(pathPlayerLogger);
            file.write(jsonObjectWrite.toJSONString());
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
