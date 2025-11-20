package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServiciosCSV <T extends SerializableCSV>{
    
    public void guardar(String nombre_archivo, List<T> objetos){ 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombre_archivo))){
            for (T objeto : objetos){
                bw.write(objeto.toCSV());
                bw.newLine();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        
    }
    
    public List<String> cargar(String nombre_archivo){ 
        List<String> listaString = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombre_archivo))){
            String linea;
            while((linea = br.readLine()) != null){
                listaString.add(linea);
            }
        } catch (Exception e){
            e.getMessage();
        }
        
        return listaString;
    }
}
