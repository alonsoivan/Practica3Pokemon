package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class ElegirPokemonController implements Initializable{

	@FXML
	public GridPane gridPanePokemons;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert gridPanePokemons != null : "Crash: gridPanePokemons no encontrado.";
	}
}
