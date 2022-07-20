package com.start.ui.home;

import  android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.start.R;
import com.start.dao.UsuarioDAO;
import com.start.databinding.ActivityHomeBinding;
import com.start.databinding.NavHeaderMainBinding;
import com.start.model.Usuario;
import com.start.session.SessionManagement;
import com.start.ui.login.LoginActivity;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        Intent intent = getIntent();
        String goTo = intent.getStringExtra("goTo");

        SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);
        int userID = sessionManagement.getSession();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View viewHeader = navigationView.getHeaderView(0);
        NavHeaderMainBinding navHeaderMainBinding = NavHeaderMainBinding.bind(viewHeader);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        Usuario usuario = usuarioDAO.getUsuarioById(String.valueOf(userID));

        navHeaderMainBinding.textNavHeaderNome.setText(usuario.getNome());
        navHeaderMainBinding.textNavHeaderEmail.setText(usuario.getEmail());

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_profile, R.id.nav_vacancies).setOpenableLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (goTo != null && goTo.equals("list")){
            navController.navigate(R.id.nav_vacancies);
        }

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return false;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public void logout() {
        SessionManagement sessionManagement = new SessionManagement(HomeActivity.this);
        sessionManagement.removeSession();

        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}