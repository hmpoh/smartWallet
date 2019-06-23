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

public class GroupAmountToPay extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bDot,bDelete;
    TextView amount;
    Long mMobileNumber;
    String mName;
    DatabaseReference RootRef,GroupNameRootRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    public String currentGroupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_amount_to_pay);

        amount = findViewById(R.id.display);

        mMobileNumber = getIntent().getLongExtra("mobileNumber",0);
        mName = getIntent().getStringExtra("MNAME");

        currentGroupName = getIntent().getExtras().get("groupName").toString();
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




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"1");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"2");
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"3");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"4");
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"5");
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"6");
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"7");
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"8");
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"9");
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+"0");
            }
        });

        bDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(amount.getText()+".");
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ansString = amount.getText().toString();
                int charCount = ansString.length();
                if (charCount > 0)
                {
                    amount.setText(ansString.substring(0,ansString.length()-1));
                }
            }
        });
    }
    public void onClickProceed (View view){
        if (amount.getText().toString().length() != 0) {

            checkBalance();


        }
        else
            Toast.makeText(GroupAmountToPay.this, "ERROR: Amount cannot be empty.", Toast.LENGTH_LONG).show();

    }

    private void checkBalance() {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();


        RootRef = FirebaseDatabase.getInstance().getReference();
        GroupNameRootRef = RootRef.child("Groups").child(currentGroupName);
        GroupNameRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public String amountRaised,savingsTarget;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                amountRaised = dataSnapshot.child("Amount Raised").getValue().toString();
                Double doubleAmountRaised = Double.parseDouble(amountRaised);
                savingsTarget = dataSnapshot.child("Savings Target").getValue().toString();
                Double doubleSavingsTarget = Double.parseDouble(savingsTarget);
                Double doubleAmount = Double.parseDouble(amount.getText().toString());

                if (!doubleAmountRaised.equals(doubleSavingsTarget) ) {

                        Intent intent = new Intent(GroupAmountToPay.this, WarningInsufficientFunds.class);
                        intent.putExtra("groupName", getIntent().getStringExtra("groupName"));
                        startActivity(intent);
                        Toast.makeText(GroupAmountToPay.this, "Amount Raised must meet Target set", Toast.LENGTH_LONG).show();
                    }
                else if (doubleAmount>doubleSavingsTarget){

                    Toast.makeText(GroupAmountToPay.this, "Insufficient Funds.", Toast.LENGTH_LONG).show();
                }
                else{
                    String amountValue = amount.getText().toString();
                    String toMobile = mMobileNumber.toString();

                    Intent intent  = new Intent(GroupAmountToPay.this, GroupConfirmPayment.class);

                    intent.putExtra("AMOUNT",amountValue);
                    intent.putExtra("TOMOBILE",toMobile);
                    intent.putExtra("mName",mName);
                    intent.putExtra("groupName",getIntent().getStringExtra("groupName"));

                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
