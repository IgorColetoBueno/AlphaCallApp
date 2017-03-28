package alphacallapp.com.br.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alphacallapp.com.br.model.Cliente;
import alphacallapp.com.br.model.Cliente;


/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class ClienteDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public ClienteDAO(Context context){
       db_helper =  new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if (sql_db == null){
            sql_db = db_helper.getWritableDatabase();
            }
        return sql_db;
    }
            //Inserir
    private Cliente CriarCliente(Cursor cur){

        Cliente model = new Cliente(

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Cliente.ID_CLIENTE)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Cliente.NCLIENTE)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Cliente.CIDADE)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Cliente.UF))
        );
        return model;

    }
    //Consultar

    public List<Cliente> ConsultarCliente(){
        Cursor cur = getDataBase().query(DatabaseHelper.Cliente.TABELA, DatabaseHelper.Cliente.COLUNAS_CLIENTE,
                null,null,null,null,null);
        List<Cliente> usuarios = new ArrayList<Cliente>();

        while (cur.moveToNext()){
            Cliente model = CriarCliente(cur);
            usuarios.add(model);
        }
        return usuarios;
    }

    public long SalvarCliente(Cliente usuario){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.Cliente.NCLIENTE, usuario.getNome_cliente());

        cv.put(DatabaseHelper.Cliente.CIDADE, usuario.getCidade_cliente());

        cv.put(DatabaseHelper.Cliente.UF, usuario.getUf());

        if (usuario.getId_cliente() ==  null) {
            return getDataBase().insert(DatabaseHelper.Cliente.TABELA, null, cv);

        }
        else{
            return sql_db.update(DatabaseHelper.Cliente.TABELA, cv,"_ID_USER = ?",
                    new String[]{(usuario.getId_cliente().toString())});

        }
    }

    public boolean DeletarCliente(Integer id){
        return getDataBase().delete(DatabaseHelper.Cliente.TABELA, "ID_CLIENTE =?", new String[]{id.toString()}) > 0;
    }

    public Cliente ConsultarId(int id){
        Cursor cur = getDataBase().query(DatabaseHelper.Cliente.TABELA, DatabaseHelper.Cliente.COLUNAS_CLIENTE,"ID_CLIENTE =?",
                new String[]{Integer.toString(id)}, null,null,null);

        if (cur.moveToFirst()){
            Cliente model = CriarCliente(cur);

            cur.close();
            return model;
        }
        return null;
    }

    public Integer ConsultarNome(String nome){
        Cursor cur = getDataBase().query(DatabaseHelper.Cliente.TABELA, DatabaseHelper.Cliente.COLUNAS_CLIENTE,"NCLIENTE =?",
                new String[]{nome}, null,null,null);

        if (cur.moveToFirst()){
            Cliente model = CriarCliente(cur);

            cur.close();
            return model.getId_cliente();
        }
        return null;
    }

    public String ConsultarCliente(Integer id){
        Cursor cur = getDataBase().query(DatabaseHelper.Cliente.TABELA, DatabaseHelper.Cliente.COLUNAS_CLIENTE,"ID_CLIENTE =?",
                new String[]{id.toString()}, null,null,null);

        if (cur.moveToFirst()){
            Cliente model = CriarCliente(cur);

            cur.close();
            return model.getNome_cliente();
        }
        return null;
    }

    public String[] ConsultarNCliente(){
        Cursor cur = getDataBase().query(DatabaseHelper.Cliente.TABELA, DatabaseHelper.Cliente.COLUNAS_CLIENTE,
                null,null,null,null,null);
        List<String> list = new ArrayList<String>();

        while (cur.moveToNext()){
            Cliente model = CriarCliente(cur);
            list.add(model.getNome_cliente());
        }

        String[] clientes = list.toArray(new String[list.size()]);
        return clientes;
    }

    public void CloseConnection(){
        db_helper.close();
    }

}
