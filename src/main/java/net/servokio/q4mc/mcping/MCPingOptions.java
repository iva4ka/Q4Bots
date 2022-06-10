package net.servokio.q4mc.mcping;

import kotlin.text.Charsets;

public class MCPingOptions {
    private String hostname;
    private String charset;
    private int port;
    private int timeout;

    public MCPingOptions(String hostname){
        this.hostname = hostname;
        if(hostname.contains(":")){
            String[] s = hostname.split(":");
            this.hostname = s[s.length - 2];
        }
        System.out.println(this.hostname);
        this.charset = Charsets.UTF_8.displayName();
        this.port = hostname.contains(":") ? Integer.parseInt(hostname.split(":")[1]) : 25565;
        this.timeout = 5000;
    }

    public String getHostname(){
        return this.hostname;
    }

    public String getCharset(){
        return this.charset;
    }

    public int getPort(){
        return this.port;
    }

    public int getTimeout(){
        return this.timeout;
    }
}
