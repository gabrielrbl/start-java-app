package com.start.ui.vacancies;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.R;
import com.start.dao.VagaDAO;
import com.start.databinding.ActivityVacancyPessoasInteressadasBinding;
import com.start.model.VagaEmpresa;
import com.start.ui.home.HomeActivity;

public class VacancyPessoasInteressadasActivity extends AppCompatActivity {
    private ActivityVacancyPessoasInteressadasBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVacancyPessoasInteressadasBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String code = intent.getStringExtra("code");

        binding.backButton.setOnClickListener(v -> { super.onBackPressed(); });

        VagaDAO vagaDAO = new VagaDAO(this);
        VagaEmpresa vagaEmpresa = vagaDAO.getVagaEmpresaById(code);

        binding.editVaga.setText(vagaEmpresa.getVaga());
        binding.editVaga.setVisibility(View.VISIBLE);

        binding.btEditarVaga.setOnClickListener(view -> {
            vagaDAO.updateVagaEmpresa(code, binding.editVaga.getText().toString());

            Toast.makeText(this, "Vaga editada com sucesso!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, HomeActivity.class).putExtra("goTo", "list"));
        });

        Cursor cursor = vagaDAO.getPessoasVaga(code);

        binding.deleteVaga.setOnClickListener(view -> {
            vagaDAO.deleteVagaEmpresa(code);

            Toast.makeText(this, "Vaga removida com sucesso!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, HomeActivity.class).putExtra("goTo", "list"));
        });

        String[] nomeCampos = new String[]{"nome", "telefone"};
        int[] idViews = new int[]{R.id.listOne, R.id.listTwo};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.layout_vagas, cursor, nomeCampos, idViews, 0);

        if (adapter.isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.homePessoas.setAdapter(adapter);
        }

        binding.homePessoas.setOnItemClickListener((adapterView, view, i, l) -> {
            cursor.moveToPosition(i);
            String tel = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));

            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + tel + "")));
        });
    }
}