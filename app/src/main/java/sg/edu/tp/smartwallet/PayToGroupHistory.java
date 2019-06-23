package sg.edu.tp.smartwallet;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PayToGroupHistory extends AppCompatActivity {

    ListView deposit_list,withdraw_list;

    private DatabaseReference groupReff,transferReff,groupTransferReff;

    private FirebaseAuth mAuth;
    private String currentUserID, accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_to_group_history);
        deposit_list= findViewById(R.id.deposit_list);
        withdraw_list = findViewById(R.id.withdraw_list);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        groupReff = FirebaseDatabase.getInstance().getReference("Groups").child( getIntent().getStringExtra("groupName"));
        groupTransferReff = groupReff.child("Transfers");
        groupReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                accountId=dataSnapshot.child("Account Id").getValue().toString();
                transferReff = FirebaseDatabase.getInstance().getReference("Transfers");
                transferReff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                            ArrayList<TransferWithName> transfers = new ArrayList<>();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Transfer transfer = ds.getValue(Transfer.class);

//                                if (transfer.fromMobileNumber.equals(accountId))
//                                {
//                                    transfers.add(new TransferWithName("PAID: $" + transfer.getAmount(), transfer.getDateTime(),"To "+transfer.getToMobileNumber()));
//                                    TransferAdapter adapter = new TransferAdapter(PayToGroupHistory.this,transfers);
//                                    withdraw_list.setAdapter(adapter);
//                                    //TODO
//                                    //End
//                                }
                                    if (transfer.toMobileNumber.equals(accountId)){

                                    transfers.add(new TransferWithName("RECEIVED: $"+ transfer.getAmount(), transfer.getDateTime(),"From "+transfer.getFromMobileNumber()));
                                    TransferAdapter adapter = new TransferAdapter(PayToGroupHistory.this,transfers);
                                    deposit_list.setAdapter(adapter);

                                }
                                else{
                                        groupTransferReff.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                groupTransferReff.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                                                            ArrayList<TransferWithName> transfers = new ArrayList<>();
                                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                                Transaction transaction = ds.getValue(Transaction.class);

//                                if (transfer.fromMobileNumber.equals(accountId))
//                                {
//                                    transfers.add(new TransferWithName("PAID: $" + transfer.getAmount(), transfer.getDateTime(),"To "+transfer.getToMobileNumber()));
//                                    TransferAdapter adapter = new TransferAdapter(PayToGroupHistory.this,transfers);
//                                    withdraw_list.setAdapter(adapter);
//                                    //TODO
//                                    //End
//                                }
                                                                if (transaction.toMobileNumber.equals(accountId)){

                                                                    transfers.add(new TransferWithName("PAID: $"+ transaction.getAmount(), transaction.getDateTime(),"From "+transaction.getFromMobileNumber()));
                                                                    TransferAdapter adapter = new TransferAdapter(PayToGroupHistory.this,transfers);
                                                                    withdraw_list.setAdapter(adapter);

                                                                }
                                                            }

                                                        }
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

                        }
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
