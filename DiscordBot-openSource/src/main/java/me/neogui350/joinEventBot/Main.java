package me.neogui350.joinEventBot;

import me.neogui350.joinEventBot.event.UserJoinServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void giveRoleBotMain(){
        final String TOKEN = "";
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.build();

        JDA jda =jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(
                        new UserJoinServer()
                )
                .build();

    }
}
