package sg.edu.tp.smartwallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends BaseActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.navigation_pay);

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

    public void btnMore (View view) {
        android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.more, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.Qr){

                    qRActivity();

                }
                if (item.getItemId()==R.id.inbox){

                    Intent QR = new Intent(HomeActivity.this, Inbox.class);
                    startActivity(QR);

                }
                if (item.getItemId()==R.id.logout){

                    logout();
                }
                // Toast.makeText(HomeActivity.this,""+item.getTitle(),Toast.LENGTH_SHORT).show();
                return  true;
            }
        });

        popupMenu.show();

    }
    public void btnStart (View view){
        Intent intent = new Intent (HomeActivity.this, AllGroups.class);
        startActivity(intent);
    }

    public void onClickPay (View view){
        Intent intent = new Intent (HomeActivity.this, FindPersonToPay.class);
        startActivity(intent);
    }

    private void logout(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        startActivity(new Intent(HomeActivity.this, LogIn.class));
    }

    private void qRActivity(){
        Intent QR = new Intent(HomeActivity.this, QR.class);
        startActivity(QR);
    }





}
