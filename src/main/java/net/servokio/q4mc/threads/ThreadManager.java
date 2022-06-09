package net.servokio.q4mc.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreadManager {
    public HashMap<String, List<Thread>> threads;

    public ThreadManager(){
        this.threads = new HashMap<>();
    }

    public void addThread(String membedID, Thread thread){
        List<Thread> t;
        if(threads.containsKey(membedID)){
            t = threads.get(membedID);
            t.add(thread);
        } else {
            t = new ArrayList<>();
            t.add(thread);
        }
        threads.put(membedID, t);
    }

    public void stopAll(String memberID){
        if(threads.containsKey(memberID)) for(Thread t: threads.get(memberID)) t.interrupt();
        threads.remove(memberID);
    }

    public void stopReallyAll(){
        for(String key : threads.keySet()) for(Thread t: threads.get(key)) t.interrupt();
        threads.clear();
    }
}
