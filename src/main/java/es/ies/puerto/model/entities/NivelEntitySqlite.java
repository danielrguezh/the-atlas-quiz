package es.ies.puerto.model.entities;

import java.util.Objects;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class NivelEntitySqlite {

    private int id;
    private String nivel;

    /**
     * Constructor vacio
     */
    public NivelEntitySqlite() {
    }

    /**
     * Constructor con clave primaria
     * @param id
     */
    public NivelEntitySqlite(int id) {
        this.id = id;
    }

    /**
     * Constructor con propiedades
     * @param id
     * @param nivel
     */
    public NivelEntitySqlite(int id, String nivel) {
        this.id = id;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "NivelEntitySqlite [id=" + id + ", nivel=" + nivel + "]";
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NivelEntitySqlite)) {
            return false;
        }
        NivelEntitySqlite nivelEntitySqlite = (NivelEntitySqlite) o;
        return id == nivelEntitySqlite.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
