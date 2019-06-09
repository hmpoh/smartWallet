package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Transfer extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        button = (Button)findViewById(R.id.btnMore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(Transfer.this, button);
                popupMenu.getMenuInflater().inflate(R.menu.more, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.Qr){

                            qRActivity();

                        }
                        if (item.getItemId()==R.id.inbox){

                            inboxActivity();

                        }
                        if (item.getItemId()==R.id.logout){

                        }
                       // Toast.makeText(Transfer.this,""+item.getTitle(),Toast.LENGTH_SHORT).show();
                        return  true;
                    }
                });

                popupMenu.show();
            }
        });

    }

    private void qRActivity(){
        Intent QR = new Intent(this, QR.class);
        startActivity(QR);
    }

    private void inboxActivity(){
        Intent QR = new Intent(this, Inbox.class);
        startActivity(QR);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.navigation_pay:
                            selectedFragment = new TransferFragment();
                            break;
                        case R.id.navigation_split:
                            selectedFragment = new SplitFragment();
                            break;
                        case R.id.navigation_account:
                            selectedFragment = new AccountFragment();
                            break;
                        case R.id.navigation_history:
                            selectedFragment = new HistoryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
