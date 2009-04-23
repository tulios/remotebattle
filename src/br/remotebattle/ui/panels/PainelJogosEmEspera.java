package br.remotebattle.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import br.remotebattle.remote.implementacao.JogoRemoto;
import br.remotebattle.ui.Main;

@SuppressWarnings("serial")
public class PainelJogosEmEspera extends JPanel {

	private static PainelJogosEmEspera instance; 
	private JButton botaoEntrarNoJogo;
	private JList listaJogos;
	
	public static PainelJogosEmEspera getInstance(){
		if(instance == null)
			instance = new PainelJogosEmEspera();
		return instance;
	}
	
	private PainelJogosEmEspera(){
		super();
		
		try {
			this.listaJogos = new JList(Main.getServicoJogos().getJogos().keySet().toArray());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		this.botaoEntrarNoJogo = new JButton("Entrar no jogo");
		this.botaoEntrarNoJogo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				clickEntrarNoJogo();
			}

		});
		
		this.add(this.listaJogos);
		this.add(botaoEntrarNoJogo);
		
	}
	
	private void clickEntrarNoJogo() {
		
		String nomeJogoSelecionado = (String)this.listaJogos.getSelectedValue();
		String nomeJogador = PainelNovoJogo.getInstance().getCampoNome().getText();
		
		if(	nomeJogoSelecionado!=null && nomeJogoSelecionado!="" &&
			nomeJogador !=null && nomeJogador!=""){
			
			try {
				System.out.println("Tentando entrar no jogo...");
				Main.setJogoRemoto(JogoRemoto.getJogoRemoto(nomeJogoSelecionado));
				Main.getJogoRemoto().entrarNoJogo(nomeJogador);
				
			} catch (RemoteException e) {
				System.out.println("Erro ao entrar no jogo!");
				e.printStackTrace();
			}
		}
	}
	
	public void atualizarComboJogosEmEspera(){
		try {
			System.out.println("Atualizando a lista de jogos...");
			instance.getListaJogos().removeAll();
			instance.getListaJogos().setListData(Main.getServicoJogos().getJogos().keySet().toArray());
		} catch (RemoteException e) {
			System.out.println("Não foi possivel reucperar a lista de jogos");
			e.printStackTrace();
		}
	}

	public JList getListaJogos() {
		return listaJogos;
	}

	public void setListaJogos(JList listaJogos) {
		this.listaJogos = listaJogos;
	}
}
