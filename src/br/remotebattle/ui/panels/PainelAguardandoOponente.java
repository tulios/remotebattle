package br.remotebattle.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class PainelAguardandoOponente extends JPanel {

	JLabel textoEspera;
	JButton botaoCancelar;
	JProgressBar barraEspera;
	
	public PainelAguardandoOponente(String textoEspera) {
		super();
		
		this.textoEspera = new JLabel(textoEspera);
		this.botaoCancelar = new JButton("Cancelar");
		this.barraEspera = new JProgressBar();
		barraEspera.setIndeterminate(true);
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridx = 0;
		cons.gridy = 0;
		cons.insets = new Insets(2,20,2,20);
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		this.add(this.textoEspera,cons);
		
		cons.gridy = 1;
		this.add(barraEspera,cons);

		cons.fill = GridBagConstraints.NONE;
		cons.gridy = 2;
		this.add(botaoCancelar,cons);
	}
}
