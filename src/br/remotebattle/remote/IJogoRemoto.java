package br.remotebattle.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.remotebattle.dominio.Jogador;
import br.remotebattle.dominio.Jogo;

public interface IJogoRemoto extends Remote{

	public String entrarNoJogo(String nomeJogador) throws RemoteException;
	public Jogo getJogo() throws RemoteException;
	public Jogador getJogador() throws RemoteException;
}
