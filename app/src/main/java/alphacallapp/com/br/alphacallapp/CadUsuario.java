package alphacallapp.com.br.alphacallapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import alphacallapp.com.br.DAO.UsuarioDAO;
import alphacallapp.com.br.model.Usuario;

public class CadUsuario extends AppCompatActivity {

    // Declarações de variáveis

    private EditText login_user, senha_user;

    private UsuarioDAO userdao;

    private Usuario user;

    private Integer id_usuario;

    long resultado;

    //fim da declaração de variáveis

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userdao = new UsuarioDAO(this);

        this.setTitle("Cadastro de Usuários");

        //atribuição às variáveis dos campos

        Declarar();

        //Capturar valor do id da tela principal

        CapturarValorEdit();

        //pegar login do usuário atual e passar para o campo Criador


    }

    private void Cadastrar() {
        boolean validacao = true;


        String criador_usuario = getIntent().getStringExtra("CRIADOR_CAD_USER");

        String login_usuario = login_user.getText().toString().trim();

        String senha_usuario = senha_user.getText().toString().trim();


        if (login_usuario.equals("") || login_usuario == null) {
            validacao = false;
            login_user.setError("Este campo não pode ficar vazio!");

        }

        if (senha_usuario.equals("") || senha_usuario == null) {
            validacao = false;
            senha_user.setError("Este campo não pode ficar vazio!");

        }


        if (validacao == true) {
            user = new Usuario();

            user.setLogin_user(login_usuario);
            user.setSenha_user(senha_usuario);


            //se for atualizar
            if (id_usuario > 0) {
                user.setId_user(id_usuario);
            }

            long resultado = userdao.SalvarUsuario(user);

            if (resultado != -1) {
                if (id_usuario > 1) {
                    Toast.makeText(getApplicationContext(), "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                ChamarTelaLogin();
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao realizar esta operação!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void CapturarValorEdit() {
        id_usuario = getIntent().getIntExtra("USUARIO_ID", 0);

        if (id_usuario > 0) {
            user = userdao.ConsultarId(id_usuario);

            login_user.setText(user.getLogin_user());

            senha_user.setText(user.getSenha_user());

            this.setTitle("Atualizar");

        } else {

        }
    }

    private void ChamarTelaLogin() {

        boolean catchdeterminante = getIntent().getBooleanExtra("DETERMINANTE_FUNCAO", false);

        if (catchdeterminante == true) {
            startActivity(new Intent(this, Tela_Login.class));
            Toast.makeText(getApplicationContext(), "Você já pode fazer o login com seu usuário", Toast.LENGTH_SHORT);
            finish();
        } else {
            startActivity(new Intent(this, Menu_usuarios.class));
            finish();
        }

    }

    private void Declarar() {

        login_user = (EditText) findViewById(R.id.txt_cad_user);

        senha_user = (EditText) findViewById(R.id.txt_cad_senha);
    }

    private void LimparCampos() {

        login_user.setText("");

        senha_user.setText("");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cad_usuarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                boolean catchdeterminante = getIntent().getBooleanExtra("DETERMINANTE_FUNCAO", false);

                if (catchdeterminante == true) {

                    Intent mIntent = new Intent(this, Tela_Login.class);
                    startActivity(mIntent);
                    finish();
                } else {

                    Intent mIntent = new Intent(this, Menu_usuarios.class);
                    startActivity(mIntent);

                    finish(); // Finaliza a Activity atual


                }
                break;
            case R.id.SalvaCadUser:
                finish();
                Cadastrar();
                break;

            case R.id.LimpaCadUser:
                LimparCampos();
                break;

            default:
                break;

        }

        return true;
    }


}






