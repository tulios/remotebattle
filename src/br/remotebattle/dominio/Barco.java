package br.remotebattle.dominio;

import java.io.Serializable;
import java.util.List;

import br.remotebattle.dominio.enums.TipoBarco;

@SuppressWarnings("serial")
public class Barco implements Serializable{

	private List<Bloco> blocos;
	
	public Barco(List<Bloco> blocos){
		this.blocos = blocos;
		
		for(Bloco bloco : blocos){
			bloco.setOcupado(true);
		}
	}
	
	public boolean estaNaCoordenada(int x, int y){
		if (getBlocoPelaCoordenada(x, y) != null){
			return true;
		}
		return false;
	}
	
	public void destruir(int x, int y){
		getBlocoPelaCoordenada(x, y).setAtingido(true);
	}
	
	private Bloco getBlocoPelaCoordenada(int x, int y){
		for (Bloco bloco: blocos){
			if (bloco.getX() == x && bloco.getY() == y){
				return bloco;
			}
		}
		return null;
	}
	
	public TipoBarco getTipo(){
		return TipoBarco.newInstance(blocos.size());
	}
	
	public boolean isDestruido(){
		int numeroBlocosAtingidos = 0;
		System.out.println(">>"+getTipo());
		for(Bloco bloco : blocos){
			if(bloco.isAtingido())
				numeroBlocosAtingidos++;
		}
		
		System.out.println("nº blocos atingidos = "+numeroBlocosAtingidos+" - (quantidade total: "+getTipo().getTamanho()+")");
		
		if(numeroBlocosAtingidos == getTipo().getTamanho()){
			return true;
		}
		
		return false;
	}
}