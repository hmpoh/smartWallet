package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WarningInsufficientFunds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_insufficient_funds);
    }

    public void onClickReturn (View view){
        Intent intent = new Intent(WarningInsufficientFunds.this, Chat.class);
        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
        startActivity(intent);
    }
}
