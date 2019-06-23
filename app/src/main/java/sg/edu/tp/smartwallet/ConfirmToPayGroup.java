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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConfirmToPayGroup extends AppCompatActivity {

    TextView textViewMobileNumber, textViewAccountNumber, textViewAmount, textViewName, textViewNotes;
    DatabaseReference reff,accountRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    String currentDate,currentTime,currentUserMobileNumber,toGroupAccountNumber;
    public String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_to_pay_group);

        textViewAccountNumber = (TextView) findViewById(R.id.account_number);
        textViewAmount = findViewById(R.id.amount);
        textViewName = findViewById(R.id.name);
        textViewNotes = findViewById(R.id.notes);


        Intent i = getIntent();
        textViewAmount.setText(getIntent().getStringExtra("AMOUNT"));
        textViewName.setText(getIntent().getStringExtra("groupName"));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        reff = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String accountNumber = dataSnapshot.child("accountNumber").getValue().toString();
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
        String id = reff.push().getKey();
        String mobile = getIntent().getStringExtra("TOMOBILE");
        if (textViewAmount.getText().toString().length() != 0) {

            Calendar ccalForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(ccalForDate.getTime());

            Calendar ccalForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(ccalForTime.getTime());

            String amountValue = textViewAmount.getText().toString();


            //TO write data into the Firebase
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Transfers");
            Transfer transfer = new Transfer(textViewMobileNumber.getText().toString(), textViewAmount.getText().toString(), textViewNotes.getText().toString(),currentUserMobileNumber,currentDate,currentTime,toGroupAccountNumber);
//            Toast.makeText(ConfirmPayment.this, "Transfer Details added", Toast.LENGTH_LONG).show();

            //TO write to the database one item
            mDatabase.child(id).setValue(transfer);
//            mDatabase.child(id).child(textViewMobileNumber.getText().toString()).setValue(transfer);
//            mDatabase.child(id).child(textViewAmount.getText().toString()).setValue(transfer);
//            mDatabase.child(id).child(textViewNotes.getText().toString()).setValue(transfer);
//            mDatabase.child(id).child(currentUserMobileNumber);
//            mDatabase.child(id).child(currentDate);
//            mDatabase.child(id).child(currentTime);

            Intent intent = new Intent(ConfirmToPayGroup.this, Transferring.class);
            intent.putExtra("AMOUNT",amountValue);
            intent.putExtra("TOMOBILE",mobile);
            intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
            startActivity(intent);




        }

    }

}
