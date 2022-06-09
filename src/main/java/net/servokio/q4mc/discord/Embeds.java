package net.servokio.q4mc.discord;

import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.servokio.q4mc.Config;
import net.servokio.q4mc.MainDC;
import net.servokio.q4mc.utils.ServerInfo;

import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.time.Instant;

public class Embeds {
    public MessageEmbed info() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("📄 Information");
        embed.addField("Dev.", "Q4 inc.", true);
        embed.addField("Lang. prog.", "JAVA", true);
        embed.addField("Link", "https://dsc.gg/q4c", true);
        embed.addField("Menu", "/help", true);
        return embed.build();
    }

    public MessageEmbed help() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("Use '/' for commands");
        embed.addField("🚀 Attack", "/attack {ip:port} {version} {method}", false);
        embed.addField("🔱 Resolve", "/resolve {domain.xyz}", false);
        embed.addField("📡 Methods", "/methods", false);
        embed.addField("⚡ Your plan", "/plan", false);
        embed.addField("Information", "/info", false);
        return embed.build();
    }

    public MessageEmbed startAttack(String addr, String method, String version, int at) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("🚀 Attack `" + addr + "` server");
        embed.addField("🔱 Method", method, true);
        embed.addField("📡 Version", version, true);
        embed.addField("🕚 Time", Config.ATTACK_TIME + " seconds", true);
        embed.addField("⚡ Power", Config.ATTACK_POWER == -1 ? "FREE" : String.valueOf(Config.ATTACK_POWER), true);
        embed.addField("№ атаки", String.valueOf(at), true);
        embed.setImage(Config.ATTACK_IMAGE_URL);
        embed.setTimestamp(Instant.now());
        return embed.build();
    }

    public MessageEmbed offlineServer(String addr) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("❎ Server `" + addr + "` is not online ");
        embed.setTimestamp(Instant.now());
        return embed.build();
    }

    public MessageEmbed serverInfo(ServerInfo info, String addr) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("✅ Server `" + addr + "` online\n```\n" + info.getMotd() + "\n```");
        embed.addField("IP:PORT", info.getRawIp(), true);
        embed.addField("Protocol",
                info.getProtocol() + "(" + MainDC.getInstance().protocols.protocolsMapOb.get(info.getProtocol()) + ")",
                true);
        embed.addField("Online", info.getNow() + "/" + info.getMaxPlayer(), true);
        embed.addField("Core", info.getInfo(), true);
        String url = info.getURLFavicon();
        if (url != null)
            embed.setThumbnail(url);
        embed.setTimestamp(Instant.now());
        return embed.build();
    }

    public MessageEmbed methods() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.setDescription("📄 Methods\n`" + String.join("`, `", MainDC.getInstance().methods.methodsString) + "\n`");
        return embed.build();
    }

    public MessageEmbed plan() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        //А старые кмд и тп? они же остаются как видишь, бота вчера раз 9 удалял и добавлял
        embed.addField("FREE", "free plan {2k-3k cps}", true);
        embed.addField("PRO", "5$ / m {5k-10k cps}", true);
        embed.addField("PRO+", "15$ / m {30k-100k cps}", true);
        return embed.build();
    }

    public MessageEmbed stop(int tCount){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(0x35AF76));
        embedBuilder.setTitle(Config.SERVER_NAME);
        embedBuilder.setDescription("Stopped "+tCount);
        return embedBuilder.build();
    }


    public MessageEmbed stats() {
        DecimalFormat df = new DecimalFormat("0.00");
        String cpu = "-";
        String ram = "-";
        String dick = "-";

        try {
            OperatingSystemMXBean systemBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            double cpuusage = systemBean.getProcessCpuLoad() * 100.0D;

            if (cpuusage > 0.0D) cpu = df.format(cpuusage);
        } catch (Throwable ignored) {
        }

        Runtime runtime = Runtime.getRuntime();
        long maxmemmb = runtime.maxMemory() / 1024L / 1024L;
        long freememmb = (runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory())) / 1024L / 1024L;
        long pro = (maxmemmb - freememmb) * 100L / maxmemmb;

        ram = df.format(pro) + "% (" + (maxmemmb - freememmb) + " / " + maxmemmb + " MB)";
        File file = new File(".");
        long maxspacegb = file.getTotalSpace() / 1024L / 1024L / 1024L;
        long freespacegb = file.getFreeSpace() / 1024L / 1024L / 1024L;
        pro = (maxspacegb - freespacegb) * 100L / maxspacegb;
        dick = df.format(pro) + "% (" + (maxspacegb - freespacegb) + " / " + maxspacegb + " GB)";

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0xCE0405));
        embed.setTitle(Config.SERVER_NAME);
        embed.addField("CPU", cpu+"%", true);
        embed.addField("RAM", ram, true);
        embed.addField("Disk", dick, true);
        return embed.build();
    }
}
