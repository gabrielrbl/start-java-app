package com.start.ui.cadastro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.start.R;
import com.start.databinding.FragmentCadastroTipoBinding;

public class CadastroTipoFragment extends Fragment {
    private FragmentCadastroTipoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCadastroTipoBinding.inflate(inflater, container, false);

        final AppCompatButton btPessoaFisica = binding.btVagas;
        final AppCompatButton btPessoaJuridica = binding.btCandidatos;

        btPessoaFisica.setOnClickListener(v -> {
            Fragment fragment = new CadastroPessoalFragment();

            Bundle bundle = new Bundle();
            bundle.putString("tipo", "fisico");
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        btPessoaJuridica.setOnClickListener(v -> {
            Fragment fragment = new CadastroPessoalFragment();

            Bundle bundle = new Bundle();
            bundle.putString("tipo", "juridico");
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}