package alphacallapp.com.br.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alphacallapp.com.br.model.Modulo;


/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class ModuloDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public ModuloDAO(Context context){
       db_helper =  new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if (sql_db == null){
            sql_db = db_helper.getWritableDatabase();
            }
        return sql_db;
    }
            //Inserir
    private Modulo CriarModulo(Cursor cur){

        Modulo model = new Modulo(

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Modulo.ID_MODULO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Modulo.NMODULO))
        );
        return model;

    }
    //Consultar

    public List<Modulo> ConsultarModulo(){
        Cursor cur = getDataBase().query(DatabaseHelper.Modulo.TABELA, DatabaseHelper.Modulo.COLUNAS_MODULO,
                null,null,null,null,null);
        List<Modulo> usuarios = new ArrayList<Modulo>();

        while (cur.moveToNext()){
            Modulo model = CriarModulo(cur);
            usuarios.add(model);
        }
        return usuarios;
    }

    public long SalvarModulo(Modulo usuario){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.Modulo.ID_MODULO, usuario.getId_modulo());

        cv.put(DatabaseHelper.Modulo.NMODULO, usuario.getNmodulo());

        if (usuario.getId_modulo() ==  null) {
            return getDataBase().insert(DatabaseHelper.Modulo.TABELA, null, cv);

        }
        else{
            return sql_db.update(DatabaseHelper.Modulo.TABELA, cv,"ID_MODULO = ?",
                    new String[]{(usuario.getId_modulo().toString())});

        }
    }

    public boolean DeletarModulo(Integer id){
        return getDataBase().delete(DatabaseHelper.Modulo.TABELA, "ID_MODULO =?", new String[]{id.toString()}) > 0;
    }

    public Modulo ConsultarId(int id){
        Cursor cur = getDataBase().query(DatabaseHelper.Modulo.TABELA, DatabaseHelper.Modulo.COLUNAS_MODULO,"ID_MODULO =?",
                new String[]{Integer.toString(id)}, null,null,null);

        if (cur.moveToFirst()){
            Modulo model = CriarModulo(cur);

            cur.close();
            return model;
        }
        return null;
    }

    public Integer ConsultarNome(String nome){
        Cursor cur = getDataBase().query(DatabaseHelper.Modulo.TABELA, DatabaseHelper.Modulo.COLUNAS_MODULO,"NMODULO =?",
                new String[]{nome}, null,null,null);

        if (cur.moveToFirst()){
            Modulo model = CriarModulo(cur);

            cur.close();
            return model.getId_modulo();
        }
        return null;
    }

    public String ConsultarModuloId(int id){
        Cursor cur = getDataBase().query(DatabaseHelper.Modulo.TABELA, DatabaseHelper.Modulo.COLUNAS_MODULO,"ID_MODULO =?",
                new String[]{Integer.toString(id)}, null,null,null);

        if (cur.moveToFirst()){
            Modulo model = CriarModulo(cur);

            cur.close();
            return model.getNmodulo();
        }
        return null;
    }

    public String[] ConsultarNModulo(){
        Cursor cur = getDataBase().query(DatabaseHelper.Modulo.TABELA, DatabaseHelper.Modulo.COLUNAS_MODULO,
                null,null,null,null,null);
        List<String> usuarios = new ArrayList<String>();

        while (cur.moveToNext()){
            Modulo model = CriarModulo(cur);
            usuarios.add(model.getNmodulo());
        }

        String[] modulos = usuarios.toArray(new String[usuarios.size()]);
        return modulos;
    }

    public void CloseConnection(){
        db_helper.close();
    }

}
