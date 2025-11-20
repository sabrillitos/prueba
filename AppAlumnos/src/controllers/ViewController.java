package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Alumno;
import models.SerializableCSV;
import models.ServiciosCSV;

public class ViewController implements Initializable {
    // Aca van las configuraciones del controlador
    
    @FXML //lO INYECTA EN EL FXML
    private ListView<Alumno> listViewAlumnos;
    
    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnEliminar;
        
    @FXML
    private Button btnModificar;
    
    private List<Alumno> listAlumnos; // el CRUD lo hago sobre la lista. despues refresco el control con este atributo.
                                      // (cuando la lista esté actualizada la paso al ListView)
    
    private ServiciosCSV<SerializableCSV> servicios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.listAlumnos = new ArrayList<>(); // me aseguro de que nunca rompa
        this.servicios = new ServiciosCSV<>();  // mismo que arriba, sino, no andan los servicios
        this.cargarDatos();
        
    }
    
    // Referencio los metodos que puse en el SceneBuilder sobre cada control (#agregar, #modificar, etc)
    
    @FXML
    private void agregar(ActionEvent a){
        
        this.abrirFormulario(null); // Ahora para trabajar el agregar, deberia trabajar en otro controlador -> necesito obtener el alumno que cargo en mi formulario
    }
    
    // 2 formas de abrir un formulario : 1. para AGREGAR un objeto con los campos vacios 2. para MODIFICAR un objeto ya existente con campos a modificar
    private void abrirFormulario(Alumno alumnoExistente){ // si me llega un alumno con != null -> ya se que es para MODIFICAR
                                                          // si me llega un alumno con == null -> ya se que es para AGREGAR
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formulario.fxml"));
            // Creo un objeto de esta clase (FXMLLoader) para poder usar los metodos del controlador (obtener el controlador del formulario) 
            
            Scene scene = new Scene(loader.load());
            
            FormularioController controller = loader.getController(); // Aca obtengo el controlador (FormualrioController al igual que la clase del formulario)
            
            controller.setAlumno(alumnoExistente); // si existe un alumno para MODIFICAR, lo carga en el formulario con sus datos existentes
            
            Stage stage = new Stage();
            
            // Mostrarlo de forma modal -> ventana modal / emergente (no puedo volver a la ventana anterior si no resuelvo la ventana modal)
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait(); // SHOWANDWAIT -> MUESTRO LA VENTANA Y ESPERO QUE PASE ALGO
            
            //Ahora me aseguro de traerme el alumno
            Alumno resultado = controller.getAlumno(); // Recordar que controller es el nombre que le puse a un objeto de tipo FormularioController
            
            if(resultado != null){ // si es que me traje un alumno via getAlumno()
                
                if(alumnoExistente == null){ // quiere decir que estoy agregando uno nuevo
                    
                    if(!this.listAlumnos.contains(resultado)){ // si la lista de alumnos NO contiene ya a el nuevo alumno, recien ahi lo agrego
                        
                        this.listAlumnos.add(resultado); // ahora que guarde esto tengo que actualizar el control (el listView)
                    }
                } 
                
                this.actualizarListView(); // actualizo la lista
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void modificar(ActionEvent a){
        
        Alumno seleccionado = this.listViewAlumnos.getSelectionModel().getSelectedItem(); // Esto me devuelve el alumno que estoy seleccionando
                                                                                          // en la lista. Si NO selecciono nada -> devuelve NULL
        if (seleccionado != null){
            this.abrirFormulario(seleccionado);
        }
                                                                                          
    }
    
    @FXML
    private void eliminar(ActionEvent a){ // si no quiero hacer el alert, deberia mostrar el formulario con TODOS los campos no editables (es otra opcion) 
                                          // En este caso lo hice con alert.                                   
        
        Alumno seleccionado = this.listViewAlumnos.getSelectionModel().getSelectedItem(); // Esto me devuelve el alumno que estoy seleccionando
                                                                                          // en la lista. Si NO selecciono nada -> devuelve NULL
        if(seleccionado != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminacion"); //titulo
            alert.setHeaderText("Estas seguro que desea eliminar al alumno " + seleccionado.getNombre()
            ); // encabezado de la alerta
            alert.setContentText(seleccionado.toString()); // contenido de la alerta  
            
            // Aca creo un boton de confirmacion para saber si el usuario quiere borrar el alumno
            Optional<ButtonType> resultado = alert.showAndWait(); // show and wait devuelve un Optional<T> (generico)
            
            if(resultado.isPresent() && resultado.get() == ButtonType.OK){ // el .isPresent() te avisa si resultado tiene un valor
                                                                           // el .get() te da el valor de resultado
                this.listAlumnos.remove(seleccionado);
                this.actualizarListView();
            }
            
        }
        
    }
    
    private void actualizarListView(){
        //Antes de actualizar la lista visual, guardo los nuevos datos en mi archivo
        this.guardarDatos();
        this.listViewAlumnos.getItems().clear(); // vacio la lista del control
        this.listViewAlumnos.getItems().addAll(this.listAlumnos); // cargo de nuevo toda la lista de alumnos
    }
    
    public void guardarDatos(){
        List<SerializableCSV> datos = new ArrayList<>();
        for (Alumno item : this.listAlumnos){
            if (item instanceof SerializableCSV serializable){
                datos.add(serializable); // tengo una lista de serializable -> falta llamar al servicioCSV para guardar los datos
            }                            
        }
        
        try {
            this.servicios.guardar("Alumnos.csv", datos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void cargarDatos(){
        List<Alumno> alumnos = new ArrayList<>();
        
        try {
            List<String> lineas = this.servicios.cargar("Alumnos.csv");
            
            for (String linea : lineas){
                Alumno alumno = new Alumno();
                if (alumno instanceof SerializableCSV serializable){
                    Alumno alumnoAuxiliar = (Alumno) serializable.fromCSV(linea);
                    alumnos.add(alumnoAuxiliar);
                }
            }
            
            this.listAlumnos = alumnos;
            this.actualizarListView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
