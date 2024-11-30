package controller;

import DAOpackage.TimeDAO;
import model.Time;
import java.util.Scanner;

public class TimeController {
    private static TimeController instancia;
    private TimeDAO timeDAO;

    private TimeController(){
        timeDAO = TimeDAO.getInstancia();
    }

    public static TimeController getInstancia(){
        if(instancia == null){
            instancia = new TimeController();
            return instancia;
        }
        return instancia;
    }

    public void cadastrarTime(Scanner scan){
        System.out.print("Digite o nome do time: ");
        scan.nextLine();
        String nomeAUX = scan.nextLine();
        timeDAO.addTime(new Time(nomeAUX));
    }
}
