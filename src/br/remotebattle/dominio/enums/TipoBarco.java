package br.remotebattle.dominio.enums;

public enum TipoBarco {

	PORTA_AVIOES(6,1), ENCOURACADO(5,2), CRUZADOR(4,3), SUBMARINO(3,3), REBOQUE(2,2);

	private int tamanho;
	private int quantidade;
	
	TipoBarco(final int tamanho, final int quantidade){
		this.tamanho = tamanho;
		this.quantidade = quantidade;
	}

	public static TipoBarco newInstance(final int tamanho){
		
		for(TipoBarco tipo : TipoBarco.values()){
			
			if(tipo.getTamanho() == tamanho)
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
	
	public int getTamanho() {
		return tamanho;
	}

	public int getQuantidade() {
		return quantidade;
	}
}
