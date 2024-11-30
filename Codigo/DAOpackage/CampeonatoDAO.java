package DAOpackage;

import model.*;
import java.io.*;
import java.util.ArrayList;

public class CampeonatoDAO {
  private final static String ARQUIVOTXT = "codigo/data/Campeonato.txt";
  private static CampeonatoDAO instancia;
  private ArrayList<Campeonato> campeonatos;
  private TimeDAO timesInstancia;

  private CampeonatoDAO() {
    campeonatos = new ArrayList<Campeonato>();
    timesInstancia = TimeDAO.getInstancia();
    lerArquivo();
  }

  public static CampeonatoDAO getInstancia() {
    if (instancia == null) {
      instancia = new CampeonatoDAO();
      return instancia;
    }
    return instancia;
  }

  public boolean campeonatoExiste(int anoDesejado){
    Boolean existe = false;
    for(Campeonato campeonato:campeonatos){
      if(Integer.parseInt(campeonato.getAno()) == anoDesejado) {
        existe = true;
      }
    }
    return existe;
  }
  public void addCampeonato(Campeonato campeonato){
    this.campeonatos.add(campeonato);
  }

  public Campeonato getCampeonatoByID(int idBuscado){
    for(Campeonato campeonato : campeonatos){
      if(campeonato.getId() == idBuscado){
        return campeonato;
      }
    }
    return null;
  }

  public void imprimirCampeonatos(){
    for(Campeonato campeonato: campeonatos){
      System.out.println("ID: " + campeonato.getId() + " - " + campeonato.getNome() + " - " + campeonato.getAno());
    }
  }

  public Campeonato getCampeonatoByYear(int anoBuscado){
    for(Campeonato campeonato:campeonatos){
      if(Integer.parseInt(campeonato.getAno()) == anoBuscado){
        return campeonato;
      }
    }
    return null;
  }

  public int lerArquivo() {
    try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVOTXT))) {
      String linha;

      // Inicialização das variáveis nulas para preenchimento durante a leitura
      int id = 0;
      int ano = 0;
      String nomeCamp = "";
      

      while ((linha = reader.readLine()) != null) {
        ArrayList<Time> timesNoCampeonato = new ArrayList<Time>();
        // Dividir a linha em campos usando "\t" pra separar
        String[] campos = linha.split(";");
        id = Integer.parseInt(campos[0]);
        nomeCamp = campos[1];
        ano = Integer.parseInt(campos[2]);

        String[] numEquipes = campos[3].split(",");
        int[] arrayDeIds = new int[numEquipes.length];

        for (int i = 0; i < arrayDeIds.length; i++) {
          arrayDeIds[i] = Integer.parseInt(numEquipes[i]);
        }

        for(int i = 0; i < arrayDeIds.length; i++){
          timesNoCampeonato.add(timesInstancia.getTimeByID(arrayDeIds[i]));
        }
        
        this.campeonatos.add(new Campeonato(nomeCamp, timesNoCampeonato, ano, id));
      }
      reader.close();
      return 0;
    } catch (Exception e) {
      System.out.println("Nn foi possivel abrir arquivo Campeonato");
      return 1;
    }
  }

  public int escreverArquivo() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVOTXT, false))) {
      for (Campeonato campeonato : this.campeonatos) {
        writer.write(campeonato + "\n");
      }
      writer.close();
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }
  }
}
