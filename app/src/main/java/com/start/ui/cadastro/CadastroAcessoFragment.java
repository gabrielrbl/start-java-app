package com.start.ui.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.start.dao.EmpresaDAO;
import com.start.dao.PessoaDAO;
import com.start.dao.UsuarioDAO;
import com.start.databinding.FragmentCadastroAcessoBinding;
import com.start.model.Empresa;
import com.start.model.Pessoa;
import com.start.model.Usuario;
import com.start.ui.login.LoginActivity;

public class CadastroAcessoFragment extends Fragment {
    private FragmentCadastroAcessoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadastroAcessoBinding.inflate(inflater, container, false);

        final AppCompatButton avancar = binding.btCadastrarAcesso;
        final EditText email = binding.editEmail;
        final EditText senha = binding.editSenha;

        Bundle received = getArguments();
        String tipo = received.getString("tipo");
        String nome = received.getString("nome");
        String doc = received.getString("doc");
        String telefone = received.getString("telefone");
        String endereco = received.getString("endereco");

        avancar.setOnClickListener(v -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity().getApplicationContext());

            if(email.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Por favor, insira o email.", Toast.LENGTH_SHORT).show();
            } else if (usuarioDAO.checkEmail(email.getText().toString())) {
                Toast.makeText(getActivity(), "Esse e-mail já está cadastrado no nosso sistema!", Toast.LENGTH_SHORT).show();
            } else if (senha.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Por favor, insira a senha.", Toast.LENGTH_SHORT).show();
            } else {
                Usuario usuario = new Usuario();
                usuario.setNome(nome);
                usuario.setTelefone(telefone);
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                String usuarioId = usuarioDAO.cadastrar(usuario);

                switch (tipo) {
                    case "fisico":
                        Pessoa pessoa = new Pessoa();
                        pessoa.setUsuarioId(usuarioId);
                        pessoa.setCpf(doc);

                        PessoaDAO pessoaDAO = new PessoaDAO(getActivity().getApplicationContext());
                        pessoaDAO.cadastrar(pessoa);
                        break;
                    case "juridico":
                        Empresa empresa = new Empresa();
                        empresa.setUsuarioId(usuarioId);
                        empresa.setCnpj(doc);
                        empresa.setEndereco(endereco);

                        EmpresaDAO empresaDAO = new EmpresaDAO(getActivity().getApplicationContext());
                        empresaDAO.cadastrar(empresa);
                        break;
                }

                Toast.makeText(getActivity(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}