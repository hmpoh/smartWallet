package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AmountToPay extends AppCompatActivity {

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bDot,bDelete;
    TextView ans, amount;
    Long mMobileNumber;
    String mName;
    DatabaseReference reff;
    private FirebaseAuth mAuth;
    private String currentUserID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_to_pay);

        amount = findViewById(R.id.display);

        mMobileNumber = getIntent().getLongExtra("mobileNumber",0);
        mName = getIntent().getStringExtra("MNAME");

        TextView mobileNumberTextView = (TextView) findViewById(R.id.mobileNumber);
        mobileNumberTextView.setText(mMobileNumber.toString());

        TextView nameTextView = (TextView) findViewById(R.id.txtMobileNumber);
        nameTextView.setText("Pay to " + mName);


        b1= (Button)findViewById(R.id.btn1);
        b2= (Button)findViewById(R.id.btn2);
        b3= (Button)findViewById(R.id.btn3);
        b4= (Button)findViewById(R.id.btn4);
        b5= (Button)findViewById(R.id.btn5);
        b6= (Button)findViewById(R.id.btn6);
        b7= (Button)findViewById(R.id.btn7);
        b8= (Button)findViewById(R.id.btn8);
        b9= (Button)findViewById(R.id.btn9);
        bDot= (Button)findViewById(R.id.btnDot);
        b0= (Button)findViewById(R.id.btn0);
        bDelete= findViewById(R.id.delete);

        ans = (TextView)findViewById(R.id.display);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"1");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"2");
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"3");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"4");
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"5");
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"6");
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"7");
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"8");
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"9");
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+"0");
            }
        });

        bDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans.setText(ans.getText()+".");
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ansString = ans.getText().toString();
                ans.setText(ansString.substring(0,ansString.length()-1));
            }
        });

    }

    public void onClickProceed (View view){
        if (amount.getText().toString().length() != 0) {

        checkBalance();


        }
        else
            Toast.makeText(AmountToPay.this, "ERROR: Amount cannot be empty.", Toast.LENGTH_LONG).show();

    }

    private void checkBalance() {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        reff = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        ValueEventListener balance = reff.addValueEventListener(new ValueEventListener() {
            public String balance;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                balance = dataSnapshot.child("balance").getValue().toString();
                Double doubleBalance = Double.parseDouble(balance);
                Double doubleAmount = Double.parseDouble(amount.getText().toString());

                if (doubleBalance < doubleAmount) {
                    Toast.makeText(AmountToPay.this, "Insufficient Funds.", Toast.LENGTH_LONG).show();
                }
                else{
                    String amountValue = amount.getText().toString();
                    String toMobile = mMobileNumber.toString();

                    Intent intent  = new Intent(AmountToPay.this, ConfirmPayment.class);

                    intent.putExtra("AMOUNT",amountValue);
                    intent.putExtra("TOMOBILE",toMobile);
                    intent.putExtra("mName",mName);

                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
