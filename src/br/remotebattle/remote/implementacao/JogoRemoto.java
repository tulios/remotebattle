package br.remotebattle.remote.implementacao;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import br.remotebattle.dominio.Barco;
import br.remotebattle.dominio.Jogador;
import br.remotebattle.dominio.Jogo;
import br.remotebattle.dominio.Mapa;
import br.remotebattle.remote.IJogoRemoto;

@SuppressWarnings("serial")
public class JogoRemoto extends UnicastRemoteObject implements IJogoRemoto {

	private Jogo jogo;
	private Jogador jogador;
	
	public JogoRemoto(Jogo jogo) throws RemoteException {
		super();
		this.jogo = jogo;
		
		if(jogo.getJogador1()!=null && jogo.getJogador2()==null){
			this.jogador = jogo.getJogador1();
		} 
		else if(jogo.getJogador1()!=null && jogo.getJogador2()!=null){
			this.jogador = jogo.getJogador2();
			this.jogador.setOponente(jogo.getJogador1());
		}
	}
	
	public String entrarNoJogo(String nomeJogador) throws RemoteException {
	
		//Monta o nome do jogo baseado no nome do jogador
		String nomeJogo = ServicoJogos.montarNomeJogo(nomeJogador);
		
		//Se nao existe o jogo com o nome passado por parametro
		if(!ServicoJogos.existeJogo(nomeJogo)){
			
			try {
				//Seta o segundo jogador do jogo e o oponente do jogador
				this.jogo.setJogador2(nomeJogador);
				this.jogador.setOponente(this.jogo.getJogador2());
				
				//Cria o jogo remoto e publica
				IJogoRemoto jogoRemoto = new JogoRemoto(this.jogo);
				
				//Publica o novo jogo remoto
				ServicoJogos.publicarObjeto(nomeJogo, jogoRemoto);
				
				System.out.println("O jogador "+nomeJogador+" entrou no jogo!");
				System.out.println("\nJogo do Jogador1: "+this);
				System.out.println("\nJogo do Jogador2: "+jogoRemoto);
				System.out.println("\nJogador 1 pode jogar? "+this.jogo.getJogador1().isPossoJogar());
				System.out.println("\nJogador 2 pode jogar? "+this.jogo.getJogador2().isPossoJogar());
				
				System.out.println("\nJogador pode jogar? "+this.jogador.isPossoJogar());
				
			} catch (MalformedURLException e) {
				//Erro. Volta o estado do jogador 2 para o estado inicial
				this.jogo.setJogador2(null);
				this.jogador.setOponente(null);
				
				System.err.println("Não foi possível publicar o jogo remoto!");
				e.printStackTrace();
				
				return null;
			}
			
			
			return nomeJogo;
		}
		
		return null;
		
	}
	
	public void iniciarJogo(List<Barco> barcos) throws RemoteException{
		this.jogador.setMapa(new Mapa(barcos));
	}
	
	public static IJogoRemoto getJogoRemoto(String nomeJogoRemoto){
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			System.out.println("Recuperado o proxy do jogoRemoto de nome "+nomeJogoRemoto+"...");
			IJogoRemoto jogoRemoto = (IJogoRemoto) Naming.lookup(nomeJogoRemoto);
			
			return jogoRemoto;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	public void mudarTurno() throws RemoteException{
		jogador.setPossoJogar(false);
		jogador.getOponente().setPossoJogar(true);
	}
	
	public boolean atirar(int x, int y) throws RemoteException{
		boolean atingido = jogador.getOponente().getMapa().atirar(x,y);
		System.out.println(jogador.getNome());
		if (jogador.getOponente().getMapa().isGameOver()){
			
			System.out.println(jogador.getNome()+" venceu! e "+jogador.getOponente().getNome()+" perdeu!");			
			
			jogador.getOponente().setPerdedor(true);
			jogador.setVencedor(true);
		}
		
		return atingido;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public Jogador getJogador() throws RemoteException{
		return jogador;
	}
	
	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public String toString(){
		String saida = "Jogo: \n"+
						"Jogador1: "+jogo.getJogador1()+"\n"+
						"Jogador2: "+jogo.getJogador2()+"\n\n"+
						"Jogador: \n"+
						"nome: "+jogador+"\n"+
						"oponente: "+jogador.getOponente()+"\n\n";
		
		return saida;
	}

}
