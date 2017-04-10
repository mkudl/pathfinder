package pl.lodz.p.pathfinder;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by QDL on 2017-04-08.
 */

public enum AccountSingleton
{
    INSTANCE;

    private GoogleSignInAccount account;

    public GoogleSignInAccount getAccount()
    {
        return account;
    }

    public void setAccount(GoogleSignInAccount account)
    {
        this.account = account;
    }

    private AccountSingleton() {}
}
