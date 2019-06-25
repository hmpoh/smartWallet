package sg.edu.tp.smartwallet;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AddParticipantsToGroup extends AppCompatActivity {{}
    private EditText search_field;
    private Button btnSearch;
    public String mName;
    ListView participantList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    public TextView mobileNumberTextView;
    ArrayList Participants=new ArrayList();
    private DatabaseReference RootRef,userRootRef,GroupNameRootRef;
    public String participants;
    long id = 0;
    private FirebaseAuth mAuth;
    private String currentUserID;
    public String mobileNumber,stringNumber;




    private RecyclerView result_list;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participants_to_group);

        participantList = (ListView) findViewById(R.id.participant_list);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        search_field = (EditText) findViewById(R.id.search_field);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        result_list = (RecyclerView) findViewById(R.id.result_list);
        result_list.setHasFixedSize(true);
        result_list.setLayoutManager(new LinearLayoutManager(this));
        RootRef = FirebaseDatabase.getInstance().getReference();



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = search_field.getText().toString();

                firebasePayeeSearch(searchText);
            }
        });

        ViewAddedParticipants();



    }

//


    private void
    firebasePayeeSearch(String searchText) {
        Toast.makeText(AddParticipantsToGroup.this, "Started Search",Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mobileNumber").startAt(Long.parseLong(searchText)).endAt(Long.parseLong(searchText)).limitToFirst(1);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(firebaseSearchQuery, User.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(options) {
            @Override
            public UsersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.participants_layout,viewGroup,false);

                //send user data over to next screen
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mAuth = FirebaseAuth.getInstance();
                        currentUserID = mAuth.getCurrentUser().getUid();
                        userRootRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
                        userRootRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                mobileNumber = dataSnapshot.child("mobileNumber").getValue().toString();
                                System.out.println(mobileNumber);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        String resultMobileNumber = mobileNumberTextView.getText().toString();


                        if(arrayList.contains(resultMobileNumber)) {
                            Toast.makeText(AddParticipantsToGroup.this,"Already it is in list",Toast.LENGTH_LONG).show();
                        }

                        else if(resultMobileNumber.equals(mobileNumber)){
                            Toast.makeText(AddParticipantsToGroup.this,"You have already been added",Toast.LENGTH_LONG).show();
                        }

                        else {
                            arrayList.add(resultMobileNumber);
                            adapter.notifyDataSetChanged();

                        }
//

                        DeleteParticipant();
                    }
                });
                return new UsersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull User model) {

                holder.setDetails(model.getMobileNumber(),model.getName());

            }


        };

        firebaseRecyclerAdapter.startListening();
        result_list.setAdapter(firebaseRecyclerAdapter);
    }

    private void DeleteParticipant() {
      participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              arrayList.remove(position);
              adapter.notifyDataSetChanged();
          }
      });
    }


    //View Holder Class

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Long mobileNumber, String name) {

            mobileNumberTextView= (TextView) mView.findViewById(R.id.mobileNumber);
            mobileNumberTextView.setText(mobileNumber.toString());

            TextView nameTextView = (TextView) mView.findViewById(R.id.txtMobileNumber);
            nameTextView.setText("Pay to " + name);
            mName = name.toString();

        }
    }

    private void ViewAddedParticipants() {
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(AddParticipantsToGroup.this, android.R.layout.simple_list_item_1,arrayList);
        participantList.setAdapter(adapter);
    }

    public void onClickNext (View view){

        RequestNewGroup();
    }

    private void RequestNewGroup() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddParticipantsToGroup.this, R.style.AlertDialog);
            builder.setTitle("Enter Group Name: ");

            final EditText groupNameField = new EditText(AddParticipantsToGroup.this);
            groupNameField.setHint("e.g Graduation Trip");
            builder.setView(groupNameField);

            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String groupName = groupNameField.getText().toString();

                    if (TextUtils.isEmpty(groupName)) {
                        Toast.makeText(AddParticipantsToGroup.this, "Please Write Group Name", Toast.LENGTH_SHORT).show();
                    } else {

                        GroupNameRootRef = RootRef.child("Groups").child(groupName);
                        for (int i = 0; i < arrayList.size(); i++) {
                            participants = (arrayList.get(i)).toString();
                            Random rn = new Random();
                            int answer = rn.nextInt(9999999)+10000001;
                            stringNumber = new Integer(answer).toString();}
                        CreateNewGroup(groupName);
                        addSelfIntoGroupchat(groupName);
                        CreateGroupAccountId();
                        Intent intent = new Intent(AddParticipantsToGroup.this, AllGroups.class);
                        intent.putExtra("ParticipantsMobiles",participants);
                        startActivity(intent);


                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    dialogInterface.cancel();

                }
            });

            builder.show();
        }
    }

    private void CreateGroupAccountId() {
        GroupNameRootRef.child("Account Id").setValue(stringNumber  )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void addSelfIntoGroupchat(final String groupName) {
        GroupNameRootRef.child("Participants").child(mobileNumber).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


    //Here is where we store the group name into the Firebase Database
    private void CreateNewGroup(final String groupName) {

        for (int i = 0; i < arrayList.size(); i++) {
            participants = (arrayList.get(i)).toString();

        GroupNameRootRef.child("Participants").child(participants).setValue("")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(AddParticipantsToGroup.this, groupName + " group is Created Successfully...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }


    public void onClickCancel(View view) {

        Intent intent = new Intent(AddParticipantsToGroup.this, AllGroups.class);
        startActivity(intent);


    }


}
