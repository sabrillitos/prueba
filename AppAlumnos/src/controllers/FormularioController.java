package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Alumno;

public class FormularioController implements Initializable {
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnConfirmar;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtLegajo;
    
    @FXML
    private TextField txtApellido;
    
    private Alumno alumno; // SERA EL ALUMNO QUE VOY A ESTAR AGREGANDO O MODIFICANDO
    
    private boolean confirmado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void confirmar(){
        
        String nombre = this.txtNombre.getText();
        String apellido = this.txtApellido.getText();
        String legajo = this.txtLegajo.getText();
        int legajoInt = Integer.parseInt(legajo);
        
        if(legajo.isEmpty() || nombre.isEmpty() || apellido.isEmpty()){ //ACA HAY QUE METER UNA BUENA EXCEPCION
            System.out.println("Campos obligatorios");
            return;
        }
        
        // Cuando yo quiero agregar, ALUMNO DEBE SER NULO
        if (alumno == null){
            alumno = new Alumno(nombre,apellido,legajoInt);
        }else{ // Si alumno NO ERA NULO, ES PORQUE ESTOY INTENTANDO MODIFICAR
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            //alumno.setLegajo(legajoInt);
        }
        
        this.confirmado = true; // asi, si está confirmado devuelve al alumno, sino, no devuelve nada el getAlumno()
        this.cerrarVentana();
        
    }
    
    @FXML
    private void cancelar(){
        
        this.cerrarVentana();
        
    }
    
    private void cerrarVentana(){
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow(); // obtengo el stage donde está el boton cancelar  
                                                                  // lo casteo a Stage porque devuelve un Window
        stage.close(); // cierro la ventana
        
    }
    
    public Alumno getAlumno(){
        return this.confirmado ? this.alumno : null; // Si está confirmado retorno el alumno, sino retorno null
    }
    
    public void setAlumno(Alumno alumno){
        this.alumno = alumno;
        if(alumno != null){
            this.txtApellido.setText(alumno.getApellido());
            this.txtNombre.setText(alumno.getNombre());
            this.txtLegajo.setText(String.valueOf(alumno.getLegajo())); // para que muestre como String el valor del legajo
            this.txtLegajo.setDisable(true); // hace que no se pueda editar el campo del legajo, debe ser unico
        }
    }
}
