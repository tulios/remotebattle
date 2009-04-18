package br.remotebattle.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class BlocoGrafico extends JButton{
	private MapaJogo mapaJogo;
	private int x;
	private int y;
	
	private boolean selecionando;
	private boolean marcado;
	
	public BlocoGrafico(MapaJogo mapaJogo, int x, int y){
		this.mapaJogo = mapaJogo;
		this.x = x;
		this.y = y;
		
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(25,25));
		this.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				click(e);
			}
			
		});
	}
	
	private void click(ActionEvent e){
		if (!isMarcado()){
			if (isSelecionando() && mapaJogo.isRoot(this)){
				mapaJogo.marcarArea(this, false);
			}else{
			
				if (isSelecionando()){
					mapaJogo.preencherBarco(this);
				}else{
					mapaJogo.marcarArea(this, true);
				}
			}
		}
	}
	
	public int getCoordX(){
		return x;
	}
	
	public int getCoordY(){
		return y;
	}
	
	public void setSelecionando(boolean selecionando) {
		this.selecionando = selecionando;
	}
	
	public boolean isSelecionando() {
		return selecionando;
	}
	
	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
}














