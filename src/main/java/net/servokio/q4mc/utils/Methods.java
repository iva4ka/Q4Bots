package net.servokio.q4mc.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class Methods {
    public ArrayList<String> methodsString;

    public Methods() {
        this.methodsString = new ArrayList<>();
        this.init();
    }

    private void init() {
        System.out.println("Initialization of methods...");

        registerMethod("join");
        registerMethod("legitjoin");
        registerMethod("localhost");
        registerMethod("ram");
        registerMethod("sf");
        registerMethod("botjoiner");
        registerMethod("spoof");
        registerMethod("ping");
        registerMethod("nullping");
        registerMethod("multikiller");
        registerMethod("handshake");
        registerMethod("bighandshake");
        registerMethod("query");
        registerMethod("bigpacket");
        registerMethod("network");
        registerMethod("randombytes");
        registerMethod("extremejoin");
        registerMethod("spamjoin");
        registerMethod("nettydowner");
        registerMethod("yoonikscry");
        registerMethod("colorcrasher");
        registerMethod("tcphit");
        registerMethod("queue");
        registerMethod("botnet");
        registerMethod("tcpbypass");
        registerMethod("ultimatesmasher");
        registerMethod("nabcry");

        System.out.println(methodsString.size() + " methods");
    }

    public OptionData getDiscordData() {
        return new OptionData(OptionType.STRING, "method", "Метод атаки", true, true);
    }

    private void registerMethod(String method) {
        this.methodsString.add(method);
    }
}
