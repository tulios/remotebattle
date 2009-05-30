package br.remotebattle.dominio.enums;

import java.awt.Color;

public enum TipoBarco {

	//PORTA_AVIOES(6,1, Color.RED), 
	//ENCOURACADO(5,2, Color.BLUE), 
	//CRUZADOR(4,3, Color.CYAN), 
	//SUBMARINO(3,3, Color.GREEN), 
	//REBOQUE(2,2, Color.MAGENTA);
	
	//PORTA_AVIOES(6,1, Color.RED), 
	//ENCOURACADO(5,2, Color.BLUE), 
	//CRUZADOR(4,3, Color.CYAN), 
	SUBMARINO(3,1, Color.GREEN), 
	REBOQUE(2,1, Color.MAGENTA);

	private int tamanho;
	private int quantidade;
	private Color cor;
	
	TipoBarco(final int tamanho, final int quantidade, Color cor){
		this.tamanho = tamanho;		
		this.quantidade = quantidade;
		this.cor = cor;
	}

	public static TipoBarco newInstance(final int tamanho){
		
		for(TipoBarco tipo : TipoBarco.values()){
			
			if(tipo.getTamanho() == tamanho)
				return tipo;
		}
		
		return null;
	}
	
	public static TipoBarco newInstance(final Color cor){
		
		for(TipoBarco tipo : TipoBarco.values()){
			
			if(tipo.getCor() == cor)
				return tipo;
		}
		
		return null;
	}
	
	public static int getQuantidadeDisponivel(){
		int quantidadeDisponivel = 0;
		
		for(TipoBarco tipo : TipoBarco.values()){
			quantidadeDisponivel += tipo.getQuantidade();
		}
		
		return quantidadeDisponivel;
	}
	
	public static boolean isBarco(Color cor){
		for (TipoBarco tipo : TipoBarco.values()){
			if (tipo.getCor() == cor){
				return true;
			}
		}
		return false;
	}
	
	public int getTamanho() {
		return tamanho;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public Color getCor() {
		return cor;
	}
}





















