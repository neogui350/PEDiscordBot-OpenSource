package me.neogui350.command;

import me.neogui350.utilutility.replyEmbeds.ReplyEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import me.neogui350.utilutility.TimeSteamp;

import java.awt.*;
import java.io.IOException;

public class NationCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("nation")){
//Get argument to string
            OptionMapping nameMappingObjectF = event.getOption("nation_name");
            String nameF = nameMappingObjectF.getAsString();

            try {
                JSONObject jsonObject = new JSONObject(Command.getPAPI("nation", "name", nameF));
                if (!(jsonObject.get("status").equals("SUCCESS"))){
                    EmbedBuilder embed_uuid = ReplyEmbeds.failedEmbed(nameF);

                    event.replyEmbeds(embed_uuid.build()).queue();
                } else {
                    event.deferReply().queue();
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                    //name, member_count, capital, leader, towns, allies, enemies, registered, nationBoard, nationSpawn, uuid

                    Color color = new Color(0, 128, 255);

                    EmbedBuilder embed_uuid = new EmbedBuilder();
                    embed_uuid.setTitle((nameF + " 국가 정보").replaceAll("_","\\\\_"), null);
                    String num1 = TimeSteamp.TimeSteamp(Long.valueOf((String) jsonObject1.get("registered")));
                    String[] tem_ = jsonObject1.get("nationSpawn").toString().split(",");
                    embed_uuid.setDescription("" +
                            "국가 이름 : " + nameF.replaceAll("_","\\\\_") +
                            "\n" +
                            "\n국가원 수 : " + jsonObject1.get("memberCount").toString().replaceAll("_","\\\\_").replaceAll("\\[","").replaceAll("\\]","") +
                            "\n국왕 : " + jsonObject1.get("leader").toString().replaceAll("_","\\\\_") +
                            "\n부왕 : (개발중)" +
                            "\n부관 : (개발중)" +
                            "\n군인 : (개발중)" +
                            "\n" +
                            "\n수도 : " + jsonObject1.get("capital").toString().replaceAll("_","\\\\_") +
                            "\n마을 : " + jsonObject1.get("towns").toString().replaceAll("_","\\\\_") +
                            "\n" +
                            "\n동맹 : " + jsonObject1.get("allies").toString().replaceAll("_","\\\\_") +
                            "\n적 : " + jsonObject1.get("enemies").toString().replaceAll("_","\\\\_") +
                            "\n" +
                            "\n설립일 : " + num1 +
                            "\n국가 보드 : " + jsonObject1.get("nationBoard") +
                            "\n국가 스폰 좌표 : " + tem_[1] + ", y = " + tem_[2] + ", z = " + tem_[3] +
                            "\n국가 uuid : " + jsonObject1.get("uuid") +
                            "\n정보 불러오기 상태 : " + jsonObject.get("status"));

                    embed_uuid.setFooter("A Discord Bot made by neogui350");
                    embed_uuid.setColor(color);

                    event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();

                }


            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
