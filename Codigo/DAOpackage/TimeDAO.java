package DAOpackage;

import model.*;
import java.io.*;
import java.util.ArrayList;


public class TimeDAO {
	private static final String ARQUIVOTXT = "codigo/data/Time.txt";
	private static TimeDAO instancia;
	private ArrayList<Time> times;

	private TimeDAO(){
		times = new ArrayList<Time>();
		lerArquivo();
	}

	public static TimeDAO getInstancia(){
		if(instancia == null){
			instancia = new TimeDAO();
			return instancia;
		}
		return instancia;
	}

	public void addTime(Time time){
		this.times.add(time);
	}

	public Time getTimeByID(int idBuscado){
		for(Time time:times){
			if(time.getID() == idBuscado){
				return time;
			}
		}
		return null;
	}

	public void imprimirTimes(){
		for(Time time:times){
			System.out.println("ID: " + time.getID() + " - " + time.getNome());
		}
	}

	public int lerArquivo() {
		try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVOTXT))) {
			String linha;

			int id = 0;
			String nome = "";
			
			while((linha = reader.readLine()) != null){
				String[] campos = linha.split(";");
				id = Integer.parseInt(campos[0]);
				nome = campos[1];
				times.add(new Time(id,nome));
			}

			
			reader.close();
			return 0;
		} catch (Exception e) {
			System.out.println("Nn foi possível abrir o arquivo");
			return 1;
		}
	}
	
	public int escreverArquivo() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVOTXT, false))){
			for(Time time : times){
				writer.write(time + "\n");
			}
			writer.close();
			return 0;
		} catch (IOException e) { 
			e.printStackTrace();
			return 1;
		}
	}
}
