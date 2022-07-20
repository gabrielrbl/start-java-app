package com.start.ui.cadastro.conf;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.start.ui.cadastro.CadastroAcessoFragment;
import com.start.ui.cadastro.CadastroPessoalFragment;
import com.start.ui.cadastro.CadastroTipoFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ViewPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) { super(fragmentManager, lifecycle); }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CadastroTipoFragment();
            case 1:
                return new CadastroPessoalFragment();
            case 2:
                return new CadastroAcessoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
