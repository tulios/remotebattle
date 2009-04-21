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

import br.remotebattle.dominio.enums.Dificuldade;
import br.remotebattle.remote.IJogoRemoto;
import br.remotebattle.remote.JogoRemoto;
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
		
		if(nomeJogador!=null && nomeJogador!=""){
			try{
				String nomeJogoRemoto = Main.getServicoJogos().criarNovoJogo(this.campoNome.getText(), (Dificuldade)this.comboDificuldades.getSelectedItem());
				IJogoRemoto jogoRemoto = JogoRemoto.getJogoRemoto(nomeJogoRemoto);
				
				if(jogoRemoto!=null){
					Main.setJogoRemoto(jogoRemoto);
				}
			}
			catch(RemoteException exception){
				exception.printStackTrace();
				JOptionPane.showMessageDialog(this,exception.getMessage());
			}
		
			PainelJogosEmEspera.atualizarComboJogosEmEspera();
			
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
