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
import br.alunos.nolascopad2.fragments.ListLocalCaps;

public class CapsScreen extends AppCompatActivity {
    public int bookid;
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caps_screen);
        Intent intento = getIntent();
        bookid = intento.getIntExtra("BookId",-1);
        ListLocalCaps fragment = new ListLocalCaps();
        Bundle bundle = new Bundle();
        bundle.putInt("CurrentBook",bookid);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.listcreatecapsframe,fragment);
        transaction.commit();
        Toolbar toolbar = findViewById(R.id.capstoolbar);
        drawer = findViewById(R.id.capsdrawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(CapsScreen.this,HomeScreen.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_allbooks:
                        intent = new Intent(CapsScreen.this,ShowAllBooks.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_showuser:
                        intent = new Intent(CapsScreen.this,ShowUserScreen.class);
                        startActivity(intent);
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
