package alphacallapp.com.br.model;

/**
 * Created by Igor Bueno on 01/03/2017.
 */

public class Submodulo {

    public Integer id_submodulo;

    public Integer id_modulo;

    public String nsubmodulo;

    public Submodulo() {
    }

    public Submodulo(Integer id_submodulo, Integer id_modulo, String nsubmodulo) {
        this.id_submodulo = id_submodulo;
        this.id_modulo = id_modulo;
        this.nsubmodulo = nsubmodulo;
    }

    public Integer getId_submodulo() {
        return id_submodulo;
    }

    public void setId_submodulo(Integer id_submodulo) {
        this.id_submodulo = id_submodulo;
    }

    public Integer getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(Integer id_modulo) {
        this.id_modulo = id_modulo;
    }

    public String getNsubmodulo() {
        return nsubmodulo;
    }

    public void setNsubmodulo(String nsubmodulo) {
        this.nsubmodulo = nsubmodulo;
    }
}
