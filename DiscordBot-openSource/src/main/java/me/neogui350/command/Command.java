package me.neogui350.command;

import me.neogui350.utilutility.Paths;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static me.neogui350.Main.API_KEY;

public class Command extends ListenerAdapter {

    public static JSONObject getPAPI(String typeF, String typeS, String name) throws IOException, ParseException {

        String a = "https://planetearth.kr/api/" + typeF + ".php?key=" + API_KEY + "&" + typeS +"=" + name;
        URL url = new URL(a);
        URLConnection conn = url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;
        while ((inputLine = br.readLine()) != null){
            JSONObject jsonObjectF = (JSONObject) new JSONParser().parse(inputLine);
            String path = Paths.jsonFileCommand(typeF, typeS, name);

            if (!jsonObjectF.get("status").equals("SUCCESS")){
                return jsonObjectF;
            }

            try {
                FileWriter file = new FileWriter(path);
                file.write(jsonObjectF.toJSONString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return jsonObjectF;
        }

        return null;
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();


        OptionData optionResidentF = new OptionData(OptionType.STRING, "resident_args_f", "Name of the player", true);
        commandData.add(Commands.slash("resident", "Look for information of player").addOptions(optionResidentF));

        OptionData optionTownF = new OptionData(OptionType.STRING, "town_name", "Name of town", true);
        OptionData optionTownS = new OptionData(OptionType.BOOLEAN, "town_details", "Show details of town", false);
        commandData.add(Commands.slash("town", "Look for information of town").addOptions(optionTownF, optionTownS));

        commandData.add(Commands.slash("ushil", "Reply with 유실 바보"));
        commandData.add(Commands.slash("carr0tholic", "Reply with 당근 바보"));
        commandData.add(Commands.slash("아홀", "Reply with 아홀 바보"));
        commandData.add(Commands.slash("바이러스","Reply with 독일 열사 만세!"));
        commandData.add(Commands.slash("너구이","Reply with 또 졌어!"));
        commandData.add(Commands.slash("라시","RElpy with 븅신 (이거 친구가 넣음)"));
        commandData.add(Commands.slash("이터에그","Relpy with 이걸 진짜 쳐보시다니 ㄷㄷ;;"));

        OptionData optionUuidCF = new OptionData(OptionType.STRING, "player_uuid", "UUID of player", true);
        commandData.add(Commands.slash("uuid_check","Check for the player uuid").addOptions(optionUuidCF));

        OptionData optionNationF = new OptionData(OptionType.STRING, "nation_name", "Name of nation", true);
        commandData.add(Commands.slash("nation","Look for information of nation").addOptions(optionNationF));

        OptionData optionDataReloadUserData = new OptionData(OptionType.USER, "reload_user_name", "Mention user", false);
        commandData.add(Commands.slash("reload_user_data", "Update user name and roles").addOptions(optionDataReloadUserData));

        OptionData optionSetNation = new OptionData(OptionType.STRING, "set_nation", "Name of nation", true);
        commandData.add(Commands.slash("set_nation", "Linking the discord server with nation").addOptions(optionSetNation));

        OptionData optionWarChest = new OptionData(OptionType.STRING, "wc_town_name", "Name of town", true);
        commandData.add(Commands.slash("war_chest_check", "Count War Chest of this town").addOptions(optionWarChest));


        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}