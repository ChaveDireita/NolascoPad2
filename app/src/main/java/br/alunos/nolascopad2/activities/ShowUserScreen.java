package br.alunos.nolascopad2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import br.alunos.nolascopad2.R;
import br.alunos.nolascopad2.fragments.ListLocalBooks;
import br.alunos.nolascopad2.fragments.UserInfo;

public class ShowUserScreen extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_screen);
        UserInfo fragment = new UserInfo();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.userdetailsframe,fragment);
        transaction.commit();
        Toolbar toolbar = findViewById(R.id.usertoolbar);
        drawer = findViewById(R.id.userdrawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(ShowUserScreen.this,HomeScreen.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_allbooks:
                        intent = new Intent(ShowUserScreen.this,ShowAllBooks.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_bar_open,R.string.navigation_bar_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.branco));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_allbooks,
                R.id.nav_showuser)
                .setDrawerLayout(drawer)
                .build();

    }

    @Override
    public void onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}
