package net.servokio.q4mc.utils;

import net.servokio.q4mc.Config;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerInfo {
    private final boolean isOnline;
    private final String motd;

    private final String favicon;

    private final int maxPlayer;
    private final int now;

    private final String info;
    private final int protocol;

    private final String rawIp;

    public ServerInfo(
            boolean isOnline,
            String motd,
            int maxPlayer,
            int now,
            String favicon,
            String info,
            int protocol,
            String rawIp
    ) {
        this.isOnline = isOnline;
        this.motd = motd;
        this.maxPlayer = maxPlayer;
        this.now = now;
        this.favicon = favicon;
        this.info = info;
        this.protocol = protocol;
        this.rawIp = rawIp;
    }

    public boolean isOnline(){
        return this.isOnline;
    }

    public String getMotd(){
        return this.motd;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getURLFavicon() {
        if(this.favicon == null) return null;
        try{
            String def = Config.SERVOKIO_BASE_API + "/b64tu";
            String newID = postReg(def, "{\"data\": \"+" + this.favicon + "+\"}");
            return addParameter(Config.SERVOKIO_BASE_API + "/b64tu/image.png", "id", newID);
        } catch (Exception e) {
            return null;
        }
    }

    public static String postReg(String url, String urlParameters) throws Exception {
        //String url = "http://servokio.ru/api2/me";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; ServOKioCorePlugin/0.0.1; +https://servokio.ru)");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept-Encoding", "gzip,deflate");
        con.setConnectTimeout(2000);
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.write(urlParameters.getBytes(StandardCharsets.UTF_8));
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
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

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getNow() {
        return now;
    }

    public String getInfo() {
        return info;
    }

    public int getProtocol() {
        return protocol;
    }

    public String getRawIp(){
        return this.rawIp;
    }
}
