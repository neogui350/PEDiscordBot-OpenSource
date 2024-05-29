package me.neogui350.logger;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
               Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-HH:mm:ss");

        System.out.println("[ConsoleLogger] ["
                + format.format(today)
                + "] Server : "
                + event.getGuild()
                + " - Channel: "
                + event.getChannel()
                + " - USER: "
                + event.getMember().getUser()
                + " - used command : "
                + event.getCommandString());


//        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
//
//        String pathPlayerLogger = ".\\LOGS\\JSONLOGS\\console_logs\\" + formatDate.format(today) + "\\";
//        JSONObject jsonObjectWrite = new JSONObject();
//
//        jsonObjectWrite.put("", "");
//
//        try{
//            FileWriter file = new FileWriter(pathPlayerLogger);
//            file.write(jsonObjectWrite.toJSONString());
//            file.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}
