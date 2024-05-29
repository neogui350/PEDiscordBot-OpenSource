package me.neogui350.joinEventBot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static me.neogui350.command.Command.getPAPI;
import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.failedEmbed;
import static me.neogui350.utilutility.replyEmbeds.ReplyEmbeds.userJoinServerFailedEmbed;

public class UserJoinServer extends ListenerAdapter {
    private static String ThisClassPath(){
        return "[me/neogui350/joinEventBot/event/UserJoinServer.java]";
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            JSONObject jsonObject = getPAPI("discord", "discord", event.getMember().getId());
            if (!jsonObject.get("status").equals("SUCCESS")){
                if (jsonObject.get("status").equals("FAILED")){
                    EmbedBuilder embedBuilder = userJoinServerFailedEmbed(event.getUser());
                    event.getGuild().getDefaultChannel().asTextChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    System.out.println(ThisClassPath() + " " + event.getMember().getUser() + "님은 정보가 없습니다. ");
                } else {
                    System.out.println(ThisClassPath() + " status 리턴 값이 정상적이지 않습니다. 상태 : " + jsonObject.get("status") + ", 대상 : " + event.getMember().getUser());
                }

            } else {

            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
