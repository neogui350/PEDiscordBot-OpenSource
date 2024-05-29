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
import me.neogui350.utilutility.UuidToName;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TownCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("town")){

            //Get argument to string
            OptionMapping nameMappingObjectF = event.getOption("town_name");
            String nameF = nameMappingObjectF.getAsString();

            try {
                JSONObject jsonObject = new JSONObject(Command.getPAPI("town", "name", nameF));

                if (!(jsonObject.get("status").equals("SUCCESS"))){
                    EmbedBuilder embed_uuid = ReplyEmbeds.failedEmbed(nameF);

                    event.replyEmbeds(embed_uuid.build()).queue();
                } else {
                    event.deferReply().queue();
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                    Color color = new Color(0, 128, 255);

                    OptionMapping nameMappingObjectS = event.getOption("town_details");
                    String nameS = "false";
                    if (nameMappingObjectS != null){
                        nameS = nameMappingObjectS.getAsString();
                    }

                    if (nameS.equals("true")){
                        String[] uuid = ((String) jsonObject1.get("trustedResidents")).split(", ");
                        ArrayList<String> playerName = new ArrayList<String>();

                        for (String s : uuid) {
                            Object name = UuidToName.uuidToName(s);
                            playerName.add((String) name);
                        }

                        EmbedBuilder embed_uuid = new EmbedBuilder();

                        embed_uuid.setTitle(nameF + " 마을 정보", null);
                        String num1 = TimeSteamp.TimeSteamp(Long.valueOf((String) jsonObject1.get("registered")));
                        String num2 = TimeSteamp.TimeSteamp(Long.valueOf((String) jsonObject1.get("joinedNationAt")));
                        String[] tem_ = jsonObject1.get("spawn").toString().split(",");
                        embed_uuid.setDescription(
                                "마을 이름 : " + nameF +
                                        "\n국가 : " + jsonObject1.get("nation").toString().replaceAll("_","\\\\_") +
                                        "\n" +
                                        "\n시장 : " + jsonObject1.get("mayor").toString().replaceAll("_","\\\\_") +
                                        "\n마을원 수 : " + jsonObject1.get("memberCount") +
                                        "\n마을원 : " + jsonObject1.get("residents").toString().replaceAll("_","\\\\_") +
                                        "\n" +
                                        "\n마을 보드 : " + jsonObject1.get("townBoard").toString().replaceAll("_","\\\\_") +
                                        "\n" +
                                        "\n클래임 : " + jsonObject1.get("claimSize") +
                                        "\n설립일 : " + num1 +
                                        "\n국가 가입일 : " + num2 +
                                        "\n설립자 : " + jsonObject1.get("founder").toString().replaceAll("_","\\\\_") +
                                        "\n마을 스폰 좌표 : x = " + tem_[1] + ", y = " + tem_[2] + ", z = " + tem_[3]);

                        embed_uuid.setFooter("A Discord Bot made by neogui350");
                        embed_uuid.setColor(color);

                        EmbedBuilder embed_trust = new EmbedBuilder();
                        embed_trust.setTitle(nameF + " 마을 트러, 블랙 리스트", null);
                        embed_trust.setDescription(
                                "\n트러스트 : " + playerName.toString().replaceAll("\\[","").replaceAll("\\]","").replaceAll("_", "\\\\_") +
                                        "\n" +
                                        "\n아웃러(블랙) : " + jsonObject1.get("outlaws").toString().replaceAll("_","\\\\_")
                        );
                        embed_trust.setFooter("A Discord Bot made by neogui350");
                        embed_trust.setColor(color);

                        event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                        event.getChannel().sendMessageEmbeds(embed_trust.build()).queue();

                    } else {
                        EmbedBuilder embed_uuid = new EmbedBuilder();

                        embed_uuid.setTitle(nameF + " 마을 정보", null);
                        String num1 = TimeSteamp.TimeSteamp(Long.valueOf((String) jsonObject1.get("registered")));

                        embed_uuid.setDescription(
                                "마을 이름 : " + nameF +
                                        "\n국가 : " + jsonObject1.get("nation").toString().replaceAll("_","\\\\_") +
                                        "\n" +
                                        "\n시장 : " + jsonObject1.get("mayor").toString().replaceAll("_","\\\\_") +
                                        "\n마을원 수 : " + jsonObject1.get("memberCount") +
                                        "\n마을원 : " + jsonObject1.get("residents").toString().replaceAll("_","\\\\_") +
                                        "\n클래임 : " + jsonObject1.get("claimSize") +
                                        "\n설립일 : " + num1
                        );
                        embed_uuid.setFooter("A Discord Bot made by neogui350");
                        embed_uuid.setColor(color);

                        event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                    }

                }


            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
