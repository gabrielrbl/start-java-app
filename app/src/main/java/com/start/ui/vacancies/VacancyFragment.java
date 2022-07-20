package com.start.ui.vacancies;

import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.start.R;
import com.start.dao.EmpresaDAO;
import com.start.dao.PessoaDAO;
import com.start.dao.UsuarioDAO;
import com.start.dao.VagaDAO;
import com.start.databinding.FragmentVacancyBinding;
import com.start.model.Empresa;
import com.start.model.Pessoa;
import com.start.model.Usuario;
import com.start.session.SessionManagement;
import com.start.ui.cadastro.CadastroAcessoFragment;
import com.start.ui.home.HomeActivity;

public class VacancyFragment extends Fragment {
    private FragmentVacancyBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVacancyBinding.inflate(inflater, container, false);

        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        SessionManagement sessionManagement = new SessionManagement(getActivity());
        int userID = sessionManagement.getSession();

        UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity().getApplicationContext());
        PessoaDAO pessoaDAO = new PessoaDAO(getActivity().getApplicationContext());
        EmpresaDAO empresaDAO = new EmpresaDAO(getActivity().getApplicationContext());

        Usuario usuario = usuarioDAO.getUsuarioById(String.valueOf(userID));
        Pessoa pessoa = pessoaDAO.getPessoaByUsuarioId(usuario.getUsuarioId());
        Empresa empresa = empresaDAO.getEmpresaByUsuarioId(usuario.getUsuarioId());

        VagaDAO vagaDAO = new VagaDAO(getActivity().getApplicationContext());

        if(checkEmpresa()) {
            mActionBar.setTitle("Vagas cadastradas");
            binding.titleHome.setText("Suas vagas cadastradas");

            Cursor cursor = vagaDAO.getVagasEmpresa(empresa.getEmpresaId());

            String[] nomeCampos = new String[]{"_id", "vaga"};
            int[] idViews = new int[]{R.id.listOne, R.id.listTwo};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.layout_vagas, cursor, nomeCampos, idViews, 0);

            if (!adapter.isEmpty()) {
                binding.listVagas.setAdapter(adapter);

                binding.listVagas.setOnItemClickListener((adapterView, view, i, l) -> {
                    cursor.moveToPosition(i);
                    String code = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                    Intent intent = new Intent(getActivity().getApplicationContext(), VacancyPessoasInteressadasActivity.class);
                    intent.putExtra("code", code);
                    startActivity(intent);
                });
            } else {
                binding.txtEmpty.setText("Sem vagas cadastradas");
                binding.txtEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            mActionBar.setTitle("Vagas interessadas");
            binding.titleHome.setText("Suas vagas interessadas");

            Cursor cursor = vagaDAO.getVagasPessoa(pessoa.getPessoaId());

            String[] nomeCampos = new String[] {"vaga", "nome"};
            int[] idViews = new int[] {R.id.listOne, R.id.listTwo};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.layout_vagas, cursor, nomeCampos, idViews, 0);

            if (!adapter.isEmpty()) {
                binding.listVagas.setAdapter(adapter);
            } else {
                binding.txtEmpty.setText("Sem vagas interessadas");
                binding.txtEmpty.setVisibility(View.VISIBLE);
            }

            binding.listVagas.setOnItemClickListener((adapterView, view, i, l) -> {
                cursor.moveToPosition(i);
                String code = cursor.getString(cursor.getColumnIndexOrThrow("idvaga_empresa"));

                Intent intent = new Intent(getActivity().getApplicationContext(), VacancyInteressePessoaActivity.class);
                intent.putExtra("code", code);
                intent.putExtra("acao", "visualizar");
                startActivity(intent);
            });
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

        return empresa != null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if(checkEmpresa()) inflater.inflate(R.menu.vacancy_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getActivity(), VacancyCreateActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}