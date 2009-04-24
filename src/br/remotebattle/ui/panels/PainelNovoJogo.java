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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.awt.windows.ThemeReader;

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
	
	public static PainelNovoJogo getInstance(){
		if(instance == null)
			instance = new PainelNovoJogo();
		
		return instance;
	}
	
	private PainelNovoJogo(){
		super();
		this.campoNome = new JTextField();
		this.campoNome.setPreferredSize(new Dimension(120,30));
		
		this.comboDificuldades = new JComboBox(Dificuldade.values());
		
		this.botaoNovoJogo = new JButton("Novo jogo");
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(5,5,5,5);
		
		this.add(campoNome,cons);
		this.add(comboDificuldades,cons);
		this.add(botaoNovoJogo,cons);
		
		this.botaoNovoJogo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickNovoJogo();
			}
			
		});
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
				JOptionPane.showMessageDialog(this,exception.getMessage());
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
									System.out.println("Não foi possivel fazer a thrad dormir...");
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
			JOptionPane.showMessageDialog(this, "Nome de jogador inválido");
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
