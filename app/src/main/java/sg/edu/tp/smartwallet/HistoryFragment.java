package sg.edu.tp.smartwallet;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static sg.edu.tp.smartwallet.R.layout.history_layout_item;

public class HistoryFragment extends Fragment {
    ListView historyList;


    private DatabaseReference userReff,transferReff;

    private FirebaseAuth mAuth;
    private String currentUserID, mobileNumber;
    ArrayList<TransferWithName> transfers = new ArrayList<>();


    public static HistoryFragment newInstance(){
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);

        historyList = (ListView) fragmentView.findViewById(R.id.listHistory);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        final TransferAdapter adapter = new TransferAdapter(getActivity(),transfers);
        historyList.setAdapter(adapter);

        userReff = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        userReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mobileNumber=dataSnapshot.child("mobileNumber").getValue().toString();
                transferReff = FirebaseDatabase.getInstance().getReference("Transfers");
                transferReff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                            transfers.clear();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                Transfer transfer = ds.getValue(Transfer.class);

                                if (transfer.fromMobileNumber.equals(mobileNumber))
                                {
                                    transfers.add(new TransferWithName("PAID: $" + transfer.getAmount(), transfer.getDateTime(),"To "+transfer.getToMobileNumber()));


                                }
                                else if (transfer.toMobileNumber.equals(mobileNumber)){

                                    transfers.add(new TransferWithName("RECEIVED: $"+ transfer.getAmount(), transfer.getDateTime(),"From "+transfer.getFromMobileNumber()));

                                }
                            }
                            adapter.notifyDataSetChanged();
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


        return fragmentView;
    }
}
