package models;


public interface SerializableCSV {
    
    String toCSV(); // no hace falta poner public, la interface ya es PUBLICA POR DEFECTO

    SerializableCSV fromCSV(String linea);
}
