package es.ies.puerto.model.entities;
import java.util.Objects;

/**
 * @author danielrguezh
 * @version 1.0.0
 */
public class UserEntity {
    private int id;  
    private String user;
    private String email;
    private String password;
    private String country;
    private int level;
    private Rank rank;

    /**
     * Empty constructor
     */
    public UserEntity() {}


    public UserEntity(int id) {this.id = id;}
    
    public UserEntity(String user) {this.user = user;}

    public UserEntity(String user, String email) {
        this.user = user;
        this.email = email;
    }

    public UserEntity(String user, String email, String password, String country) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.password = password;
        this.country = country;
    }
    
    /**
     * Constructor sin id
     * @param user
     * @param email
     * @param password
     * @param country
     * @param level
     * @param rank
     */
    public UserEntity(String user, String email, String password, String country, int level, Rank rank) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.country = country;
        this.level = level;
        this.rank = rank;
    }

    /**
     * Constructor con propiedades
     * @param id
     * @param user
     * @param email
     * @param password
     * @param country
     * @param level
     * @param rank
     */
    public UserEntity(int id, String user, String email, String password, String country, int level, Rank rank) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.password = password;
        this.country = country;
        this.level = level;
        this.rank = rank;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserEntity)) {
            return false;
        }
        UserEntity userEntity = (UserEntity) o;
        return  Objects.equals(user, userEntity.user) && Objects.equals(email, userEntity.email) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash( user, email);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", country='" + getCountry() + "'" +
            ", level='" + getLevel() + "'" +
            ", rank='" + getRank() + "'" +
            "}";
    }


}
