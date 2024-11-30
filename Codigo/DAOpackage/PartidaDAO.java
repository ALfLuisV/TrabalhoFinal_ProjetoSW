package DAOpackage;

import model.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;


public class PartidaDAO{
    private static final String ARQUIVOTXT = "codigo/data/Partida.txt";
    private static PartidaDAO instancia;
    private ArrayList<Partida> partidas;
    private TimeDAO timesInstancia;

    private PartidaDAO(){
        partidas = new ArrayList<Partida>();
        timesInstancia = TimeDAO.getInstancia();
        lerArquivo();
    }

    public static PartidaDAO getInstancia(){
        if(instancia == null){
            instancia = new PartidaDAO();
            return instancia;
        }
        return instancia;
    }
    
    public void addPartida(Partida partida){
        this.partidas.add(partida);
    }

    public void imprimirPartidasSemPlacar(){
        for(Partida partida : partidas){
            if(LocalDate.now().compareTo(partida.getData()) > 0){
                if(partida.getPontosTime1() == 0 && partida.getPontosTime2() == 0){
                    System.out.println(partida.getID() + " --> " + partida.getData() + " - " + partida.getTime1().getNome() + ": " + partida.getPontosTime1() + " X " + partida.getTime2().getNome() + ": " + partida.getPontosTime2());
                }
            }
        }
    }

    public int getArraySize(){
        return this.partidas.size();
    }

    public Partida getPartidaByID(int idBuscado){
        return partidas.get(idBuscado-1);
    }

    public int lerArquivo(){
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVOTXT))){
            String linha;

            // Inicialização das variaveis nulas
            int id = 0;
            LocalDate data;
            Time equipe1;
            Time equipe2;
            Boolean equipe1Presente;
            Boolean equipe2Presente;

            

            while((linha = reader.readLine()) != null){
                String[] campos = linha.split(";");

                id = Integer.parseInt(campos[0]);
                data = LocalDate.parse(campos[1]);
                equipe1 = timesInstancia.getTimeByID(Integer.parseInt(campos[2]));
                equipe2 = timesInstancia.getTimeByID(Integer.parseInt(campos[3]));
                

                String placarEquipe1String[] = campos[4].split(",");
                String placarEquipe2String[] = campos[5].split(",");

                int placarEquipe1[] = new int[placarEquipe1String.length];
                int placarEquipe2[] = new int[placarEquipe2String.length];

                for(int i = 0; i < placarEquipe1String.length; i++){
                    placarEquipe1[i] = Integer.parseInt(placarEquipe1String[i]);
                    placarEquipe2[i] = Integer.parseInt(placarEquipe2String[i]);
                }

                equipe1Presente = Boolean.parseBoolean(campos[6]);
                equipe2Presente = Boolean.parseBoolean(campos[7]);

                Partida partidaAUX = new Partida(id, data, equipe1, equipe2, equipe1Presente, equipe2Presente, placarEquipe1, placarEquipe2);
                partidas.add(partidaAUX);
                equipe1.addPartida(partidaAUX);
                equipe2.addPartida(partidaAUX);
            }
            
            reader.close();
            return 0;
        } catch (Exception e) {
            System.out.println("Nn foi possivel abrir arquivo Partida");
            return 1;
        }
    }
    
    public void imprimirPartidasDaEdicao(int anoDesejado){
        for(Partida partida:this.partidas){
            if(partida.getYear() == anoDesejado){
                System.out.println(partida.getTime1().getNome() + ": " + partida.getPontosTime1() + " X " + partida.getTime2().getNome() + ": " + partida.getPontosTime2());
            }
        }
    }

    public int escreverArquivo(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVOTXT,false))) {
            for(Partida partida : this.partidas){
                writer.write(partida + "\n");
            }
            writer.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}