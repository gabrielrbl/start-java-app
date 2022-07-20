package com.start.dao;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.start.database.DataBaseHelper;
import com.start.model.Usuario;
import com.start.model.VagaEmpresa;
import com.start.model.VagaPessoa;

import java.util.ArrayList;
import java.util.List;

public class VagaDAO {
    private SQLiteDatabase db;
    private DataBaseHelper dataBaseHelper;

    public VagaDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void inserirVagaEmpresa(VagaEmpresa vaga) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("idempresa", vaga.getEmpresaId());
        dados.put("vaga", vaga.getVaga());

        db.insertOrThrow("vaga_empresa", null, dados);
    }

    public void inserirVagaPessoa(ContentValues dados) {
        db = dataBaseHelper.getWritableDatabase();

        db.insertOrThrow("vaga_pessoa", null, dados);
    }

    public Cursor getVagasWithoutInteresse(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select ve.idvaga_empresa _id, ve.vaga, us.nome empresa " +
            "from vaga_empresa ve " +
            "join empresa em on ve.idempresa = em.idempresa " +
            "join usuario us on us.idusuario = em.idusuario " +
            "where ve.idvaga_empresa not in (select idvaga_empresa from vaga_pessoa where idpessoa = ?)", new String[]{ id });

        return cursor;
    }

    public void updateVagaEmpresa(String id, String vaga) {
        db = dataBaseHelper.getWritableDatabase();

        db.execSQL("update vaga_empresa set vaga = ? where idvaga_empresa = ?", new String[] { vaga, id });
    }

    public Cursor getVagas() {
        db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select ve.idvaga_empresa _id, ve.vaga, us.nome empresa " +
            "from vaga_empresa ve " +
            "join empresa em on ve.idempresa = em.idempresa " +
            "join usuario us on us.idusuario = em.idusuario", null);

        return cursor;
    }

    public VagaEmpresa getVagaEmpresaById(String id) {
        db = dataBaseHelper.getWritableDatabase();

        VagaEmpresa vagaEmpresa = null;

        Cursor cursor = db.rawQuery("select * from vaga_empresa where idvaga_empresa = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            vagaEmpresa = new VagaEmpresa();

            vagaEmpresa.setVagaEmpresaId(cursor.getString(0));
            vagaEmpresa.setEmpresaId(cursor.getString(1));
            vagaEmpresa.setVaga(cursor.getString(2));
        }

        return vagaEmpresa;
    }

    public VagaPessoa getVagaPessoaById(String id) {
        db = dataBaseHelper.getWritableDatabase();

        VagaPessoa vagaPessoa = null;

        Cursor cursor = db.rawQuery("select * from vaga_pessoa where idvaga_pessoa = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            vagaPessoa = new VagaPessoa();

            vagaPessoa.setVagaPessoaId(cursor.getString(0));
            vagaPessoa.setVagaEmpresaId(cursor.getString(1));
            vagaPessoa.setPessoaId(cursor.getString(2));
        }

        return vagaPessoa;
    }

    public VagaPessoa getVagaPessoaByVagaEmpresaId(String id) {
        db = dataBaseHelper.getWritableDatabase();

        VagaPessoa vagaPessoa = null;

        Cursor cursor = db.rawQuery("select * from vaga_pessoa where idvaga_empresa = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            vagaPessoa = new VagaPessoa();

            vagaPessoa.setVagaPessoaId(cursor.getString(0));
            vagaPessoa.setVagaEmpresaId(cursor.getString(1));
            vagaPessoa.setPessoaId(cursor.getString(2));
        }

        return vagaPessoa;
    }

    public void deleteVagaEmpresa(String id) {
        db = dataBaseHelper.getWritableDatabase();

        db.execSQL("delete from vaga_empresa where idvaga_empresa = ?", new String[] { id });
    }

    public void deleteVagaPessoa(String id) {
        db = dataBaseHelper.getWritableDatabase();

        db.execSQL("delete from vaga_pessoa where idvaga_pessoa = ?", new String[] { id });
    }

    public Cursor getVagasEmpresa(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select idvaga_empresa _id, idempresa, vaga from vaga_empresa where idempresa = ?", new String[]{ id });

        return cursor;
    }

    public Cursor getVagasPessoa(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select vp.idvaga_pessoa _id, ve.idvaga_empresa, ve.vaga, us.nome " +
                "from vaga_pessoa vp " +
                "join vaga_empresa ve on vp.idvaga_empresa = ve.idvaga_empresa " +
                "join empresa em on em.idempresa = ve.idempresa " +
                "join usuario us on us.idusuario = em.idusuario " +
                "where idpessoa = ?", new String[]{ id });

        return cursor;
    }

    public Cursor getPessoasVaga(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select vp.idvaga_pessoa _id, us.nome, us.telefone " +
                "from vaga_pessoa vp " +
                "join pessoa pe on vp.idpessoa = pe.idpessoa " +
                "join usuario us on us.idusuario = pe.idusuario " +
                "where vp.idvaga_empresa = ?", new String[]{ id });

        return cursor;
    }
}