package alphacallapp.com.br.model;

/**
 * Created by Igor Bueno on 17/01/2017.
 */

public class Cliente {
    private Integer id_cliente;
    private String nome_cliente;
    private String cidade;
    private String uf;

    public Cliente(){

    }

    public Cliente(Integer id_cliente, String nome_cliente, String cidade, String uf) {
        this.id_cliente = id_cliente;
        this.nome_cliente = nome_cliente;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getCidade_cliente() {
        return cidade;
    }

    public void setCidade_cliente(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Menu_usuarios{" +
                "id_cliente=" + id_cliente +
                '}';
    }
}
