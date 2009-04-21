package br.remotebattle.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mapa implements Serializable {

	private Bloco[][] blocos;
	private List<Barco> barcos;
	
	public Mapa(int tamanho){
		blocos = new Bloco[tamanho][tamanho];
		inicializarBlocos();
		barcos = new ArrayList<Barco>();
	}
	
	private void inicializarBlocos(){
		int x = 0;
		int y = 0;
		
		for (Bloco[] linhaBlocos : blocos) {
			for(Bloco bloco : linhaBlocos){
				bloco = new Bloco(x++,y);
			}

			y++;
		}
	}
	
	public boolean isGameOver(){
		int numeroBarcosDestruidos = 0;
		
		for(Barco barco : this.barcos){
			if(barco.isDestruido())
				numeroBarcosDestruidos++;
		}
		
		if(numeroBarcosDestruidos == this.barcos.size())
			return true;
		
		return false;
	}
	
	public void adicionarBarco(Barco barco){
		barcos.add(barco);
	}
	
	public List<Barco> getBarcos(){
		return this.barcos;
	}
	
	public Bloco getBlocoADireita(Bloco blocoAtual){
		
		if(temVizinhoADireita(blocoAtual))
			return this.blocos[blocoAtual.getX()+1][blocoAtual.getY()];
		
		return null;
	}
	
	public Bloco getBlocoAEsquerda(Bloco blocoAtual){
		
		if(temVizinhoAEsquerda(blocoAtual))
			return this.blocos[blocoAtual.getX()-1][blocoAtual.getY()];
		
		return null;
	}
	
	public Bloco getBlocoAcima(Bloco blocoAtual){
		
		if(temVizinhoAcima(blocoAtual))
			return this.blocos[blocoAtual.getX()][blocoAtual.getY()-1];
		
		return null;
	}
	
	public Bloco getBlocoAbaixo(Bloco blocoAtual){
		
		if(temVizinhoAbaixo(blocoAtual))
			return this.blocos[blocoAtual.getX()][blocoAtual.getY()+1];
		
		return null;
	}

	private boolean temVizinhoADireita(Bloco blocoAtual) {
		return blocoAtual != null && blocoAtual.getX() < blocos.length -1;
	}
	
	private boolean temVizinhoAEsquerda(Bloco blocoAtual) {
		return blocoAtual != null && blocoAtual.getX() > 0;
	}
	
	private boolean temVizinhoAcima(Bloco blocoAtual) {
		return blocoAtual != null && blocoAtual.getY() > 0;
	}
	
	private boolean temVizinhoAbaixo(Bloco blocoAtual) {
		return blocoAtual != null && blocoAtual.getY() < blocos.length -1;
	}
	
	public Bloco getBloco(int x, int y){
		return this.blocos[x][y];
	}
	
	public Bloco getBloco(Coordenada coordenada){
		return this.blocos[coordenada.getX()][coordenada.getY()];
	}
	
	public int getTamanho(){
		return blocos.length;
	}
}
