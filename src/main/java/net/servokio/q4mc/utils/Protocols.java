package net.servokio.q4mc.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.HashMap;

public class Protocols {
    public HashMap<String, Integer> protocolsMap;
    public HashMap<Integer, String> protocolsMapOb;
    public ArrayList<String> availableString;

    public Protocols(){
        this.protocolsMap = new HashMap<>();
        this.protocolsMapOb = new HashMap<>();
        this.availableString = new ArrayList<>();
        this.init();
    }

    private void init() {
        System.out.println("Initialization of protocols...");

        //1.8
        addProtocol("1.8", 47);
        addProtocol("1.8.1", 47);
        addProtocol("1.8.2", 47);
        addProtocol("1.8.3", 47);
        addProtocol("1.8.4", 47);
        addProtocol("1.8.5", 47);
        addProtocol("1.8.6", 47);
        addProtocol("1.8.7", 47);
        addProtocol("1.8.8", 47);
        addProtocol("1.8.9", 47);

        //1.9
        addProtocol("1.9", 107);
        addProtocol("1.9.1", 108);
        addProtocol("1.9.2", 109);
        addProtocol("1.9.3", 110);
        addProtocol("1.9.4", 110);
        addProtocol("1.9.4", 110);

        //1.10
        addProtocol("1.10", 210);
        addProtocol("1.10.1", 210);
        addProtocol("1.10.2", 210);

        //1.11
        addProtocol("1.11", 315);
        addProtocol("1.11.1", 316);
        addProtocol("1.11.2", 316);

        //1.12
        addProtocol("1.12", 335);
        addProtocol("1.12.1", 338);
        addProtocol("1.12.2", 340);

        //1.13
        addProtocol("1.13", 393);
        addProtocol("1.13.1", 401);
        addProtocol("1.13.2", 404);

        //1.14
        addProtocol("1.14", 477);
        addProtocol("1.14.1", 480);
        addProtocol("1.14.2", 485);
        addProtocol("1.14.3", 490);
        addProtocol("1.14.4", 498);

        //1.15
        addProtocol("1.15", 573);
        addProtocol("1.15.1", 575);
        addProtocol("1.15.2", 578);

        //1.16
        addProtocol("1.16", 735);
        addProtocol("1.16.1", 736);
        addProtocol("1.16.2", 751);
        addProtocol("1.16.3", 753);
        addProtocol("1.16.4", 754);
        addProtocol("1.16.5", 754);

        //1.17
        addProtocol("1.17", 755);
        addProtocol("1.17.1", 756);

        //1.18
        addProtocol("1.18", 757);
        addProtocol("1.18.1", 757);
        addProtocol("1.18.2", 758);

        System.out.println(availableString.size()+" protocols");
    }

    public OptionData getDiscordData(){
        return new OptionData(OptionType.STRING, "protocol", "Версия для атаки", true, true);
    }

    private void addProtocol(String version, int protocol){
        protocolsMap.put(version, protocol);
        protocolsMapOb.put(protocol, version);
        availableString.add(version);
    }
}
