package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GroupConfirmPayment extends AppCompatActivity {
    TextView personName,personMobileNumber,amountValue,account_number,notes;
    private DatabaseReference RootRef,GroupNameRootRef;
    String currentDate,currentTime;
    public  String accountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        RootRef = FirebaseDatabase.getInstance().getReference();
        GroupNameRootRef = RootRef.child("Groups").child(getIntent().getStringExtra("groupName"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_confirm_payment);
        personName = findViewById(R.id.name);
        personName.setText(getIntent().getStringExtra("mName"));
        personMobileNumber = findViewById(R.id.personMobileNumber);
        personMobileNumber.setText(getIntent().getStringExtra("TOMOBILE"));
        amountValue = findViewById(R.id.amount);
        amountValue.setText(getIntent().getStringExtra("AMOUNT"));
        account_number = findViewById(R.id.account_number);
        notes = findViewById(R.id.notes);

        GroupNameRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                accountNumber=dataSnapshot.child("Account Id").getValue().toString();
                account_number.setText(accountNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onClickGo (View view){
        //Why is there a for loop here?

        String id = GroupNameRootRef.push().getKey();
        Calendar ccalForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(ccalForDate.getTime());

        Calendar ccalForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(ccalForTime.getTime());



        //TO write data into the Firebase
        DatabaseReference mDatabase = GroupNameRootRef;
        Transaction transaction = new Transaction(personMobileNumber.getText().toString(), amountValue.getText().toString(), notes.getText().toString(),account_number.getText().toString(),currentDate,currentTime);
//            Toast.makeText(ConfirmPayment.this, "Transfer Details added", Toast.LENGTH_LONG).show();

        //TO write to the database one item
        mDatabase.child("Transfers").child(id).setValue(transaction);

        Intent intent = new Intent(GroupConfirmPayment.this, GroupTransferring.class);
        intent.putExtra("AMOUNT",getIntent().getStringExtra("AMOUNT"));
        intent.putExtra("personName",getIntent().getStringExtra("mName"));
        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
        intent.putExtra("accountNumber",getIntent().getStringExtra(accountNumber));
        intent.putExtra("TOMOBILE",getIntent().getStringExtra("TOMOBILE"));
//        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));

        startActivity(intent);



    }

}
