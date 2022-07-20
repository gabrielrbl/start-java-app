package com.start.ui.vacancies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.start.dao.EmpresaDAO;
import com.start.dao.VagaDAO;
import com.start.databinding.ActivityVacancyCreateBinding;
import com.start.model.Empresa;
import com.start.model.VagaEmpresa;
import com.start.session.SessionManagement;
import com.start.ui.home.HomeActivity;

public class VacancyCreateActivity extends AppCompatActivity {
    private ActivityVacancyCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVacancyCreateBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SessionManagement sessionManagement = new SessionManagement(this);
        int userID = sessionManagement.getSession();

        EmpresaDAO empresaDAO = new EmpresaDAO(this);
        Empresa empresa = empresaDAO.getEmpresaByUsuarioId(String.valueOf(userID));

        binding.backButton.setOnClickListener(v -> { super.onBackPressed(); });

        binding.btCriar.setOnClickListener(v -> {
            VagaEmpresa vagaEmpresa = new VagaEmpresa();
            vagaEmpresa.setEmpresaId(empresa.getEmpresaId());
            vagaEmpresa.setVaga(binding.editVaga.getText().toString());

            VagaDAO vagaDAO = new VagaDAO(this);
            vagaDAO.inserirVagaEmpresa(vagaEmpresa);

            Toast.makeText(this, "Nova vaga cadastrada!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, HomeActivity.class).putExtra("goTo", "list"));
        });
    }
}