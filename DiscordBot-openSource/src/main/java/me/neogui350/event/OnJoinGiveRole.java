package me.neogui350.event;

import me.neogui350.command.Command;
import me.neogui350.utilutility.GetPlayerNationTown;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class OnJoinGiveRole extends ListenerAdapter {
    private static boolean hasRole(Member member, Role role) {
        List<Role> memberRoles = member.getRoles();
        return memberRoles.contains(role);
    }
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            JSONObject jsonObjectM = Command.getPAPI("discord", "discord", event.getMember().getId());
            if (jsonObjectM.get("status").equals("SUCCESS")){


                JSONArray jsonArray = (JSONArray) jsonObjectM.get("data");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                String memberName = jsonObject1.get("name").toString();

                Member member = event.getMember();
                member.modifyNickname(memberName).queue();
                String verifyPath= ".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\Verify\\" + event.getGuild().getId() + ".json";
                new File(".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\Verify\\").mkdirs();
                if (!(new File(verifyPath).exists())){
                    Role role = event.getGuild().createRole().setName("Verified by RaccoonBot").setMentionable(false).complete();
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
                } else {
                    JSONParser parser = new JSONParser();
                    Reader reader = new FileReader(verifyPath);
                    JSONObject jsonObject2 = (JSONObject) parser.parse(reader);

                    Long roleID = (Long) jsonObject2.get("role-id");
                    Role retrievedRole = event.getGuild().getRoleById(roleID);

                    assert retrievedRole != null;
                    event.getGuild().addRoleToMember(member,retrievedRole).queue();
                }

                //국가 불러오는 함수를 이용해 국가 UUID를 불러옴
                String nationsUuid = GetPlayerNationTown.GetNationUuidByName(memberName).replaceAll("_", "");

                //국가가 존재하지 않을때
                if (!nationsUuid.equals("")) {
                    //파일,경로
                    String path = ".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId() + "\\" + nationsUuid + ".json";
                    String UserPath = ".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId() + "\\UsersUuid\\" + member.getId() + "\\info.json";
                    new File(".\\LOGS\\JSON\\JSONFile-nationsUuidWithDiscordId\\" + event.getGuild().getId()).mkdirs(); //경로 생성
                    String nationsName = GetPlayerNationTown.GetNationByName(memberName); //국가 이름을 가져옴

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

                            System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 파일 생성됨 : " + UserPath);
                            System.out.println("[me.neogui350.event.OnJoinGiveRole.java] JSON파일 내용 : " + jsonObjectWrite);

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

                                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 파일 수정됨 : " + UserPath);
                                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] JSON파일 수정된 내용 : " + jsonObjectWrite);

                            } else {
                                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 파일 수정안됨 : " + UserPath);
                            }
                        }

                        assert retrievedRole != null;
                        event.getGuild().addRoleToMember(member,retrievedRole).queue();
                        System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 역할 지급됨 : (대상) " + member + " (지급된 역할) " + retrievedRole);

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

                            System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 파일 생성됨 : " + UserPath);
                            System.out.println("[me.neogui350.event.OnJoinGiveRole.java] JSON파일 내용 : " + jsonObjectWrite);

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

                                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 파일 수정됨 : " + UserPath);
                                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] JSON파일 수정된 내용 : " + jsonObjectWrite);

                            } else {
                                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 파일 수정안됨 : " + UserPath);
                            }
                        }

                        if (!hasRole(member, retrievedRole)){
                            assert retrievedRole != null;
                            event.getGuild().addRoleToMember(member, retrievedRole).queue();
                            System.out.println("[me.neogui350.event.OnJoinGiveRole.java] 역할 지급됨 : (대상) " + member + " (지급된 역할) " + retrievedRole);

                        }

                    }
                }

            } else {
                System.out.println("[me.neogui350.event.OnJoinGiveRole.java] Failed to load \"SUCCESS\" value.\n" +
                        event.getMember() + "\n" + jsonObjectM);
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
