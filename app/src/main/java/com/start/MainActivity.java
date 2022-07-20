package com.start;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.start.session.SessionManagement;
import com.start.ui.home.HomeActivity;
import com.start.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userID = sessionManagement.getSession();

        if(userID != -1){
            moveToMainActivity();
        } else {
            moveToLogin();
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
