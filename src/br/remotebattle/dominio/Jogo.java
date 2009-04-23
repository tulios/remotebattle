package br.remotebattle.dominio;

import java.io.Serializable;

import br.remotebattle.dominio.enums.Dificuldade;

@SuppressWarnings("serial")
public class Jogo implements Serializable {

	private Dificuldade dificuldade;
	private Jogador jogador1;
	private Jogador jogador2;
	
	public Jogo(String nomeJogador1, Dificuldade dificuldade){
		this.dificuldade = dificuldade;
		this.jogador1 = new Jogador(nomeJogador1, dificuldade);		
	}

	public boolean isGameOver(){
		if(jogador1.getMapa().isGameOver() || jogador2.getMapa().isGameOver())
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "Jogador1: "+this.jogador1.getNome()+" - "+dificuldade;
	}

	public void setJogador2(String nome){
		if(jogador2 == null)
			this.jogador2 = new Jogador(nome, this.dificuldade);
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public Jogador getJogador1() {
		return jogador1;
	}

	public Jogador getJogador2() {
		return jogador2;
	}
	
}
