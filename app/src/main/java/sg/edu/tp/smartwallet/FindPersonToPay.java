package sg.edu.tp.smartwallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class FindPersonToPay extends AppCompatActivity {
//
//    private EditText mSearchField;
//    private Button mSearchBtn;
//
//    private RecyclerView mResultList;
//
//    private DatabaseReference mUserDatabase;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_find_person_to_pay);
//
//        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
//
//        mSearchField = (EditText) findViewById(R.id.search_field);
//        mSearchBtn = (Button) findViewById(R.id.btnSearch);
//
//        mResultList = (RecyclerView) findViewById(R.id.result_list);
//
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                firebasePayeeSearch();
//            }
//        });
//
//    }
//
//    private void firebasePayeeSearch() {
//
//        FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>{
//
//            Users.class;
//            R.layout.list_layout;
//            UsersViewHolder.class;
//            mUserDatabase
//
//    }
//
//    //View Holder Class
//
//    public class UsersViewHolder extends RecyclerView.ViewHolder{
//
//        View mView;
//
//        public UsersViewHolder(View itemView){
//            super(itemView);
//            mView = itemView;
//        }
//
//        public void setDetailsString(String mobileNumber) {
//
//            TextView mobileNumber = (TextView) mView.findViewById(R.id.search_field);
//
//            mobileNumber.setText(mobileNumber)
//
//        }
//    }
//
//    }
}

