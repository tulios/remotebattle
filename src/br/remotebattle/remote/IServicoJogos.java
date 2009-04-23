package br.remotebattle.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;

public interface IServicoJogos extends Remote {

	public String criarNovoJogo(String nomeJogador1, Dificuldade dificuldade) throws RemoteException;
	public Map<String,Jogo> getJogos() throws RemoteException;

}
