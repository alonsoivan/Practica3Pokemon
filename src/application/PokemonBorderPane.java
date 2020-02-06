package application;

import javafx.scene.layout.BorderPane;

public class PokemonBorderPane extends BorderPane{
	
	Pokemon pokemon;
	
	public PokemonBorderPane(Pokemon pokemon){
		super();
		this.pokemon = pokemon;
	}
	
	public Pokemon getPokemon(){
		return pokemon;
	}
}
