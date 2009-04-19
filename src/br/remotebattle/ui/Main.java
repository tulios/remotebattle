package br.remotebattle.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.ui.panels.Info;
import br.remotebattle.ui.panels.MapaJogo;

public class Main {
	
	private static Info info;
	private static MapaJogo mapaJogo;
	
	public static void main(String[] args) {
		Jogo jogo = new Jogo("Túlio", Dificuldade.DIFICIL);
		
		info = new Info();
		Janela.getInstance().add(info, BorderLayout.PAGE_START);
		
		mapaJogo = new MapaJogo(jogo.getJogador1());
		JPanel centro = new JPanel(new BorderLayout());
		
		centro.add(new JLabel(" "), BorderLayout.PAGE_START);
		centro.add(mapaJogo, BorderLayout.CENTER);
		centro.add(new JLabel(" "), BorderLayout.PAGE_END);
		
		Janela.getInstance().add(centro, BorderLayout.CENTER);
		Janela.getInstance().pack();
		
		Janela.getInstance().setVisible(true);
	}
	
	public static Info getInfo() {
		return info;
	}
	
	public static MapaJogo getMapaJogo() {
		return mapaJogo;
	}
	
}
