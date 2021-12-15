package fr.pablozapata.zsecurity.events.trigger;

import fr.pablozapata.zsecurity.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class GuildMessage {
    public GuildMessage(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isSystem() || event.getAuthor().isBot() || event.getAuthor().equals(event.getJDA().getSelfUser())) {
            return;
        }
        final Member member = event.getMember();
        final Message message = event.getMessage();
        final TextChannel channel = event.getChannel();
        final Guild guild = event.getGuild();
        final Member selfMember = guild.getSelfMember();

        if (!(selfMember.getPermissions().containsAll(Arrays.asList(Permission.MESSAGE_MANAGE, Permission.MESSAGE_READ, Permission.MESSAGE_WRITE)))) {
            return;
        }

        if (message.getInvites().size() > 0 && (!member.getPermissions().contains(Permission.MESSAGE_MANAGE))) {
            final List<Invite> guildInvites = guild.retrieveInvites().complete();
            int requiered = message.getInvites().size();
            int granted = 0;
            for (String invite : message.getInvites()) {
                if (invite.equals(guild.getVanityCode())) {
                    granted = granted + 1;
                    continue;
                }
                for (Invite guildInvite : guildInvites) {
                    if (guildInvite.getCode().equals(invite)) {
                        granted = granted + 1;
                    }
                }
            }

            if (granted != requiered) {
                message.delete().queue(unused -> {
                    channel.sendMessage(String.format("> %s les invitations Discord sont interdites !", member.getAsMention())).queue();
                    return;
                });
            }
        }
    }
}
