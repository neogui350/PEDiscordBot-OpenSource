package me.neogui350.utilutility.GiveRolesToMember;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class verifiedRole {
    public static boolean verifyMember(SlashCommandInteraction event, Member member) throws IOException, ParseException {
        String verifyPath= ".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\Verify\\" + event.getGuild().getId() + ".json";
        new File(".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\Verify\\").mkdirs();

        //인증 역할 파일이 존재한지 확인후 역할 지급
        if (!(new File(verifyPath).exists())){
            Role role = event.getGuild().createRole().setName("verified by RaccoonBot").setMentionable(false).complete();
            long roleID = role.getIdLong();
            Role retrievedRole = event.getGuild().getRoleById(roleID);

            assert retrievedRole != null;
            event.getGuild().addRoleToMember(member,retrievedRole).queue();

            JSONObject jsonObjectWrite = new JSONObject();
            jsonObjectWrite.put("server-id",event.getGuild().getId());
            jsonObjectWrite.put("role-id",roleID);
            try {
                FileWriter file = new FileWriter(verifyPath);
                file.write(jsonObjectWrite.toString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(verifyPath);
            JSONObject jsonObject2 = (JSONObject) parser.parse(reader);

            Long roleID = (Long) jsonObject2.get("role-id");
            Role retrievedRole = event.getGuild().getRoleById(roleID);

            assert retrievedRole != null;
            event.getGuild().addRoleToMember(member,retrievedRole).queue();

            return false;
        }
    }
}
