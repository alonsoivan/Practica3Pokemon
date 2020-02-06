package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	static String[] nombres = new String[] {"Charmander","Diglett","Pikachu","Psyduck","Squirtle","Vulpix"};
	static Pokemon pkm;
		
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		ArrayList<Pokemon> listaPokemons = new ArrayList<Pokemon>();
		int numPokemons = new Random().nextInt(94)+7;
		
		listaPokemons.addAll(generarPokemons(numPokemons));
		
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
		
		initPokemons(elegirPokemonController.gridPanePokemons, listaPokemons);
		Scene scene = new Scene(root,750,700);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void initPokemons(GridPane pane,ArrayList<Pokemon> listaPokemons) {
		ArrayList<PokemonBorderPane> paneles = new ArrayList<PokemonBorderPane>();
		
		int maxColumns = 3;
		int maxRows = listaPokemons.size()/3;
		
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
				Label lbNombre = new Label(" "+listaPokemons.get(cont).getNombre());
				Label lbNivel = new Label(" Nv " +  listaPokemons.get(cont).getNivel());
				lbNombre.setFont(new Font("Agency FB", 25));
				lbNombre.setTextFill(Color.web("#0076a3"));
				lbNivel.setFont(new Font("Agency FB", 25));
				lbNivel.setTextFill(Color.web("#0076a3"));
				EventHandler<MouseEvent> onClickListenerNombre = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						
						if(event.getClickCount() == 2) {
							FlowPane fp = (FlowPane)event.getSource();
						
							Label lbNombre = (Label)fp.getChildren().get(0);
							
							cambiarNombrePokemons(listaPokemons,paneles);
								
							System.out.println("Nombre "+lbNombre.getText());
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
				lbPs.setTextFill(Color.web("#0076a3"));
				fp.getChildren().add(lbPs);
				fp.getChildren().add(pb);
				
				borderPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #661a33)");
				borderPane.setBottom(fp);
				//borderPane.autosize();
				borderPane.setPrefSize(215, 200);
				pane.add(borderPane, currentColumn, currentRow);
				
				cont++;
			}
		}
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
	
			lb.setText(" Nv " +String.valueOf(panel.getPokemon().getNivel()));
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
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static ArrayList<Pokemon> generarPokemons(int numPokemons){
		ArrayList<Pokemon> aux = new ArrayList<Pokemon>();
		
		try {
			for (int i = 0; i<numPokemons ;i++)
				aux.add(new Pokemon(nombres[i%5]+i,getNivel(),getVida(),getImagen(i),i));
			
		} catch (FileNotFoundException e) {e.printStackTrace();}
			
		return aux;
	}
	
	public static Image getImagen(int i) throws FileNotFoundException {
		return new Image(new FileInputStream("imagenes\\"+nombres[i%5]+".png"));
	}
	
	public static int getNivel() {
		return new Random().nextInt(99)+1;
	}
	
	public static int getVida() {
		return new Random().nextInt(100)+1;
	}
}
