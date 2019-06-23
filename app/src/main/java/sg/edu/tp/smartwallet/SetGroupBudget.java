package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetGroupBudget extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bDot,bDelete;
    TextView amount;
    private DatabaseReference RootRef, GroupNameRootRef;
    private String currentGroupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_group_budget);
        currentGroupName = getIntent().getExtras().get("groupName").toString();
        RootRef = FirebaseDatabase.getInstance().getReference();
        GroupNameRootRef = RootRef.child("Groups").child(currentGroupName);


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

        amount = (TextView)findViewById(R.id.display);


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
    public void onClickYes (View view){

        if (amount.getText().toString().length() != 0) {
            GroupNameRootRef.child("Savings Target").setValue(amount.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
            GroupNameRootRef.child("Amount Raised").setValue("0.00")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

            GroupNameRootRef.child("Available Amount").setValue(amount.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

            Intent intent = new Intent(SetGroupBudget.this, ConfirmGroupBudgetEqualSplit.class);
            intent.putExtra("groupName", currentGroupName);
            intent.putExtra("AMOUNT",amount.getText().toString() );
            startActivity(intent);

        }
        else
            Toast.makeText(SetGroupBudget.this, "ERROR: Amount cannot be empty.", Toast.LENGTH_LONG).show();
    }



    public void onClickNo (View view){
        if (amount.getText().toString().length() != 0) {

                           GroupNameRootRef.child("Savings Target").setValue(amount.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            String amountValue = amount.getText().toString();
            Intent intent = new Intent(SetGroupBudget.this, ManualSplitting.class);
            intent.putExtra("AMOUNT",amountValue );
            startActivity(intent);
        }
        else
            Toast.makeText(SetGroupBudget.this, "ERROR: Amount cannot be empty.", Toast.LENGTH_LONG).show();
    }


    }
