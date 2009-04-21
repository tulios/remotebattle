package br.remotebattle.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;

@SuppressWarnings("serial")
public class ServicoJogos extends UnicastRemoteObject implements IServicoJogos {
	
	private List<String> jogos;
	
	protected ServicoJogos() throws RemoteException {
		super();
		this.jogos = new ArrayList<String>();
	}

	public String criarNovoJogo(String nomeJogador1, Dificuldade dificuldade) throws RemoteException {
		Jogo jogo = new Jogo(nomeJogador1,dificuldade);
		
		String nomeJogo = "JogoRemoto"+nomeJogador1;
		
		IJogoRemoto jogoRemoto = new JogoRemoto(jogo);
		publicarObjeto("JogoRemoto"+nomeJogador1, jogoRemoto);
		
		this.jogos.add(nomeJogo);
		
		System.out.println("\n\nNovo jogo criado!\n Jogador: "+nomeJogador1+"\n Dificuldade: "+dificuldade);
		
		return nomeJogo;
	}
	
	public List<String> getJogos(){
		return this.jogos;
	}
	
	private static void publicarObjeto(String nome, Remote objetoRemoto){
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			Naming.rebind(nome, objetoRemoto);
			System.out.println("O objeto remoto foi publicado");
		}
		catch(RemoteException exception) {
			System.out.println("Erro ao publicar objeto remoto");
			exception.printStackTrace();
		}
		catch(MalformedURLException exception) {
			System.out.println("Erro ao publicar o objeto remoto");
			exception.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
		try {
			IServicoJogos servicoJogos = new ServicoJogos();
			publicarObjeto("RemoteBattle", servicoJogos);
		}
		catch(RemoteException exception){
			System.out.println("Erro ao instanciar objeto remoto");
			exception.printStackTrace();
		}
	}

}
