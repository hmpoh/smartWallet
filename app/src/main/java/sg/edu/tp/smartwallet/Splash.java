package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private Handler handler;
    /*A handler is basically a message queue.
    You post a message to it,
    and it will eventually process it by calling its run method and passing the message to it.
     Since these run calls will always occur in the order of messages received on the same thread,
     it allows you to serialize events.*/
    private Runnable runnable;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent=new Intent(Splash.this,MainActivity.class);
                Intent intent=new Intent(Splash.this,LogIn.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}

