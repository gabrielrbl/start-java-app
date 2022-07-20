package com.start.ui.cadastro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.start.R;
import com.start.databinding.FragmentCadastroPessoalBinding;

public class CadastroPessoalFragment extends Fragment {
    private FragmentCadastroPessoalBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadastroPessoalBinding.inflate(inflater, container, false);

        final AppCompatButton avancar = binding.btCadastroPessoal;
        final TextView titulo = binding.textDadospessoais;
        final EditText nome = binding.editNome;
        final EditText doc = binding.editDoc;
        final EditText telefone = binding.editTelefone;
        final EditText endereco = binding.editEndereco;

        Bundle received = getArguments();
        String tipo = received.getString("tipo");
        String documento = "";

        switch (tipo) {
            case "fisico":
                documento = "CPF";
                titulo.setText("Dados pessoais");
                nome.setHint("Nome completo");
                doc.setHint(documento);
                break;
            case "juridico":
                documento = "CNPJ";
                titulo.setText("Dados da empresa");
                nome.setHint("Nome da empresa");
                doc.setHint(documento);
                endereco.setVisibility(View.VISIBLE);
                break;
        }

        String finalDocumento = documento;

        avancar.setOnClickListener(v -> {
            if(nome.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Por favor, insira o nome.", Toast.LENGTH_SHORT).show();
            } else if (doc.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Por favor, insira o " + finalDocumento + ".", Toast.LENGTH_SHORT).show();
            } else if (telefone.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Por favor, insira o telefone.", Toast.LENGTH_SHORT).show();
            } else if (tipo.equals("juridico") && endereco.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Por favor, insira o endereço.", Toast.LENGTH_SHORT).show();
            } else {
                Fragment fragment = new CadastroAcessoFragment();

                Bundle sending = new Bundle();
                sending.putString("tipo", tipo);
                sending.putString("nome", nome.getText().toString());
                sending.putString("doc", doc.getText().toString());
                sending.putString("telefone", telefone.getText().toString());
                sending.putString("endereco", endereco.getText().toString());

                fragment.setArguments(sending);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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