package me.neogui350.command.simplCommand;

import me.neogui350.utilutility.UuidToName;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class UuidCheck extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("uuid_check")){

            OptionMapping nameMappingObjectF = event.getOption("player_uuid");
            String uuid = nameMappingObjectF.getAsString();
            try {
                event.reply((String) UuidToName.uuidToName(uuid)).queue();
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
