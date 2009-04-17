package br.remotebattle.dominio;

import br.remotebattle.dominio.enums.Dificuldade;

public class Jogo {

	private Dificuldade dificuldade;
	private Jogador jogador1;
	private Jogador jogador2;
	
	public Jogo(String nomeJogador1, Dificuldade dificuldade){
		this.dificuldade = dificuldade;
		this.jogador1 = new Jogador(nomeJogador1, dificuldade);		
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
