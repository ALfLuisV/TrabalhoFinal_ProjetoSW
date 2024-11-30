package controller;

import java.util.ArrayList;
import java.util.Scanner;

import DAOpackage.*;
import model.Time;
import model.Campeonato;

public class CampeonatoController {
    private static CampeonatoController instancia;
    private CampeonatoDAO campeonatoDAO;
    private TimeDAO timeDAO;
    
    private CampeonatoController(){
        campeonatoDAO = CampeonatoDAO.getInstancia();
        timeDAO = TimeDAO.getInstancia();
    }

    public static CampeonatoController getInstancia(){
        if(instancia == null){
            instancia = new CampeonatoController();
            return instancia;
        }
        return instancia;
    }

    public void cadastrarCampeonato(Scanner scanCamp){
        System.out.print("Informe o ano da edicao a ser cadastrada com 4 digitos: ");
        while (!scanCamp.hasNextInt()) {
            System.out.println("Opção inválida, favor digitar um dos número listados.");
            scanCamp.nextLine(); 
        }
        int anoEdicao = scanCamp.nextInt();
        String anoEdicaoString = Integer.toString(anoEdicao);
        if(anoEdicaoString.length() != 4){
            System.out.println("Ano invalido");
        }else{
            if(!campeonatoDAO.campeonatoExiste(anoEdicao)){
                timeDAO.imprimirTimes();
                System.out.println("Digite o ID das 8 equipes que vao estar presentes: ");
                ArrayList<Time> timesAUX = new ArrayList<Time>();
                for(int i = 0; i<8; i++){
                    while (!scanCamp.hasNextInt()) {
                        System.out.println("Opção inválida, favor digitar um dos número listados.");
                        scanCamp.nextLine(); 
                    }
                    int idTimeAUX = scanCamp.nextInt();
                    timesAUX.add(timeDAO.getTimeByID(idTimeAUX));
                }
                anoEdicaoString = anoEdicaoString.substring(2);
                String nomeEdicao = "LLAB" + anoEdicaoString;
                campeonatoDAO.addCampeonato(new Campeonato(nomeEdicao, timesAUX, anoEdicao));
            }else{
                System.out.println("Essa edicao ja foi cadastrada");
            }
        }
        scanCamp.nextLine();
    }
}   
