package controllerTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import controller.TimeController;
import DAOpackage.TimeDAO;
import model.Time;

public class TimeControllerTest {

    private TimeController timeController;
    private TimeDAO timeDAO;
    private ByteArrayInputStream inputStream;
    private Scanner scanner;

    @Before
    public void setUp() {
        timeController = TimeController.getInstancia();
        timeDAO = TimeDAO.getInstancia();

        // Redefine o InputStream para simular entrada do teclado
        inputStream = new ByteArrayInputStream("Time de Teste\n".getBytes());
        scanner = new Scanner(inputStream);
    }

    @Test
    public void testCadastrarTime() {
        // Verifique se o time não existe inicialmente
        assertNull(timeDAO.getTimeByID(1));

        // Simule o cadastro de um time
        timeController.cadastrarTime(scanner);

        // Verifique se o time foi cadastrado
        assertNotNull(timeDAO.getTimeByID(1));
    }
}
