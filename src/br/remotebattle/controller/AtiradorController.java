package br.remotebattle.controller;

import java.rmi.RemoteException;

import br.remotebattle.ui.Main;

public class AtiradorController implements IController{

	private int x;
	private int y;
	private boolean atingido;

	public void definirAlvo(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean isAtingido() {
		return atingido;
	}

	@Override
	public void execute() {
		try{
			atingido = Main.getJogoRemoto().atirar(x, y);			
		}catch (RemoteException e) {
			System.err.println(e.getMessage());
		}
	}

}
