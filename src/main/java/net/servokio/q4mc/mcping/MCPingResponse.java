package net.servokio.q4mc.mcping;

import java.util.List;

public class MCPingResponse {
    private Description description;
    private Players players;
    private Version version;
    private String favicon;
    private long ping;

    private String ip;
    public int port;

    public class Description {
        private String text;

        public String getStrippedText() {
            return MCPingUtil.stripColors(this.text);
        }

        public String getText(){
            return text;
        }

    }

    public class Players {
        private int max;
        private int online;
        private List<Player> sample;

        public int getOnline(){
            return this.online;
        }

        public int getMax(){
            return this.max;
        }

        public List<Player> getSample(){
            return this.sample;
        }

    }

    public class Player {
        private String name;
        private String id;

        public String getName(){
            return this.name;
        }

        public String getId(){
            return this.id;
        }

    }

    public class Version {

        /**
         * @return Version name (ex: 13w41a)
         */
        private String name;
        /**
         * @return Protocol version
         */
        private int protocol;

        public int getProtocol() {
            return protocol;
        }

        public String getName() {
            return name;
        }
    }

    public void setPing(long ping){
        this.ping = ping;
    }

    public Description getDescription(){
        return this.description;
    }

    public Players getPlayers(){
        return this.players;
    }

    public Version getVersion() {
        return version;
    }

    public long getPing() {
        return ping;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setHostAndPost(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getRawIp(){
        return this.ip+":"+this.port;
    }
}
