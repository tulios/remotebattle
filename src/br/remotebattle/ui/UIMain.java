package br.remotebattle.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import br.remotebattle.controller.MediadorController;
import br.remotebattle.controller.MudarTurnoController;
import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.ui.panels.Info;
import br.remotebattle.ui.panels.MapaJogo;

public class UIMain {
	private static Info info;
	private static MapaJogo mapaJogo;
	private static MapaJogo mapaOponente;
	private static JPanel rodape;

	private static JProgressBar andamentoJogo;

	private static MediadorController mediadorController;
	private static MudarTurnoController mudarTurnoController;

	static{
		mediadorController = new MediadorController();
		mudarTurnoController = new MudarTurnoController();
	}

	public static void main(String[] args) {

		Jogo jogo = new Jogo("Túlio", Dificuldade.DIFICIL);
		init(jogo);

	}
	
	public static MudarTurnoController getMudarTurnoController() {
		return mudarTurnoController;
	}

	private static void inicializarThreadMediador(){
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				while(true){
					mediadorController.execute();
					boolean possoJogar = mediadorController.possoJogar();

					System.out.println("posso jogar? "+possoJogar);

					if(possoJogar){
						mapaJogo.atualizarBloco(mediadorController.getUltimoTiroNesseMapa());
						
						if (mediadorController.getJogador().isPerdedor()){
							
							mudarTurnoController.execute();
							
							info.mudarTurno("Você perdeu!", false);
							JOptionPane.showMessageDialog(Janela.getInstance(),
  														  mediadorController.getJogador().getNome()+" você perdeu!", 
														  "Game Over",
														  JOptionPane.ERROR_MESSAGE);
							break;			
						}else if (mediadorController.getJogador().isVencedor()){
							
							mudarTurnoController.execute();
							
							info.mudarTurno("Você venceu!", false);
							JOptionPane.showMessageDialog(Janela.getInstance(),
  														  mediadorController.getJogador().getNome()+" você venceu!", 
														  "Game Over",
														  JOptionPane.INFORMATION_MESSAGE);
							break;
						}
						
						Janela.getInstance().getGlassPane().setVisible(false);
						info.mudarTurno("Sua vez!", false);
					}else{						
						Janela.getInstance().getGlassPane().setVisible(true);
						info.mudarTurno("Aguardando oponente", true);
					}

					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Thread t = new Thread(runnable);
		t.start();
	}

	public static void bindMapa(){
		info.ativarModoJogo();
		inicializarThreadMediador();

		Janela.getInstance().add(info, BorderLayout.PAGE_START);

		JPanel centro = new JPanel(new BorderLayout());

		mapaOponente = new MapaJogo(mapaJogo.getJogador());

		Border borda = new LineBorder(Color.BLACK);
		Border bordaMeuMapa = new TitledBorder(borda, "Meu mapa:");
		Border bordaMapaOponente = new TitledBorder(borda, "Mapa oponente:");

		mapaJogo.setBorder(bordaMeuMapa);
		mapaJogo.bloquearMapa();

		mapaOponente.setBorder(bordaMapaOponente);
		mapaOponente.setModoJogo(true);

		centro.add(mapaJogo, BorderLayout.WEST);
		centro.add(mapaOponente, BorderLayout.CENTER);

		rodape = new JPanel(new BorderLayout());
		rodape.setPreferredSize(new Dimension(40,40));
		centro.add(rodape, BorderLayout.PAGE_END);

		andamentoJogo = new JProgressBar();
		andamentoJogo.setMaximum(100);
		andamentoJogo.setMinimum(0);
		andamentoJogo.setStringPainted(true);

		rodape.add(new JLabel("Andamento do jogo:"), BorderLayout.PAGE_START);
		rodape.add(andamentoJogo, BorderLayout.CENTER);

		Janela.getInstance().add(centro, BorderLayout.CENTER);
		Janela.getInstance().validate();
		Janela.getInstance().pack();

		Janela.getInstance().setVisible(true);
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

	public static MapaJogo getMapaOponente() {
		return mapaOponente;
	}

	public static JPanel getRodape(){
		return UIMain.rodape;
	}

	public static JProgressBar getAndamentoJogo() {
		return andamentoJogo;
	}
}
