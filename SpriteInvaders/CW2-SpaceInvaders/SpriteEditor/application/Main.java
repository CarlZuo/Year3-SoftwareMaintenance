package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Sprite Editor");				//set the title of primary stage
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root);						//new a scene and size will follow the root
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Parent content = FXMLLoader.load(getClass().getClassLoader().getResource("Sprite.fxml"));//load the fxml file
			root.setCenter(content);
			
			primaryStage.setScene(scene);						//show the primary stage
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {					//start the program
		launch(args);
	}
}
