package com.start.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "Start", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String usuario = "CREATE TABLE IF NOT EXISTS usuario (" +
            "idusuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "nome VARCHAR(100) NOT NULL," +
            "telefone VARCHAR(9) NOT NULL," +
            "email VARCHAR(100) NOT NULL," +
            "senha VARCHAR(100) NOT NULL);";

        String pessoa = "CREATE TABLE IF NOT EXISTS pessoa (" +
            "idpessoa INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "idusuario INTEGER NOT NULL," +
            "cpf VARCHAR(11) NOT NULL," +
            "FOREIGN KEY (idusuario) REFERENCES usuario(idusuario));";

        String empresa = "CREATE TABLE IF NOT EXISTS empresa (" +
            "idempresa INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "idusuario INTEGER NOT NULL," +
            "cnpj VARCHAR(14) NOT NULL," +
            "endereco VARCHAR(255) NOT NULL," +
            "FOREIGN KEY (idusuario) REFERENCES usuario(idusuario));";

        String vaga_empresa = "CREATE TABLE IF NOT EXISTS vaga_empresa (" +
            "idvaga_empresa INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "idempresa INTEGER NOT NULL," +
            "vaga VARCHAR(100) NOT NULL," +
            "FOREIGN KEY (idempresa) REFERENCES empresa(idempresa));";

        String vaga_pessoa = "CREATE TABLE IF NOT EXISTS vaga_pessoa (" +
                "idvaga_pessoa INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "idvaga_empresa INTEGER NOT NULL," +
                "idpessoa INTEGER NOT NULL," +
                "FOREIGN KEY (idvaga_empresa) REFERENCES vaga_empresa(idvaga_empresa)," +
                "FOREIGN KEY (idpessoa) REFERENCES pessoa(idpessoa));";

        sqLiteDatabase.execSQL(usuario);
        sqLiteDatabase.execSQL(pessoa);
        sqLiteDatabase.execSQL(empresa);
        sqLiteDatabase.execSQL(vaga_empresa);
        sqLiteDatabase.execSQL(vaga_pessoa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
