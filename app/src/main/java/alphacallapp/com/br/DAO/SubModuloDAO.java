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

import alphacallapp.com.br.model.Submodulo;


/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class SubModuloDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public SubModuloDAO(Context context) {
        db_helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase() {
        if (sql_db == null) {
            sql_db = db_helper.getWritableDatabase();
        }
        return sql_db;
    }

    //Inserir
    private Submodulo CriarSubmodulo(Cursor cur) {

        Submodulo model = new Submodulo(

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.SubModulo.ID_SUBMODULO)),

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.SubModulo.ID_MODULO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SubModulo.NSUBMODULO))
        );
        return model;

    }
    //Consultar

    public List<Submodulo> ConsultarSubmodulo() {
        Cursor cur = getDataBase().query(DatabaseHelper.SubModulo.TABELA, DatabaseHelper.SubModulo.COLUNAS_SUBMODULO,
                null, null, null, null, null);
        List<Submodulo> usuarios = new ArrayList<Submodulo>();

        while (cur.moveToNext()) {
            Submodulo model = CriarSubmodulo(cur);
            usuarios.add(model);
        }
        return usuarios;
    }

    public long SalvarSubmodulo(Submodulo usuario) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.SubModulo.ID_SUBMODULO, usuario.getId_submodulo());

        cv.put(DatabaseHelper.SubModulo.ID_MODULO, usuario.getId_modulo());

        cv.put(DatabaseHelper.SubModulo.NSUBMODULO, usuario.getNsubmodulo());

        if (usuario.getId_submodulo() == null) {
            return getDataBase().insert(DatabaseHelper.SubModulo.TABELA, null, cv);

        } else {
            return sql_db.update(DatabaseHelper.SubModulo.TABELA, cv, "ID_SUBMODULO = ?",
                    new String[]{(usuario.getId_submodulo().toString())});

        }
    }

    public boolean DeletarSubmodulo(Integer id) {
        return getDataBase().delete(DatabaseHelper.SubModulo.TABELA, "ID_SUBMODULO =?", new String[]{id.toString()}) > 0;
    }

    public Submodulo ConsultarId(int id) {
        Cursor cur = getDataBase().query(DatabaseHelper.SubModulo.TABELA, DatabaseHelper.SubModulo.COLUNAS_SUBMODULO, "ID_SUBMODULO =?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cur.moveToFirst()) {
            Submodulo model = CriarSubmodulo(cur);

            cur.close();
            return model;
        }
        return null;
    }

    public Integer ConsultarNome(String nome) {
        Cursor cur = getDataBase().query(DatabaseHelper.SubModulo.TABELA, DatabaseHelper.SubModulo.COLUNAS_SUBMODULO, "NSUBMODULO =?",
                new String[]{nome}, null, null, null);

        if (cur.moveToFirst()) {
            Submodulo model = CriarSubmodulo(cur);

            cur.close();
            return model.getId_submodulo();
        }

        return null;
    }

    public String ConsultarSubmoduloId(Integer id) {
        Cursor cur = getDataBase().query(DatabaseHelper.SubModulo.TABELA, DatabaseHelper.SubModulo.COLUNAS_SUBMODULO, "ID_SUBMODULO=?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cur.moveToFirst()) {
            Submodulo model = CriarSubmodulo(cur);

            cur.close();
            return model.getNsubmodulo();
        }
        return null;
    }

    public String[] ConsultarNSubModulo(Integer id_modulo) {
        Cursor cur = getDataBase().query(DatabaseHelper.SubModulo.TABELA, DatabaseHelper.SubModulo.COLUNAS_SUBMODULO,
                "ID_MODULO=?", new String[]{Integer.toString(id_modulo)}, null, null, null);
        List<String> usuarios = new ArrayList<String>();

        while (cur.moveToNext()) {
            Submodulo model = CriarSubmodulo(cur);
            usuarios.add(model.getNsubmodulo());
        }
        String[] sub_modulos = usuarios.toArray(new String[usuarios.size()]);
        return sub_modulos;
    }

    public void CloseConnection() {
        db_helper.close();
    }

    public int ConsultarIndice(Integer id_modulo_atd) {
        Cursor cur = getDataBase().query(DatabaseHelper.SubModulo.TABELA, DatabaseHelper.SubModulo.COLUNAS_SUBMODULO,
                "ID_MODULO=?", new String[]{Integer.toString(id_modulo_atd)}, null, null, null);
        Submodulo model = null;
        if (cur.moveToFirst()) {
            model = CriarSubmodulo(cur);
        }

        return model.getId_submodulo();
    }
}
