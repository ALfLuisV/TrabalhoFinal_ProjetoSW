import DAOpackage.*;
import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TimeDAO timeDAO = TimeDAO.getInstancia();
        CampeonatoDAO campeonatoDAO = CampeonatoDAO.getInstancia();
        PartidaDAO partidaDAO = PartidaDAO.getInstancia(); 

        menuPrincipal(campeonatoDAO, timeDAO, partidaDAO, scanner);

        campeonatoDAO.escreverArquivo();
        timeDAO.escreverArquivo();
        partidaDAO.escreverArquivo();
    }

    public static void menuPrincipal(CampeonatoDAO campeonatoDAO, TimeDAO TimeDAO, PartidaDAO partidaDAO, Scanner scanner ) {
        int escolha;
        do {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Escolha a opção desejada: ");
            System.out.println("[1] Cadastrar partida ");
            System.out.println("[2] Registrar placar de partidas que ja aconteceram ");
            System.out.println("[3] Mostrar registro de todas as partidas de um campeonato ");
            System.out.println("[4] Gerar a tabela de classificacao de um campeonato ");
            System.out.println("[5] Indicar a melhor defesa ou ataque de um campeonato ");
            System.out.println("[6] Registrar novo campeonato ou time ");
            System.out.println("[9] Para fechar o programa");

            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida, favor digitar um dos número listados.");
                scanner.nextLine(); 
            }

            escolha = scanner.nextInt();
            switch (escolha) {
                case 1: {
                    PartidaController.getInstancia().cadastrarPartida(scanner);
                    break;
                }
                case 2: {
                    PartidaController.getInstancia().cadastrarPlacar(scanner);
                    break;
                }
                case 3: {
                    campeonatoDAO.imprimirCampeonatos();
                    System.out.print("Digite o ID do campeonato desejado: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Opção inválida, favor digitar um dos número listados.");
                        scanner.nextLine(); 
                    }
                    int idAUX = scanner.nextInt();
                    int anoDesejado = Integer.parseInt(campeonatoDAO.getCampeonatoByID(idAUX).getAno());
                    partidaDAO.imprimirPartidasDaEdicao(anoDesejado);
                    break;
                }
                case 4: {
                    campeonatoDAO.imprimirCampeonatos();
                    System.out.print("Digite o ID do campeonato desejado: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Opção inválida, favor digitar um dos número listados.");
                        scanner.nextLine(); 
                    }
                    int idAUX = scanner.nextInt();
                    System.out.println(campeonatoDAO.getCampeonatoByID(idAUX).gerarClassificacao());
                    break;
                }
                case 5: {
                    campeonatoDAO.imprimirCampeonatos();
                    System.out.print("Digite o ID do campeonato desejado: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Opção inválida, favor digitar um dos número listados.");
                        scanner.nextLine(); 
                    }
                    int idAUX = scanner.nextInt();
                    ArrayList<Time> melhorAtaque = campeonatoDAO.getCampeonatoByID(idAUX).melhorAtaque();
                    System.out.print("Melhor ataque: ");
                    for(Time time:melhorAtaque){
                        System.out.print(time.getNome() + "\t");
                    }
                    System.out.println();
                    ArrayList<Time> melhorDefesa = campeonatoDAO.getCampeonatoByID(idAUX).melhorDefesa();
                    System.out.print("Melhor defesa: ");
                    for(Time time:melhorDefesa){
                        System.out.print(time.getNome() + "\t");
                    }
                    System.out.println();
                    break;
                }  
                case 6: {
                    System.out.println("[1] Cadastrar campeonato");
                    System.out.println("[2] Cadastrar equipe");
                   
                    while (!scanner.hasNextInt()) {
                        System.out.println("Opção inválida, favor digitar um dos número listados.");
                        scanner.nextLine(); 
                    }

                    int opcaoAUX = scanner.nextInt();
                    if(opcaoAUX == 1){
                        CampeonatoController.getInstancia().cadastrarCampeonato(scanner);
                    }
                    else if(opcaoAUX == 2){
                        TimeController.getInstancia().cadastrarTime(scanner);
                    }else{
                        System.out.println("opcao Invalida");
                    }
                    break;
                }      
                case 9: {
                    System.out.println("O programa foi encerrado.");
                    break;
                }
                default:{
                    System.out.println("Opção inválida.");
                    break;
                }
            }
        } while (escolha != 9);
    }
}
