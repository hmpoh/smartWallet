package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity implements LogoutListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp)getApplication()).registerSessionListener(this);
        ((MyApp)getApplication()).startUserSession();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        ((MyApp)getApplication()).onUserInteracted();
    }

    @Override
    public void onSessionLogout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(BaseActivity.this, LogIn.class));
    }
}
