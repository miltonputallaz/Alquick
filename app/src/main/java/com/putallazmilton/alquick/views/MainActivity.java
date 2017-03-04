package com.putallazmilton.alquick.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.putallazmilton.alquick.R;
import com.putallazmilton.alquick.adapters.PagerAdapter;
import com.putallazmilton.alquick.views.fragments.favouritesFragment;
import com.putallazmilton.alquick.views.fragments.homeFragment;
import com.putallazmilton.alquick.views.fragments.misAlquileresFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            goLoginScreen();
        } else {
            Button cerrarsesion=(Button) findViewById(R.id.btncerrarsesion);
            cerrarsesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent inte=new Intent(getApplicationContext(),LoginActivity.class);
                    inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(inte);
                }
            });
            FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.floatbutton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),agregarActivity.class);

                    startActivity(intent);
                }
            });
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new homeFragment(), "Home");
            adapter.addFragment(new favouritesFragment(),"Mis favoritos");
            adapter.addFragment(new misAlquileresFragment(),"Mis alquileres");

            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);

        }

    }

    private void goLoginScreen(){
        Intent inte=new Intent(this,LoginActivity.class);
        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(inte);
    }

}
