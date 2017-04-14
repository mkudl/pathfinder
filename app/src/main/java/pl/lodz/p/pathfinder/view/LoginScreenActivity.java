package pl.lodz.p.pathfinder.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import pl.lodz.p.pathfinder.AccountSingleton;
import pl.lodz.p.pathfinder.Configuration;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.TripMenuType;

public class LoginScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener//, View.OnClickListener
{

    GoogleApiClient googleApiClient;
    final static int RC_SIGN_IN=1337;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);



        if (AccountSingleton.INSTANCE.getAccount() != null)
        {
            launchMainMenu();
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(Configuration.GOOGLE_SERVER_CLIENT_ID)
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(v ->
                startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), RC_SIGN_IN) );

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

        Snackbar.make( findViewById(R.id.login_content_layout),
                getResources().getString(R.string.error_network_no_connection),
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

//    @Override
//    public void onClick(View v)
//    {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            userSignedIn(result);
        }
    }



    private void userSignedIn(GoogleSignInResult result)
    {
        if(result.isSuccess())  //save user account in singleton
        {
            GoogleSignInAccount asd = result.getSignInAccount();
            AccountSingleton.INSTANCE.setAccount(asd);
            //TODO launch main menu

            launchMainMenu();
        }
        else{   //display error message
            Snackbar.make( findViewById(R.id.login_content_layout),
                    getResources().getString(R.string.error_login_failed),
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    private void launchMainMenu()
    {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }


}
