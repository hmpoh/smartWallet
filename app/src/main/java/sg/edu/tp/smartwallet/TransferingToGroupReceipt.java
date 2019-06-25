package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransferingToGroupReceipt extends AppCompatActivity {
    TextView textGroupName,textAmount;
    DatabaseReference reff;
    private FirebaseAuth mAuth;
    private String currentUserID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfering_to_group_receipt);
        textGroupName = findViewById(R.id.groupName);
        textAmount = findViewById(R.id.amountPaid);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        String key = getIntent().getStringExtra("SKEY");
        textAmount.setText("$ "+getIntent().getStringExtra(("AMOUNT")));
        textGroupName.setText(getIntent().getStringExtra("groupName"));


    }


    public void onClickDone(View view){
        Intent intent = new Intent (TransferingToGroupReceipt.this, Chat.class);
        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
        startActivity(intent);
    }
}

