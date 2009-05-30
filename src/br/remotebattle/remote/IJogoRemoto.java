package br.remotebattle.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import br.remotebattle.dominio.Barco;
import br.remotebattle.dominio.Jogador;
import br.remotebattle.dominio.Jogo;

public interface IJogoRemoto extends Remote{

	public String entrarNoJogo(String nomeJogador) throws RemoteException;
	public Jogo getJogo() throws RemoteException;
	public Jogador getJogador() throws RemoteException;
	public void iniciarJogo(List<Barco> barcos) throws RemoteException;
	public boolean atirar(int x, int y) throws RemoteException;
	public void mudarTurno() throws RemoteException;
}
