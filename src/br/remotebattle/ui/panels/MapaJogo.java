package br.remotebattle.ui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.remotebattle.controller.IniciarJogoController;
import br.remotebattle.dominio.Jogador;
import br.remotebattle.dominio.enums.TipoBarco;
import br.remotebattle.ui.UIMain;
import br.remotebattle.ui.panels.componentes.BlocoGrafico;

@SuppressWarnings("serial")
public class MapaJogo extends JPanel {
	private static final int TAMANHO_AREA = 5;

	private BlocoGrafico[][] blocos;
	private Jogador jogador;

	private JButton iniciarJogo;
	
	private BlocoGrafico root;
	private Color corPadraoBotao =  Color.WHITE;
	
	private IniciarJogoController controller;
	
	/**
	 * Construtor	
	 * @param jogador - {@link Jogador}
	 */
	public MapaJogo(Jogador jogador){
		this.jogador = jogador;
		this.setLayout(new GridBagLayout());
		
		controller = new IniciarJogoController();

		inicializarBlocosGraficos();
		apresentarMapaGrafico();
	}

	private void inicializarBlocosGraficos(){
		blocos = new BlocoGrafico[getTamanho()][getTamanho()];
	}

	private void apresentarMapaGrafico(){
		GridBagConstraints cons = new GridBagConstraints();

		for(int y = 0; y < getTamanho(); y++){
			for (int x = 0; x < getTamanho(); x++){
				setBloco(x, y);

				cons.gridx = x;
				cons.gridy = y;
				this.add(getBloco(x, y), cons);
			}
		}
	}

	public void preencherBarco(BlocoGrafico bloco){
		if (!isRoot(bloco)){
			preencherBlocosDoBarcoComACor(bloco);
		}
		liberarMapa();		
	}

	public void marcarArea(BlocoGrafico blocoAtual, boolean marcar){
		this.root = blocoAtual;

		List<BlocoGrafico> area = new ArrayList<BlocoGrafico>();
		area.add(root);
		area.addAll(getBlocosADireita(blocoAtual, TAMANHO_AREA));
		area.addAll(getBlocosAEsquerda(blocoAtual, TAMANHO_AREA));
		area.addAll(getBlocosAcima(blocoAtual, TAMANHO_AREA));
		area.addAll(getBlocosAbaixo(blocoAtual, TAMANHO_AREA));

		marcarBlocosDaAreaDeSelecao(area, marcar);		

		if (marcar){
			bloquearBlocosForaDaAreaDeSelecao();			
		}else{
			liberarMapa();
		}

		this.validate();
	}

	/*
	 * 
	 * getters and setters
	 * 
	 */

	public BlocoGrafico getBloco(int x, int y){
		return blocos[x][y];
	}

	public void setBloco(int x, int y){
		blocos[x][y] = new BlocoGrafico(this, x, y);
	}

	public int getTamanho(){
		return jogador.getMapa().getTamanho();
	}

	public BlocoGrafico getRoot() {
		return root;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public BlocoGrafico[][] getMapaGrafico() {
		return blocos;
	}

	/*
	 * 
	 * 
	 * Utilitários
	 * 
	 *
	 */

	public boolean isBarco(BlocoGrafico bloco){
		return TipoBarco.isBarco(bloco.getBackground());
	}

	public boolean isRoot(BlocoGrafico bloco){
		return bloco.getCoordX() == getRoot().getCoordX() && bloco.getCoordY() == getRoot().getCoordY();
	}
	
	public boolean isMesmaLinhaDoRoot(BlocoGrafico bloco){
		return bloco.getCoordY() == root.getCoordY();
	}

	public boolean isAEsquerdaDoRoot(BlocoGrafico bloco){
		return bloco.getCoordX() < root.getCoordX();
	}

	public boolean isAcimaDoRoot(BlocoGrafico bloco){
		return bloco.getCoordY() < root.getCoordY();
	}
	
	private BlocoGrafico getBlocoADireita(BlocoGrafico blocoAtual){

		if(temVizinhoADireita(blocoAtual))
			return this.blocos[blocoAtual.getCoordX()+1][blocoAtual.getCoordY()];

		return null;
	}

	private List<BlocoGrafico> getBlocosADireita(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoADireita(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	private BlocoGrafico getBlocoAEsquerda(BlocoGrafico blocoAtual){

		if(temVizinhoAEsquerda(blocoAtual))
			return this.blocos[blocoAtual.getCoordX()-1][blocoAtual.getCoordY()];

		return null;
	}

	private List<BlocoGrafico> getBlocosAEsquerda(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoAEsquerda(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	private BlocoGrafico getBlocoAcima(BlocoGrafico blocoAtual){

		if(temVizinhoAcima(blocoAtual))
			return this.blocos[blocoAtual.getCoordX()][blocoAtual.getCoordY()-1];

		return null;
	}

	private List<BlocoGrafico> getBlocosAcima(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoAcima(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	private BlocoGrafico getBlocoAbaixo(BlocoGrafico blocoAtual){

		if(temVizinhoAbaixo(blocoAtual))
			return this.blocos[blocoAtual.getCoordX()][blocoAtual.getCoordY()+1];

		return null;
	}

	private List<BlocoGrafico> getBlocosAbaixo(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoAbaixo(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	private boolean temVizinhoADireita(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordX() < blocos.length -1;
	}

	private boolean temVizinhoAEsquerda(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordX() > 0;
	}

	private boolean temVizinhoAcima(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordY() > 0;
	}

	private boolean temVizinhoAbaixo(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordY() < blocos.length -1;
	}

	private void adicionarTodosPelaDireita(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoADireita(bloco)) != null && bloco.getCoordX() != root.getCoordX()){
			lista.add(bloco);
		}
	}

	private void adicionarTodosPelaEsquerda(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoAEsquerda(bloco)) != null && bloco.getCoordX() != root.getCoordX()){
			lista.add(bloco);
		}
	}

	private void adicionarTodosParaBaixo(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoAbaixo(bloco)) != null && bloco.getCoordY() != root.getCoordY()){
			lista.add(bloco);
		}
	}

	private void adicionarTodosParaCima(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoAcima(bloco)) != null && bloco.getCoordY() != root.getCoordY()){
			lista.add(bloco);
		}
	}

	private void preencherBlocosDoBarcoComACor(BlocoGrafico blocoSelecionado){
		List<BlocoGrafico> partesBarco = getPartesBarco(blocoSelecionado);
		TipoBarco tipo = TipoBarco.newInstance(partesBarco.size());
		
		if (aindaPossoAdicionarBarco(tipo)){
			marcarBarco(partesBarco, tipo);
		}else{
			getRoot().setMarcado(false);
		}
		
		getRoot().setEnabled(true);				
	}

	private void marcarBarco(List<BlocoGrafico> partesBarco, TipoBarco tipo) {
		atualizarInformacoesSobreBarcos(tipo);
		pintarBarco(partesBarco, tipo);
		getRoot().setMarcado(true);
		
		//descobre quando o usuário colocou todos os barcos
		if (UIMain.getInfo().getQuantidadeBarcosAdiconados() == TipoBarco.getQuantidadeDisponivel()){
			iniciarJogo = new JButton("Iniciar jogo");
			iniciarJogo.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.execute();
				}
			});
			UIMain.getRodape().add(iniciarJogo);
			UIMain.getRodape().validate();
		}
	}

	private void pintarBarco(List<BlocoGrafico> partesBarco, TipoBarco tipo) {
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();
		
		for (BlocoGrafico bloco : partesBarco){
			if (!bloco.isMarcado()){
				bloco.setBackground(tipo.getCor());
				bloco.setMarcado(true);
				
				lista.add(bloco);
			}
		}
		
		controller.gerarBarco(lista);
	}

	private boolean aindaPossoAdicionarBarco(TipoBarco tipo) {
		return tipo.getQuantidade() > UIMain.getInfo().getQuantidadeBarco(tipo);
	}

	private void atualizarInformacoesSobreBarcos(TipoBarco tipo) {
		UIMain.getInfo().incrementarQuantidadeBarco(tipo);
	}

	private void liberarMapa(){
		for(int y = 0; y < getTamanho(); y++){
			for (int x = 0; x < getTamanho(); x++){

				if (!isBarco(getBloco(x, y))){
					getBloco(x, y).setSelecionando(false);
					getBloco(x, y).setBackground(corPadraoBotao);
					getBloco(x, y).setEnabled(true);
				}

			}
		}
	}

	private void marcarBlocosDaAreaDeSelecao(List<BlocoGrafico> area, boolean marcar){
		for (BlocoGrafico b: area){
			if (!b.isMarcado()){
				if (marcar){
					b.setBackground(Color.BLACK);
					b.setSelecionando(true);
				}else{
					b.setBackground(corPadraoBotao);
					b.setSelecionando(false);
				}
			}
		}
	}

	private void bloquearBlocosForaDaAreaDeSelecao(){
		for(int y = 0; y < getTamanho(); y++){
			for (int x = 0; x < getTamanho(); x++){
				if (!getBloco(x,y).isSelecionando()){
					getBloco(x,y).setEnabled(false);
				}
			}
		}
	}

	private List<BlocoGrafico> getPartesBarco(BlocoGrafico bloco){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		if (isMesmaLinhaDoRoot(bloco)){

			if (isAEsquerdaDoRoot(bloco)){
				adicionarTodosPelaDireita(lista, bloco);

			}else{
				adicionarTodosPelaEsquerda(lista, bloco);
			}

		}else{
			if (isAcimaDoRoot(bloco)){
				adicionarTodosParaBaixo(lista, bloco);

			}else{
				adicionarTodosParaCima(lista, bloco);
			}
		}

		//+ o root (1° bloco clicado, origem da seleção)
		lista.add(root);
		return lista;
	}
}




















