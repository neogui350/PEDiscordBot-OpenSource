package me.neogui350.event;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class Ready implements EventListener {
    @Override
    public void onEvent(GenericEvent genericEvent) {
        if(genericEvent instanceof Ready){
            System.out.println("Bot loaded!!");
        }
    }
}
