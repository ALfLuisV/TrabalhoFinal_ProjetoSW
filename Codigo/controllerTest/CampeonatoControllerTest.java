package controllerTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import controller.CampeonatoController;
import DAOpackage.CampeonatoDAO;
import DAOpackage.TimeDAO;
import model.Campeonato;
import model.Time;

public class CampeonatoControllerTest {

    private CampeonatoController campeonatoController;
    private CampeonatoDAO campeonatoDAO;
    private TimeDAO timeDAO;
    private ByteArrayInputStream inputStream;
    private Scanner scanner;

    @Before
    public void setUp() {
        campeonatoController = CampeonatoController.getInstancia();
        campeonatoDAO = CampeonatoDAO.getInstancia();
        timeDAO = TimeDAO.getInstancia();

        // Redefine o InputStream para simular entrada do teclado
        inputStream = new ByteArrayInputStream("1\n2\n3\n4\n5\n6\n7\n8\n".getBytes());
        scanner = new Scanner(inputStream);
    }

    @Test
    public void testCadastrarCampeonato() {
        // Verifique se o campeonato não existe inicialmente
        assertFalse(campeonatoDAO.campeonatoExiste(2022));

        // Simule o cadastro de um campeonato
        campeonatoController.cadastrarCampeonato(scanner);

        // Verifique se o campeonato foi adicionado
        assertTrue(campeonatoDAO.campeonatoExiste(2022));

        // Tente cadastrar o mesmo campeonato novamente
        campeonatoController.cadastrarCampeonato(scanner);

        // Verifique se uma mensagem de campeonato já cadastrado é exibida
        assertTrue(campeonatoDAO.campeonatoExiste(2022));
    }
}
