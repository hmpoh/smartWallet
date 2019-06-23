package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.Timer;

public class TransferringtoGroup extends AppCompatActivity {
    Timer timer;
    DatabaseReference reff, toReff,groupAccountIdReff;
    private FirebaseAuth mAuth;
    private String currentUserID, balance, toMobile, groupAccountId;
    private static final String DEBUG_TAG = "potato";
    public String Skey,group_account_id,amountRaised;



    private Handler handler;
    /*A handler is basically a message queue.
    You post a message to it,
    and it will eventually process it by calling its run method and passing the message to it.
     Since these run calls will always occur in the order of messages received on the same thread,
     it allows you to serialize events.*/
    private Runnable runnable;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferring);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        final String amount = intent.getStringExtra("AMOUNT");


        
        //Withdraw
        //https://stackoverflow.com/questions/43404184/ondatachange-stuck-in-infinite-loop
        reff = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        toReff = FirebaseDatabase.getInstance().getReference().child("Users");

        //Use addListenerForSingleValueEvent
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                balance = dataSnapshot.child("balance").getValue().toString();



                final Double doubleBalance = Double.parseDouble(balance);
                final Double doubleAmount = Double.parseDouble(getIntent().getStringExtra("AMOUNT"));
                double Balance = doubleBalance - doubleAmount;

                    String str = new Double(Balance).toString();

                    //retrieve the selected user ans updates the values

                    mDatabase.child("Users").child(currentUserID).child("balance").setValue(str);

                  //  groupAccountIdReff = FirebaseDatabase.getInstance().getReference().child("Groups").child(getIntent().getStringExtra("groupName"));
                groupAccountIdReff = FirebaseDatabase.getInstance().getReference().child("Groups").child("Bali Trip");

                    groupAccountIdReff.addListenerForSingleValueEvent(new ValueEventListener()  {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //        group_account_id = dataSnapshot.child("Account Id").getValue().toString();

                            toMobile = getIntent().getStringExtra("TOMOBILE");
                            int intTOMobile = Integer.parseInt(toMobile);
//                            groupAccountId = group_account_id;

                            //      Query query = toReff.child("Users").orderByChild("mobileNumber").equalTo(toMobile);
                         //   Query query = groupAccountIdReff.orderByChild("Account Id").equalTo(intTOMobile);
                            Query query = groupAccountIdReff.orderByChild("Account Id").equalTo("18374657");
                            ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                //    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                        String myParentNode=dataSnapshot.getKey();
                                        amountRaised = dataSnapshot.child("Amount Raised").getValue().toString();
                                        Double creditDoubleAmountRaised = Double.parseDouble(amountRaised);

                                        double raisedAmount = creditDoubleAmountRaised + doubleAmount;

                                        String strRaisedAmount = String.valueOf(raisedAmount);

                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("Groups").child(getIntent().getStringExtra("groupName")).child("Amount Raised").setValue(strRaisedAmount);

                                        Intent intent = new Intent(TransferringtoGroup.this, TransferingToGroupReceipt.class);
                                        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
                                        intent.putExtra("TOMOBILE",getIntent().getStringExtra("TOMOBILE"));
                                        intent.putExtra("Amount",amount);
                                        Log.d("potato",Skey);
                                        startActivity(intent);
                                        finish();

                                   //}
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };
                            query.addListenerForSingleValueEvent(valueEventListener);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





    }

}

