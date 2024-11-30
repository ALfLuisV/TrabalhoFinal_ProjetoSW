package controller;

import java.time.LocalDate;
import java.util.Scanner;

import DAOpackage.CampeonatoDAO;
import DAOpackage.PartidaDAO;
import DAOpackage.TimeDAO;
import model.Campeonato;
import model.Partida;
import model.Time;

public class PartidaController {
    private static PartidaController instancia;
    private TimeDAO timeDAO;
    private PartidaDAO partidaDAO;
    private CampeonatoDAO campeonatoDAO;

    private PartidaController() {
        timeDAO = TimeDAO.getInstancia();
        campeonatoDAO = CampeonatoDAO.getInstancia();
        partidaDAO = PartidaDAO.getInstancia();
    }

    public static PartidaController getInstancia() {
        if (instancia == null) {
            instancia = new PartidaController();
            return instancia;
        }
        return instancia;
    }

    public void cadastrarPlacar(Scanner scan) {
        PartidaDAO.getInstancia().imprimirPartidasSemPlacar();
        int idBuscado;
        Partida partidaBuscada;
        Boolean partidaInvalida = false;

        do {
            do {
                System.out.print("digite o ID da partida desejada: ");
                while (!scan.hasNextInt()) {
                    System.out.println("Digite um numero valido.");
                    scan.nextLine();
                }
                idBuscado = scan.nextInt();
            } while (idBuscado >= partidaDAO.getArraySize() + 1);

            partidaBuscada = partidaDAO.getPartidaByID(idBuscado);

            if (partidaBuscada.getPontosTime1() > 0 || partidaBuscada.getPontosTime2() > 0
                    || LocalDate.now().compareTo(partidaBuscada.getData()) < 0) {
                partidaInvalida = true;
            }
        } while (partidaInvalida);
        boolean eqp1pres;
        boolean eqp2pres;

        do {
            System.out.println("A equipe 1 estava presente?(true ou false) ");
            while (!scan.hasNextBoolean()) {
                System.out.println("Digite true ou false");
                scan.nextLine();
            }
            eqp1pres = scan.nextBoolean();

            System.out.println("A equipe 2 estava presente?(true ou false) ");
            while (!scan.hasNextBoolean()) {
                System.out.println("Digite true ou false");
                scan.nextLine();
            }
            eqp2pres = scan.nextBoolean();
            if (!eqp1pres && !eqp2pres) {
                System.out.println("Alguma das duas equipes precisa estar presente para registrar o placar");
            }
        } while (!eqp1pres && !eqp2pres);

        if (!eqp1pres) {
            partidaBuscada.setEquipe1presente(false);
        } else if (!eqp2pres) {
            partidaBuscada.setEquipe2presente(false);
        } else {
            boolean desempate;
            System.out.println("A partida teve desempate? (true ou false) ");
            while (!scan.hasNextBoolean()) {
                System.out.println("Digite true ou false");
            }
            desempate = scan.nextBoolean();
            if (desempate) {
                int placar1[] = new int[5];
                int placar2[] = new int[5];

                System.out.println("Digite o placar do time 1:");
                for (int i = 0; i < 5; i++) {
                    placar1[i] = scan.nextInt();
                }

                System.out.println("Digite o placar do time 2:");
                for (int i = 0; i < 5; i++) {
                    placar2[i] = scan.nextInt();
                }

                partidaBuscada.setPlacar(placar1, placar2);
            } else {
                int placar1[] = new int[4];
                int placar2[] = new int[4];
                int contador1 = 0;
                int contador2 = 0;
                do {
                    System.out.println("Digite o placar do time 1:");
                    for (int i = 0; i < 4; i++) {
                        placar1[i] = scan.nextInt();
                        contador1 += placar1[i];
                    }

                    System.out.println("Digite o placar do time 2:");
                    for (int i = 0; i < 4; i++) {
                        placar2[i] = scan.nextInt();
                        contador2 += placar2[i];
                    }
                    if (contador1 == contador2) {
                        System.out.println("Os placares não podem ser iguais!");
                    }
                } while (contador1 == contador2);

                partidaBuscada.setPlacar(placar1, placar2);
                partidaBuscada.setEquipe1presente(true);
                partidaBuscada.setEquipe2presente(true);
            }
        }
    }

    public void cadastrarPartida(Scanner scan) {
        scan.nextLine();
        int ano = lerAno(scan);
        int mes = lerMes(scan);
        int dia = lerDia(scan);
        LocalDate dataAUX = LocalDate.of(ano, mes, dia);

        if (dataAUX.compareTo(LocalDate.now()) > 0) {
            Campeonato campeonatoDesejado = campeonatoDAO.getCampeonatoByYear((ano));
            campeonatoDesejado.imprimirTimesDaEdicao();
            System.out.println("Quais equipes irão jogar essa partida?");
            int idEquipe1;
            Time time1;

            System.out.print("Digite o id da equipe 1: ");
            idEquipe1 = scan.nextInt();
            time1 = timeDAO.getTimeByID(idEquipe1);
            while (!campeonatoDesejado.verificacaoSeTimeEstaNaEdicao(time1)) {
                while (!scan.hasNextInt()) {
                    System.out.println("Digite um ID valido para a equipe 1: ");
                }
                idEquipe1 = scan.nextInt();
                time1 = timeDAO.getTimeByID(idEquipe1);
            }

            int idEquipe2;
            Time time2;

            System.out.print("Digite o id da equipe 2: ");
            idEquipe2 = scan.nextInt();
            time2 = timeDAO.getTimeByID(idEquipe2);
            while (!campeonatoDesejado.verificacaoSeTimeEstaNaEdicao(time2) && time2 != time1) {
                while (!scan.hasNextInt()) {
                    System.out.println("Digite um ID valido para a equipe 2: ");
                }
                idEquipe2 = scan.nextInt();
                time2 = timeDAO.getTimeByID(idEquipe2);
            }
            Partida partidaAUX = new Partida(dataAUX, time1, time2);
            partidaDAO.addPartida(partidaAUX);

        } else {
            Campeonato campeonatoDesejado = campeonatoDAO.getCampeonatoByYear((ano));
            campeonatoDesejado.imprimirTimesDaEdicao();

            int idEquipe1;
            Time time1;

            System.out.print("Digite o id da equipe 1: ");
            idEquipe1 = scan.nextInt();
            time1 = timeDAO.getTimeByID(idEquipe1);
            while (!campeonatoDesejado.verificacaoSeTimeEstaNaEdicao(time1)) {
                while (!scan.hasNextInt()) {
                    System.out.println("Digite um ID valido para a equipe 1: ");
                }
                idEquipe1 = scan.nextInt();
                time1 = timeDAO.getTimeByID(idEquipe1);
            }

            int idEquipe2;
            Time time2;

            System.out.print("Digite o id da equipe 2: ");
            idEquipe2 = scan.nextInt();
            time2 = timeDAO.getTimeByID(idEquipe2);
            while (!campeonatoDesejado.verificacaoSeTimeEstaNaEdicao(time2) && time2 != time1) {
                while (!scan.hasNextInt()) {
                    System.out.println("Digite um ID valido para a equipe 2: ");
                }
                idEquipe2 = scan.nextInt();
                time2 = timeDAO.getTimeByID(idEquipe2);
            }

            System.out.print("A equipe 1 estava presente (True ou False)? ");
            Boolean eqp1pres;
            while (!scan.hasNextBoolean()) {
                System.out.println("Digite true ou false: ");
                scan.nextLine();
            }
            eqp1pres = scan.nextBoolean();

            System.out.print("A equipe 2 estava presente (True ou False)? ");
            Boolean eqp2pres;
            while (!scan.hasNextBoolean()) {
                System.out.println("Digite true ou false: ");
                scan.nextLine();
            }
            eqp2pres = scan.nextBoolean();

            if (!eqp1pres && !eqp2pres) {
                int placar1[] = new int[4];
                int placar2[] = new int[4];
                Partida partidaAUX = new Partida(dataAUX, timeDAO.getTimeByID(idEquipe1),
                        timeDAO.getTimeByID(idEquipe2), placar1, placar2, eqp1pres, eqp2pres);
                PartidaDAO.getInstancia().addPartida(partidaAUX);
                scan.nextLine();
            } else {
                System.out.print("A partida teve desempate (True ou False)? ");
                boolean desempate = scan.nextBoolean();
                // VERIFICAÇÃO PLACAR 1 != PLACAR 2
                if (desempate) {
                    int placar1[] = new int[5];
                    int placar2[] = new int[5];
                    int contador1 = 0, contador2 = 0;
                    do {
                        System.out.println("Digite os pontos por quarto da equipe 1:");
                        for (int i = 0; i < 5; i++) {
                            placar1[i] = scan.nextInt();
                            contador1 += placar1[i];
                        }
                        System.out.println("Digite os pontos por quarto da equipe 2:");
                        for (int i = 0; i < 5; i++) {
                            placar2[i] = scan.nextInt();
                            contador2 += placar2[i];
                        }
                    } while (contador1 == contador2);
                    partidaDAO.addPartida(new Partida(dataAUX, timeDAO.getTimeByID(idEquipe1),
                            timeDAO.getTimeByID(idEquipe2), placar1, placar2, eqp1pres, eqp2pres));
                } else {
                    int placar1[] = new int[4];
                    int placar2[] = new int[4];
                    int contador1 = 0, contador2 = 0;
                    do {
                        System.out.println("Digite os pontos por quarto da equipe 1:");
                        for (int i = 0; i < 4; i++) {
                            placar1[i] = scan.nextInt();
                            contador1 += placar1[i];
                        }
                        System.out.println("Digite os pontos por quarto da equipe 2:");
                        for (int i = 0; i < 4; i++) {
                            placar2[i] = scan.nextInt();
                            contador2 += placar2[i];
                        }
                    } while (contador1 == contador2);
                    Partida partidaAUX = new Partida(dataAUX, timeDAO.getTimeByID(idEquipe1),
                            timeDAO.getTimeByID(idEquipe2), placar1, placar2, eqp1pres, eqp2pres);
                    PartidaDAO.getInstancia().addPartida(partidaAUX);
                    scan.nextLine();
                }
            }
        }
    }

    public static int lerAno(Scanner scan) {
        int ano;
        boolean campExiste;
        do {
            System.out.print("Digite o ano da partida com 4 digitos: ");
            while (!scan.hasNextInt()) {
                System.out.println("Favor digitar um número válido.");
                scan.nextLine();
            }
            ano = scan.nextInt();
            campExiste = CampeonatoDAO.getInstancia().campeonatoExiste(ano);
            if (!campExiste) {
                System.out.println("Este ano ainda não tem uma edição cadastrada. Digite um ano valido.");
                ano = 0;
            }
        } while (String.valueOf(ano).length() != 4 && !campExiste);
        return ano;
    }

    public static int lerMes(Scanner scan) {
        int mes;
        do {
            System.out.print("Digite o mês da partida com 2 digitos: ");
            while (!scan.hasNextInt()) {
                System.out.println("Favor digitar um número válido.");
                scan.nextLine();
            }
            mes = scan.nextInt();
        } while (mes < 1 || mes > 12);
        return mes;
    }

    public static int lerDia(Scanner scan) {
        int dia;
        do {
            System.out.print("Digite o dia da partida com 2 digitos: ");
            while (!scan.hasNextInt()) {
                System.out.println("Favor digitar um número válido.");
                scan.nextLine();
            }
            dia = scan.nextInt();
        } while (dia < 1 || dia > 31);
        return dia;
    }

}
