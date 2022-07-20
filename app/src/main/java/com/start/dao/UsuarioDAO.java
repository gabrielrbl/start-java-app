package com.start.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.start.database.DataBaseHelper;
import com.start.model.Usuario;

public class UsuarioDAO {
    private SQLiteDatabase db;
    private DataBaseHelper dataBaseHelper;

    public UsuarioDAO (Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public String cadastrar(Usuario usuario) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("nome", usuario.getNome());
        dados.put("telefone", usuario.getTelefone());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());

        String id = String.valueOf(db.insertOrThrow("usuario", null, dados));

        return id;
    }

    public void atualizar(Integer id, String nome, String telefone, String email) {
        db = dataBaseHelper.getWritableDatabase();

        ContentValues dados = new ContentValues();

        String where;

        where = "idusuario = " + id;

        dados.put("nome", nome);
        dados.put("telefone", telefone);
        dados.put("email", email);

        db.update("usuario", dados, where,null);
    }

    public Boolean checkEmail(String email) {
        db = dataBaseHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from usuario where email = ?", new String[]{ email });

        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Usuario login(String email, String senha) {
        db = dataBaseHelper.getWritableDatabase();

        Usuario usuario = null;

        Cursor cursor = db.rawQuery("select * from usuario where email = ? and senha = ?", new String[]{ email, senha });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            usuario = new Usuario();

            usuario.setUsuarioId(cursor.getString(0));
            usuario.setNome(cursor.getString(1));
            usuario.setTelefone(cursor.getString(2));
            usuario.setEmail(cursor.getString(3));
        }

        return usuario;
    }

    public Usuario getUsuarioById(String id) {
        db = dataBaseHelper.getWritableDatabase();

        Usuario usuario = null;

        Cursor cursor = db.rawQuery("select * from usuario where idusuario = ?", new String[]{ id });

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            usuario = new Usuario();

            usuario.setUsuarioId(cursor.getString(0));
            usuario.setNome(cursor.getString(1));
            usuario.setTelefone(cursor.getString(2));
            usuario.setEmail(cursor.getString(3));
        }

        return usuario;
    }

    public Boolean resetPassword(String email) {
        db = dataBaseHelper.getWritableDatabase();

        if(checkEmail(email)) {
            db.rawQuery("update usuario set senha = '123' where email = ?", new String[]{ email });

            return true;
        } else {
            return false;
        }
    }
}
