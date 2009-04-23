package br.remotebattle.dominio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bloco implements Serializable {

	private Coordenada coordenada;
	private boolean ocupado;
	private boolean atingido;
	
	public Bloco(){
		this.coordenada = new Coordenada(-1,-1);
	}
	
	public Bloco(Coordenada coordenada, boolean ocupado, boolean atingido) {
		this.coordenada = coordenada;
		this.ocupado = ocupado;
		this.atingido = atingido;
	}
	
	public Bloco(int x, int y, boolean ocupado, boolean atingido) {
		this.coordenada = new Coordenada(x,y);
		this.ocupado = ocupado;
		this.atingido = atingido;
	} 
	
	public Bloco(int x, int y) {		
		this.coordenada = new Coordenada(x,y);
	}
	
	public Bloco(Coordenada coordenada) {		
		this.coordenada = coordenada;
	}
	
	

	public Coordenada getCoordenada() {
		return coordenada;
	}
	
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	
	public boolean isOcupado() {
		return ocupado;
	}
	
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
	
	public boolean isAtingido() {
		return atingido;
	}
	
	public void setAtingido(boolean atingido) {
		this.atingido = atingido;
	}
	
	public int getX(){
		return this.coordenada.getX();
	}
	
	public int getY(){
		return this.coordenada.getY();
	}
}
