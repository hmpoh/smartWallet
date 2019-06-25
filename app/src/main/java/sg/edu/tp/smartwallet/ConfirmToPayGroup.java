package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConfirmToPayGroup extends AppCompatActivity {

    TextView textViewMobileNumber, textViewAccountNumber, textViewAmount, textViewName, textViewNotes;
    DatabaseReference reff,accountRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    String currentDate,currentTime,currentUserMobileNumber,toGroupAccountNumber;
    public String mName,accountNumber,balance, id;
    public Double doubleBalance, doubleAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_to_pay_group);

        textViewAccountNumber = (TextView) findViewById(R.id.account_number);
        textViewAmount = findViewById(R.id.amount);
        textViewName = findViewById(R.id.name);
        textViewNotes = findViewById(R.id.notes);
        textViewMobileNumber = findViewById(R.id.groupAccountId);


        Intent i = getIntent();
        textViewAmount.setText(getIntent().getStringExtra("AMOUNT"));
        textViewName.setText(getIntent().getStringExtra("groupName"));
        textViewMobileNumber.setText(getIntent().getStringExtra("TOMOBILE"));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        reff = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                accountNumber = dataSnapshot.child("accountNumber").getValue().toString();
                balance = dataSnapshot.child("balance").getValue().toString();
                doubleBalance = Double.parseDouble(balance);
                doubleAmount = Double.parseDouble(getIntent().getStringExtra("AMOUNT"));
                textViewAccountNumber.setText(accountNumber);

                String name = dataSnapshot.child("name").getValue().toString();


                currentUserMobileNumber = dataSnapshot.child("mobileNumber").getValue().toString();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickCancel(View view){
        Intent intent = new Intent(ConfirmToPayGroup.this, Chat.class);
        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
        startActivity(intent);
    }


    public void onClickGo(View view) {
        id = reff.push().getKey();
        String mobile = getIntent().getStringExtra("TOMOBILE");

        if (doubleBalance < doubleAmount) {
            Toast.makeText(ConfirmToPayGroup.this, "Insufficient Funds.", Toast.LENGTH_LONG).show();
        }

        else if (textViewAmount.getText().toString().length() != 0) {

            Calendar ccalForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(ccalForDate.getTime());

            Calendar ccalForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(ccalForTime.getTime());

            String amountValue = textViewAmount.getText().toString();


            //TO write data into the Firebase
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Transfers");
            Transfer transfer = new Transfer(textViewMobileNumber.getText().toString(), textViewAmount.getText().toString(), textViewNotes.getText().toString(),currentUserMobileNumber,currentDate,currentTime);
//            Toast.makeText(ConfirmPayment.this, "Transfer Details added", Toast.LENGTH_LONG).show();

            //TO write to the database one item
            mDatabase.child(id).setValue(transfer);
//            mDatabase.child(id).child(textViewMobileNumber.getText().toString()).setValue(transfer);
//            mDatabase.child(id).child(textViewAmount.getText().toString()).setValue(transfer);
//            mDatabase.child(id).child(textViewNotes.getText().toString()).setValue(transfer);
//            mDatabase.child(id).child(currentUserMobileNumber);
//            mDatabase.child(id).child(currentDate);
//            mDatabase.child(id).child(currentTime);

            Intent intent = new Intent(ConfirmToPayGroup.this, TransferringtoGroup.class);
            intent.putExtra("AMOUNT",getIntent().getStringExtra("AMOUNT"));
            intent.putExtra("TOMOBILE",mobile);
            intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
            intent.putExtra("TOMOBILE",getIntent().getStringExtra("TOMOBILE"));
            intent.putExtra("SKEY",id);
            startActivity(intent);




        }

    }

}
