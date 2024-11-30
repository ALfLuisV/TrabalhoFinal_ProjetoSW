package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Campeonato {
    private String nomeCampeonato;
    private ArrayList<Time> times = new ArrayList<Time>();
    private int ano;
    private int id;
    private static int nextID;

    static {
        nextID = 1;
    }

    // Construtor para leitura de arquivos
    public Campeonato(String nomeCampeonato, ArrayList<Time> times, int ano, int id) {
        this.nomeCampeonato = nomeCampeonato;
        this.times = times;
        this.ano = ano;
        this.id = id;
        nextID++;
    }

    // Construtor para cadastro via CLI
    public Campeonato(String nome, ArrayList<Time> times, int ano) {
        this.nomeCampeonato = nome;
        this.times = times;
        this.ano = ano;
        this.id = nextID++;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nomeCampeonato;
    }

    public String getAno() {
        return String.valueOf(this.ano);
    }

    public ArrayList<Time> melhorAtaque() {
        Time melhorTime = null;
        ArrayList<Time> timesEmpate = new ArrayList<Time>();
        for (Time time : times) {
            if (melhorTime == null) {
                melhorTime = time;
            }
            if (time.totalPontosFavorNaEdicao(this.ano) > melhorTime.totalPontosFavorNaEdicao(this.ano)) {
                melhorTime = time;
                timesEmpate.clear();
                timesEmpate.add(melhorTime);
            } else if (time.totalPontosFavorNaEdicao(this.ano) == melhorTime.totalPontosFavorNaEdicao(this.ano)) {
                timesEmpate.add(time);
            } 
        }
        return timesEmpate;
    }

    public ArrayList<Time> melhorDefesa() {
        Time melhorTime = null;
        ArrayList<Time> timesEmpate = new ArrayList<Time>();
        for (Time time : times) {
            if (melhorTime == null) {
                melhorTime = time;
            }
            if (time.totalPontosContraNaEdicao(this.ano) < melhorTime.totalPontosContraNaEdicao(this.ano)) {
                melhorTime = time;
                timesEmpate.clear();
                timesEmpate.add(melhorTime);
            } else if (time.totalPontosContraNaEdicao(this.ano) == melhorTime.totalPontosContraNaEdicao(this.ano)) {
                timesEmpate.add(time);
            } 
        }
        return timesEmpate;
    }

    public String gerarClassificacao() {
        int tamanhoTimes = times.size();

        for (int i = 0; i < tamanhoTimes - 1; i++) {
            for (int j = 0; j < tamanhoTimes - i - 1; j++) {
                if (times.get(j).totalDePontosNaCompeticao(this.ano) < times.get(j + 1).totalDePontosNaCompeticao(this.ano)) {
                    Time temp = times.get(j);
                    times.set(j, times.get(j + 1));
                    times.set(j + 1, temp);
                }
            }
        }
        String tabela = "";
        for(Time time:times){
            tabela += "Equipe: " + time.getNome() + " - Jogos: " + time.getQuantidadeDeJogosNaEdicao(this.ano) + " - Pontos: " + time.totalDePontosNaCompeticao(this.ano) + " - Vitorias: " + time.numeroDeVitoriasNaEdicao(this.ano) + " - Derrotas: " + time.numeroDeDerrotasNaEdicao(this.ano) + " - Total de pontos marcados: " + time.totalPontosFavorNaEdicao(this.ano) + " - Total de pontos sofridos: " + time.totalPontosContraNaEdicao(this.ano) + "\n";
        }
        return tabela;
    }

    public void imprimirTimesDaEdicao(){
        for(Time time:times){
            System.out.println(time.getID() + " - " + time.getNome());
        }
    }

    public boolean verificacaoSeTimeEstaNaEdicao(Time timeDesejado){
        return this.times.contains(timeDesejado);
    }
    @Override
    public String toString() {
        int[] idsEquipeInt = new int[this.times.size()];
        int i = 0;
        for (Time time : this.times) {
            idsEquipeInt[i] = time.getID();
            i++;
        }
        String stringIds = Arrays.toString(idsEquipeInt);
        // tirar [] no começo e fim da string
        stringIds = stringIds.substring(1, stringIds.length() - 1);
        // Tirar os espaços dps das virgulas pra ficar mais facil de ler no DAO
        stringIds = stringIds.replace(", ", ",");

        return this.id + ";" + this.nomeCampeonato + ";" + this.ano + ";" + stringIds;
    }
}