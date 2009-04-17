package br.remotebattle.ui;

import javax.swing.JFrame;

public class Janela extends JFrame{

	private static Janela janela;
	
	public static Janela getInstance(){
		if(janela == null)
			janela = new Janela();
		
		return janela;
	}
	
	private Janela(){
		
		super("Remote Battle");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
