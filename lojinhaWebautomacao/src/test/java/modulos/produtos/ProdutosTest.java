package modulos.produtos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@DisplayName("Testes Web do Módulo de Produtos")
public class ProdutosTest {
    @Test
    @DisplayName("Não é permitido registrar um produto com valor igual a zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualZero() {
        // Abrir o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver_win32_v109\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();

        // Maximizar a tela
        navegador.manage().window().maximize();

        /*
            Definir um tempo de espera padrão de 5 segundos caso não carregar um elemento devido servidor lento,
            espere 5 seg. antes de falhar o teste
         */
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navegar para a página da Lojinha Web
        navegador.get("http://165.227.93.41/lojinha-web/v2/");

        // Fazer login
        navegador.findElement(By.cssSelector("label[for='usuario']")).click();
        navegador.findElement(By.id("usuario")).sendKeys("usuario");

        navegador.findElement(By.cssSelector("label[for='senha']")).click();
        navegador.findElement(By.id("senha")).sendKeys("senha");

        navegador.findElement(By.cssSelector("button[type='submit']")).click();

        // Ir para a tela de registro de produto
        navegador.findElement(By.linkText("ADICIONAR PRODUTO")).click();

        // Preencher dados do produto e o valor será igual a zero
        navegador.findElement(By.id("produtonome")).sendKeys("teste aut. Web");

        navegador.findElement(By.id("produtovalor")).sendKeys("000");

        navegador.findElement(By.id("produtocores")).sendKeys("verde, rosa");

        // Submeter o formulário
        navegador.findElement(By.cssSelector("button[type='submit']")).click();

        // Validar que a mensagem de erro foi apresentada
        //<div class="toast rounded" style="top: 0px; opacity: 1;">O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00</div>
        String mensagemToast = navegador.findElement(By.cssSelector(".toast.rounded")).getText();
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemToast);

        // Fechar o navegador
        navegador.quit();
    }
}
