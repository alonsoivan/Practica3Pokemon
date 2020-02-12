package application;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


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
			
			float vida = (float) (Main.pkm.getVida()+ale);
			
			if (vida>100)
				vida = 100;
			
			Main.pkm.setVida(vida);
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
		Main.pkm.setVida((Main.pkm.getVida()-20));
		
		psAliado.setProgress(Main.pkm.getVida()/100f);
		if (psAliado.getProgress()<=0) {
			psAliado.setProgress(0);
			Main.pkm.setVida(0);
		}
	}
	
	@FXML  
	public void atacar(ActionEvent event) {		
		if(((Node) event.getSource()).getId().equals("btAtacar")) {
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
		ivCombate.setImage(Main.pkm.getImagen());
		nombreCombate.setText(Main.pkm.getNombre());
		psAliado.setProgress(Main.pkm.getVida()/100f);
		
		panelMensaje.setVisible(false);
		panelCursarseOAtacar.setVisible(true);
	}

}
