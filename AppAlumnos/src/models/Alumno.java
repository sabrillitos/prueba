package models;

public class Alumno implements SerializableCSV {
    private String nombre;
    private String apellido;
    private int legajo;

    public Alumno(String nombre, String apellido, int legajo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.legajo = legajo;
    }
    
    public Alumno(){
        
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alumno{");
        sb.append("nombre=").append(nombre);
        sb.append(", apellido=").append(apellido);
        sb.append(", legajo=").append(legajo);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alumno other = (Alumno) obj;
        return this.legajo == other.legajo;
    }

    @Override
    public String toCSV() {
        return String.join(",", this.nombre, this.apellido, String.valueOf(this.legajo));
    }

    @Override
    public SerializableCSV fromCSV(String linea) {
        String[] alumnoPartes = linea.split(",");
        String nom = alumnoPartes[0];
        String apell = alumnoPartes[1];
        int lega = Integer.parseInt(alumnoPartes[2]);
        
        Alumno alumno = new Alumno(nom,apell,lega);
        return alumno;
        
    }

}
