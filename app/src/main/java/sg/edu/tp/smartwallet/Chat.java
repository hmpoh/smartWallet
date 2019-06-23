package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Chat extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton SendMessageButton;
    private EditText userMessageInput;
    private ScrollView mScrollView;
    private TextView displayTextMessages,targetGoal;
    private Button pay;
    private ListView listView;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, GroupNameRef, GroupMessageKeyRef,ChatsReff;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_participants = new ArrayList<>();
    public String amountOwed,strtoPay,accountid;

    private String currentGroupName, currentUserID, currentUserName, currentDate, currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        pay = findViewById(R.id.pay);
        targetGoal = findViewById(R.id.TargetGoal);
        listView = findViewById(R.id.listView);



        //Getting the group name from the previous group fragment and storing inside the current group name
     //   currentGroupName = getIntent().getExtras().get("groupName").toString();
        currentGroupName = getIntent().getStringExtra("groupName");
        Toast.makeText(Chat.this, currentGroupName, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);
        ChatsReff = GroupNameRef.child("Chats");
        arrayAdapter = new ArrayAdapter<String>(Chat.this, android.R.layout.simple_list_item_1, list_of_participants);
        listView.setAdapter(arrayAdapter);

        InitializeFields();

        GetUserInfo();


        SendMessageButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                SaveMessageInfoToDatabase();

                userMessageInput.setText("");

                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

            }
        });

        SetUpTopBar();
        GetAccountNumberInfo();

    }

    private void GetAccountNumberInfo() {

        GroupNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                accountid=dataSnapshot.child("Account Id").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SetUpTopBar() {
        GroupNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Savings Target")){
                   String target = dataSnapshot.child("Savings Target").getValue().toString();
                    targetGoal.setText("Savings Target: "+target);

                    setIndividualAmountToPay(dataSnapshot);
                }
                else {
                    targetGoal.setText("No Target Set");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setIndividualAmountToPay(DataSnapshot dataSnapshot)
    {
        DatabaseReference payReff = FirebaseDatabase.getInstance().getReference().child("Groups").child(getIntent().getStringExtra("groupName")).child("Participants");
        payReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve Group line by line
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext())
                {
                    DataSnapshot ds = (DataSnapshot)iterator.next();
                    String mobileNumber = ds.getKey();
                    amountOwed= ds.child("Amount Owed").getValue().toString();
                    final Double doubleAmountOwed = Double.parseDouble(amountOwed);
                    String amountPaid = ds.child("Amount Paid").getValue().toString();
                    final Double doubleAmountPaid = Double.parseDouble(amountPaid);
                    Double toPay = doubleAmountOwed - doubleAmountPaid;
                    strtoPay = new Double(toPay).toString();

                    set.add(mobileNumber + " to Pay: $"+ strtoPay);
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

    @Override
    protected void onStart() {
        super.onStart();

        ChatsReff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    private void InitializeFields() {
        mToolbar = (Toolbar)findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(currentGroupName);

        SendMessageButton = (ImageButton)findViewById(R.id.send_message_button);
        userMessageInput = (EditText) findViewById(R.id.input_group_message);
        displayTextMessages = (TextView)findViewById(R.id.group_chat_text_display);
        mScrollView = (ScrollView)findViewById(R.id.my_scroll_view);

    }

    private void GetUserInfo()
    {

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //Check if users id exist or not

                if(dataSnapshot.exists()){
                    currentUserName = dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void SaveMessageInfoToDatabase() {

        String message = userMessageInput.getText().toString();
        //Create unique key for each message
        String messageKey = ChatsReff.push().getKey();

        if(TextUtils.isEmpty(message)){
            Toast.makeText(this, "Please Write Message First...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Calendar ccalForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(ccalForDate.getTime());

            Calendar ccalForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(ccalForTime.getTime());

            HashMap<String, Object> groupMessageKey = new HashMap<>();
            ChatsReff.updateChildren(groupMessageKey);

            //Getting the reference from the message key and storing inside Group Message Key Reference
            GroupMessageKeyRef = ChatsReff.child(messageKey);

            HashMap<String, Object> messageInfoMap = new HashMap<>();
                messageInfoMap.put("name", currentUserName);
                messageInfoMap.put("message", message);
                messageInfoMap.put("date", currentDate);
                messageInfoMap.put("time", currentTime);

            GroupMessageKeyRef.updateChildren(messageInfoMap);


        }
    }
    private void DisplayMessages(DataSnapshot dataSnapshot)

    {

        Iterator iterator = dataSnapshot.getChildren().iterator();

        while (iterator.hasNext()){
            String chatDate = (String)((DataSnapshot)iterator.next()).getValue();
            String chatMessage = (String)((DataSnapshot)iterator.next()).getValue();
            String chatName = (String)((DataSnapshot)iterator.next()).getValue();
            String chatTime = (String)((DataSnapshot)iterator.next()).getValue();

            displayTextMessages.append(chatName + ":\n" + chatMessage + "\n" + chatDate +"  " +chatTime+ "\n\n\n");

            //Scroll automatically down to the new message first
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

        }


    }

    public void onClickAttachment(View view){
        android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.attachments_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.set_savings_target){

                    Intent intent = new Intent(Chat.this, SetGroupBudget.class);
                    intent.putExtra("groupName", currentGroupName);
                    startActivity(intent);

                }
                if (item.getItemId()==R.id.pay){

                    Intent intent = new Intent(Chat.this, GroupScanToPay.class);
                    intent.putExtra("groupName", currentGroupName);
                    startActivity(intent);

                }
                if (item.getItemId()==R.id.receipt){

                    Intent intent = new Intent(Chat.this, PayToGroupHistory.class);
                    intent.putExtra("groupName", currentGroupName);
                    startActivity(intent);

                }

                // Toast.makeText(HomeActivity.this,""+item.getTitle(),Toast.LENGTH_SHORT).show();
                return  true;
            }
        });

        popupMenu.show();

    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Chat.this, AllGroups.class));
        finish();

    }

    public void onClickPay(View view) {
        Intent intent = new Intent(Chat.this,ConfirmToPayGroup.class);
        intent.putExtra("groupName",getIntent().getStringExtra("groupName"));
        //TODO: Get and send over the group account number?
        intent.putExtra("TOMOBILE",accountid);
        intent.putExtra("AMOUNT",strtoPay);
        startActivity(intent);
    }
}

