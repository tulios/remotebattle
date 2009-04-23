package br.remotebattle.remote.implementacao;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.remote.IJogoRemoto;
import br.remotebattle.remote.IServicoJogos;

@SuppressWarnings("serial")
public class ServicoJogos extends UnicastRemoteObject implements IServicoJogos {
	
	private static Map<String,Jogo> jogos;
	
	protected ServicoJogos() throws RemoteException {
		super();
		
		if(jogos==null)
			jogos = new HashMap<String,Jogo>();
	}

	public String criarNovoJogo(String nomeJogador1, Dificuldade dificuldade) throws RemoteException {
		
		//Monta a chave do jogo
		String nomeJogo = montarNomeJogo(nomeJogador1);
	
		//Se o jogo não existir
		if(!jogos.keySet().contains(nomeJogo)){
			
			//Instancia o jogo e o jogo remoto
			Jogo jogo = new Jogo(nomeJogador1,dificuldade);
			IJogoRemoto jogoRemoto = new JogoRemoto(jogo);
		
			try {
				//Publica o objeto e adiciona o nome e o jogo no mapa
				publicarObjeto(nomeJogo, jogoRemoto);
				jogos.put(nomeJogo,jogo);
				System.out.println("\n\nNovo jogo criado!\n Jogador: "+nomeJogador1+"\n Dificuldade: "+dificuldade);
				System.out.println(jogoRemoto);
			} 
			catch (MalformedURLException e) {
				//Se der erro imprime uma mensagem de erro
				System.err.println("Não foi possível publicar o objeto "+nomeJogo);
			}
		}
		
		return nomeJogo;
	}

	public static String montarNomeJogo(String nomeJogador) {
		String nomeJogo = "JogoRemoto"+nomeJogador.toUpperCase();
		return nomeJogo;
	}
	
	public Map<String,Jogo> getJogos(){
		if(jogos==null)
			jogos = new HashMap<String,Jogo>();
		return jogos;
	}
	
	public static boolean existeJogo(String nomeJogo){
		return jogos.keySet().contains(nomeJogo);
	}
	
	public static void publicarObjeto(String nome, Remote objetoRemoto) throws RemoteException,MalformedURLException{
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		Naming.rebind(nome, objetoRemoto);
		System.out.println("O objeto "+nome+" foi publicado");
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
		catch(MalformedURLException exception){
			System.out.println("Erro ao publicar o objeto. Url mal formatada.");
			exception.printStackTrace();
		}
	}

}
