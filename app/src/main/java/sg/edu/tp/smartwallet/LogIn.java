package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    EditText accessCode;
    EditText pin;
    private FirebaseAuth mAuth;
    private static final String TAG ="LoginScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            handleUserLogin(currentUser);
        }
    }

    private void handleUserLogin(FirebaseUser currentUser) {
        Intent intent = new Intent(this, Transfer.class);
        startActivity(intent);

        Log.d(TAG, "handling user log in");
    }

    public void onClickButtonLogin(View view){
        accessCode = (EditText) findViewById(R.id.accessCode);
        pin = (EditText) findViewById(R.id.pin);

        String accessCodeString = accessCode.getText().toString() + "@smartwallet.sg";
        String pinString = pin.getText().toString();

        if (accessCodeString.equals("@smartwallet.sg")){
            Toast.makeText(LogIn.this, "please fill in your Access Code.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (pinString.equals("")){
            Toast.makeText(LogIn.this, "please fill in your Pin.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(accessCodeString, pinString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            handleUserLogin(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onBackPressed() {

    }
}
