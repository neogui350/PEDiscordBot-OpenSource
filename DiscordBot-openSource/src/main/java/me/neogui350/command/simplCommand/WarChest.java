package me.neogui350.command.simplCommand;

import me.neogui350.command.Command;
import me.neogui350.utilutility.IsNumeric;
import me.neogui350.utilutility.replyEmbeds.ReplyEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;

import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.footer;

public class WarChest extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("war_chest_check")){
            OptionMapping nameMappingObjectF = event.getOption("wc_town_name");
            String nameF = nameMappingObjectF.getAsString();
            event.deferReply().queue();

            try {
                JSONObject jsonObject = new JSONObject(Command.getPAPI("town", "name", nameF));
                if (!(jsonObject.get("status").equals("SUCCESS"))){
                    EmbedBuilder embed_uuid = ReplyEmbeds.failedEmbed(nameF);

                    event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                } else {
                    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                    String nation = jsonObject1.get("nation").toString();


                    JSONObject jsonObject2 = new JSONObject(Command.getPAPI("nation", "name", nation));
                    if (!(jsonObject2.get("status").equals("SUCCESS"))){
                        EmbedBuilder embed_uuid = ReplyEmbeds.failedEmbed(nation);

                        event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                    }

                    JSONArray jsonArray1 = (JSONArray) jsonObject2.get("data");
                    JSONObject jsonObject4 = (JSONObject) jsonArray1.get(0);

                    if (!IsNumeric.intNumeric(jsonObject1.get("claimSize").toString()) && !IsNumeric.intNumeric(jsonObject4.get("member_count").toString())){
                        event.getHook().editOriginal("ERROR:WarChest - L50").queue();
                        return;
                    }

                    int nationsCount = Integer.parseInt(jsonObject4.get("memberCount").toString());
                    int townsClaim = Integer.parseInt(jsonObject1.get("claimSize").toString());

                    int monney = 20000 + (nationsCount * 200) + (townsClaim * 15);
                    DecimalFormat df = new DecimalFormat("###,###");
                    String money = df.format(monney);

                    EmbedBuilder embed_uuid = new EmbedBuilder();
                    embed_uuid.setTitle("War Chest - " + nameF);
                    embed_uuid.setDescription(nameF + " 마을의 War Chest 비용은 : " + money + "원 입니다. ");
                    embed_uuid.setFooter(footer);
                    embed_uuid.setColor(Color.green);

                    event.getHook().editOriginalEmbeds(embed_uuid.build()).queue();
                }

            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
