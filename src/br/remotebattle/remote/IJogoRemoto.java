package br.remotebattle.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJogoRemoto extends Remote{

	public void entrarNoJogo(String nomeJogador) throws RemoteException;
}
