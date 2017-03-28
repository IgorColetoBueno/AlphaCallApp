package alphacallapp.com.br.model;

/**
 * Created by Igor Bueno on 01/03/2017.
 */

public class Funcao {

    public Integer id_funcao;

    public Integer id_modulo;

    public Integer id_submodulo;

    public String nfuncao;

    public Funcao() {
    }

    public Funcao(Integer id_funcao, Integer id_modulo, Integer id_submodulo, String nfuncao) {
        this.id_funcao = id_funcao;
        this.id_modulo = id_modulo;
        this.id_submodulo = id_submodulo;
        this.nfuncao = nfuncao;
    }

    public Integer getId_funcao() {
        return id_funcao;
    }

    public void setId_funcao(Integer id_funcao) {
        this.id_funcao = id_funcao;
    }

    public Integer getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(Integer id_modulo) {
        this.id_modulo = id_modulo;
    }

    public Integer getId_submodulo() {
        return id_submodulo;
    }

    public void setId_submodulo(Integer id_submodulo) {
        this.id_submodulo = id_submodulo;
    }

    public String getNfuncao() {
        return nfuncao;
    }

    public void setNfuncao(String nfuncao) {
        this.nfuncao = nfuncao;
    }
}
