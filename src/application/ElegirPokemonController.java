package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;	
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ElegirPokemonController implements Initializable{
	static String[] nombres = new String[] {"Charmander","Diglett","Pikachu","Psyduck","Squirtle","Vulpix"};
	static Pokemon pkm;
	static ArrayList<PokemonBorderPane> paneles = new ArrayList<PokemonBorderPane>();

	private Stage secondStage;
	
	@FXML
	public GridPane gridPanePokemons;

	@FXML
	private Button btSiguiente;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert gridPanePokemons != null : "Crash: gridPanePokemons no encontrado.";
	}
	
	public void initPokemons(GridPane pane,ArrayList<Pokemon> listaPokemons) {
		
		int maxColumns = listaPokemons.size()/3;
		int maxRows = 3;
		
		int currentColumn = 0;
		int currentRow = 0;
		
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		int cont = 0;
		for (currentColumn = 0; currentColumn < maxColumns; currentColumn++) {
			for (currentRow = 0; currentRow < maxRows; currentRow++) {
				PokemonBorderPane borderPane = new PokemonBorderPane(listaPokemons.get(cont));
				borderPane.setId("");
				ImageView img = new ImageView(listaPokemons.get(cont).getImagen());
				img.setFitHeight(247);
				img.setFitWidth(247);
				borderPane.setCenter(img);
				FlowPane fpNombreNivel = new FlowPane();
				String nombre =  String.format(" %-12s",listaPokemons.get(cont).getNombre());
				Label lbNombre = new Label(nombre);
				String nivel =  String.format("%5s"," Nv "+listaPokemons.get(cont).getNivel());
				Label lbNivel = new Label(nivel);
				lbNombre.setFont(new Font("Agency FB", 25));
				lbNombre.setTextFill(Color.web("#000000"));
				lbNivel.setFont(new Font("Agency FB", 25));
				lbNivel.setTextFill(Color.web("#000000"));
				
				EventHandler<MouseEvent> onClickListenerNombre = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						
						if(event.getClickCount() == 2) {
							FlowPane fp = (FlowPane)event.getSource();
						
							Label lbNombre = (Label)fp.getChildren().get(0);
							
							cambiarNombrePokemons(listaPokemons,paneles);
							
							mostrarAlertaNivel();
						}
					}
		        };
		        
		        fpNombreNivel.setOnMouseClicked(onClickListenerNombre);
				fpNombreNivel.getChildren().add(lbNombre);
				fpNombreNivel.getChildren().add(lbNivel);
				borderPane.setTop(fpNombreNivel);
				ProgressBar pb = new ProgressBar(listaPokemons.get(cont).getVida()/100.0);
				pb.setPrefSize(180, 10);
				
				paneles.add(borderPane);
				
				// Creamos un MouseEvent que será ejecutado cuando se haga click sobre el borderPane de cada pokemon
				EventHandler<MouseEvent> onClickListenerPane = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						
						PokemonBorderPane pokemonBorderPane = (PokemonBorderPane)event.getSource();
						cambiarEstilo(paneles);
						pkm = pokemonBorderPane.getPokemon();
						pokemonBorderPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #b9b901, #dfdf00);");
						
						//System.out.println("Soy el Pokemon " + pokemonBorderPane.getPokemon().getNombre());
					}
		        };
		        
		        // Añadimos el listener
		        borderPane.setOnMouseClicked(onClickListenerPane);
				
				FlowPane fp = new FlowPane();
				Label lbPs = new Label(" PS: ");
				lbPs.setFont(new Font("Agency FB", 25));
				lbPs.setTextFill(Color.web("#000000"));
				fp.getChildren().add(lbPs);
				fp.getChildren().add(pb);
				
				borderPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #661a33)");
				borderPane.setBottom(fp);
				//borderPane.autosize();
				borderPane.setPrefSize(215, 200);
				pane.add(borderPane,  currentRow, currentColumn);
				
				cont++;
			}
		}
	}
	
	public void mostrarAlertaNivel() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("SUBIDA DE NIVEL");
		alert.setHeaderText(null);
		alert.setContentText("¡NIVELES++!");

		alert.showAndWait();
	}
	
	public void cambiarNombrePokemons(ArrayList<Pokemon> listaPokemons,ArrayList<PokemonBorderPane> paneles){
		for (Pokemon pk : listaPokemons)
			pk.setNivel(pk.getNivel()+1);
		
		cambiarLabels(paneles);
	}
	
	public void cambiarLabels(ArrayList<PokemonBorderPane> paneles) {
		for (PokemonBorderPane panel : paneles) {
			FlowPane fp = (FlowPane)panel.getChildren().get(1);
			Label lb = (Label) fp.getChildren().get(1);
	
			String nivel =  String.format("%5s"," Nv "+String.valueOf(panel.getPokemon().getNivel()));
			lb.setText(nivel);
		}
	}	
	
	public static void actualizarProgressBar() {
		for (PokemonBorderPane panel : paneles) {
			FlowPane fp = (FlowPane)panel.getChildren().get(2);
			ProgressBar pb = (ProgressBar) fp.getChildren().get(1);
			
			pb.setProgress(panel.getPokemon().getVida()/100);
		}
	}	
	
	public void cambiarEstilo(ArrayList<PokemonBorderPane> paneles) {
		for(PokemonBorderPane bp : paneles)
			bp.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #661a33)");
	}
	
	public List<String> getNombres(ArrayList<Pokemon> listaPokemons) {
		ArrayList<String> nombres = new ArrayList<String>();
		for (Pokemon pk : listaPokemons)
			nombres.add(pk.getNombre());
		
		return nombres;
	}
	
	public static ArrayList<Pokemon> generarPokemons(int numPokemons){
		ArrayList<Pokemon> aux = new ArrayList<Pokemon>();
		
		try {
			for (int i = 0; i<numPokemons ;i++)
				aux.add(new Pokemon(nombres[i%6]+(i+1),getNivel(),getVida(),getImagen(i),i+1));
			
		} catch (FileNotFoundException e) {e.printStackTrace();}
			
		return aux;
	}
	
	public static Image getImagen(int i) throws FileNotFoundException {
		return new Image(new FileInputStream("imagenes\\"+nombres[i%6]+".png"));
	}
	
	public static int getNivel() {
		return new Random().nextInt(99)+1;
	}
	
	public static int getVida() {
		return new Random().nextInt(100)+1;
	}
	
	@FXML
	public void abrirCombate(ActionEvent event) {
		if(pkm == null)
			mostrarAlertaSeleccionePokemon();
		else{
			if(pkm.getVida() <= 3)  // Limito a 3 porque de 1 a 3 de vida no lo muestra la progressbar y parece que no tiene vida.
				mostrarAlertaPokemonSinVida();
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
	}
	
	public void mostrarAlertaPokemonSinVida() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(null);
		alert.setContentText("¡ELIGE UN POKÉMON QUE TENGA VIDA!");
	
		alert.showAndWait();
	}
	
	public void mostrarAlertaSeleccionePokemon() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(null);
		alert.setContentText("¡DEBES SELECCIONAR UN POKEMON!");

		alert.show();
	}
}
