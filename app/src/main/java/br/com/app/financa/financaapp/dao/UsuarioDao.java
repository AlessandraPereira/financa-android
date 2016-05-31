package br.com.app.financa.financaapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.app.financa.financaapp.bean.Usuario;

/**
 * Created by jccf-note on 25/05/2016.
 */
public class UsuarioDao extends Dao implements IDao<Usuario> {
    ContentValues valores;
    Long resultado;

    public UsuarioDao(Context contexto) {
        super(contexto);
    }

    @Override
    public void inserir(Usuario objeto) {
        db = banco.getWritableDatabase();

        valores = new ContentValues();
        //valores.put("idcodigo", objeto.getCodigo());
        valores.put("nome", objeto.getNome());
        valores.put("email", objeto.getEmail());
        valores.put("senha", objeto.getSenha());

        resultado = db.insert(Usuario.NOME_TABELA, null, valores);
        db.close();
    }

    @Override
    public void alterar(Usuario objeto) {

    }

    @Override
    public void deletar(Usuario objeto) {

    }

    @Override
    public Usuario consultar(Usuario objeto) {
        return null;
    }

    @Override
    public void listar(Usuario objeto) {

    }

    @Override
    public boolean comErro() {
        // -1 significa que houve erro ao inserir o usuário
        return resultado == -1;
    }

    @Override
    public String getMensagemErro() {
        return "Erro ao inserir usuário.";
    }

    public Boolean logar(Usuario usuario) {
        Boolean retorno = false;
        Cursor cursor;
        String[] camposRetorno = {"idusuario"};
        String camposWhere = "email=? and senha=?";
        String[] valoresWhere = {usuario.getEmail(), usuario.getSenha()};

        db = banco.getReadableDatabase();
        cursor = db.query(Usuario.NOME_TABELA, camposRetorno, camposWhere, valoresWhere, null, null, null);

        if (cursor != null) {
            retorno = cursor.moveToFirst();
        }

        db.close();
        return retorno;
    }
}
