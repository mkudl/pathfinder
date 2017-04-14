package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.TripMenuType;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Button createdButton = (Button) findViewById(R.id.main_menu_button_created);
        Button favoritesButton = (Button) findViewById(R.id.main_menu_button_favorites);
        Button recButton = (Button) findViewById(R.id.main_menu_button_recommended);
        Button poiButton = (Button) findViewById(R.id.main_menu_button_poi);

        createdButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, TripMenuActivity.class);
            intent.putExtra("TRIPMENU_TYPE", TripMenuType.CREATED);
            startActivity(intent);});

        favoritesButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, TripMenuActivity.class);
            intent.putExtra("TRIPMENU_TYPE",TripMenuType.FAVORITES);
            startActivity(intent);});

        recButton.setOnClickListener( v -> {
            Intent intent = new Intent(this, TripMenuActivity.class);
            intent.putExtra("TRIPMENU_TYPE",TripMenuType.RECOMMENDED);
            startActivity(intent);});

        poiButton.setOnClickListener( v ->  startActivity(new Intent(this, PoiMenuActivity.class)));


        View header = navigationView.getHeaderView(0);

        TextView navHeaderName = (TextView) header.findViewById(R.id.navheader_name);
        TextView navHeaderEmail = (TextView) header.findViewById(R.id.navheader_email);


        TextView welcomeText = (TextView) findViewById(R.id.main_menu_welcome);
        if(AccountSingleton.INSTANCE.getAccount() != null)
        {
            welcomeText.setText(getResources()
                    .getString(R.string.main_menu_message_welcome,
                                AccountSingleton.INSTANCE.getAccount().getDisplayName()));
            navHeaderName.setText(AccountSingleton.INSTANCE.getAccount().getDisplayName());
            navHeaderEmail.setText( AccountSingleton.INSTANCE.getAccount().getEmail());
        } else Log.wtf("MAIN_MENU", "User accessed main menu without logging in");  //should never happen

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_logout)
        {
            // TODO
        } else if (id == R.id.menu_info)
        {
            //TODO
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
