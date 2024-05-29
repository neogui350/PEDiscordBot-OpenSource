package me.neogui350.command.simplCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UshilBaBo extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("ushil")){
            event.reply("유실바보").queue();
        } else if (command.equals("carr0tholic")) {
            event.reply("ㄷ ㅏ ㄱ ㅂ ㅏ ㅂ" +
                    "\n ㅇ     ㅡ            ㅗ" +
                    "\n          ㄴ").queue();
        } else if (command.equals("아홀")) {
            event.reply("ㅇ ㅏ ㅎ ㅂ ㅏ ㅂ" +
                    "\n           ㅗ            ㅗ" +
                    "\n           ㄹ").queue();
        } else if (command.equals("바이러스")) {
            event.reply("독일 열사 만세!") ;
        } else if (command.equals("너구이")) {
            event.reply("또 졌어!").queue();
        }else if (command.equals("라시")) {
            event.reply("븅신(이거 친구가 넣음)").queue();
        }else if (command.equals("이스터에그")) {
            event.reply("이걸 진짜 쳐보시다니 ㄷㄷ;;").queue();
        }

    }
}