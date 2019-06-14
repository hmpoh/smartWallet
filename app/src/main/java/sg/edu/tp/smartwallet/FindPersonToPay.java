package sg.edu.tp.smartwallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindPersonToPay extends AppCompatActivity {

    private EditText mSearchField;
    private Button mSearchBtn;

    private RecyclerView mResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_person_to_pay);

        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (Button) findViewById(R.id.btnSearch);

        mResultList = (RecyclerView) findViewById(R.id.result_list);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebasePayeeSearch();
            }
        });

    }

    private void firebasePayeeSearch() {



        }
    }

