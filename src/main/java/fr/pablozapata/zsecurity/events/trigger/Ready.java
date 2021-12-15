package fr.pablozapata.zsecurity.events.trigger;

import fr.pablozapata.zsecurity.Main;
import net.dv8tion.jda.api.events.ReadyEvent;

public class Ready {
    public Ready(ReadyEvent event) {
        Main.setPabloDiscord(event.getJDA().getGuildById(741640428335661071L));
    }
}
