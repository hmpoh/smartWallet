package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class FindPersonToPay extends AppCompatActivity {

    private EditText mSearchField;
    private Button mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_person_to_pay);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (Button) findViewById(R.id.btnSearch);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

                firebasePayeeSearch(searchText);
            }
        });

    }

    private void firebasePayeeSearch(String searchText) {

        Toast.makeText(FindPersonToPay.this, "Started Search",Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mobileNumber").startAt(Long.parseLong(searchText)).limitToFirst(1);

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(firebaseSearchQuery, User.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(options) {
            @Override
            public UsersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.numbers_layout,viewGroup,false);

                //send user data over to next screen
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FindPersonToPay.this,AmountToPay.class );
                        intent.putExtra("mobileNumber",Long.parseLong(mSearchField.getText().toString()));
                        startActivity(intent);
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
        mResultList.setAdapter(firebaseRecyclerAdapter);

    }

    //View Holder Class

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Long mobileNumber, String name) {

            TextView mobileNumberTextView = (TextView) mView.findViewById(R.id.mobileNumber);
            mobileNumberTextView.setText(mobileNumber.toString());

            TextView nameTextView = (TextView) mView.findViewById(R.id.person);
            nameTextView.setText(name);

        }
    }
    }

