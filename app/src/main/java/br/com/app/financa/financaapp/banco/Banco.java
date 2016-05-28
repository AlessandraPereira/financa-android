package br.com.app.financa.financaapp.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.app.financa.financaapp.bean.Usuario;

/**
 * Created by jccf-note on 26/05/2016.
 */
public class Banco extends SQLiteOpenHelper {
    private static final String BANCO_NOME = "financaapp.db";
    private static final Integer BANCO_VERSAO = 1;

    public Banco(Context context) {
        super(context, BANCO_NOME, null, BANCO_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria tabela de usuário
        db.execSQL(Usuario.getSqlCreateZero());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO implementar controle de versão
    }
}
