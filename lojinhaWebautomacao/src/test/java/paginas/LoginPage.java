package paginas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
    principios de page objects
    -tenha um atributo da classe que seja web driver
    -tenha um construtor que pegue uma variável e jogue dentro do atributo
    -ter métodos de interação com cada elemento
 */
public class LoginPage {
    private WebDriver navegador;

    public LoginPage(WebDriver navegador) {
        this.navegador = navegador;
    }

    public LoginPage informarOUsuario(String usuario) {
        // Aqui não precisa de this.navegador pois já foi instanciado em beforeEach.
        navegador.findElement(By.cssSelector("label[for='usuario']")).click();
        navegador.findElement(By.id("usuario")).sendKeys(usuario);

        return this; /* retorna a própria classe ou instância do objeto pois vai permanecer na
        mesma página permitindo encadear chamadas de métodos em uma única instancia do objeto sem precisar
        criar nova instância cada vez.*/
    }

    public LoginPage informarASenha(String senha) {
        navegador.findElement(By.cssSelector("label[for='senha']")).click();
        navegador.findElement(By.id("senha")).sendKeys(senha);

        return this;
    }

    public ListaDeProdutosPage submeterFormularioDeLogin() {
        navegador.findElement(By.cssSelector("button[type='submit']")).click();

        return new ListaDeProdutosPage(navegador);
    }

}
