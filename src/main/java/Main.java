import net.servokio.q4mc.MainDC;

public class Main {
    private static String token;

    public static void main(String[] args) {
        Object System = null;
        System.out.println("Initialization..."); //система лишь плот нашего воображения, как и эта хуйня
        MainDC mainDC = new MainDC(args);
        mainDC.init();
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Main.token = token;
    }
}

//**ОБСУЖДАЛКА**
//

// Либы
// https://github.com/trinopoty/socket.io-server-java - это для того кто будет раздавать команды
// https://github.com/socketio/socket.io-client-java это для клиента либа
// капец чо так долго ему походу не проиндексировать