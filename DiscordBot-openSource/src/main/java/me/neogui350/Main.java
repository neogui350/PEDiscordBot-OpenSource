package me.neogui350;

import me.neogui350.command.*;
import me.neogui350.command.simplCommand.UshilBaBo;
import me.neogui350.command.simplCommand.UuidCheck;
import me.neogui350.command.simplCommand.WarChest;
import me.neogui350.event.OnJoinGiveRole;
import me.neogui350.event.Ready;
import me.neogui350.logger.ConsoleLogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    public static final String API_KEY = "";

    public static void main(String[] args){

        final String TOKEN = "";
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);
        jdaBuilder.build();

        JDA jda =jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new Ready(),
                        new Command(),
                        new ConsoleLogger(),
                        new ResidentCommand(),
                        new TownCommand(),
                        new UshilBaBo(),
                        new UuidCheck(),
                        new NationCommand(),
                        new ReloadPlayerDiscordName(),
//                        new OnJoinGiveRole(), 임시 비활성화
                        new SetNationOnDiscord(),
                        new WarChest()
                )
                .build();

    }
}
