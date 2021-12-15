package fr.pablozapata.zsecurity.events.trigger;

import fr.pablozapata.zsecurity.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class GuildMessage {
    public GuildMessage(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isSystem() || event.getAuthor().isBot() || event.getAuthor().equals(event.getJDA().getSelfUser())) {
            return;
        }
        final Member member = event.getMember();
        final Message message = event.getMessage();
        final TextChannel channel = event.getChannel();

        if (message.getInvites().size() > 0 && (!member.getPermissions().contains(Permission.MESSAGE_MANAGE))) {
            final List<Invite> guildInvites = Main.getPabloDiscord().retrieveInvites().complete();
            int requiered = message.getInvites().size();
            int granted = 0;
            for (String invite : message.getInvites()) {
                if (invite.equals(Main.getPabloDiscord().getVanityCode())) {
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
