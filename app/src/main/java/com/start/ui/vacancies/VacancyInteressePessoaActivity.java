package com.start.ui.vacancies;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.start.dao.EmpresaDAO;
import com.start.dao.PessoaDAO;
import com.start.dao.UsuarioDAO;
import com.start.dao.VagaDAO;
import com.start.databinding.ActivityVacancyInteressePessoaBinding;
import com.start.model.Empresa;
import com.start.model.Pessoa;
import com.start.model.Usuario;
import com.start.model.VagaEmpresa;
import com.start.model.VagaPessoa;
import com.start.session.SessionManagement;
import com.start.ui.home.HomeActivity;

public class VacancyInteressePessoaActivity extends AppCompatActivity {
    private ActivityVacancyInteressePessoaBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVacancyInteressePessoaBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SessionManagement sessionManagement = new SessionManagement(this);
        int userID = sessionManagement.getSession();

        PessoaDAO pessoaDAO = new PessoaDAO(this);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        VagaDAO vagaDAO = new VagaDAO(this);
        EmpresaDAO empresaDAO = new EmpresaDAO(this);

        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        String acao = intent.getStringExtra("acao");

        binding.backButton.setOnClickListener(v -> { super.onBackPressed(); });

        VagaEmpresa vagaEmpresa = vagaDAO.getVagaEmpresaById(code);
        Empresa empresa = empresaDAO.getEmpresaByEmpresaId(vagaEmpresa.getEmpresaId());
        Usuario usuario = usuarioDAO.getUsuarioById(empresa.getUsuarioId());

        binding.vaga.setText(vagaEmpresa.getVaga());
        binding.nomeEmpresa.setText(usuario.getNome());
        binding.telEmpresa.setText(usuario.getTelefone());
        binding.enderecoEmpresa.setText(empresa.getEndereco());

        switch (acao){
            case "visualizar":
                binding.btAcao.setText("REMOVER INTERESSE");

                VagaPessoa vagaPessoa = vagaDAO.getVagaPessoaByVagaEmpresaId(code);

                binding.btAcao.setOnClickListener(v -> {
                    vagaDAO.deleteVagaPessoa(vagaPessoa.getVagaPessoaId());

                    Toast.makeText(VacancyInteressePessoaActivity.this, "O interesse pela vaga foi removido.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, HomeActivity.class).putExtra("goTo", "list"));
                });
                break;
            case "interesse":
                binding.btAcao.setText("TENHO INTERESSE");

                binding.btAcao.setOnClickListener(v -> {
                    ContentValues dados = new ContentValues();

                    Pessoa pessoa = pessoaDAO.getPessoaByUsuarioId(String.valueOf(userID));

                    dados.put("idvaga_empresa", vagaEmpresa.getVagaEmpresaId());
                    dados.put("idpessoa", pessoa.getPessoaId());

                    vagaDAO.inserirVagaPessoa(dados);

                    Toast.makeText(VacancyInteressePessoaActivity.this, "Foi declarado interesse pela vaga. A empresa entrará em contato!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(this, HomeActivity.class));
                });
                break;
        }


    }
}