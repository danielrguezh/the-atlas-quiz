package es.ies.puerto.model.entities;
import java.util.Objects;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class QuestionEntity {
    int id;
    String category;
    String description;
    String answer;
    Rank rank;
    String rutaImagen;


    public QuestionEntity() {
    }

    public QuestionEntity(int id, String category, String description, String answer, Rank rank, String rutaImagen) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.answer = answer;
        this.rank = rank;
        this.rutaImagen = rutaImagen;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getRutaImagen() {
        return this.rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public QuestionEntity id(int id) {
        setId(id);
        return this;
    }

    public QuestionEntity category(String category) {
        setCategory(category);
        return this;
    }

    public QuestionEntity description(String description) {
        setDescription(description);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof QuestionEntity)) {
            return false;
        }
        QuestionEntity questionEntity = (QuestionEntity) o;
        return id == questionEntity.id && Objects.equals(category, questionEntity.category) && Objects.equals(description, questionEntity.description) && Objects.equals(answer, questionEntity.answer) && Objects.equals(rank, questionEntity.rank) && Objects.equals(rutaImagen, questionEntity.rutaImagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, description, answer, rank, rutaImagen);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", category='" + getCategory() + "'" +
            ", description='" + getDescription() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", rank='" + getRank() + "'" +
            ", rutaImagen='" + getRutaImagen() + "'" +
            "}";
    }


}