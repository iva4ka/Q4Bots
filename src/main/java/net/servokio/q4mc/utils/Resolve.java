package net.servokio.q4mc.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.servokio.q4mc.Config;
import net.servokio.q4mc.mcping.MCPing;
import net.servokio.q4mc.mcping.MCPingOptions;
import net.servokio.q4mc.mcping.MCPingResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Resolve {
    public static ServerInfo getServerInfo(String addr) {
        if (Config.GET_INFO_METHOD == 0) {
            try {

                StringBuilder result = new StringBuilder();
                URL url = new URL("https://mcapi.us/server/status?ip=" + addr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; G4MC/"+Config.VERSION+"; +http://servokio.ru)");
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                conn.setRequestMethod("GET");
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                        for (String line; (line = reader.readLine()) != null;) {
                            result.append(line);
                        }
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    JsonObject object = JsonParser.parseString(result.toString()).getAsJsonObject();
                    boolean io = object.get("online").getAsBoolean();
                    return new ServerInfo(
                            object.get("online").getAsBoolean(),
                            object.get("motd").getAsString(),
                            io ? object.get("players").getAsJsonObject().get("max").getAsInt() : null,
                            io ? object.get("players").getAsJsonObject().get("now").getAsInt() : null,
                            io && object.has("favicon") && !object.get("favicon").isJsonNull()
                                    ? object.get("favicon").getAsString()
                                    : null,
                            io ? object.get("server").getAsJsonObject().get("name").getAsString() : "",
                            io ? object.get("server").getAsJsonObject().get("protocol").getAsInt() : null,
                            "ip");
                } else {
                    return null;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else if (Config.GET_INFO_METHOD == 1) {
            MCPingOptions options = new MCPingOptions(addr);
            MCPingResponse reply;
            try {
                reply = MCPing.getPing(options);
            } catch (IOException ex) {
                return null;
            }

            MCPingResponse.Description description = reply.getDescription();

            String motd = description.getStrippedText();
            MCPingResponse.Players players = reply.getPlayers();
            int now = players.getOnline();
            int max = players.getMax();
            MCPingResponse.Version version = reply.getVersion();

            int protocol = version.getProtocol();
            String info = version.getName();
            String favi = reply.getFavicon();

            return new ServerInfo(
                    true,
                    motd,
                    max,
                    now,
                    favi,
                    info,
                    protocol,
                    reply.getRawIp());
        }
        return null;
    }
}
