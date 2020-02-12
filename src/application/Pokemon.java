package application;

import javafx.scene.image.Image;

public class Pokemon {
	private String nombre;
	private int nivel;
	private float vida;
	private Image imagen;
	
	private String seguro;
	private String medio;
	private String arriesgado;
	
	public Pokemon(String nombre, int nivel, int vida, Image imagen, int ataques) {
		super();
		this.nombre = nombre;
		this.nivel = nivel;
		this.vida = vida;
		this.imagen = imagen;
		this.seguro = "Seguro"+ataques;
		this.medio = "Medio"+ataques;
		this.arriesgado = "Arriesgado"+ataques;
	}
	
	public Pokemon() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public float getVida() {
		return vida;
	}
	public void setVida(float f) {
		this.vida = f;
	}
	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
	public String getSeguro() {
		return seguro;
	}
	public void setSeguro(String seguro) {
		this.seguro = seguro;
	}
	public String getMedio() {
		return medio;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public String getArriesgado() {
		return arriesgado;
	}
	public void setArriesgado(String arriesgado) {
		this.arriesgado = arriesgado;
	}
	


}
