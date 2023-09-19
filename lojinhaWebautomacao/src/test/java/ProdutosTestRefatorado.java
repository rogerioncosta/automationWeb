package modulos.produtos;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.LoginPage;

import java.time.Duration;

@DisplayName("Testes Web do Módulo de Produtos")
public class ProdutosTestRefatorado {

    private WebDriver navegador;

    @BeforeEach
    public void beforeEach() {
        // Abrir o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver_win32_v109\\chromedriver.exe");

        this.navegador = new ChromeDriver();

        // Maximizar a tela
        this.navegador.manage().window().maximize();

        /*
            Definir um tempo de espera padrão de 5 segundos caso não carregar um elemento devido servidor lento,
            espere 5 seg. antes de falhar o teste
         */
        this.navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navegar para a página da Lojinha Web
        this.navegador.get("http://165.227.93.41/lojinha-web/v2/");
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor igual a zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualZero() {
        // Fazer login
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("teste aut. web")
                .informarValorDoProduto("000")
                .informarCoresDoProduto("verde, rosa")
                .submeterFormularioDeAdcicaoComErro()
                .capturarMensagemApresentada();
        /* return this retorna a própria instância pois vai permanecer na mesma página permitindo
        encadear chamadas de métodos em uma única instancia do objeto sem precisar criar nova instância cada vez.*/

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor acima de 7000,00")
    public void testNaoEPermitidoRegistrarPordutoComValorMaiorQueSeteMil() {
        String mensagemApresentada =  new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("teste 7000")
                .informarValorDoProduto("700001")
                .informarCoresDoProduto("vermelho")
                .submeterFormularioDeAdcicaoComErro()
                .capturarMensagemApresentada();

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produtos que estejam no limite de 0,01")
    public void testPossoAdicionarProdutoComValorDeUmCentavo() {
        String mensagemApresentada =  new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("teste 0,01")
                .informarValorDoProduto("001")
                .informarCoresDoProduto("azul")
                .submeterFormularioDeAdicicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produtos que estejam no limite de 7000,00")
    public void possoAdicionarProdutosComValorDeSeteMil() {
        String mensagemApresentada = new LoginPage(navegador)
                .informarOUsuario("admin")
                .informarASenha("admin")
                .submeterFormularioDeLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("teste 7000,00")
                .informarValorDoProduto("7000001")
                .informarCoresDoProduto("roxo")
                .submeterFormularioDeAdicicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);

    }

    @AfterEach
    public void afterEach() {
        // Fechar o navegador
        navegador.quit();
    }
}

