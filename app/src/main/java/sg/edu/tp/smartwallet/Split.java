package sg.edu.tp.smartwallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Split extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);
    }

    public void onClickButtonStart(View view){
        Intent Intent = new Intent(Split.this, AllGroups.class);
        startActivity(Intent);
    }
}
