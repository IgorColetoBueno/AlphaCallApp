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
import android.widget.Toast;

import java.util.List;

import alphacallapp.com.br.DAO.UsuarioDAO;
import alphacallapp.com.br.adapter.UsuarioAdapter;
import alphacallapp.com.br.model.Usuario;
import alphacallapp.com.br.util.Mensagem;

public class Menu_usuarios extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    private ListView list;
    private List<Usuario> ListUser;
    private UsuarioDAO usuarioDAO;
    private UsuarioAdapter usuarioAdapter;
    private AlertDialog alertDialog, alertConfirmacao;
    private int idposicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_usuarios);
        this.setTitle("Usuários");

        alertDialog = Mensagem.CriarAlertDialogUsers(this);
        alertConfirmacao = Mensagem.CriarDialogConfirmacao(this);

        usuarioDAO = new UsuarioDAO(this);
        ListUser = usuarioDAO.ConsultarUsuario();
        usuarioAdapter = new UsuarioAdapter(this, ListUser);

        list = (ListView) findViewById(R.id.ListaUser);

        list.setAdapter(usuarioAdapter);

        list.setOnItemClickListener(this);


    }

    private void ChamarCadUser() {
        String criador = getIntent().getStringExtra("CRIADOR_USER");
        Intent intent = new Intent(this, CadUsuario.class);
        intent.putExtra("CRIADOR_CAD_USER", criador);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        int id = ListUser.get(idposicao).getId_user();

        switch (which) {
            case 0:
                if (id != 1) {
                    String criador = getIntent().getStringExtra("CRIADOR_USER");
                    Intent intent = new Intent(this, CadUsuario.class);
                    intent.putExtra("USUARIO_ID", id);
                    intent.putExtra("CRIADOR_USER", criador);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), "O usuário Master não pode ser alterado!", Toast.LENGTH_SHORT).show();
                }
                break;

            case 1:
                alertConfirmacao.show();
                break;

            case DialogInterface.BUTTON_POSITIVE:
                if (id != 1) {
                    ListUser.remove(idposicao);
                    usuarioDAO.DeletarUsuario(id);
                    list.invalidateViews();
                } else {
                    Toast.makeText(getApplicationContext(), "O usuário Master não pode ser deletado!", Toast.LENGTH_SHORT).show();
                }

                break;

            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuarios, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        idposicao = position;

        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SalvarUser:
                ChamarCadUser();
                break;

            case android.R.id.home:

                Intent mIntent = new Intent(this, Menu_Principal.class);
                startActivity(mIntent);

                finish(); // Finaliza a Activity atual

                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
