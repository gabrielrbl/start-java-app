package com.start.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.start.database.DataBaseHelper;
import com.start.model.Pessoa;

public class PessoaDAO {
    private SQLiteDatabase db;
    private DataBaseHelper dataBaseHelper;

    public PessoaDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void cadastrar(Pessoa pessoa) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("idusuario", pessoa.getUsuarioId());
        dados.put("cpf", pessoa.getCpf());

        db.insertOrThrow("pessoa", null, dados);
    }

    public void atualizar(String id, String cpf) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        String where;

        where = "idpessoa = " + id;

        dados.put("cpf", cpf);

        db.update("pessoa", dados, where,null);
    }

    public Pessoa getPessoaByUsuarioId(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Pessoa pessoa = null;

        Cursor cursor = db.rawQuery("select * from pessoa where idusuario = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            pessoa = new Pessoa();

            pessoa.setPessoaId(cursor.getString(0));
            pessoa.setUsuarioId(cursor.getString(1));
            pessoa.setCpf(cursor.getString(2));
        }

        return pessoa;
    }
}
