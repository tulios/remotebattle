package br.remotebattle.dominio;

import java.io.Serializable;

import br.remotebattle.dominio.enums.Dificuldade;

@SuppressWarnings("serial")
public class Jogador implements Serializable {

	private String nome;
	private Mapa mapa;
	private Jogador oponente;
	
	public Jogador(String nome, Dificuldade dificuldade){
		this.nome = nome;
		this.mapa = new Mapa(dificuldade.getTamanho());
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public Mapa getMapa() {
		return mapa;
	}

	public Jogador getOponente() {
		return oponente;
	}

	public void setOponente(Jogador oponente) {
		this.oponente = oponente;
	}
	
	public String toString(){
		return this.nome;
	}
}
