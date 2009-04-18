package br.remotebattle.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import br.remotebattle.dominio.Jogador;

@SuppressWarnings("serial")
public class MapaJogo extends JPanel {

	private BlocoGrafico[][] mapaGrafico;
	private Jogador jogador;

	private BlocoGrafico root;
	private Color corPadraoBotao =  Color.WHITE;//new JButton().getBackground();

	public MapaJogo(Jogador jogador){
		this.jogador = jogador;
		this.setLayout(new GridBagLayout());
		
		inicializarBlocosGraficos();
		apresentarMapaGrafico();
	}

	public void inicializarBlocosGraficos(){
		mapaGrafico = new BlocoGrafico[getTamanho()][getTamanho()];
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
			preencherBlocosDoBardoDeVermelho(bloco);
		}
		liberarMapa();		
	}

	public void marcarArea(BlocoGrafico blocoAtual, boolean marcar){
		this.root = blocoAtual;
		final int TAMANHO = 3;
		
		List<BlocoGrafico> area = new ArrayList<BlocoGrafico>();
		area.add(root);
		area.addAll(getBlocosADireita(blocoAtual, TAMANHO));
		area.addAll(getBlocosAEsquerda(blocoAtual, TAMANHO));
		area.addAll(getBlocosAcima(blocoAtual, TAMANHO));
		area.addAll(getBlocosAbaixo(blocoAtual, TAMANHO));
		
		marcarBlocosDaArea(area, marcar);		
		
		if (marcar){
			bloquearBlocosForaDaArea();			
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
		return mapaGrafico[x][y];
	}
	
	public void setBloco(int x, int y){
		mapaGrafico[x][y] = new BlocoGrafico(this, x, y);
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
		return mapaGrafico;
	}
	
	/*
	 * 
	 * 
	 * Utilitários
	 * 
	 *
	 */

	public BlocoGrafico getBlocoADireita(BlocoGrafico blocoAtual){

		if(temVizinhoADireita(blocoAtual))
			return this.mapaGrafico[blocoAtual.getCoordX()+1][blocoAtual.getCoordY()];

		return null;
	}

	public List<BlocoGrafico> getBlocosADireita(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoADireita(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	public BlocoGrafico getBlocoAEsquerda(BlocoGrafico blocoAtual){

		if(temVizinhoAEsquerda(blocoAtual))
			return this.mapaGrafico[blocoAtual.getCoordX()-1][blocoAtual.getCoordY()];

		return null;
	}

	public List<BlocoGrafico> getBlocosAEsquerda(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoAEsquerda(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	public BlocoGrafico getBlocoAcima(BlocoGrafico blocoAtual){

		if(temVizinhoAcima(blocoAtual))
			return this.mapaGrafico[blocoAtual.getCoordX()][blocoAtual.getCoordY()-1];

		return null;
	}

	public List<BlocoGrafico> getBlocosAcima(BlocoGrafico blocoAtual, int quantidade){
		List<BlocoGrafico> lista = new ArrayList<BlocoGrafico>();

		for (int x = 0; x < quantidade; x++){
			blocoAtual = getBlocoAcima(blocoAtual);
			if (blocoAtual != null){
				lista.add(blocoAtual);
			}
		}

		return lista;
	}

	public BlocoGrafico getBlocoAbaixo(BlocoGrafico blocoAtual){

		if(temVizinhoAbaixo(blocoAtual))
			return this.mapaGrafico[blocoAtual.getCoordX()][blocoAtual.getCoordY()+1];

		return null;
	}

	public List<BlocoGrafico> getBlocosAbaixo(BlocoGrafico blocoAtual, int quantidade){
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
		return blocoAtual != null && blocoAtual.getCoordX() < mapaGrafico.length -1;
	}

	private boolean temVizinhoAEsquerda(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordX() > 0;
	}

	private boolean temVizinhoAcima(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordY() > 0;
	}

	private boolean temVizinhoAbaixo(BlocoGrafico blocoAtual) {
		return blocoAtual != null && blocoAtual.getCoordY() < mapaGrafico.length -1;
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
	
	public void adicionarTodosPelaDireita(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoADireita(bloco)) != null && bloco.getCoordX() != root.getCoordX()){
			lista.add(bloco);
		}
	}
	
	public void adicionarTodosPelaEsquerda(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoAEsquerda(bloco)) != null && bloco.getCoordX() != root.getCoordX()){
			lista.add(bloco);
		}
	}
	
	public void adicionarTodosParaBaixo(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoAbaixo(bloco)) != null && bloco.getCoordY() != root.getCoordY()){
			lista.add(bloco);
		}
	}
	
	public void adicionarTodosParaCima(List<BlocoGrafico> lista, BlocoGrafico bloco){
		lista.add(bloco);
		while ((bloco = getBlocoAcima(bloco)) != null && bloco.getCoordY() != root.getCoordY()){
			lista.add(bloco);
		}
	}
	
	public void preencherBlocosDoBardoDeVermelho(BlocoGrafico blocoSelecionado){
		for (BlocoGrafico bloco : getPartesBarco(blocoSelecionado)){
			if (!bloco.isMarcado()){
				bloco.setBackground(Color.RED);
				bloco.setMarcado(true);
			}
		}
		getRoot().setMarcado(true);
		getRoot().setEnabled(true);
	}
	
	public boolean isBarco(BlocoGrafico bloco){
		return bloco.getBackground() == Color.RED;
	}
	
	public boolean isRoot(BlocoGrafico bloco){
		return bloco.getCoordX() == getRoot().getCoordX() && bloco.getCoordY() == getRoot().getCoordY();
	}
	
	public void liberarMapa(){
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

	private void marcarBlocosDaArea(List<BlocoGrafico> area, boolean marcar){
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
	
	public void bloquearBlocosForaDaArea(){
		for(int y = 0; y < getTamanho(); y++){
			for (int x = 0; x < getTamanho(); x++){
				if (!getBloco(x,y).isSelecionando()){
					getBloco(x,y).setEnabled(false);
				}
			}
		}
	}
	
	public List<BlocoGrafico> getPartesBarco(BlocoGrafico bloco){
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




















