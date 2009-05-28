package br.remotebattle.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.ui.panels.Info;
import br.remotebattle.ui.panels.MapaJogo;

public class UIMain {
	private static Info info;
	private static MapaJogo mapaJogo;
	private static JPanel rodape;
	
	public static void main(String[] args) {
		
		Jogo jogo = new Jogo("Túlio", Dificuldade.DIFICIL);
		init(jogo);
		
	}
	
	public static void init(Jogo jogo){
		info = new Info();
		Janela.getInstance().add(info, BorderLayout.PAGE_START);
		
		mapaJogo = new MapaJogo(jogo.getJogador1());
		JPanel centro = new JPanel(new BorderLayout());
		
		centro.add(new JLabel(" "), BorderLayout.PAGE_START);
		centro.add(mapaJogo, BorderLayout.CENTER);
		
		rodape = new JPanel();
		rodape.setPreferredSize(new Dimension(40,40));
		centro.add(rodape, BorderLayout.PAGE_END);
		
		Janela.getInstance().add(centro, BorderLayout.CENTER);
		Janela.getInstance().validate();
		Janela.getInstance().pack();
		
		Janela.getInstance().setVisible(true);
	}
	
	public static Info getInfo() {
		return UIMain.info;
	}
	
	public static MapaJogo getMapaJogo() {
		return UIMain.mapaJogo;
	}
	
	public static JPanel getRodape(){
		return UIMain.rodape;
	}
	
}
