package br.remotebattle.ui;

import java.awt.BorderLayout;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.remotebattle.remote.IJogoRemoto;
import br.remotebattle.remote.IServicoJogos;
import br.remotebattle.ui.panels.Info;
import br.remotebattle.ui.panels.MapaJogo;
import br.remotebattle.ui.panels.PainelJogosEmEspera;
import br.remotebattle.ui.panels.PainelNovoJogo;

public class Main {
	
	private static Info info;
	private static MapaJogo mapaJogo;
	private static IServicoJogos servicoJogos;
	private static IJogoRemoto jogoRemoto;
	private static PainelNovoJogo novoJogo;
	private static PainelJogosEmEspera painelJogosEmEspera;
	
	public static void main(String[] args) {
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			servicoJogos = (IServicoJogos) Naming.lookup("RemoteBattle");
			System.out.println("Setado o proxy do servidor de jogos");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		/*
		Jogo jogo = new Jogo("Túlio", Dificuldade.DIFICIL);
		
		info = new Info();
		Janela.getInstance().add(info, BorderLayout.PAGE_START);
		
		mapaJogo = new MapaJogo(jogo.getJogador1());
		JPanel centro = new JPanel(new BorderLayout());
		
		centro.add(new JLabel(" "), BorderLayout.PAGE_START);
		centro.add(mapaJogo, BorderLayout.CENTER);
		centro.add(new JLabel(" "), BorderLayout.PAGE_END);
		*/
		
		novoJogo = PainelNovoJogo.getInstance();
		painelJogosEmEspera = PainelJogosEmEspera.getInstance();
		
		JPanel centro = new JPanel(new BorderLayout());
		
		
		centro.add(new JLabel(" "), BorderLayout.PAGE_START);
		centro.add(novoJogo, BorderLayout.CENTER);
		centro.add(painelJogosEmEspera, BorderLayout.PAGE_END);

		
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
	
	public static IServicoJogos getServicoJogos(){
		return servicoJogos;
	}

	public static IJogoRemoto getJogoRemoto() {
		return jogoRemoto;
	}

	public static void setJogoRemoto(IJogoRemoto jogoRemoto) {
		Main.jogoRemoto = jogoRemoto;
	}
}
