package net.servokio.q4mc.threads;

import net.servokio.q4mc.Config;
import net.servokio.q4mc.MainDC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class autoUpdate extends Thread{
    boolean o = true;

    @Override
    public void run(){
        while (o){
            loadAdmins();
            loadBans();
            try {
                Thread.sleep(1000 * 60 * 30); //30 minutes
            } catch (Exception e){
                o = false;
            }
        }
    }


    private void loadBans() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("https://raw.githubusercontent.com/ivanoffskiy/test/main/ban.txt");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (compatible; G4MC/" + Config.VERSION + "; +http://servokio.ru)");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            conn.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {
                    result.append(line);
                }
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200){
                MainDC.getInstance().banned.clear();
                MainDC.getInstance().banned.addAll(Arrays.asList(result.toString().split(",")));
                System.out.println("Banned: "+MainDC.getInstance().banned.size());
            }

        } catch (Exception e) {
            System.out.println("Схлеснулись членами как-то боты");
        }
    }

    private void loadAdmins() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("https://raw.githubusercontent.com/ivanoffskiy/test/main/admins.txt");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (compatible; G4MC/" + Config.VERSION + "; +http://servokio.ru)");
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
                MainDC.getInstance().admins.clear();
                MainDC.getInstance().admins.addAll(Arrays.asList(result.toString().split(",")));
                System.out.println("Admins: "+MainDC.getInstance().admins.size());
            }

        } catch (Exception e) {
            System.out.println("Схлеснулись членами как-то боты");
        }
    }
}
