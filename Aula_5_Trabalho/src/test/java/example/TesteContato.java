package example;

import org.example.controller.ContatoController;
import org.example.model.Contato;
import org.junit.jupiter.api.*;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteContato {
    String nome = "Joao";
    int telefone = 123456789;
    String email = "joao@example.com";
    int ano = 1990;
    int mes = 5;
    int dia = 15;
    private ContatoController contatoController;


    @BeforeEach
    public void setUp() {
        contatoController = new ContatoController();
    }

    @Test
    @Order(1)
    void adicionarContato() {
        contatoController.adicionarContato(nome, telefone, email, ano, mes, dia);

        assertEquals(1, contatoController.getContatos().size(), "Deveria ter um contato na lista");

        Contato contatoAdicionado = contatoController.getContatos().get(0);
        assertEquals(nome, contatoAdicionado.getNome(), "Nome do contato deve ser igual");
        assertEquals(telefone, contatoAdicionado.getNumero(), "Número de telefone do contato deve ser igual");
        assertEquals(email, contatoAdicionado.getEmail(), "Email do contato deve ser igual");
        assertEquals(ano, contatoAdicionado.getNascimento().get(Calendar.YEAR), "Ano de nascimento do contato deve ser igual");
        assertEquals(mes - 1, contatoAdicionado.getNascimento().get(Calendar.MONTH), "Mês de nascimento do contato deve ser igual");
        assertEquals(dia, contatoAdicionado.getNascimento().get(Calendar.DAY_OF_MONTH), "Dia de nascimento do contato deve ser igual");
    }

    @Test
    @Order(2)
    void listarContatos() {

        contatoController.adicionarContato("Roberto", telefone, email, ano, mes, dia);

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        contatoController.listarContatos();
        assertTrue(outContent.toString().contains("ID:"), "A listagem deve conter o ID do contato");

    }

    @Test
    @Order(3)
    void testdeletarContato() {
        contatoController.adicionarContato("Roberto", telefone, email, ano, mes, dia);
        contatoController.removerContato(1);
        assertEquals(1, contatoController.getContatos().size(), "Deveria ter apenas um contato após a remoção");

        assertFalse(contatoController.getContatos().stream().anyMatch(c -> c.getId() == (1)),
                "O contato removido não deve estar mais na lista");
    }

    @Test
    @Order(4)
    void testEditarContato() {
        contatoController.adicionarContato("Roberto", telefone, email, ano, mes, dia);

        contatoController.editarContato(0, "Robson", 987654321, "Robson@gmail.com", ano, mes, dia);

        Contato contatoAdicionado = contatoController.getContatos().get(0);

        assertEquals("Robson", contatoAdicionado.getNome(), "Nome do contato deve ser igual");
        assertEquals(987654321, contatoAdicionado.getNumero(), "Número de telefone do contato deve ser igual");
        assertEquals("Robson@gmail.com", contatoAdicionado.getEmail(), "Email do contato deve ser igual");
        assertEquals(ano, contatoAdicionado.getNascimento().get(Calendar.YEAR), "Ano de nascimento do contato deve ser igual");
        assertEquals(mes - 1, contatoAdicionado.getNascimento().get(Calendar.MONTH), "Mês de nascimento do contato deve ser igual");
        assertEquals(dia, contatoAdicionado.getNascimento().get(Calendar.DAY_OF_MONTH), "Dia de nascimento do contato deve ser igual");
    }
    @Test
    @Order(5)
    void pesquisarContatosporLetra() {
        contatoController.adicionarContato("Ana", 123456789, "ana@example.com", 1990, 5, 15);
        contatoController.adicionarContato("Antônio", 987654321, "antonio@example.com", 1995, 6, 20);
        contatoController.adicionarContato("Pedro", 555555555, "pedro@example.com", 1985, 3, 10);

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        contatoController.pesquisarContatosporLetra("an");

        assertTrue(outContent.toString().contains("ID:"), "A listagem deve conter o ID do contato");
        assertTrue(outContent.toString().contains("Nome: Ana"), "A listagem deve conter o contato com nome iniciado por 'Ana'");
        assertTrue(outContent.toString().contains("Telefone: 123456789"), "A listagem deve conter o telefone do contato");
        assertTrue(outContent.toString().contains("Email: ana@example.com"), "A listagem deve conter o email do contato");
        assertFalse(outContent.toString().contains("Pedro"), "A listagem não deve conter o contato com nome iniciado por 'Pedro'");
    }


    @Test
    @Order(6)
    void pesquisarContatosporMes() {
        contatoController.adicionarContato("Roberto", telefone, email, ano, 1, dia);
        contatoController.adicionarContato("Maria", 987654321, "maria@example.com", ano, 5, dia);
        contatoController.adicionarContato("João", 555555555, "joao@example.com", ano, 9, dia);

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        contatoController.pesquisarContatosporMes(5);

        assertTrue(outContent.toString().contains("Maria"), "A lista deve conter o contato com mês de nascimento 5");
        assertFalse(outContent.toString().contains("João"), "A lista não deve conter o contato com mês de nascimento 9");
    }

}
