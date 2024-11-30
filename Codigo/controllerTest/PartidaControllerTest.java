package controllerTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import controller.PartidaController;
import DAOpackage.CampeonatoDAO;
import DAOpackage.PartidaDAO;
import DAOpackage.TimeDAO;
import model.Campeonato;
import model.Partida;
import model.Time;

public class PartidaControllerTest {

    private PartidaController partidaController;
    private PartidaDAO partidaDAO;
    private CampeonatoDAO campeonatoDAO;
    private TimeDAO timeDAO;
    private ByteArrayInputStream inputStream;
    private Scanner scanner;

    @Before
    public void setUp() {
        partidaController = PartidaController.getInstancia();
        partidaDAO = PartidaDAO.getInstancia();
        campeonatoDAO = CampeonatoDAO.getInstancia();
        timeDAO = TimeDAO.getInstancia();

        // Redefine o InputStream para simular entrada do teclado
        inputStream = new ByteArrayInputStream("2022\n10\n15\n1\n2\ntrue\ntrue\ntrue\nfalse\n3\n4\nfalse\nfalse\ntrue\n5\n6\nfalse\ntrue\nfalse\n".getBytes());
        scanner = new Scanner(inputStream);
    }

    @Test
    public void testCadastrarPlacar() {
        // Simule o cadastro de um campeonato
        Campeonato campeonato = new Campeonato("LLAB2022", new ArrayList<Time>(), 2022);
        campeonatoDAO.addCampeonato(campeonato);

        // Simule o cadastro de uma partida
        Partida partida = new Partida(LocalDate.of(2022, 10, 15), new Time(null), new Time(null));
        partidaDAO.addPartida(partida);

        // Verifique se o placar da partida é nulo inicialmente
        assertNull(partida.exibirPlacar());

        // Simule o cadastro de placar para a partida
        partidaController.cadastrarPlacar(scanner);

        // Verifique se o placar da partida foi definido
        assertNotNull(partida.exibirPlacar());
    }

    @Test
    public void testCadastrarPartida() {
        // Simule o cadastro de um campeonato
        Campeonato campeonato = new Campeonato("LLAB2022", new ArrayList<Time>(), 2022);
        campeonatoDAO.addCampeonato(campeonato);

        // Verifique se a partida não existe inicialmente
        assertNull(partidaDAO.getPartidaByID(1));

        // Simule o cadastro de uma partida
        partidaController.cadastrarPartida(scanner);

        // Verifique se a partida foi cadastrada
        assertNotNull(partidaDAO.getPartidaByID(1));
    }
}
