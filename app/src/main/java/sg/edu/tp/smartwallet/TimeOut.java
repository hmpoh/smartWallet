package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.sql.Time;

public class TimeOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_out);
    }

    public void onClickDone (View view){
        Intent intent = new Intent(TimeOut.this, LogIn.class);
        startActivity(intent);
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();

    }
}
