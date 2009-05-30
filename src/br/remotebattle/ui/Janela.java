package br.remotebattle.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import br.remotebattle.ui.panels.componentes.GlassPanel;

@SuppressWarnings("serial")
public class Janela extends JFrame{

	private static Janela janela;
	
	public static Janela getInstance(){
		if(janela == null){
			janela = new Janela();			
		}
		
		return janela;
	}
	
	public static Janela getInstance(boolean nova){
		if(janela == null || nova)
			janela = new Janela();
		
		return janela;
	}
	
	private Janela(){
		super("Remote Battle");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setGlassPane(new GlassPanel());
	}
	
}













