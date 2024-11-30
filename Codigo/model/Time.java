package model;
import java.util.ArrayList;

public class Time{
    private String nome;
    private int id;
    private static int nextID;
    public ArrayList<Partida> partidas;

    static{
        nextID = 1;
    }


    //Construtor da classe Time pela CLI
    public Time(String nome){
        this.nome = nome;
        this.id = nextID++;
        this.partidas = new ArrayList<Partida>();
    }
    
    //Construtor para leitura de arquivo
    public Time(int id, String nome){
        this.nome = nome;
        this.id = id;
        nextID++;
        this.partidas = new ArrayList<Partida>();
    }

    //Metodos
    public int getID(){
        return this.id;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public int numeroDeVitoriasNaEdicao(int anoDesejado){
        int vitoria = 0;
        for(Partida partida : this.partidas){
            if(partida.getYear() == anoDesejado){
                if(partida.getVencedor() == this){
                    vitoria++;
                }
            }
        }
        return vitoria;
    }

    public int numeroDeDerrotasNaEdicao(int anoDesejado){
        int derrota = 0;
        for(Partida partida : this.partidas){
            if(partida.getYear() == anoDesejado){
                if(partida.getVencedor() != this){
                    derrota++;
                }
            }
        }
        return derrota;
    }

    public int totalPontosFavorNaEdicao(int anoDesejado){
        int pontosFavor = 0;
        for(Partida partida : this.partidas){
            if(partida.getYear() == anoDesejado){
                if(partida.getTime1() == this){
                    pontosFavor += partida.getPontosTime1();
                }else if(partida.getTime2() == this){
                    pontosFavor += partida.getPontosTime2();
                }
            }
        }
        return pontosFavor;
    }

    public void addPartida(Partida partida){
        this.partidas.add(partida);
    }

    public int totalPontosContraNaEdicao(int anoDesejado){
       int pontosContra = 0;
        for(Partida partida : this.partidas){
            if(partida.getYear() == anoDesejado){
                if(partida.getTime1() == this){
                    pontosContra += partida.getPontosTime2();
                }else if(partida.getTime2() == this){
                    pontosContra += partida.getPontosTime1();
                }
            }
        }
        return pontosContra;
    }

    public int getWOsDaEdicao(int anoDesejado){
        int wo = 0;
        for(Partida partida:partidas){
            if(partida.getYear() == anoDesejado){
                if(partida.getTime1() == this){
                    if(!partida.getEquipe1Presente()) wo++;
                }else if(partida.getTime2() == this){
                    if(!partida.getEquipe2Presente()) wo++;
                }
            }
        }
        return wo;
    }

    public int totalDePontosNaCompeticao(int anoDaEdicao){
        int pontos = 0;
        for(Partida partida:partidas){
            if(partida.getYear() == anoDaEdicao){
                if(partida.getVencedor() == this){
                    pontos += 2;
                }else{
                    pontos++;
                }
            }
        }
        return pontos;
    }
    
    public int getQuantidadeDeJogosNaEdicao(int anoDaEdicao){
        int jogos = 0;
        for(Partida partida:this.partidas){
            if(partida.getYear() == anoDaEdicao){
                jogos++;
            }
        }
        return jogos;
    }
    
    @Override
    public String toString(){
        return this.id + ";" + this.nome;
    }

}
