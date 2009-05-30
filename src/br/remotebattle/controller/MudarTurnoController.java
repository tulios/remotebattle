package br.remotebattle.controller;

import java.rmi.RemoteException;

import br.remotebattle.ui.Main;

public class MudarTurnoController implements IController {

	@Override
	public void execute() {
		try {
			Main.getJogoRemoto().mudarTurno();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

}
