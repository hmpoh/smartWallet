package sg.edu.tp.smartwallet;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

public class MyApp extends Application {

    private LogoutListener listener;

    public void startUserSession() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listener.onSessionLogout();
            }
        }, 10000);
    }

    public void registerSessionListener(LogoutListener listener) {
        this.listener = listener;
    }
}

