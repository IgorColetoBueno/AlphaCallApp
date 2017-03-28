package alphacallapp.com.br.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

import alphacallapp.com.br.model.Atendimentos;

/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class AtendimentosDAO {

    private DatabaseHelper db_helper;
    private SQLiteDatabase sql_db;

    public AtendimentosDAO(Context context) {
        db_helper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDataBase() {
        if (sql_db == null) {
            sql_db = db_helper.getWritableDatabase();
        }
        return sql_db;
    }

    private Atendimentos CriarAtendimentos(Cursor cur) {

        Atendimentos att = new Atendimentos();
        att.setNumero(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.NUMERO)));
        att.setId_cliente_atd(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.ID_CLIENTE_ATD)));
        att.setNusuario(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.NUSUARIO)));
        att.setDescricao(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.DESCRICAO)));
        att.setData_agd(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.DATA_AGD)));
        att.setHora_agd(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.HORA_AGD)));
        att.setId_modulo_atd(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.ID_MODULO_ATD)));
        att.setParecer(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.PARECER)));
        att.setData_con(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.DATA_CON)));
        att.setHora_con(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.HORA_CON)));
        att.setObs(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.OBS)));
        att.setSituacao(cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.SITUACAO)));
        att.setId_submodulo_atd(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.ID_SUBMODULO_ATD)));
        att.setId_funcao_atd(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.ID_FUNCAO_ATD)));
        att.setId_usuario(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.ID_USUARIO)));
        att.setId_tatendimento(cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.Atendimentos.ID_TATENDIMENTO)));

        return att;

    }

    public LinkedList<Atendimentos> ConsultarAtendimentos() {
        Cursor cur = getDataBase().query(DatabaseHelper.Atendimentos.TABELA, DatabaseHelper.Atendimentos.COLUNAS_ATENDIMENTOS,
                null, null, null, null, null);
        LinkedList<Atendimentos> atendimentos = new LinkedList<Atendimentos>();

        while (cur.moveToNext()) {
            Atendimentos model = CriarAtendimentos(cur);
            atendimentos.addFirst(model);
        }
        return atendimentos;
    }

    public long SalvarAtendimentos(Atendimentos atendimento) {
        ContentValues cv = new ContentValues();

        cv.put(DatabaseHelper.Atendimentos.ID_CLIENTE_ATD, atendimento.getId_cliente_atd());

        cv.put(DatabaseHelper.Atendimentos.NUSUARIO, atendimento.getNusuario());

        cv.put(DatabaseHelper.Atendimentos.DESCRICAO, atendimento.getDescricao());

        cv.put(DatabaseHelper.Atendimentos.DATA_AGD, atendimento.getData_agd());

        cv.put(DatabaseHelper.Atendimentos.HORA_AGD, atendimento.getHora_agd());

        cv.put(DatabaseHelper.Atendimentos.ID_MODULO_ATD, atendimento.getId_modulo_atd());

        cv.put(DatabaseHelper.Atendimentos.PARECER, atendimento.getParecer());

        cv.put(DatabaseHelper.Atendimentos.DATA_CON, atendimento.getData_con());

        cv.put(DatabaseHelper.Atendimentos.HORA_CON, atendimento.getHora_con());

        cv.put(DatabaseHelper.Atendimentos.OBS, atendimento.getObs());

        cv.put(DatabaseHelper.Atendimentos.SITUACAO, atendimento.getSituacao());

        cv.put(DatabaseHelper.Atendimentos.ID_SUBMODULO_ATD, atendimento.getId_submodulo_atd());

        cv.put(DatabaseHelper.Atendimentos.ID_FUNCAO_ATD, atendimento.getId_funcao_atd());

        cv.put(DatabaseHelper.Atendimentos.ID_USUARIO, atendimento.getId_usuario());

        cv.put(DatabaseHelper.Atendimentos.ID_TATENDIMENTO, atendimento.getId_tatendimento());


        if (atendimento.getNumero() == null) {

            return getDataBase().insert(DatabaseHelper.Atendimentos.TABELA, null, cv);
        } else {

            return getDataBase().update(DatabaseHelper.Atendimentos.TABELA, cv, "NUMERO = ?",
                    new String[]{Integer.toString(atendimento.getNumero())});
        }
    }

    public boolean DeletarAtendimentos(int id) {
        return getDataBase().delete(DatabaseHelper.Atendimentos.TABELA, "NUMERO =?", new String[]{Integer.toString(id)}) > 0;
    }

    public Atendimentos ConsultarId(int id) {
        Cursor cur = getDataBase().query(DatabaseHelper.Atendimentos.TABELA, DatabaseHelper.Atendimentos.COLUNAS_ATENDIMENTOS, "NUMERO =?",
                new String[]{Integer.toString(id)}, null, null, null);

        if (cur.moveToFirst()) {
            Atendimentos model = CriarAtendimentos(cur);

            cur.close();
            return model;
        }
        cur.close();
        return null;
    }

    public void CloseConnection() {
        db_helper.close();
    }

}
