package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ManualSplitting extends AppCompatActivity {
String amount;
TextView amountSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_splitting);
        amount = getIntent().getStringExtra("AMOUNT");
        amountSet = findViewById(R.id.amountSet);
        amountSet.setText("$ " + amount);
    }
}
