package com.tinydino.graffiti.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.identitytoolkit.GitkitClient;
import com.google.identitytoolkit.GitkitUser;
import com.google.identitytoolkit.IdToken;
import com.tinydino.graffiti.R;


public class UserLoginActivity extends ActionBarActivity {

    private GitkitClient client;
    private TextView userNameEditText;
    private Button loginButton;
    private final static String _defaultServerUri = "https://thawing-island-7364.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GitkitClient.SignInCallbacks callbacks = new GitkitClient.SignInCallbacks() {
            // Implement the onSignIn method of GitkitClient.SignInCallbacks interface.
            @Override
            public void onSignIn(IdToken idToken, GitkitUser gitkitUser) {
                // Send the idToken to the server. The server should issue a session cookie/token
                // for the app if the idToken is verified.
                // authenticate(idToken.getTokenString());
            }

            // Implement the onSignInFailed method of GitkitClient.SignInCallbacks interface.
            @Override
            public void onSignInFailed() {
                // Handle the sign in failure.
            }
        };

        setContentView(R.layout.activity_user_login);
        userNameEditText = (TextView)findViewById(R.id.userNameTextEdit);
        loginButton = (Button)findViewById(R.id.loginButton);

        client = GitkitClient.newBuilder(this, callbacks).build();
        client.startSignIn();

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(v);
            }
        }); */

    }

    public void doLogin(View view) {
        String userName = userNameEditText.getText().toString().trim();

        if (userName == null || userName.isEmpty())
            userName = new String("DefaultUserMcGee");

        Intent messageBoardIntent = new Intent();
        messageBoardIntent.putExtra("userName", userName);
        messageBoardIntent.putExtra("serverUri", _defaultServerUri);
        messageBoardIntent.setClass(this, MessageBoardActivity.class);

        startActivity(messageBoardIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (client.handleIntent(intent)) {
            // intent is handled by the GitkitClient.
            return;
        }
    }

    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (client.handleActivityResult(requestCode, resultCode, data)) {
            // result is handled by GitkitClient.
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
