package br.remotebattle.dominio.enums;

public enum Dificuldade {

	FACIL(10), MEDIO(15), DIFICIL(20);
	
	private int tamanho;
	
	private Dificuldade(final int tamanho) {
		this.tamanho = tamanho;
	}
	
	public int getTamanho(){
		return this.tamanho;
	}
}
