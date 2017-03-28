package alphacallapp.com.br.alphacallapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedList;

import alphacallapp.com.br.DAO.AtendimentosDAO;
import alphacallapp.com.br.adapter.AtendimentoAdapter;
import alphacallapp.com.br.model.Atendimentos;
import alphacallapp.com.br.util.GeradorPDF;
import alphacallapp.com.br.util.Mensagem;

import static android.R.attr.version;

public class Menu_Principal extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    private ListView lista;

    private LinkedList<Atendimentos> lista_att;

    private AtendimentoAdapter att_adapter;

    AtendimentosDAO attdao;

    private int idposicao;

    private AlertDialog alertDialog, alertConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_atendimentos);
        this.setTitle("Menu Principal");

        PrepararListView();

    }

    private void ChamarCadUser() {
        Intent intent = new Intent(this, Menu_usuarios.class);

        startActivity(intent);
        finish();

    }

    private void ChamarCadAtt() {
        Intent intent = new Intent(this, CadAtendimentos.class);
        startActivity(intent);
        finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SalvarAtt:
                ChamarCadAtt();
                break;

            case R.id.Logoff:
                Intent intent = new Intent(this, Tela_Login.class);
                startActivity(intent);
                finish();
                break;

            case R.id.AbrirUser:
                ChamarCadUser();
                break;
            case R.id.Sobre:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Sobre");
                alert.setMessage("Criador: Igor Bueno\n\n Empresa: Alpha Software\n\n Versão: " + "1.3.0");
                alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int id = lista_att.get(idposicao).getNumero();
        boolean decisao = false;
        boolean editar = true;

        switch (which) {
            case 0:
                decisao = true;
                Intent intent = new Intent(this, CadAtendimentos.class);
                intent.putExtra("ATENDIMENTO_ID", id);
                intent.putExtra("EDITAR", decisao);
                startActivity(intent);
                break;

            case 1:
                decisao = true;
                editar = false;
                Intent duplicar = new Intent(this, CadAtendimentos.class);
                duplicar.putExtra("ATENDIMENTO_ID", id);
                duplicar.putExtra("EDITAR", decisao);
                duplicar.putExtra("EDITAVEL", editar);
                startActivity(duplicar);
                break;

            case 2:
                alertConfirmacao.show();
                break;
            //validar opções
            //opção sim
            case DialogInterface.BUTTON_POSITIVE:
                lista_att.remove(idposicao);
                attdao.DeletarAtendimentos(id);
                lista.invalidateViews();
                break;
            //opção não
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;

            case 3:
                //Geração do PDF de acordo com os parâmetros necessários :)
                try {
                    GeradorPDF pdf = new GeradorPDF();
                    AtendimentosDAO dao = new AtendimentosDAO(this);
                    Atendimentos model = dao.ConsultarId(id);
                    Intent intent1 = new Intent(Intent.ACTION_SEND);

                    pdf.GerarPDF(getApplicationContext(), model, intent1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        idposicao = position;

        alertDialog.show();
        return true;
    }    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int id_item = lista_att.get(idposicao).getNumero();

        boolean decisao;

        idposicao = position;

        decisao = true;
        Intent visualizar = new Intent(this, CadAtendimentos.class);
        visualizar.putExtra("ATENDIMENTO_ID", id_item);
        visualizar.putExtra("VISUALIZAR", decisao);
        startActivity(visualizar);
    }

    private void PrepararListView() {
        alertDialog = Mensagem.CriarAlertDialog(this);
        alertConfirmacao = Mensagem.CriarDialogConfirmacao(this);

        attdao = new AtendimentosDAO(this);
        lista_att = attdao.ConsultarAtendimentos();
        att_adapter = new AtendimentoAdapter(this, lista_att);

        lista = (ListView) findViewById(R.id.ListaAtt);

        lista.setAdapter(att_adapter);

        lista.setOnItemLongClickListener(this);
        lista.setOnItemClickListener(this);


    }

}
