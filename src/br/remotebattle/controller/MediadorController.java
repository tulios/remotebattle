package br.remotebattle.controller;

import java.rmi.RemoteException;

import br.remotebattle.ui.Main;

public class MediadorController implements IController {
	
	private boolean resultado;
	
	public boolean possoJogar(){
		return resultado;
	}
	
	@Override
	public void execute() {
		try {
			resultado = Main.getJogoRemoto().possoJogar();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
