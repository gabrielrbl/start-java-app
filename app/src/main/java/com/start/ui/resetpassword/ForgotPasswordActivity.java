package com.start.ui.resetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.start.dao.UsuarioDAO;
import com.start.databinding.ActivityForgotPasswordBinding;
import com.start.ui.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btResetar.setOnClickListener(v -> {
            if (binding.editEmail.getText().toString().equals("")) {
                Toast.makeText(this, "Informe o e-mail!", Toast.LENGTH_SHORT).show();
            } else {
                UsuarioDAO usuarioDAO = new UsuarioDAO(this);

                if (usuarioDAO.resetPassword(binding.editEmail.getText().toString())){
                    Toast.makeText(this, "A senha do usuário " + binding.editEmail.getText().toString() + " foi redefinida para: 123.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Não foi possível redefinir a senha do usuário " + binding.editEmail.getText().toString() + ".", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.textTelaLogin.setOnClickListener(view -> {
            startActivity(new Intent(getApplication(), LoginActivity.class));
        });
    }
}