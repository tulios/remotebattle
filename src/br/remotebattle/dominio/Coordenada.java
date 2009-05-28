package br.remotebattle.dominio;

import java.io.Serializable;

public class Coordenada implements Serializable{

	private int x;
	private int y;
	
	public Coordenada(final int x, final int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
