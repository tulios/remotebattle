package br.remotebattle.remote;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.remotebattle.dominio.Jogo;

public class JogoRemoto extends UnicastRemoteObject implements IJogoRemoto {

	private Jogo jogo;

	protected JogoRemoto(Jogo jogo) throws RemoteException {
		super();
		this.jogo = jogo;
	}
	
	public void entrarNoJogo(String nomeJogador) throws RemoteException {
	
		this.jogo.setJogador2(nomeJogador);
		System.out.println("O jogador "+nomeJogador+" entrou no jogo!");
	}
	
	public static IJogoRemoto getJogoRemoto(String nomeJogoRemoto){
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			IJogoRemoto jogoRemoto = (IJogoRemoto) Naming.lookup(nomeJogoRemoto);
			System.out.println("Recuperado o proxy do jogoRemoto");
			
			return jogoRemoto;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return null;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
}
