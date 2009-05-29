package br.remotebattle.util;

import javax.swing.JProgressBar;

public class ProgressUtil {

	public int calcularPorcentagem(int total, int atual){
		return ((100 * atual) / total);
	}
	
	public void atualizarProgres(JProgressBar bar, int novoValor){
		bar.setValue(novoValor);
		bar.validate();		
	}
	
}
