package com.start.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.start.dao.EmpresaDAO;
import com.start.dao.PessoaDAO;
import com.start.dao.UsuarioDAO;
import com.start.databinding.FragmentProfileBinding;
import com.start.model.Empresa;
import com.start.model.Pessoa;
import com.start.model.Usuario;
import com.start.session.SessionManagement;
import com.start.ui.home.HomeActivity;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        SessionManagement sessionManagement = new SessionManagement(getActivity());
        int userID = sessionManagement.getSession();

        UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity().getApplicationContext());
        PessoaDAO pessoaDAO = new PessoaDAO(getActivity().getApplicationContext());
        EmpresaDAO empresaDAO = new EmpresaDAO(getActivity().getApplicationContext());

        Usuario usuario = usuarioDAO.getUsuarioById(String.valueOf(userID));
        Pessoa pessoa = pessoaDAO.getPessoaByUsuarioId(usuario.getUsuarioId());
        Empresa empresa = empresaDAO.getEmpresaByUsuarioId(usuario.getUsuarioId());

        final EditText nome = binding.txtNome;
        final EditText telefone = binding.txtTelefone;
        final EditText email = binding.txtEmail;
        final EditText dado = binding.txtDado;
        final EditText endereco = binding.txtEndereco;

        nome.setText(usuario.getNome());
        telefone.setText(usuario.getTelefone());
        email.setText(usuario.getEmail());

        if (checkEmpresa()) {
            dado.setHint("CNPJ");
            dado.setText(empresa.getCnpj());

            endereco.setVisibility(View.VISIBLE);
            endereco.setText(empresa.getEndereco());
        } else {
            dado.setHint("CPF");
            dado.setText(pessoa.getCpf());
        }

        binding.btAcao.setOnClickListener(v -> {
            if (nome.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "O campo nome não pode ser vazio.", Toast.LENGTH_SHORT).show();
            } else if (telefone.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "O campo telefone não pode ser vazio.", Toast.LENGTH_SHORT).show();
            } else if (email.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "O campo e-mail não pode ser vazio.", Toast.LENGTH_SHORT).show();
            }

            if (checkEmpresa()) {
                if (dado.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "O campo CNPJ não pode ser vazio.", Toast.LENGTH_SHORT).show();
                } else if (endereco.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "O campo endereço não pode ser vazio.", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (dado.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "O campo CPF não pode ser vazio.", Toast.LENGTH_SHORT).show();
                }
            }

            usuarioDAO.atualizar(userID, nome.getText().toString(), telefone.getText().toString(), email.getText().toString());

            if (checkEmpresa()) {
                empresaDAO.atualizar(empresa.getEmpresaId(), dado.getText().toString(), endereco.getText().toString());
            } else {
                pessoaDAO.atualizar(pessoa.getPessoaId(), dado.getText().toString());
            }

            Toast.makeText(getActivity(), "Dados atualizados!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
        });

        return binding.getRoot();
    }

    private Boolean checkEmpresa() {
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        int userID = sessionManagement.getSession();

        UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity().getApplicationContext());
        EmpresaDAO empresaDAO = new EmpresaDAO(getActivity().getApplicationContext());

        Usuario usuario = usuarioDAO.getUsuarioById(String.valueOf(userID));
        Empresa empresa = empresaDAO.getEmpresaByUsuarioId(usuario.getUsuarioId());

        return empresa != null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}