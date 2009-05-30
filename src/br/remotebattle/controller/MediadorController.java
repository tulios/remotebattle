package br.remotebattle.controller;

import java.rmi.RemoteException;

import br.remotebattle.dominio.Bloco;
import br.remotebattle.dominio.Jogador;
import br.remotebattle.ui.Main;

public class MediadorController implements IController {
	
	private Jogador jogador;
	
	public boolean possoJogar(){
		return jogador.isPossoJogar();
	}
	
	public Bloco getUltimoTiroNesseMapa() {
		return jogador.getMapa().getUltimoTiroNesseMapa();
	}
	
	@Override
	public void execute() {
		try {
			jogador = Main.getJogoRemoto().getJogador();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
