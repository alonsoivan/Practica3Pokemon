package application;
	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
	
public class Main extends Application {
		
	@Override	
	public void start(Stage primaryStage) throws FileNotFoundException {
		ArrayList<Pokemon> listaPokemons = new ArrayList<Pokemon>();
		int numPokemons = new Random().nextInt(94)+7;
		
		listaPokemons.addAll(ElegirPokemonController.generarPokemons(numPokemons));
		
		try {
			initMenuPokemon(primaryStage,listaPokemons);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initMenuPokemon(Stage primaryStage,ArrayList<Pokemon> listaPokemons) throws IOException {		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ElegirPokemon.fxml"));
		BorderPane root = (BorderPane)loader.load();
		ElegirPokemonController elegirPokemonController = (ElegirPokemonController)loader.getController();			
		
		elegirPokemonController.initPokemons(elegirPokemonController.gridPanePokemons, listaPokemons);
		Scene scene = new Scene(root,750,750);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
