package br.remotebattle.controller;

import java.util.ArrayList;
import java.util.List;

import br.remotebattle.dominio.Barco;
import br.remotebattle.dominio.Bloco;
import br.remotebattle.ui.panels.componentes.BlocoGrafico;

public class IniciarJogoController implements IController{
	
	private List<Barco> barcos;
	
	public IniciarJogoController(){
		barcos = new ArrayList<Barco>();
	}
	
	public void gerarBarco(List<BlocoGrafico> lista){
		List<Bloco> blocos = new ArrayList<Bloco>();
		
		for (BlocoGrafico bg: lista){
			blocos.add(new Bloco(bg.getCoordX(), bg.getCoordY(), true, false));
		}
		
		barcos.add(new Barco(blocos));
	}
	
	@Override
	public void execute(){
	}
	
}
