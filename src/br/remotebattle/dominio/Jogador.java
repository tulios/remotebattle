package br.remotebattle.dominio;

import java.io.Serializable;

import br.remotebattle.dominio.enums.Dificuldade;

public class Jogador implements Serializable {

	private String nome;
	private Mapa mapa;
	
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
	
	public Mapa getMapa() {
		return mapa;
	}
}
