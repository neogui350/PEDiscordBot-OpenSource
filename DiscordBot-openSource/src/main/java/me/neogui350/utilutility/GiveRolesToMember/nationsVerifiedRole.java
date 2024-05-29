package me.neogui350.utilutility.GiveRolesToMember;

import me.neogui350.utilutility.GetPlayerNationTown;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.successEmbed;

public class nationsVerifiedRole {
    private static boolean hasRole(Member member, Role role) {
        List<Role> memberRoles = member.getRoles();
        return memberRoles.contains(role);
    }
    public static boolean nationsVerifiedMember(SlashCommandInteraction event, Member member, String memberName) throws IOException, ParseException {
        String nationsUuid = GetPlayerNationTown.GetNationUuidByName(memberName).replaceAll("_", "");

        //국가가 존재한지 확인하고 역할 지급
        if (!nationsUuid.equals("")) {
            event.deferReply().queue();

            String path = ".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId() + "\\" + nationsUuid + ".json";
            String UserPath = ".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId() + "\\UsersUuid\\" + member.getId() + "\\info.json";
            new File(".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId()).mkdirs();
            String nationsName = GetPlayerNationTown.GetNationByName(memberName);

            //파일 존재 무일때 역할 지급
            if (!(new File(path).exists())) {

                Role role = event.getGuild().createRole().setName(nationsName).setMentionable(false).complete();
                long roleID = role.getIdLong();
                Role retrievedRole = event.getGuild().getRoleById(roleID);

                if (new File(".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId() + "\\UsersUuid\\" + member.getId()).mkdirs()){
                    //해당 경로가 존재하지 않아 생성했을때

                    JSONObject jsonObjectWrite = new JSONObject();
                    jsonObjectWrite.put("discord-name",memberName);
                    jsonObjectWrite.put("nation-uuid",nationsUuid);
                    jsonObjectWrite.put("nation-name",nationsName);
                    jsonObjectWrite.put("role-id",roleID);
                    try {
                        FileWriter file = new FileWriter(UserPath);
                        file.write(jsonObjectWrite.toString());
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("[nationsVerifiedRole.java57] 파일 생성됨 : " + UserPath);
                    System.out.println("[nationsVerifiedRole.java58] JSON파일 내용 : " + jsonObjectWrite);

                } else {    //해당 경로가 이미 존재할경우
                    JSONParser parser = new JSONParser();
                    Reader reader = new FileReader(UserPath);
                    JSONObject jsonObject = (JSONObject) parser.parse(reader);

                    Role retrievedRole1 = event.getGuild().getRoleById(Long.parseLong(jsonObject.get("role-id").toString()));
                    if (Long.parseLong(jsonObject.get("role-id").toString()) != roleID && hasRole(member, retrievedRole1)){
                        assert retrievedRole1 != null;
                        event.getGuild().removeRoleFromMember(member,retrievedRole1).queue();

                        JSONObject jsonObjectWrite = new JSONObject();
                        jsonObjectWrite.put("discord-name",memberName);
                        jsonObjectWrite.put("nation-uuid",nationsUuid);
                        jsonObjectWrite.put("nation-name",nationsName);
                        jsonObjectWrite.put("role-id",roleID);

                        try {
                            FileWriter file = new FileWriter(UserPath);
                            file.write(jsonObjectWrite.toString());
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("[nationsVerifiedRole.java84] 파일 수정됨 : " + UserPath);
                        System.out.println("[nationsVerifiedRole.java85] JSON파일 수정된 내용 : " + jsonObjectWrite);

                    } else {
                        System.out.println("[nationsVerifiedRole.java88] 파일 수정안됨 : " + UserPath);
                    }
                }

                assert retrievedRole != null;
                event.getGuild().addRoleToMember(member,retrievedRole).queue();
                System.out.println("[nationsVerifiedRole.java94] 역할 지급됨 : (대상) " + member + " (지급된 역할) " + retrievedRole);

                JSONObject jsonObjectWrite = new JSONObject();
                jsonObjectWrite.put("server-id",event.getGuild().getId());
                jsonObjectWrite.put("nation-uuid",nationsUuid);
                jsonObjectWrite.put("nation-name",nationsName);
                jsonObjectWrite.put("role-id",roleID);
                try {
                    FileWriter file = new FileWriter(path);
                    file.write(jsonObjectWrite.toString());
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EmbedBuilder embedBuilder = successEmbed(member.getAsMention() + "님의 역할을 업데이트 했습니다. 1");
                event.getHook().editOriginalEmbeds(embedBuilder.build()).queue();

                return true;

            } else { //파일 존재 유 확인후 역할 지급여부
                JSONParser parser = new JSONParser();
                Reader reader = new FileReader(path);
                JSONObject jsonObject2 = (JSONObject) parser.parse(reader);

                Long roleID = (Long) jsonObject2.get("role-id");
                Role retrievedRole = event.getGuild().getRoleById(roleID);

                if (new File(".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId() + "\\UsersUuid\\" + member.getId()).mkdirs()){
                    //해당 경로가 존재하지 않아 생성했을때

                    JSONObject jsonObjectWrite = new JSONObject();
                    jsonObjectWrite.put("discord-name",memberName);
                    jsonObjectWrite.put("nation-uuid",nationsUuid);
                    jsonObjectWrite.put("nation-name",nationsName);
                    jsonObjectWrite.put("role-id",roleID);
                    try {
                        FileWriter file = new FileWriter(UserPath);
                        file.write(jsonObjectWrite.toString());
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("[nationsVerifiedRole.java137] 파일 생성됨 : " + UserPath);
                    System.out.println("[nationsVerifiedRole.java138] JSON파일 내용 : " + jsonObjectWrite);

                } else {    //해당 경로가 이미 존재할경우
                    JSONParser parser1 = new JSONParser();
                    Reader reader1 = new FileReader(UserPath);
                    JSONObject jsonObject = (JSONObject) parser1.parse(reader1);

                    Role retrievedRole1 = event.getGuild().getRoleById(Long.parseLong(jsonObject.get("role-id").toString()));

                    if (Long.parseLong(jsonObject.get("role-id").toString()) != roleID && hasRole(member, retrievedRole1)){
                        assert retrievedRole1 != null;
                        event.getGuild().removeRoleFromMember(member,retrievedRole1).queue();

                        JSONObject jsonObjectWrite = new JSONObject();
                        jsonObjectWrite.put("discord-name",memberName);
                        jsonObjectWrite.put("nation-uuid",nationsUuid);
                        jsonObjectWrite.put("nation-name",nationsName);
                        jsonObjectWrite.put("role-id",roleID);

                        try {
                            FileWriter file = new FileWriter(UserPath);
                            file.write(jsonObjectWrite.toString());
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("[nationsVerifiedRole.java165] 파일 수정됨 : " + UserPath);
                        System.out.println("[nationsVerifiedRole.java166] JSON파일 수정된 내용 : " + jsonObjectWrite);

                    } else {
                        System.out.println("[nationsVerifiedRole.java169] 파일 수정안됨 : " + UserPath);
                    }
                }

                if (!hasRole(member, retrievedRole)){
                    assert retrievedRole != null;
                    event.getGuild().addRoleToMember(member, retrievedRole).queue();
                    System.out.println("[nationsVerifiedRole.java176] 역할 지급됨 : (대상) " + member + " (지급된 역할) " + retrievedRole);

                    EmbedBuilder embedBuilder = successEmbed(member.getAsMention() + "님의 역할을 업데이트 했습니다. 2" +
                            "\n업데이트 내역 : " + retrievedRole.getName().replaceAll("_", "\\\\_"));
                    event.getHook().editOriginalEmbeds(embedBuilder.build()).queue();
                } else {
                    System.out.println("[nationsVerifiedRole.java177] 역할 지급안됨 : (대상) " + member);

                    EmbedBuilder embedBuilder = successEmbed(member.getAsMention() + "님의 역할은 업데이트할게 없습니다. 3");
                    event.getHook().editOriginalEmbeds(embedBuilder.build()).queue();
                }


                return true;
            }
        }
        return false;
    }
}
