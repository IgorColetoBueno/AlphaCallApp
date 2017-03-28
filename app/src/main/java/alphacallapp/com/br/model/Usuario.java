package alphacallapp.com.br.model;

/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class Usuario {
    private Integer id_usuario;
    private String nusuario;
    private String password;

    public Usuario(){

    }

    public Usuario(Integer id_usuario, String nusuario, String password) {
        this.id_usuario = id_usuario;
        this.nusuario = nusuario;
        this.password = password;
    }

    public Integer getId_user() {
        return id_usuario;
    }

    public void setId_user(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getLogin_user() {
        return nusuario;
    }

    public void setLogin_user(String nusuario) {
        this.nusuario = nusuario;
    }

    public String getSenha_user() {
        return password;
    }

    public void setSenha_user(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Menu_usuarios{" +
                "id_usuario=" + id_usuario +
                '}';
    }
}
