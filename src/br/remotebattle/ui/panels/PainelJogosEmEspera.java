package br.remotebattle.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import br.remotebattle.remote.JogoRemoto;
import br.remotebattle.ui.Main;

public class PainelJogosEmEspera extends JPanel {

	private static PainelJogosEmEspera instance; 
	private JComboBox comboJogosEmEspera;
	private JButton botaoEntrarNoJogo;
	
	public static PainelJogosEmEspera getInstance(){
		if(instance == null)
			instance = new PainelJogosEmEspera();
		return instance;
	}
	
	private PainelJogosEmEspera(){
		super();
		
		try {
			this.comboJogosEmEspera = new JComboBox(Main.getServicoJogos().getJogos().toArray());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		this.botaoEntrarNoJogo = new JButton("Entrar no jogo");
		this.botaoEntrarNoJogo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				clickEntrarNoJogo();
			}

		});
		
		this.add(comboJogosEmEspera);
		this.add(botaoEntrarNoJogo);
		
	}
	
	private void clickEntrarNoJogo() {
		
		try {
			System.out.println("Tentando entrar no jogo...");
			Main.setJogoRemoto(JogoRemoto.getJogoRemoto((String)this.comboJogosEmEspera.getSelectedItem()));
			Main.getJogoRemoto().entrarNoJogo("Jogador2");
			System.out.println("Entrou no jogo!");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void atualizarComboJogosEmEspera(){
		try {
			System.out.println("Atualizando a lista de jogos...");
			instance.getComboJogosEmEspera().removeAllItems();
			instance.getComboJogosEmEspera().setModel(new DefaultComboBoxModel(Main.getServicoJogos().getJogos().toArray()));
		} catch (RemoteException e) {
			System.out.println("Não foi possivel reucperar a lista de jogos");
			e.printStackTrace();
		}
	}

	public JComboBox getComboJogosEmEspera() {
		return comboJogosEmEspera;
	}

	public void setComboJogosEmEspera(JComboBox comboJogosEmEspera) {
		this.comboJogosEmEspera = comboJogosEmEspera;
	}
	
}
