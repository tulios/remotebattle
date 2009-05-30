package br.remotebattle.ui.panels;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.remotebattle.dominio.enums.TipoBarco;
import br.remotebattle.ui.Main;

@SuppressWarnings("serial")
public class Info extends JPanel{
	
	private JLabel labelBarcosUtilizados;
	private JPanel[] barcos;
	
	private JLabel infoJogadores;
	
	public Info(){
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLACK);
		
		prepararComponentes();
	}
	
	private void prepararComponentes(){
		GridBagConstraints cons = new GridBagConstraints();
		cons.insets = new Insets(1,1,1,1);
		
		String jogador = null;
		String oponente = null;
		try {
			jogador = Main.getJogoRemoto().getJogador().getNome();
			oponente = Main.getJogoRemoto().getJogador().getOponente().getNome();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		infoJogadores = new JLabel("Jogador: "+jogador+" => Oponente: "+oponente);
		infoJogadores.setForeground(Color.WHITE);
		
		labelBarcosUtilizados = new JLabel("Barcos utilizados:");
		labelBarcosUtilizados.setForeground(Color.WHITE);
		
		inicializarBarcos();
		
		cons.gridwidth = 2;
		cons.anchor = GridBagConstraints.LINE_START;
		cons.gridy = 0;
		cons.gridx = 0;
		
		this.add(infoJogadores, cons);
		
		cons.gridwidth = 1;
		cons.gridy = 1;
		cons.gridx = 0;
		
		this.add(labelBarcosUtilizados, cons);
		
		adicionarBarcosAoPainel(cons);
	}
	
	public int getQuantidadeBarcosAdiconados(){
		int total = 0;
		for (TipoBarco tipo : TipoBarco.values()){
			JLabel barco = this.getBarco(tipo);
			total += Integer.parseInt(barco.getText());
		}
		return total;
	}

	public void incrementarQuantidadeBarco(TipoBarco tipo){
		JLabel barco = this.getBarco(tipo);
		
		int valor = Integer.parseInt(barco.getText()) + 1;
		barco.setText(String.valueOf(valor));
		this.validate();
	}
	
	public int getQuantidadeBarco(TipoBarco tipo){
		JLabel barco = this.getBarco(tipo);
		return Integer.parseInt(barco.getText());
	}
	
	public JLabel getBarco(TipoBarco tipo){
		for (JPanel barco : barcos){
			if (barco.getName().equals(tipo.name())){
				return (JLabel) barco.getComponent(1);
			}
		}
		return null;
	}
	
	public void inicializarBarcos() {
		barcos = new JPanel[TipoBarco.values().length];
		
		int i = 0;
		for (TipoBarco tipo : TipoBarco.values()){
			barcos[i] = new JPanel();
			barcos[i].setBackground(Color.BLACK);
			       
			barcos[i].setName(tipo.name());
			barcos[i].setLayout(new FlowLayout());
			
			barcos[i].add(newJLabel(tipo.name()+":", tipo.getCor()));
			barcos[i].add(newJLabel("0", Color.WHITE));
			barcos[i].add(newJLabel(" / "+tipo.getQuantidade(), Color.WHITE));
			i++;
		}
	}
	
	private void adicionarBarcosAoPainel(GridBagConstraints cons) {
		int i = 0;
		for (int y = 0; y < TipoBarco.values().length; y++){
			cons.gridy = 2;			
			cons.gridx = i;
			i += 4;
			
			this.add(barcos[y], cons);
		}
	}

	private JLabel newJLabel(String texto, Color cor){
		JLabel label = new JLabel(texto);
		label.setForeground(cor);
		
		return label;
	}
}






















