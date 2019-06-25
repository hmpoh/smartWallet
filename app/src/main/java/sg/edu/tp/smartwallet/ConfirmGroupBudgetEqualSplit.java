package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConfirmGroupBudgetEqualSplit extends AppCompatActivity {
    public String amount, stringNewAmount;
    private DatabaseReference RootRef;
    public int  numOfParticpants=0;
    public  double doubleAmount;
    ListView participants;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String>list_of_participants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_group_budget_equal_split);
        RootRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(getIntent().getStringExtra("groupName")).child("Participants");
        amount = getIntent().getStringExtra("AMOUNT");
        doubleAmount = Double.parseDouble(amount);
        participants = (ListView) findViewById(R.id.listSplitEqualBillWith);
        arrayAdapter = new ArrayAdapter<String>(ConfirmGroupBudgetEqualSplit.this, android.R.layout.simple_list_item_1, list_of_participants);
        participants.setAdapter(arrayAdapter);

        //Listener for getting  numOfParticpants
        RootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   numOfParticpants = (int)dataSnapshot.getChildrenCount();
                   Double newAmount = doubleAmount/numOfParticpants;
                   stringNewAmount = new Double(newAmount).toString();

               }
               else {

                   Toast.makeText(ConfirmGroupBudgetEqualSplit.this, "ERROR: There are no Participants.", Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve Group line by line
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext())
                {
                    set.add((((DataSnapshot)iterator.next()).getKey())+"                           "+ "$ "+stringNewAmount);
                    //getKey will basically get all the group names
              ;

                }

                list_of_participants.clear();
                list_of_participants.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
//TODO
    public void onClickProceed(View view){
        RootRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Retrieve Group line by line
                    Set<String> set = new HashSet<>();
                    Iterator iterator = dataSnapshot.getChildren().iterator();

                    while (iterator.hasNext())
                    {
                        DatabaseReference ds = ((DataSnapshot)iterator.next()).getRef();
                        ds.child("Amount Owed").setValue(stringNewAmount);
                        ds.child("Amount Paid").setValue("0.00");
                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Intent intent = new Intent(ConfirmGroupBudgetEqualSplit.this, Chat.class);
        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
        startActivity(intent);
    }
}
