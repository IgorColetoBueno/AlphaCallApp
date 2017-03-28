package alphacallapp.com.br.alphacallapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import alphacallapp.com.br.DAO.UsuarioDAO;

public class Tela_Login extends AppCompatActivity {
    private EditText edt_usuario, edt_senha;
    private UsuarioDAO userDAO;
    Button btn_login;
    Button btn_cria_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        userDAO = new UsuarioDAO(this);

        edt_usuario = (EditText) findViewById(R.id.txt_user);

        edt_senha = (EditText) findViewById(R.id.txt_senha);

        btn_login = (Button) findViewById(R.id.id_btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ValidarCampos();
            }
        });

        btn_cria_user = (Button) findViewById(R.id.btn_criar_cadastro);

        btn_cria_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChamarTelaCadastro();

            }
        });
    }

    private void ChamarTelaCadastro() {
        boolean determinante = true;

        Intent intent = new Intent(this, CadUsuario.class);
        intent.putExtra("DETERMINANTE_FUNCAO", determinante);

        startActivity(intent);
        finish();
    }

    //Método de validação, caso ocorra a validação o método acessar menu é acionado
    private void ValidarCampos() {
        String usuario = edt_usuario.getText().toString().trim();
        String senha = edt_senha.getText().toString().trim();

        boolean validacao = true;

        if (usuario == null | usuario.equals("")) {
            edt_usuario.setError("O campo de usuário não pode ficar vazio");
        } else {
        }

        if (senha == null | senha.equals("")) {
            edt_senha.setError("O campo de senha não pode ficar vazio");
        }

        if (validacao == true) {
            AcessarMenu(usuario, senha);
        }
    }

    /*Método para acessar menu e utilizar dentro da validação*/
    public void AcessarMenu(String usuario, String senha) {
        //logar
        if (userDAO.Logar(usuario, senha,this) == true) {

            ChamarTelaPrincipal();

        } else {
            edt_senha.setText("");
            edt_senha.setError("Usuário e/ou senha estão errados!");

        }

    }
    //Chama a tela principal
    private void ChamarTelaPrincipal() {
        boolean logado = true;
        String usuario = edt_usuario.getText().toString().trim();

        Intent intent = new Intent(this, Menu_Principal.class);
        intent.putExtra("LOGADO", logado);
        intent.putExtra("NOME_USER", usuario);

        startActivity(intent);
        finish();
    }

}



