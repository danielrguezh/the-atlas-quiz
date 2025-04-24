package es.ies.puerto.controller;

import java.sql.SQLException;
import java.util.regex.Pattern;

import es.ies.puerto.config.ConfigManager;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.entities.Rank;
import es.ies.puerto.model.entities.UserEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author danielrguezh
 * @version 1.0.0
 */

public class RegistroController extends AbstractController{

    private UserEntity usuarioEditado;
    String countryCode;

    @FXML
    ComboBox comboSelectCountry;

    @FXML
    public Text textRegistroTitulo;

    @FXML 
    private TextField textFieldUsuario;

    @FXML 
    private PasswordField textFieldPassword;

    @FXML 
    private PasswordField textFieldPasswordRepit;

    @FXML 
    private TextField textFieldEmail;

    @FXML 
    private Text textMensaje;

    @FXML 
    private Button openRegistrarButton;

    @FXML
    private Button buttonVolverAtras;

    /**
     * Metodo de inicializacion de la interfaz
     */
    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    /**
     * Carga los datos del usuario en los campos de la interfaz grafica
     * @param usuario con los datos que se mostraran en pantalla
     */
    public void cargarDatosUsuario(UserEntity usuario) {
        if (usuario != null) {
            this.usuarioEditado = usuario;
            textFieldUsuario.setText(usuario.getUser());
            textFieldEmail.setText(usuario.getEmail());
        }
    }

    /**
     * Maneja el evento de clic en el boton de registro
     * Valida los datos ingresados por el usuario y crea un nuevo usuario si son correctos
     * Muestra mensajes de error si los datos no cumplen con los requisitos
     */
    @FXML
    protected void onRegistrarButtonClick() {
        if (textFieldUsuario == null || textFieldUsuario.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioVacio"));
            return;
        }
        if (textFieldPassword == null ||  textFieldPassword.getText().isEmpty() 
            || textFieldPasswordRepit == null || textFieldPasswordRepit.getText().isEmpty()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorPasswordVacio"));
            return;
        }
        if (textFieldPassword.getText().length() < 8) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorPasswordLongitud"));
            return;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(textFieldEmail.getText()).matches()) {
            textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailFormato"));
            return;
        }
        
        if (textFieldPassword.getText().equals(textFieldPasswordRepit.getText())) {
            try {
                if (usuarioEditado == null) { 
                    UserEntity nuevoUsuario = new UserEntity(
                        textFieldUsuario.getText(),
                        textFieldEmail.getText(),
                        textFieldPassword.getText(),
                        countryCode,
                        0,
                        Rank.BEGINNER
                    );
                    boolean insertado = getUsuarioServiceSqlite().insertarUsuario(nuevoUsuario);
                    if (!insertado) {
                        textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorUsuarioExiste"));
                        return;
                    }
                    mostrarPantalla(openRegistrarButton, "profile.fxml", nuevoUsuario);
                } else {
                    usuarioEditado.setUser(textFieldUsuario.getText());
                    usuarioEditado.setEmail(textFieldEmail.getText());
                    usuarioEditado.setCountry(countryCode);
                    if (!textFieldPassword.getText().equals("")) {
                        usuarioEditado.setPassword(textFieldPassword.getText());
                    }
                    boolean actualizado = getUsuarioServiceSqlite().actualizarUsuario(usuarioEditado);
                    if (!actualizado) {
                        textMensaje.setText("Error al actualizar el usuario");
                        return;
                    }
                    mostrarPantalla(openRegistrarButton, "profile.fxml", usuarioEditado);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        textMensaje.setText(ConfigManager.ConfigProperties.getProperty("errorEmailOPasswordNoCoincide"));
    }

    /**
     * Metodo que redirige a la pantalla de inicio de sesion (login)
     */
    @FXML
    protected void onVolverAtrasClick() {
        mostrarPantalla(buttonVolverAtras, "login.fxml");
    }

    /**
     * Maneja el evento de seleccion de pais en el ComboBox
     */
    @FXML
    protected void onCountryCombo() {
        countryCode = comboSelectCountry.getValue().toString();
        comboSelectCountry.setValue(countryCode);
    }

    /**
     * Methot that loads all the combo elements
     */
    public void loadComboBox(){
        comboSelectCountry.getItems().add("ad");
        comboSelectCountry.getItems().add("ae");
        comboSelectCountry.getItems().add("af");
        comboSelectCountry.getItems().add("ag");
        comboSelectCountry.getItems().add("ai");
        comboSelectCountry.getItems().add("al");
        comboSelectCountry.getItems().add("am");
        comboSelectCountry.getItems().add("ao");
        comboSelectCountry.getItems().add("aq");
        comboSelectCountry.getItems().add("ar");
        comboSelectCountry.getItems().add("as");
        comboSelectCountry.getItems().add("at");
        comboSelectCountry.getItems().add("au");
        comboSelectCountry.getItems().add("aw");
        comboSelectCountry.getItems().add("ax");
        comboSelectCountry.getItems().add("az");
        comboSelectCountry.getItems().add("ba");
        comboSelectCountry.getItems().add("bb");
        comboSelectCountry.getItems().add("bd");
        comboSelectCountry.getItems().add("be");
        comboSelectCountry.getItems().add("bf");
        comboSelectCountry.getItems().add("bg");
        comboSelectCountry.getItems().add("bh");
        comboSelectCountry.getItems().add("bi");
        comboSelectCountry.getItems().add("bj");
        comboSelectCountry.getItems().add("bl");
        comboSelectCountry.getItems().add("bm");
        comboSelectCountry.getItems().add("bn");
        comboSelectCountry.getItems().add("bo");
        comboSelectCountry.getItems().add("bq");
        comboSelectCountry.getItems().add("br");
        comboSelectCountry.getItems().add("bs");
        comboSelectCountry.getItems().add("bt");
        comboSelectCountry.getItems().add("bv");
        comboSelectCountry.getItems().add("bw");
        comboSelectCountry.getItems().add("by");
        comboSelectCountry.getItems().add("bz");
        comboSelectCountry.getItems().add("ca");
        comboSelectCountry.getItems().add("cc");
        comboSelectCountry.getItems().add("cd");
        comboSelectCountry.getItems().add("cf");
        comboSelectCountry.getItems().add("cg");
        comboSelectCountry.getItems().add("ch");
        comboSelectCountry.getItems().add("ci");
        comboSelectCountry.getItems().add("ck");
        comboSelectCountry.getItems().add("cl");
        comboSelectCountry.getItems().add("cm");
        comboSelectCountry.getItems().add("cn");
        comboSelectCountry.getItems().add("co");
        comboSelectCountry.getItems().add("cr");
        comboSelectCountry.getItems().add("cu");
        comboSelectCountry.getItems().add("cv");
        comboSelectCountry.getItems().add("cw");
        comboSelectCountry.getItems().add("cx");
        comboSelectCountry.getItems().add("cy");
        comboSelectCountry.getItems().add("cz");
        comboSelectCountry.getItems().add("de");
        comboSelectCountry.getItems().add("dj");
        comboSelectCountry.getItems().add("dk");
        comboSelectCountry.getItems().add("dm");
        comboSelectCountry.getItems().add("do");
        comboSelectCountry.getItems().add("dz");
        comboSelectCountry.getItems().add("ec");
        comboSelectCountry.getItems().add("ee");
        comboSelectCountry.getItems().add("eg");
        comboSelectCountry.getItems().add("eh");
        comboSelectCountry.getItems().add("er");
        comboSelectCountry.getItems().add("eg");
        comboSelectCountry.getItems().add("eh");
        comboSelectCountry.getItems().add("er");
        comboSelectCountry.getItems().add("es");
        comboSelectCountry.getItems().add("et");
        comboSelectCountry.getItems().add("fi");
        comboSelectCountry.getItems().add("fj");
        comboSelectCountry.getItems().add("fk");
        comboSelectCountry.getItems().add("fm");
        comboSelectCountry.getItems().add("fo");
        comboSelectCountry.getItems().add("fr");
        comboSelectCountry.getItems().add("ga");
        comboSelectCountry.getItems().add("gb");
        comboSelectCountry.getItems().add("gd");
        comboSelectCountry.getItems().add("ge");
        comboSelectCountry.getItems().add("gf");
        comboSelectCountry.getItems().add("gg");
        comboSelectCountry.getItems().add("gh");
        comboSelectCountry.getItems().add("gi");
        comboSelectCountry.getItems().add("gl");
        comboSelectCountry.getItems().add("gm");
        comboSelectCountry.getItems().add("gn");
        comboSelectCountry.getItems().add("gp");
        comboSelectCountry.getItems().add("gq");
        comboSelectCountry.getItems().add("gr");
        comboSelectCountry.getItems().add("gs");
        comboSelectCountry.getItems().add("gt");
        comboSelectCountry.getItems().add("gu");
        comboSelectCountry.getItems().add("gw");
        comboSelectCountry.getItems().add("gy");
        comboSelectCountry.getItems().add("hk");
        comboSelectCountry.getItems().add("hm");
        comboSelectCountry.getItems().add("hn");
        comboSelectCountry.getItems().add("hn");
        comboSelectCountry.getItems().add("hr");
        comboSelectCountry.getItems().add("ht");
        comboSelectCountry.getItems().add("hu");
        comboSelectCountry.getItems().add("id");
        comboSelectCountry.getItems().add("ie");
        comboSelectCountry.getItems().add("il");
        comboSelectCountry.getItems().add("im");
        comboSelectCountry.getItems().add("in");
        comboSelectCountry.getItems().add("io");
        comboSelectCountry.getItems().add("iq");
        comboSelectCountry.getItems().add("ir");
        comboSelectCountry.getItems().add("is");
        comboSelectCountry.getItems().add("it");
        comboSelectCountry.getItems().add("je");
        comboSelectCountry.getItems().add("jm");
        comboSelectCountry.getItems().add("jo");
        comboSelectCountry.getItems().add("jp");
        comboSelectCountry.getItems().add("ke");
        comboSelectCountry.getItems().add("kg");
        comboSelectCountry.getItems().add("kh");
        comboSelectCountry.getItems().add("ki");
        comboSelectCountry.getItems().add("km");
        comboSelectCountry.getItems().add("kn");
        comboSelectCountry.getItems().add("kp");
        comboSelectCountry.getItems().add("kr");
        comboSelectCountry.getItems().add("kw");
        comboSelectCountry.getItems().add("ky");
        comboSelectCountry.getItems().add("kz");
        comboSelectCountry.getItems().add("la");
        comboSelectCountry.getItems().add("lb");
        comboSelectCountry.getItems().add("lc");
        comboSelectCountry.getItems().add("li");
        comboSelectCountry.getItems().add("lk");
        comboSelectCountry.getItems().add("lr");
        comboSelectCountry.getItems().add("lr");
        comboSelectCountry.getItems().add("ls");
        comboSelectCountry.getItems().add("lt");
        comboSelectCountry.getItems().add("lu");
        comboSelectCountry.getItems().add("lv");
        comboSelectCountry.getItems().add("ly");
        comboSelectCountry.getItems().add("ma");
        comboSelectCountry.getItems().add("mc");
        comboSelectCountry.getItems().add("md");
        comboSelectCountry.getItems().add("me");
        comboSelectCountry.getItems().add("mf");
        comboSelectCountry.getItems().add("mg");
        comboSelectCountry.getItems().add("mh");
        comboSelectCountry.getItems().add("mk");
        comboSelectCountry.getItems().add("ml");
        comboSelectCountry.getItems().add("mm");
        comboSelectCountry.getItems().add("mn");
        comboSelectCountry.getItems().add("mo");
        comboSelectCountry.getItems().add("mp");
        comboSelectCountry.getItems().add("mr");
        comboSelectCountry.getItems().add("ms");
        comboSelectCountry.getItems().add("mt");
        comboSelectCountry.getItems().add("mu");
        comboSelectCountry.getItems().add("mv");
        comboSelectCountry.getItems().add("mw");
        comboSelectCountry.getItems().add("mx");
        comboSelectCountry.getItems().add("my");
        comboSelectCountry.getItems().add("mz");
        comboSelectCountry.getItems().add("na");
        comboSelectCountry.getItems().add("nc");
        comboSelectCountry.getItems().add("ne");
        comboSelectCountry.getItems().add("nf");
        comboSelectCountry.getItems().add("ng");
        comboSelectCountry.getItems().add("ni");
        comboSelectCountry.getItems().add("nl");
        comboSelectCountry.getItems().add("no");
        comboSelectCountry.getItems().add("np");
        comboSelectCountry.getItems().add("nr");
        comboSelectCountry.getItems().add("nu");
        comboSelectCountry.getItems().add("nz");
        comboSelectCountry.getItems().add("om");
        comboSelectCountry.getItems().add("pa");
        comboSelectCountry.getItems().add("pe");
        comboSelectCountry.getItems().add("pf");
        comboSelectCountry.getItems().add("pg");
        comboSelectCountry.getItems().add("ph");
        comboSelectCountry.getItems().add("pk");
        comboSelectCountry.getItems().add("pl");
        comboSelectCountry.getItems().add("pm");
        comboSelectCountry.getItems().add("pn");
        comboSelectCountry.getItems().add("pr");
        comboSelectCountry.getItems().add("ps");
        comboSelectCountry.getItems().add("pt");
        comboSelectCountry.getItems().add("pw");
        comboSelectCountry.getItems().add("py");
        comboSelectCountry.getItems().add("qa");
        comboSelectCountry.getItems().add("re");
        comboSelectCountry.getItems().add("re");
        comboSelectCountry.getItems().add("ro");
        comboSelectCountry.getItems().add("rs");
        comboSelectCountry.getItems().add("ru");
        comboSelectCountry.getItems().add("rw");
        comboSelectCountry.getItems().add("sa");
        comboSelectCountry.getItems().add("sb");
        comboSelectCountry.getItems().add("sc");
        comboSelectCountry.getItems().add("sd");
        comboSelectCountry.getItems().add("se");
        comboSelectCountry.getItems().add("sg");
        comboSelectCountry.getItems().add("sh");
        comboSelectCountry.getItems().add("si");
        comboSelectCountry.getItems().add("sj");
        comboSelectCountry.getItems().add("sk");
        comboSelectCountry.getItems().add("sm");
        comboSelectCountry.getItems().add("sn");
        comboSelectCountry.getItems().add("so");
        comboSelectCountry.getItems().add("sr");
        comboSelectCountry.getItems().add("ss");
        comboSelectCountry.getItems().add("st");
        comboSelectCountry.getItems().add("sv");
        comboSelectCountry.getItems().add("sx");
        comboSelectCountry.getItems().add("sy");
        comboSelectCountry.getItems().add("sz");
        comboSelectCountry.getItems().add("tc");
        comboSelectCountry.getItems().add("td");
        comboSelectCountry.getItems().add("tf");
        comboSelectCountry.getItems().add("tg");
        comboSelectCountry.getItems().add("th");
        comboSelectCountry.getItems().add("tj");
        comboSelectCountry.getItems().add("tk");
        comboSelectCountry.getItems().add("tl");
        comboSelectCountry.getItems().add("tm");
        comboSelectCountry.getItems().add("tn");
        comboSelectCountry.getItems().add("to");
        comboSelectCountry.getItems().add("tr");
        comboSelectCountry.getItems().add("tt");
        comboSelectCountry.getItems().add("tv");
        comboSelectCountry.getItems().add("tw");
        comboSelectCountry.getItems().add("tz");
        comboSelectCountry.getItems().add("ua");
        comboSelectCountry.getItems().add("ug");
        comboSelectCountry.getItems().add("um");
        comboSelectCountry.getItems().add("us");
        comboSelectCountry.getItems().add("uy");
        comboSelectCountry.getItems().add("uz");
        comboSelectCountry.getItems().add("va");
        comboSelectCountry.getItems().add("vc");
        comboSelectCountry.getItems().add("ve");
        comboSelectCountry.getItems().add("vi");
        comboSelectCountry.getItems().add("vn");
        comboSelectCountry.getItems().add("vu");
        comboSelectCountry.getItems().add("wf");
        comboSelectCountry.getItems().add("ws");
        comboSelectCountry.getItems().add("xk");
        comboSelectCountry.getItems().add("ye");
        comboSelectCountry.getItems().add("yt");
        comboSelectCountry.getItems().add("za");
        comboSelectCountry.getItems().add("zm");
        comboSelectCountry.getItems().add("zw");
    }
}