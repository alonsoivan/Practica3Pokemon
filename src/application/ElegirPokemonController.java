package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ElegirPokemonController implements Initializable{

	private Stage secondStage;
	
	@FXML
	public GridPane gridPanePokemons;

	@FXML
	private Button btSiguiente;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert gridPanePokemons != null : "Crash: gridPanePokemons no encontrado.";
	}
	
	@FXML
	public void abrirCombate(ActionEvent event) {
		if(Main.pkm == null)
			mostrarAlertaPokemon();
		else{
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CombatePokemon.fxml"));
				AnchorPane ap = (AnchorPane)loader.load();        	
				secondStage = new Stage();
				secondStage.setScene(new Scene(ap, 500, 500));
				secondStage.show();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mostrarAlertaPokemon() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(null);
		alert.setContentText("¡DEBES SELECCIONAR UN POKEMON!");

		alert.showAndWait();
	}
}
