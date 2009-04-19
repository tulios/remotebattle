package br.remotebattle.ui;

import java.awt.BorderLayout;

import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.ui.panels.MapaJogo;

public class Main {
	
	public static void main(String[] args) {
		Jogo jogo = new Jogo("Túlio", Dificuldade.DIFICIL);
		Janela.getInstance().add(new MapaJogo(jogo.getJogador1()), BorderLayout.CENTER);
		Janela.getInstance().pack();
		
		Janela.getInstance().setVisible(true);
	}
	
}
