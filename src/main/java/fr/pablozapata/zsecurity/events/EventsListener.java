package fr.pablozapata.zsecurity.events;

import fr.pablozapata.zsecurity.events.trigger.GuildMessage;
import fr.pablozapata.zsecurity.events.trigger.Ready;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventsListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        new Ready(event);
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        new GuildMessage(event);
    }
}
