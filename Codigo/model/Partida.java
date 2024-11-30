package model;

import java.time.LocalDate;
import java.util.Arrays;

public class Partida{

    private int id;
    private LocalDate data;
    private Time equipe1;
    private Time equipe2;
    private int placarTime1[];
    private int placarTime2[];
    private Time vencedor;
    private boolean equipe1Presente;
    private boolean equipe2Presente;
    private static int nextID;

    static{
        nextID = 1;
    }

    //Construtor para o registro de partida já realizada
    public Partida(LocalDate data, Time equipe1, Time equipe2, int[] placarTime1, int[] placarTime2, boolean equipe1Presente, boolean equipe2Presente) {
        this.id = nextID++;
        this.data = data;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.placarTime1 = placarTime1;
        this.placarTime2 = placarTime2;
        this.equipe1Presente = equipe1Presente;
        this.equipe2Presente = equipe2Presente;
        if(this.equipe1Presente && this.equipe2Presente){
            this.vencedor = this.getVencedor();
        }else if(!this.equipe1Presente){
            this.vencedor = equipe2;
        }else{
            this.vencedor = equipe1;
        }
        
    }
    
    //Construtor para o registro de partida ainda não realizada
     public Partida(LocalDate data, Time equipe1, Time equipe2) {
        this.id = nextID++;
        this.data = data;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.placarTime1 = new int[4];
        this.placarTime2 = new int[4];
        this.equipe1Presente = false;
        this.equipe2Presente = false;
    }

    //Construtor para leitura de arquivo
    public Partida(int id, LocalDate data, Time time1, Time time2, Boolean presente1, Boolean presente2, int[] placar1, int[] placar2){
        this.id = id;
        nextID++;
        this.data = data;
        this.equipe1 = time1;
        this.equipe2 = time2;
        this.equipe1Presente = presente1;
        this.equipe2Presente = presente2;
        this.placarTime1 = placar1;
        this.placarTime2 = placar2;
        if(this.equipe1Presente && this.equipe2Presente){
            this.vencedor = this.getVencedor();
        }else if(!this.equipe1Presente && this.equipe2Presente){
            this.vencedor = equipe2;
        }else if(this.equipe1Presente && !this.equipe2Presente){
            this.vencedor = equipe1;
        }else{
            this.vencedor = null;
        }
    }

    public int getID(){
        return this.id;
    }

    public void setPlacar(int placar1[], int placar2[]){
        this.placarTime1 = placar1;
        this.placarTime2 = placar2;
    }

    public boolean getEquipe1Presente(){
        return this.equipe1Presente;
    }

    public boolean getEquipe2Presente(){
        return this.equipe2Presente;
    }
    public Time getTime1() {
        return this.equipe1;
    }

    public Time getTime2() {
        return this.equipe2;
    }

    public int getPontosTime1() {
        int pontos = 0;
        if(placarTime1.length>0){
            for(int i = 0; i<placarTime1.length;i++){
                pontos += placarTime1[i];
            }
            return pontos;
        } 
        return -1;
    }

    public int getPontosTime2() {
        int pontos = 0; 
        if(placarTime2.length>0){
            for(int i = 0; i<placarTime2.length;i++){
                pontos += placarTime2[i];
            }
            return pontos;
        }
        return -1;
    }
    
    public LocalDate getData(){
        return this.data;
    }

    public Time getVencedor(){
        if(!equipe1Presente && !equipe2Presente){
            return null;
        }
        if(!equipe1Presente && equipe2Presente){
            vencedor = equipe2;
            return vencedor;
        }else if(equipe1Presente && !equipe2Presente){
            vencedor = equipe1;
            return vencedor;
        }
        if(vencedor == null){
            int somaPontos1 = getPontosTime1(); 
            int somaPontos2 = getPontosTime2();
            
            if(somaPontos1 > somaPontos2){
                vencedor = equipe1;
            }else if(somaPontos1 < somaPontos2){
                vencedor = equipe2;
            }
            return vencedor;
        }else{
            return vencedor;
        }
    }

    public void setEquipe1presente(Boolean bool){
        this.equipe1Presente = bool;
    }

    public void setEquipe2presente(Boolean bool){
        this.equipe2Presente = bool;
    }
    
    public void registrarResultado(boolean equipe1Presente, boolean equipe2Presente, int placarTime1[], int placarTime2[]){
        this.equipe1Presente = equipe1Presente;
        this.equipe2Presente = equipe2Presente;
        if(this.equipe1Presente && this.equipe2Presente){
            this.placarTime1 = placarTime1;
            this.placarTime2 = placarTime2;

            this.getVencedor();

        }else if(this.equipe1Presente && !this.equipe2Presente){
            this.vencedor = equipe1;
        }else if(!this.equipe1Presente && this.equipe2Presente){
            this.vencedor = equipe2;
        }else{
            this.vencedor = null;
        }
    }

    public String exibirPlacar(){
        return this.getTime2().getNome() + ": " + this.getPontosTime1() + "X" + getTime2().getNome() + ": " + getPontosTime2();
    }

    public int getYear(){
        return this.data.getYear();
    }

    @Override
    public String toString(){
        String stringPlacar1 = Arrays.toString(placarTime1);
        stringPlacar1 = stringPlacar1.substring(1, stringPlacar1.length()-1);
        stringPlacar1 = stringPlacar1.replace(", ", ",");

        String stringPlacar2 = Arrays.toString(placarTime2);
        stringPlacar2 = stringPlacar2.substring(1, stringPlacar2.length()-1);
        stringPlacar2 = stringPlacar2.replace(", ", ",");
        
        return this.id + ";" + this.data + ";" + this.equipe1.getID() + ";" + this.equipe2.getID() + ";" + stringPlacar1 + ";" + stringPlacar2 + ";" + equipe1Presente + ";" + equipe2Presente ;
    }
}