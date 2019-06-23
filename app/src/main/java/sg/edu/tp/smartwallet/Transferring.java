package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class Transferring extends AppCompatActivity {
    Timer timer;
    DatabaseReference reff, toReff;
    private FirebaseAuth mAuth;
    private String currentUserID, balance, toMobile, userMobile;
    private static final String DEBUG_TAG = "potato";
    public String Skey;



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

//                reff.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        toMobile = getIntent().getStringExtra("TOMOBILE");
                        int intTOMobile = Integer.parseInt(toMobile);
                        userMobile = dataSnapshot.child("mobileNumber").getValue().toString();

                      //      Query query = toReff.child("Users").orderByChild("mobileNumber").equalTo(toMobile);
                Query query = toReff.orderByChild("mobileNumber").equalTo(intTOMobile);
                          ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String myParentNode=dataSnapshot.getKey();
                                    for (DataSnapshot ds: dataSnapshot.getChildren()) {

                                       String key = ds.getKey();
                                       Log.d(DEBUG_TAG,key);
                                       balance = ds.child("balance").getValue().toString();
                                       Double creditDoubleBalance = Double.parseDouble(balance);

                                        double Balance = creditDoubleBalance + doubleAmount;

                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                        mDatabase.child("Users").child(key).child("balance").setValue(Balance);

                                        Skey = key.toString();
                                        Intent intent = new Intent(Transferring.this, Receipt.class);
                                        intent.putExtra("SKEY",Skey);
                                        intent.putExtra("Amount",amount);
                                        Log.d("potato",Skey);
                                        startActivity(intent);
                                        finish();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };
                            query.addListenerForSingleValueEvent(valueEventListener);


 //                   }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//

                    //Step 1: Get Balance from database
                    //Step 2 : Balance from database minus off amount
                    //Step 3: new balance update to database

// }
 //               );

//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//
//                        Intent intent = new Intent(Transferring.this, Receipt.class);
//                        startActivity(intent);
//                        intent.putExtra("SKEY",Skey);
//                        Log.d("potato",Skey);
//                        finish();
//
//                    }
//                }, 1000);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }
}

