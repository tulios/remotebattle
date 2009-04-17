package br.remotebattle.dominio;

import br.remotebattle.dominio.enums.Dificuldade;

public class Jogador {

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
