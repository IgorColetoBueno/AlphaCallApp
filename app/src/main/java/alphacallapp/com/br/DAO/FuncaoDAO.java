package alphacallapp.com.br.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import alphacallapp.com.br.model.Funcao;


/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class FuncaoDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public FuncaoDAO(Context context) {
        db_helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase() {
        if (sql_db == null) {
            sql_db = db_helper.getWritableDatabase();
        }
        return sql_db;
    }

    //Inserir
    private Funcao CriarFuncao(Cursor cur) {

        Funcao model = new Funcao(

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Funcao.ID_FUNCAO)),

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Funcao.ID_MODULO)),

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Funcao.ID_SUBMODULO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Funcao.NFUNCAO))
        );
        return model;

    }
    //Consultar

    public List<Funcao> ConsultarFuncao() {
        Cursor cur = getDataBase().query(DatabaseHelper.Funcao.TABELA, DatabaseHelper.Funcao.COLUNAS_FUNCAO,
                null, null, null, null, null);
        List<Funcao> funcoes = new ArrayList<Funcao>();

        while (cur.moveToNext()) {
            Funcao model = CriarFuncao(cur);
            funcoes.add(model);
        }
        return funcoes;
    }

    public long SalvarFuncao(Funcao usuario) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.Funcao.ID_FUNCAO, usuario.getId_funcao());

        cv.put(DatabaseHelper.Funcao.ID_MODULO, usuario.getId_modulo());

        cv.put(DatabaseHelper.Funcao.ID_SUBMODULO, usuario.getId_submodulo());

        cv.put(DatabaseHelper.Funcao.NFUNCAO, usuario.getNfuncao());

        if (usuario.getId_submodulo() == null) {
            CloseConnection();
            return getDataBase().insert(DatabaseHelper.Funcao.TABELA, null, cv);

        } else {
            CloseConnection();
            return sql_db.update(DatabaseHelper.Funcao.TABELA, cv, "ID_FUNCAO = ?",
                    new String[]{(usuario.getId_submodulo().toString())});

        }
    }

    public boolean DeletarFuncao(Integer id) {
        CloseConnection();
        return getDataBase().delete(DatabaseHelper.Funcao.TABELA, "ID_FUNCAO =?", new String[]{id.toString()}) > 0;
    }

    public Funcao ConsultarId(int id) {
        Cursor cur = getDataBase().query(DatabaseHelper.Funcao.TABELA, DatabaseHelper.Funcao.COLUNAS_FUNCAO, "ID_FUNCAO =?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cur.moveToFirst()) {
            Funcao model = CriarFuncao(cur);

            cur.close();
            return model;
        }
        CloseConnection();
        return null;
    }

    public Integer ConsultarNome(String nome) {

        Cursor cur = getDataBase().query(DatabaseHelper.Funcao.TABELA, DatabaseHelper.Funcao.COLUNAS_FUNCAO, "NFUNCAO =?",
                new String[]{nome}, null, null, null);

        if (cur.moveToFirst()) {
            Funcao model = CriarFuncao(cur);

            cur.close();
            return model.getId_funcao();
        }

        CloseConnection();
        return null;
    }

    public String ConsultarModuloId(Integer id) {
        Cursor cur = getDataBase().query(DatabaseHelper.Funcao.TABELA, DatabaseHelper.Funcao.COLUNAS_FUNCAO, "ID_FUNCAO=?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cur.moveToFirst()) {
            Funcao model = CriarFuncao(cur);

            cur.close();
            return model.getNfuncao();
        }
        CloseConnection();
        return null;
    }

    public String[] ConsultarNFuncao(Integer id_submodulo) {
        List<String> list = new ArrayList<String>();

        String[] funcoes;
        Cursor cur = getDataBase().query(DatabaseHelper.Funcao.TABELA, DatabaseHelper.Funcao.COLUNAS_FUNCAO,
                "ID_SUBMODULO=?", new String[]{Integer.toString(id_submodulo)}
                , null, null, null);


        while (cur.moveToNext()) {
            Funcao model = CriarFuncao(cur);
            list.add(model.getNfuncao());
        }
        funcoes = list.toArray(new String[list.size()]);
        CloseConnection();
        return funcoes;
    }

    public void CloseConnection() {
        db_helper.close();
    }

    public int ConsultarIndice(Integer id_submodulo_atd) {

        Cursor cur = getDataBase().query(DatabaseHelper.Funcao.TABELA, DatabaseHelper.Funcao.COLUNAS_FUNCAO,
                "ID_SUBMODULO=?", new String[]{Integer.toString(id_submodulo_atd)}, null, null,null);
        Funcao model = null;
        if (cur.moveToFirst()){
            model = CriarFuncao(cur);
        }
        CloseConnection();
        return model.getId_funcao();
    }
}
