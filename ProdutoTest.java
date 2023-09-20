package modulos.produto;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@DisplayName("Testes Mobile do Módulo de Produto")
public class ProdutoTest {
    @DisplayName("Validação do valor de Produto Não Permitido")
    @Test
    public void testValidacaoDoValorDeProdutoNaoPermitido() throws MalformedURLException {
        // Abrir o App
        DesiredCapabilities capacidades = new DesiredCapabilities();
        capacidades.setCapability("deviceName","LDPlayer");
        // Inicie o emulador android e no cmd do pc, digite adb devices e copie o identificador apresentado
        capacidades.setCapability("platformName", "Android");
        capacidades.setCapability("udid", "emulator-5554");
        //capacidades.setCapability("automationName", "UiAutomator2");
        capacidades.setCapability("appPackage", "com.lojinha");
        capacidades.setCapability("appActivity", "com.lojinha.ui.MainActivity");
        // Instala o arquivo da lojinha.apk
        capacidades.setCapability("app", "C:\\Users\\Rogerio\\Desktop\\Lojinha+Android+Nativa\\lojinha-nativa.apk");

        // Isso fará a var app ser executado no dispostivo apontado (emulator-5554)
        WebDriver app = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capacidades);
        // Se não achar algum elemento, tente 5 seg. até quebrar o teste. Serve para lentidão no app.
        app.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Fazer login
        app.findElement(By.id("com.lojinha:id/user")).click();
        app.findElement(By.id("com.lojinha:id/user")).findElement(By.id("com.lojinha:id/editText")).sendKeys("rogerioncosta");

        app.findElement(By.id("com.lojinha:id/password")).click();
        app.findElement(By.id("com.lojinha:id/password")).findElement(By.id("com.lojinha:id/editText")).sendKeys("rogerion");

        app.findElement(By.id("com.lojinha:id/loginButton")).click();

        /*
            A procura do botão adicionar foi mais rápida que o carregamento da tela. Emulador lento.
            Espere 5 seg. antes de falhar o teste
         */
        //app.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Abrir o formulário de novo produto com.lojinha:id/floatingActionButton
        app.findElement(By.id("com.lojinha:id/floatingActionButton")).click();

        //app.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Cadastrar um produto com valor inválido
        app.findElement(By.id("com.lojinha:id/productName")).click();
        app.findElement(By.id("com.lojinha:id/productName")).findElement(By.id("com.lojinha:id/editText")).sendKeys("Aut. appium");
        app.findElement(By.id("com.lojinha:id/productValue")).click();
        app.findElement(By.id("com.lojinha:id/productValue")).findElement(By.id("com.lojinha:id/editText")).sendKeys("700001");
        app.findElement(By.id("com.lojinha:id/productColors")).click();
        app.findElement(By.id("com.lojinha:id/productColors")).findElement(By.id("com.lojinha:id/editText")).sendKeys("vermelho appium");

        app.findElement(By.id("com.lojinha:id/saveButton")).click();

        // Validar que a mensagem de valor inválido foi apresentada
        String mensagemApresentada = app.findElement(By.xpath("//android.widget.Toast")).getText();
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);

    }
}
