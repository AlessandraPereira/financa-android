package br.com.app.financa.financaapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.app.financa.financaapp.banco.Banco;

/**
 * Created by jccf-note on 26/05/2016.
 */
public class Dao {
    protected Context contexto;
    protected Banco banco;
    protected SQLiteDatabase db;

    public Dao(Context contexto) {
        this.contexto = contexto;
        banco = new Banco(contexto);
    }
}
