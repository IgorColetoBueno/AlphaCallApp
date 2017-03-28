package alphacallapp.com.br.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alphacallapp.com.br.model.TAtendimento;


/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class TAtendimentoDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public TAtendimentoDAO(Context context){
       db_helper =  new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if (sql_db == null){
            sql_db = db_helper.getWritableDatabase();
            }
        return sql_db;
    }
            //Inserir
    private TAtendimento CriarTAtendimento(Cursor cur){

        TAtendimento model = new TAtendimento(

                cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.TAtendimento.ID_TATENDIMENTO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.TAtendimento.NATENDIMENTO)),

                cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.TAtendimento.EIMPLANTACAO))
        );
        return model;

    }
            //Consultar

    public List<TAtendimento> ConsultarTAtendimento(){
        Cursor cur = getDataBase().query(DatabaseHelper.TAtendimento.TABELA, DatabaseHelper.TAtendimento.COLUNAS_TATENDIMENTO,
                null,null,null,null,null);
        List<TAtendimento> tatendimentos = new ArrayList<TAtendimento>();

        while (cur.moveToNext()){
            TAtendimento model = CriarTAtendimento(cur);
            tatendimentos.add(model);
        }
        return tatendimentos;
    }

    public long SalvarTAtendimento(TAtendimento usuario){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.TAtendimento.ID_TATENDIMENTO, usuario.getId_tatendimento());

        cv.put(DatabaseHelper.TAtendimento.NATENDIMENTO, usuario.getNatendimento());

        cv.put(DatabaseHelper.TAtendimento.EIMPLANTACAO, usuario.getEimplantacao());

        if (usuario.getId_tatendimento() ==  null) {
            return getDataBase().insert(DatabaseHelper.TAtendimento.TABELA, null, cv);

        }
        else{
            return sql_db.update(DatabaseHelper.TAtendimento.TABELA, cv,"ID_TATENDIMENTO = ?",
                    new String[]{(usuario.getId_tatendimento().toString())});

        }
    }

    public boolean DeletarTAtendimento(Integer id){
        return getDataBase().delete(DatabaseHelper.TAtendimento.TABELA, "ID_TATENDIMENTO =?", new String[]{id.toString()}) > 0;
    }

    public TAtendimento ConsultarId(int id){
        Cursor cur = getDataBase().query(DatabaseHelper.TAtendimento.TABELA, DatabaseHelper.TAtendimento.COLUNAS_TATENDIMENTO,"ID_TATENDIMENTO = ?",
                new String[]{Integer.toString(id)}, null,null,null);

        if (cur.moveToFirst()){
            TAtendimento model = CriarTAtendimento(cur);

            cur.close();
            return model;
        }
        return null;
    }

    public Integer ConsultarNome(String nome){
        Cursor cur = getDataBase().query(DatabaseHelper.TAtendimento.TABELA, DatabaseHelper.TAtendimento.COLUNAS_TATENDIMENTO,"NATENDIMENTO = ?",
                new String[]{nome}, null,null,null);

        if (cur.moveToFirst()){
            TAtendimento model = CriarTAtendimento(cur);

            cur.close();
            return model.getId_tatendimento();
        }
        return null;
    }

    public ArrayList<String> ConsultarTipos(){
       Cursor cur = getDataBase().query(DatabaseHelper.TAtendimento.TABELA, DatabaseHelper.TAtendimento.COLUNAS_TATENDIMENTO,null,
               null,null,null,null,null);
        ArrayList<String> itens = new ArrayList<String>();
        while (cur.moveToNext()){
            TAtendimento model = CriarTAtendimento(cur);
            itens.add(model.getNatendimento());
        }
        return itens;
    }

    public String[] ConsultarNTAtendimento(){
        Cursor cur = getDataBase().query(DatabaseHelper.TAtendimento.TABELA, DatabaseHelper.TAtendimento.COLUNAS_TATENDIMENTO,
                null,null,null,null,null);
        List<String> tatendimentos = new ArrayList<String>();

        while (cur.moveToNext()){
            TAtendimento model = CriarTAtendimento(cur);
            tatendimentos.add(model.getNatendimento());
        }
        String[] tAttArray = tatendimentos.toArray(new String[tatendimentos.size()]);
        return tAttArray;
    }

    public void CloseConnection(){
        db_helper.close();
    }

}
