package com.start.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.start.ui.cadastro.CadastroActivity;
import com.start.ui.home.HomeActivity;
import com.start.dao.UsuarioDAO;
import com.start.databinding.ActivityLoginBinding;
import com.start.model.Usuario;
import com.start.session.SessionManagement;
import com.start.ui.resetpassword.ForgotPasswordActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        binding.editSenha.setOnEditorActionListener((textView, i, keyEvent) -> {
            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                binding.btEntrar.performClick();
            }
            return false;
        });

        binding.textTelaEsqueceusenha.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(intent);
        });

        binding.btEntrar.setOnClickListener(v -> {
            Usuario usuario = usuarioDAO.login(binding.editEmail.getText().toString(), binding.editSenha.getText().toString());

            if(usuario != null) {
                Toast.makeText(getApplicationContext(), "Bem vindo, " + usuario.getNome() + "!", Toast.LENGTH_SHORT).show();

                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                sessionManagement.saveSession(usuario);

                moveToHomeActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Dados incorretos!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.textTelaCadastro.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
        });
    }

    private void moveToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}