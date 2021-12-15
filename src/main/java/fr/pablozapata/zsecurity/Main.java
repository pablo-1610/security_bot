package fr.pablozapata.zsecurity;

import fr.pablozapata.zsecurity.events.EventsListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {
    private static JDA jda;
    private static Guild pabloDiscord;

    public static void main(String[] args) throws LoginException {
        System.getenv().forEach((key, value) -> System.out.println(key + " : " + value));
        jda = JDABuilder
                .createLight(System.getenv("ZSECURITY_TOKEN"))
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_INVITES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_BANS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_VOICE_STATES
                ).enableCache(
                        CacheFlag.VOICE_STATE,
                        CacheFlag.EMOTE,
                        CacheFlag.ONLINE_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.MEMBER_OVERRIDES
                ).setMemberCachePolicy(MemberCachePolicy.ALL)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setAutoReconnect(true)
                .addEventListeners(new EventsListener())
                .build();
    }

    public static JDA getJda() {
        return jda;
    }

    public static Guild getPabloDiscord() {
        return pabloDiscord;
    }

    public static void setPabloDiscord(Guild pabloDiscord) {
        Main.pabloDiscord = pabloDiscord;
    }
}
