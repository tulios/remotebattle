package br.remotebattle.ui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import br.remotebattle.dominio.Jogador;
import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.remote.IJogoRemoto;
import br.remotebattle.remote.implementacao.JogoRemoto;
import br.remotebattle.ui.Main;

@SuppressWarnings("serial")
public class PainelNovoJogo extends JPanel{

	private static PainelNovoJogo instance;

	private JTextField campoNome;
	private JComboBox comboDificuldades;
	private JButton botaoNovoJogo;

	private JButton botaoEntrarNoJogo;
	private JList listaJogos;

	public static PainelNovoJogo getInstance(){
		if(instance == null)
			instance = new PainelNovoJogo();

		return instance;
	}

	private PainelNovoJogo(){
		super();
		campoNome = new JTextField(15);
		comboDificuldades = new JComboBox(Dificuldade.values());
		botaoNovoJogo = new JButton("Novo jogo");
		botaoEntrarNoJogo = new JButton("Entrar no jogo");

		try {
			this.listaJogos = new JList(Main.getServicoJogos().getJogos().keySet().toArray());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		JScrollPane scroll = new JScrollPane(listaJogos);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(180,120));

		this.setLayout(new GridBagLayout());

		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5,5,5,5);

		JLabel labelLista = new JLabel("Salas disponíveis:");

		cons.fill = GridBagConstraints.HORIZONTAL;

		cons.gridy = 0;
		cons.gridx = 0;
		cons.gridwidth = 2;

		this.add(labelLista, cons);

		cons.gridy = 1;
		cons.gridwidth = 4;
		cons.gridheight = 4;

		this.add(scroll,cons);

		cons.gridy = 0;
		cons.gridx = 4;
		cons.gridwidth = 2;
		cons.gridheight = 1;

		JLabel labelNome = new JLabel("Nome do jogador:");
		this.add(labelNome, cons);

		cons.gridy = 1;		
		this.add(campoNome,cons);

		cons.gridy = 2;

		this.add(botaoEntrarNoJogo,cons);

		cons.gridy = 3;

		this.add(comboDificuldades,cons);

		cons.gridy = 4;

		this.add(botaoNovoJogo,cons);

		this.botaoNovoJogo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				clickNovoJogo();
			}
		});
		
		this.botaoEntrarNoJogo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clickEntrarNoJogo();
			}
		});
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

	private void clickNovoJogo() {

		String nomeJogador = this.campoNome.getText();
		String nomeJogoRemoto = null;

		if(nomeJogador!=null && nomeJogador!=""){
			try{
				nomeJogoRemoto = Main.getServicoJogos().criarNovoJogo(nomeJogador, (Dificuldade)this.comboDificuldades.getSelectedItem());
				IJogoRemoto jogoRemoto = JogoRemoto.getJogoRemoto(nomeJogoRemoto);

				if(jogoRemoto!=null){
					Main.setJogoRemoto(jogoRemoto);
				}
			}
			catch(RemoteException exception){
				exception.printStackTrace();
				JOptionPane.showMessageDialog(this,exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}

			PainelJogosEmEspera.getInstance().atualizarComboJogosEmEspera();
			Main.bloquearTela();

			final String nomeJogo2 = nomeJogoRemoto;

			if(nomeJogoRemoto!=null && nomeJogoRemoto!=""){
				Runnable verificador =  new Runnable(){

					public void run() {

						Jogador jogador2 = null;
						try {
							while(jogador2 == null){
								jogador2 = JogoRemoto.getJogoRemoto(nomeJogo2).getJogo().getJogador2();

								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									System.out.println("Não foi possivel fazer a thread dormir...");
									e.printStackTrace();
								}
							}

							Main.desbloquearTela();

						} catch (RemoteException e) {
							System.out.println("Não foi possivel recuperar o jogador 2...");
							e.printStackTrace();
						}
					}

				};
				Thread thread = new Thread(verificador);
				thread.start();
			}

			System.out.println("Novo jogo criado!");
		} else {
			JOptionPane.showMessageDialog(this, "Nome de jogador inválido", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * Getters e Setters
	 */

	public JTextField getCampoNome() {
		return campoNome;
	}

	public void setCampoNome(JTextField campoNome) {
		this.campoNome = campoNome;
	}

	public JComboBox getComboDificuldades() {
		return comboDificuldades;
	}

	public void setComboDificuldades(JComboBox comboDificuldades) {
		this.comboDificuldades = comboDificuldades;
	}

	public JButton getBotaoNovoJogo() {
		return botaoNovoJogo;
	}

	public void setBotaoNovoJogo(JButton botaoNovoJogo) {
		this.botaoNovoJogo = botaoNovoJogo;
	}

}
