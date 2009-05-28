package br.remotebattle.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import br.remotebattle.dominio.Barco;
import br.remotebattle.dominio.Bloco;
import br.remotebattle.ui.Main;
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
	
	public List<Barco> getBarcos() {
		return barcos;
	}
	
	@Override
	public void execute(){
		try {
			Main.getJogoRemoto().iniciarJogo(barcos);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
