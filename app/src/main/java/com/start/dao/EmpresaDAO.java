package com.start.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.start.database.DataBaseHelper;
import com.start.model.Empresa;

public class EmpresaDAO {
    private SQLiteDatabase db;
    private DataBaseHelper dataBaseHelper;

    public EmpresaDAO(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public void cadastrar(Empresa empresa) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("idusuario", empresa.getUsuarioId());
        dados.put("cnpj", empresa.getCnpj());
        dados.put("endereco", empresa.getEndereco());

        db.insertOrThrow("empresa", null, dados);
    }

    public void atualizar(String id, String cnpj, String endereco) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        String where;

        where = "idempresa = " + id;

        dados.put("cnpj", cnpj);
        dados.put("endereco", endereco);

        db.update("empresa", dados, where,null);
    }

    public Empresa getEmpresaByEmpresaId(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Empresa empresa = null;

        Cursor cursor = db.rawQuery("select * from empresa where idempresa = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            empresa = new Empresa();

            empresa.setEmpresaId(cursor.getString(0));
            empresa.setUsuarioId(cursor.getString(1));
            empresa.setCnpj(cursor.getString(2));
            empresa.setEndereco(cursor.getString(3));
        }

        return empresa;
    }

    public Empresa getEmpresaByUsuarioId(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Empresa empresa = null;

        Cursor cursor  = db.rawQuery("select * from empresa where idusuario = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            empresa = new Empresa();

            empresa.setEmpresaId(cursor.getString(0));
            empresa.setUsuarioId(cursor.getString(1));
            empresa.setCnpj(cursor.getString(2));
            empresa.setEndereco(cursor.getString(3));
        }

        return empresa;
    }
}
