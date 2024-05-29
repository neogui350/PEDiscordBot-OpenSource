package me.neogui350.command;

import me.neogui350.utilutility.Paths;
import me.neogui350.utilutility.TimeSteamp;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static me.neogui350.Main.API_KEY;


public class ResidentCommand extends ListenerAdapter {

    String town_ranks;
    String nation_ranks;
    String uuid;
    String friends;
    String town;
    String name;
    String status;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("resident")){

            try {

                //Get argument to string
                OptionMapping nameMappingObjectF = event.getOption("resident_args_f");
                String nameF = nameMappingObjectF.getAsString();

                //URL to get JSON info
                String a = "https://planetearth.kr/api/resident.php?key=" + API_KEY + "&name=" + nameF;
                URL url = new URL(a);
                URLConnection conn = url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                //Command.getPEAPI("resident", "name", nameF);

                String inputLine;
                while ((inputLine = br.readLine()) != null){

                    JSONObject jsonObjectF = (JSONObject) new JSONParser().parse(inputLine);
                    Date today = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-z");

                    try {
                        FileWriter file = new FileWriter(Paths.jsonFileCommand("resident", "name", nameF));
                        file.write(jsonObjectF.toJSONString());
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONParser parser = new JSONParser();
                    Reader reader = new FileReader(Paths.jsonFileCommand("resident", "name", nameF));
                    JSONObject jsonObjectS = (JSONObject) parser.parse(reader);

                    if (jsonObjectS.get("status").equals("FAILED")){
                        EmbedBuilder embed_uuid = new EmbedBuilder();
                        embed_uuid.setDescription("정보를 불러오는데 실패했습니다. \n" +
                                "해당 닉네임을 가진 유저가 플래닛어스 데이터 베이스에 없거나 존재하지 않습니다. \n" +
                                "상태 : " + jsonObjectS.get("status"));
                        embed_uuid.setFooter("A Discord Bot made by neogui350");

                        event.replyEmbeds(embed_uuid.build()).queue();
                        break;
                    }

                    JSONArray jsonArray = (JSONArray) jsonObjectS.get("data");
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);


                    status = (String) jsonObjectS.get("status");
                    name = jsonObject.get("name").toString().replaceAll("_","\\\\_");
                    town = jsonObject.get("town").toString().replaceAll("_","\\\\_");
                    friends = jsonObject.get("friends").toString().replaceAll("_","\\\\_");
                    uuid = (String) jsonObject.get("uuid");

                    nation_ranks = (String) jsonObject.get("nationRanks");

                    if(!(town.equals(""))) {

                        String b = "https://planetearth.kr/api/town.php?key=" + API_KEY + "&name=" + town.replaceAll("\\\\","");
                        URL urlS = new URL(b);
                        URLConnection connS = urlS.openConnection();

                        BufferedReader brS = new BufferedReader(new InputStreamReader(connS.getInputStream()));

                        String inputLineS;
                        String nation = "";
                        while ((inputLineS = brS.readLine()) != null){
                            JSONObject TjsonObject = (JSONObject) new JSONParser().parse(inputLineS);
                            JSONArray jsonArray1 = (JSONArray) TjsonObject.get("data");
                            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(0);

                            String mayer = (String) jsonObject1.get("mayor");
                            nation = (String) jsonObject1.get("nation");

                            if (mayer.equals(name)){
                                town_ranks = "시장" + jsonObject.get("townRanks").toString().replaceAll("_","\\\\_");
                            } else {
                                town_ranks = jsonObject.get("townRanks").toString().replaceAll("_","\\\\_");
                            }
                        }
                        if (!(nation.equals(""))){

                            String c = "https://planetearth.kr/api/nation.php?key=" + API_KEY + "&name=" + nation;
                            URL urlT = new URL(c);
                            URLConnection connT = urlT.openConnection();

                            BufferedReader brT = new BufferedReader(new InputStreamReader(connT.getInputStream()));

                            String inputLineT;
                            while ((inputLineT = brT.readLine()) != null){
                                JSONObject FjsonObject = (JSONObject) new JSONParser().parse(inputLineT);
                                JSONArray jsonArray2 = (JSONArray) FjsonObject.get("data");
                                JSONObject jsonObject2 = (JSONObject) jsonArray2.get(0);

                                String king = (String) jsonObject2.get("leader");

                                if (king.equals(name)){
                                    nation_ranks = "국왕, " + jsonObject.get("nationRanks").toString().replaceAll("_","\\\\_");
                                } else {
                                    nation_ranks = jsonObject.get("nationRanks").toString().replaceAll("_","\\\\_");
                                }
                            }

                        }
                    }
                    String dateF = TimeSteamp.TimeSteamp(Long.valueOf((String) jsonObject.get("registered")));
                    String dateS = TimeSteamp.TimeSteamp(Long.valueOf((String) jsonObject.get("joinedTownAt")));

                    Color color = new Color(0, 128, 255);

                    EmbedBuilder embed_uuid = new EmbedBuilder();
                    embed_uuid.setTitle("유저 정보", null);
                    embed_uuid.setDescription("" +
                            "유저 이름 : " + name +
                            "\n소속 마을 : " + town +
                            "\n마을 권한 : "  + town_ranks +
                            "\n국가 권한 : " + nation_ranks +
                            "\n첫 접속 날짜 : "  + dateF +
                            "\n마을 가입 날짜 : " + dateS +
                            "\n친구 목록 : " + friends +
                            "\n유저 uuid : " + uuid +
                            "\n" +
                            "\n정보 불러오기 상태 : " + status);

                    embed_uuid.setFooter("A Discord Bot made by neogui350");
                    embed_uuid.setColor(color);

                    event.replyEmbeds(embed_uuid.build()).queue();



                }

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
