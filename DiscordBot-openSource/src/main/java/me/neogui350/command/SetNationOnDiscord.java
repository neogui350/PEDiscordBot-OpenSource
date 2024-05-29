package me.neogui350.command;

import me.neogui350.utilutility.Paths;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.failedEmbed;
import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.failedToProcessEmbed;

public class SetNationOnDiscord extends ListenerAdapter {
    private static String thisJavaClass(String text1, String text2){
        return "[me.neogui350.command.SetNationOnDiscord.java" + text1 + "] " + text2;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("set_nation")){
            OptionMapping nameMappingObject = event.getOption("set_nation");
            String name = nameMappingObject.getAsString();

            try {
                JSONObject jsonObject = new JSONObject(Command.getPAPI("nation", "name", name));
                JSONObject jsonObjectDiscord = new JSONObject(Command.getPAPI("discord", "discord", event.getMember().getId()));

                event.deferReply().queue();
                if (!(jsonObjectDiscord.get("status").equals("SUCCESS"))){
                    EmbedBuilder embed_uuid = failedEmbed("명령어를 사용한 사용자");
                    event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                    return;
                }

                if (!(jsonObject.get("status").equals("SUCCESS"))){
                    EmbedBuilder embed_uuid = failedEmbed("해당 국가 이름");
                    event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                } else {
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                    if (event.getMember().hasPermission(Permission.ADMINISTRATOR)){
                        String nationPath = ".\\LOGS\\JSON\\JSONFile-DiscordLinksNation\\" + Objects.requireNonNull(event.getGuild()).getId();
                        if (new File(nationPath).mkdirs()){
                            //파일 경로를 생성했을떄
                            String path = nationPath + "\\" + jsonObject1.get("uuid") + ".json";
                            new File(path);

//                            try {
//                                FileWriter file = new FileWriter(path);
//                                file.write(jsonObject1.toJSONString());
//                                file.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
                            System.out.println(thisJavaClass("64", "파일 생성됨"));


                        } else {
                            //파일 경로를 생성이 안됬을때
                        }
                    } else {
                        String Tf;
                        if (jsonObject1.get("leader") == jsonObjectDiscord.get("name")){
                            Tf = "TRUE";
                        } else {
                            Tf = "FALSE";
                        }
                        String ATF;
                        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)){
                            ATF = "TRUE";
                        } else {
                            ATF = "FALSE";
                        }
                        EmbedBuilder embed_uuid = failedToProcessEmbed("당신은 해당 명령어를 사용할수 없습니다. " +
                                "\n해당 명령어는 **플래닛어스 인게임 국가장 그리고 디스코드 서버내의 관리자 권한**을 소지하고 계셔야 사용할수 있습니다. \n" +
                                "\n디스코드 서버 관리자 권한 유무 : " + ATF +
                                "\n인게임 국가장 여부 : " + Tf);
                        event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                    }
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
