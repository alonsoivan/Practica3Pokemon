package application;

import java.util.Optional;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class CombatePokemonController {
	@FXML
	private Button btAtaqueSeguro;
	@FXML
	private Button btAtaqueArriesgado;
	@FXML
	private Button btAtaqueMuyArriesgado;
	@FXML
	private Button btCancelar;


	@FXML
	private Label nombreCombate;
	@FXML
	private ImageView ivCombate;
	@FXML
	private ProgressBar psAliado;
	@FXML
	private ProgressBar psEnemigo;


	@FXML
	private Label psLabelEnemigo;
	@FXML
	private Label psLabelAliado;


	@FXML
	private Pane panelCursarseOAtacar;
	@FXML
	private Pane panelMensaje;
	@FXML
	private Pane panelAtaques;
	
	@FXML 
	public void curarse(ActionEvent event) {
		if(((Node)event.getSource()).getId().equals("btCurarse")) {
			
			float ale = (new Random().nextInt(51)+25);
			
			float vida = (float) (ElegirPokemonController.pkm.getVida()+ale);
			
			if (vida>100)
				vida = 100;
			
			ElegirPokemonController.pkm.setVida(vida);
			psAliado.setProgress(vida/100f);
			
			enemigoAtaca();  
		}
	}
	
	@FXML
	public void ataques(ActionEvent event) {	
		float ale = 0;
		
		if(event.getSource() == btAtaqueSeguro) {
			ale = 20;
		}else if(event.getSource() == btAtaqueArriesgado) {
			ale = (new Random().nextInt(16)+10);
		}else if(event.getSource() == btAtaqueMuyArriesgado) {
			ale = (new Random().nextInt(51));
		}else if(event.getSource() == btCancelar) {
			panelAtaques.setVisible(false);
			panelCursarseOAtacar.setVisible(true);
		}
		
		float vida = (float) (psEnemigo.getProgress()-(ale/100));
		
		if(vida<=0.001) {
			psEnemigo.setProgress(0);
		}else
			psEnemigo.setProgress(vida);
		
		if(event.getSource() != btCancelar) 
			enemigoAtaca();
	}
	
	public void enemigoAtaca() {
		ElegirPokemonController.pkm.setVida((ElegirPokemonController.pkm.getVida()-20));
		
		psAliado.setProgress(ElegirPokemonController.pkm.getVida()/100f);
		if (psAliado.getProgress()<=0) {
			psAliado.setProgress(0);
			ElegirPokemonController.pkm.setVida(0);
			mostrarAlerta("Tu pokemon se debilitó","penita pena",ivCombate);
		}
	}
	
	@FXML  
	public void atacar(ActionEvent event) {		
		if(((Node) event.getSource()).getId().equals("btAtacar")) {
			
			btAtaqueSeguro.setText(ElegirPokemonController.pkm.getSeguro());
			btAtaqueArriesgado.setText(ElegirPokemonController.pkm.getMedio());
			btAtaqueMuyArriesgado.setText(ElegirPokemonController.pkm.getArriesgado());
			
			panelAtaques.setVisible(true);
			panelCursarseOAtacar.setVisible(false);
		}
	}
	
	
	@FXML
	public void mostrarVida(MouseEvent event) {
		if(event.getSource() == psEnemigo)
			psLabelEnemigo.setText(String.valueOf(Math.round(psEnemigo.getProgress()*100))+"/100");
		else
			psLabelAliado.setText(String.valueOf(Math.round(psAliado.getProgress()*100))+"/100");
	}
	
	@FXML
	public void mostrarPS(MouseEvent event) {	
		if(event.getSource() == psEnemigo)
			psLabelEnemigo.setText("PS:");
		else
			psLabelAliado.setText("PS:");
	}
	
	@FXML
	public void clickMensaje(MouseEvent event) {		
		ivCombate.setImage(ElegirPokemonController.pkm.getImagen());
		nombreCombate.setText(ElegirPokemonController.pkm.getNombre());
		psAliado.setProgress(ElegirPokemonController.pkm.getVida()/100f);
		
		panelMensaje.setVisible(false);
		panelCursarseOAtacar.setVisible(true);
	}
	
	private void mostrarAlerta(String titulo, String content, ImageView imagenPokemon) {
		ButtonType volver = new ButtonType("Volver al menu", ButtonData.OK_DONE);
		ButtonType salir = new ButtonType("Salir", ButtonData.CANCEL_CLOSE);
		Alert alerta = new Alert(AlertType.NONE, content, salir, volver);
		
		
		alerta.setGraphic(imagenPokemon);

		alerta.setTitle(titulo);
		
		Optional<ButtonType> resultado = alerta.showAndWait();
		if(!resultado.isPresent()) {
			Stage stage = (Stage) btCancelar.getScene().getWindow();
			stage.close();
		} else if(resultado.get() == volver) {
			Stage stage = (Stage) btCancelar.getScene().getWindow();
			stage.close();
		} else if (resultado.get() == salir) {
			System.exit(0);
		}
	}

}
