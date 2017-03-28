package alphacallapp.com.br.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alphacallapp.com.br.DAO.DatabaseHelper;
import alphacallapp.com.br.DAO.TAtendimentoDAO;
import alphacallapp.com.br.alphacallapp.R;
import alphacallapp.com.br.model.TAtendimento;

/**
 * Created by Igor Bueno on 31/01/2017.
 */

public class Mensagem {

    public static void MsgConfirma(Activity activity, String titulo, String msg, int icone,
                                   DialogInterface.OnClickListener dialogInterface) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setPositiveButton("Sim", dialogInterface);
        alert.setNegativeButton("Não", null);
        alert.setIcon(icone);
        alert.show();

    }

    public static AlertDialog CriarAlertDialog(Activity activity) {
        final CharSequence[] items = {
                "Editar",
                "Duplicar",
                "Excluir",
                "Gerar PDF"
        };

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Opções");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    public static AlertDialog CriarDialogConfirmacao(Activity activity) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        alert.setMessage("Deseja realmente excluir?");
        alert.setPositiveButton("Sim", (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton("Não", (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    public static AlertDialog CriarAlertDialogUsers(Activity activity) {
        final CharSequence[] items = {
                "Editar",
                "Excluir"
        };

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Opções");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    public static AlertDialog CriarAlertSituacao(Activity activity) {
        final CharSequence[] items = {
                "Aberto",
                "Fechado",
                "Em andamento",
                "Cancelado",
                "Adiado"
        };

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Situações");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    public static AlertDialog CriarAlertTAtendimento(Activity activity, final Context context) {
        TAtendimentoDAO tAtendimentoDAO = new TAtendimentoDAO(context);

        ArrayList<String> itens = tAtendimentoDAO.ConsultarTipos();

        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, itens);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setAdapter(adapter, (DialogInterface.OnClickListener) activity);
        builder.setTitle("Tipos de atendimento");
        //define o diálogo como uma lista, passa o adapter.

        return builder.create();
    }

}
