package appalumnos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppAlumnos extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/view.fxml")); // Le paso la ruta donde tengo el archivo para levantar la escena
        
        Scene scene = new Scene(loader.load());
        
        stage.setScene(scene);
        stage.setTitle("App alumnos");
        stage.show();
    
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }

}
