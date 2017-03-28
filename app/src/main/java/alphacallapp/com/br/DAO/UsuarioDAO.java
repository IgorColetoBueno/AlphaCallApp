package alphacallapp.com.br.DAO;

import alphacallapp.com.br.model.Usuario;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class UsuarioDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public UsuarioDAO(Context context){
       db_helper =  new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if (sql_db == null){
            sql_db = db_helper.getWritableDatabase();
            }
        return sql_db;
    }
            //Inserir
    private Usuario CriarUsuario(Cursor cur){

        Usuario model = new Usuario(

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Usuario.ID_USUARIO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Usuario.NUSUARIO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Usuario.PASSWORD))
        );
        return model;

    }
            //Consultar

    public List<Usuario> ConsultarUsuario(){
        Cursor cur = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,
                null,null,null,null,null);
        List<Usuario> usuarios = new ArrayList<Usuario>();

        while (cur.moveToNext()){
            Usuario model = CriarUsuario(cur);
            usuarios.add(model);
        }
        return usuarios;
    }

    public long SalvarUsuario(Usuario usuario){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.Usuario.NUSUARIO, usuario.getLogin_user());

        cv.put(DatabaseHelper.Usuario.PASSWORD, usuario.getSenha_user());

        if (usuario.getId_user() ==  null) {
            return getDataBase().insert(DatabaseHelper.Usuario.TABELA, null, cv);

        }
        else{
            return sql_db.update(DatabaseHelper.Usuario.TABELA, cv,"ID_USUARIO = ?",
                    new String[]{(usuario.getId_user().toString())});

        }
    }

    public boolean DeletarUsuario(Integer id){
        return getDataBase().delete(DatabaseHelper.Usuario.TABELA, "ID_USUARIO =?", new String[]{id.toString()}) > 0;
    }

    public Usuario ConsultarId(int id){
        Cursor cur = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,"ID_USUARIO = ?",
                new String[]{Integer.toString(id)}, null,null,null);

        if (cur.moveToFirst()){
            Usuario model = CriarUsuario(cur);

            cur.close();
            return model;
        }
        return null;
    }

    public String ConsultarUsuarioId(Integer id){
        Cursor cur = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,"ID_USUARIO = ?",
                new String[]{Integer.toString(id)}, null,null,null);

        if (cur.moveToFirst()){
            Usuario model = CriarUsuario(cur);

            cur.close();
            return model.getLogin_user();
        }
        return null;
    }

    public Integer ConsultarNome(String nome){
        Cursor cur = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,"NUSUARIO = ?",
                new String[]{nome}, null,null,null);

        if (cur.moveToFirst()){
            Usuario model = CriarUsuario(cur);

            cur.close();
            return model.getId_user();
        }
        return null;
    }

    public boolean Logar(String usuario, String senha,Context context){
        Cursor cur = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,"NUSUARIO=? AND PASSWORD=?",
                new String[]{usuario,senha} , null, null, null);
        if (cur.moveToFirst()){
            Usuario model = CriarUsuario(cur);
            cur.close();

            SharedPreferences sp = context.getSharedPreferences("PREF_LOGIN", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("ID_PREF", model.getId_user());
            editor.commit();
            return true;
        }
       return false;

    }

    public String[] ConsultarNUsuario(){
        Cursor cur = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,
                null,null,null,null,null);
        List<String> list = new ArrayList<>();

        while (cur.moveToNext()){
            Usuario model = CriarUsuario(cur);
            list.add(model.getLogin_user());
        }
        String[] usuarios = list.toArray(new String[list.size()]);
        return usuarios;
    }

    public Integer RetornaId(String nome_user){
        Cursor cursor = getDataBase().query(DatabaseHelper.Usuario.TABELA, DatabaseHelper.Usuario.COLUNAS_USUARIO,"NUSUARIO=?",
                new String[]{nome_user},null,null,"NUSUARIO ASC",null);
        while (cursor.moveToFirst()){
            Usuario model = CriarUsuario(cursor);
            return model.getId_user();
        }
        return null;
    }


    public void CloseConnection(){
        db_helper.close();
    }

}
