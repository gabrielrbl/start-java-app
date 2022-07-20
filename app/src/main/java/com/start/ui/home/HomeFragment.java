package com.start.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.start.R;
import com.start.dao.EmpresaDAO;
import com.start.dao.UsuarioDAO;
import com.start.dao.VagaDAO;
import com.start.databinding.FragmentHomeBinding;
import com.start.model.Empresa;
import com.start.model.Usuario;
import com.start.session.SessionManagement;
import com.start.ui.vacancies.VacancyCreateActivity;
import com.start.ui.vacancies.VacancyInteressePessoaActivity;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        SessionManagement sessionManagement = new SessionManagement(getActivity());
        int userID = sessionManagement.getSession();

        UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity().getApplicationContext());
        Usuario usuario = usuarioDAO.getUsuarioById(String.valueOf(userID));

        if(!checkEmpresa()) {
            binding.titleHome.setVisibility(View.VISIBLE);

            VagaDAO vagaDAO = new VagaDAO(getActivity().getApplicationContext());
            Cursor cursor = vagaDAO.getVagasWithoutInteresse(String.valueOf(userID));
            String[] nomeCampos = new String[] {"vaga", "empresa"};
            int[] idViews = new int[] {R.id.listOne, R.id.listTwo};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.layout_vagas, cursor, nomeCampos, idViews, 0);

            if(!adapter.isEmpty()) {
                binding.homeVagas.setVisibility(View.VISIBLE);
                binding.homeVagas.setAdapter(adapter);

                binding.homeVagas.setOnItemClickListener((adapterView, view, i, l) -> {
                    cursor.moveToPosition(i);
                    String code = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                    Intent intent = new Intent(getActivity().getApplicationContext(), VacancyInteressePessoaActivity.class);
                    intent.putExtra("code", code);
                    intent.putExtra("acao", "interesse");
                    startActivity(intent);
                });
            } else {
                binding.txtEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            binding.txtWelcome.setText("Olá, " + usuario.getNome().split(" ", 2)[0]);

            binding.btVisualizarMinhasVagas.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(R.id.nav_vacancies);
            });

            binding.btCadastrarVaga.setOnClickListener(view -> {
                startActivity(new Intent(getActivity().getApplicationContext(), VacancyCreateActivity.class));
            });

            binding.txtWelcome.setVisibility(View.VISIBLE);
            binding.btVisualizarMinhasVagas.setVisibility(View.VISIBLE);
            binding.btCadastrarVaga.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    private Boolean checkEmpresa() {
        SessionManagement sessionManagement = new SessionManagement(getActivity());
        int userID = sessionManagement.getSession();

        UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity().getApplicationContext());
        EmpresaDAO empresaDAO = new EmpresaDAO(getActivity().getApplicationContext());

        Usuario usuario = usuarioDAO.getUsuarioById(String.valueOf(userID));
        Empresa empresa = empresaDAO.getEmpresaByUsuarioId(usuario.getUsuarioId());

        if(empresa != null)
            return true;
        else
            return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}