package br.remotebattle.dominio;

import java.util.List;

import br.remotebattle.dominio.enums.TipoBarco;

public class Barco {

	private List<Bloco> blocos;
	
	public Barco(List<Bloco> blocos){
		this.blocos = blocos;
		
		for(Bloco bloco : blocos){
			bloco.setOcupado(true);
		}
	}
	
	public TipoBarco getTipo(){
		return TipoBarco.newInstance(blocos.size());
	}
	
	public boolean isDestruido(){
		int numeroBlocosAtingidos = 0;
		
		for(Bloco bloco : blocos){
			if(bloco.isAtingido())
				numeroBlocosAtingidos++;
		}
		
		if(numeroBlocosAtingidos == getTipo().getQuantidade()){
			return true;
		}
		
		return false;
	}
}