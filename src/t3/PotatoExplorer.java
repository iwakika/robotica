package t3;

import java.util.ArrayList;

import javax.microedition.location.Orientation;

import org.apache.commons.cli.PosixParser;
import org.jfree.date.AnnualDateRule;
import org.jfree.date.RelativeDayOfWeekRule;

import lejos.robotics.kinematics.RobotArm;
import t3.Mapa.EnumMapa;
import t3.Nodo.EnumStatus;

/** * 
 * @author Francisca
 *
 */

public class PotatoExplorer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}		
	
	
public static PotatoRobo robo = PotatoRobo.getInstance();
public static Mapa mapa = Mapa.getInstance();
public static ArrayList<Nodo> nodoListExplorados = new ArrayList<Nodo>();
private int cont = 0;
private static boolean olharParaTraz =  true;

private static ArrayList<Nodo> produtoList = new ArrayList<Nodo>();
private static ArrayList<EnumProduto> produtoToColetar = new ArrayList<EnumProduto>();
private static ArrayList<ArrayList<Nodo>> caminhoProdutoList = new ArrayList<ArrayList<Nodo>>();

@SuppressWarnings("static-access")
public PotatoExplorer( int  posicaoRoboI, int posicaoRoboJ, EnumDirecao direcaoRobo, boolean olharParaTraz){
	robo.setDirecaoRobo(direcaoRobo);
	//considera que a cabe�a esta sempre a mesma direcao do robo
	//A dire��o da cabe�a � global e n�o em rela��o ao robo
	robo.setDirecaoCabeca(direcaoRobo);
	robo.nodoAtual = Mapa.getNodo(posicaoRoboI, posicaoRoboJ);
	mapa.setMatrizSimulacao(mapa.createDefaultMapa());
	
		

}



/* s� existe um mapa e um robo
public PotatoExplorer(PotatoRobo robo, Mapa mapa) {
	this.robo = robo;
	this.mapa = mapa;
		
	}	*/

	
	@SuppressWarnings("static-access")
	public void explorerMapa(Nodo nodoAtual, ArrayList<Nodo> caminho,int volta) throws InterruptedException {
	//System.out.println(cont++);
		if(mapa.getMatrizSimulacao()[nodoAtual.getI()*2][nodoAtual.getJ()*2] == 0) {
			mapa.getMatrizSimulacao()[nodoAtual.getI()*2][nodoAtual.getJ()*2] = 1;
		}
	
		nodoAtual.setNodoPercorrido(true);		
		robo.nodoAtual = nodoAtual;
		
		if(nodoAtual.getStatus() == EnumStatus.N_EXPLORADO)
		{
			
			procuraProduto(nodoAtual);
			
			addAdjacentes(nodoAtual);
			//Nodo nodoAnterior = null;	
				
			Nodo nodoFrente = nodoAtual.getNodoFrente();
		    Nodo nodoEsquerda = nodoAtual.getNodoEsquerda();
		    Nodo nodoDireita = nodoAtual.getNodoDireita();
		    Nodo nodoTraz = nodoAtual.getNodoTraz();
			caminho.add(nodoAtual);
			
			//System.out.println(robo.nodoAtual.getNome());
	
			if(nodoFrente!= null && nodoFrente.getStatus()  == EnumStatus.N_EXPLORADO && !nodoFrente.isNodoPercorrido() ) {
				robo.Move4dDistancia(EnumDirecao.FRENTE, robo.getDirecaoRobo(), mapa.tamanhoQuadros);
				explorerMapa(nodoFrente, caminho, 0);
				
			}
			if(nodoEsquerda != null && nodoEsquerda.getStatus()  == EnumStatus.N_EXPLORADO && !nodoEsquerda.isNodoPercorrido()) {
				robo.Move4dDistancia(EnumDirecao.ESQUERDA, robo.getDirecaoRobo(), mapa.tamanhoQuadros);
				explorerMapa(nodoEsquerda,caminho, 0);
			}
			if(nodoDireita!= null && nodoDireita.getStatus()  == EnumStatus.N_EXPLORADO && !nodoDireita.isNodoPercorrido()) {
				robo.Move4dDistancia(EnumDirecao.DIREITA, robo.getDirecaoRobo(), mapa.tamanhoQuadros);
				explorerMapa(nodoDireita,caminho, 0);
				
			}
			if(nodoTraz != null && nodoTraz.getStatus()  == EnumStatus.N_EXPLORADO && !nodoTraz.isNodoPercorrido()) {
				robo.Move4dDistancia(EnumDirecao.TRAZ, robo.getDirecaoRobo(), mapa.tamanhoQuadros);
				explorerMapa(nodoTraz,caminho , 0);
			}			
			addNodoEplorado(nodoAtual);			
			nodoAtual.setStatus(EnumStatus.EXPLORADO);
			
		}else {
			
		}
		
		if(Mapa.isTodoExplorado()){
			//System.out.println("TODOS EXPLORADOS");			
				
			
		}else{
			volta++;
			voltaCaminho(nodoAtual, caminho, volta);
			
			//robo.manager.andaCaminho(caminho, robo.getDirecaoRobo(), mapa.tamanhoQuadros, true);
		}
		//String mapaS = "\n"+ mapa.imprimeRoboEmString(robo.desenhaRobo(),robo.nodoAtual.getI(),robo.nodoAtual.getJ(),true);
		
		//System.out.println(mapaS);
		
		//caminho inverso
		//volta o caminho percorrido
		
	}
	
	public void voltaCaminho(Nodo nodoAtual, ArrayList<Nodo> caminho, int volta) throws InterruptedException {
		robo.nodoAtual = nodoAtual;
		int idx = caminho.size()- (volta +1);		
		Nodo nodoAnterior = caminho.get(idx);
		EnumDirecao direcao = direcaoNodoAdjacente(nodoAtual, nodoAnterior);	
		volta++;
		//caminho.add(volta);
		//caminho.remove(caminho.size()-1);
		
		
		robo.Move4dDistancia(direcao, robo.getDirecaoRobo(), mapa.tamanhoQuadros);	
		explorerMapa(nodoAnterior, caminho, volta);
	}
	
	public EnumDirecao direcaoNodoAdjacente(Nodo pai ,Nodo adjacente) {
		
		if(pai.getNodoFrente() != null && pai.getNodoFrente().equals(adjacente)) {
			return EnumDirecao.FRENTE;
		}else if (pai.getNodoEsquerda() != null && pai.getNodoEsquerda().equals(adjacente)) {
			return EnumDirecao.ESQUERDA;
		}else if (pai.getNodoDireita() != null && pai.getNodoDireita().equals(adjacente)) {
			return EnumDirecao.DIREITA;
		}else if(pai.getNodoTraz() != null && pai.getNodoTraz().equals(adjacente)) {
			return EnumDirecao.TRAZ;			
		}
				
		
		return null;
		
	}
	
	public static String imprimeProdutoPosicoes() {
		String listaProdutos = "Produtos:";
		for(Nodo n : produtoList) {
		 listaProdutos += n.getNome()+";"; 
		}		
		
		return listaProdutos;
	}
	
	@SuppressWarnings("static-access")
	public static void coletaProduto(Nodo nodoOrigem) {
		robo.nodoAtual = Mapa.getNodo(6, 0);		
		ArrayList<ArrayList<Nodo>>  caminhosList = getCaminhoProdutoList(nodoOrigem);
		
		for(ArrayList<Nodo> caminho : caminhosList) {
			try {
				robo.manager.andaCaminho(caminho, robo.getDirecaoRobo(), Mapa.tamanhoQuadros, false);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	

/**
 * Nodo faz parte do caminho do percorrido atualmentes? 
 * @param nodo
 * @param caminho
 * @return
 */
	private boolean isNodoNoCaminho(Nodo nodo, ArrayList<Nodo> caminho){

		boolean  isNodoInCaminho = false;
		
		for(int i = 0 ; i<caminho.size(); i++){
		
			if(caminho.get(i).equals(nodo)){
			isNodoInCaminho = true;
			break;
			}
		}
		
		return isNodoInCaminho;		
	  
		
	}

	/**
	 * Adiona Ajacentes de cada direcao encontrada em rela��o ao mapa global
	 * @param nodoAtual
	 */


	private void addAdjacentes(Nodo nodoAtual) {
		int i = nodoAtual.getI();
		int j = nodoAtual.getJ();
		String adjacentes = "";		
	
		if(isCaminho(EnumDirecao.DIREITA, nodoAtual) ) {
			
			Nodo n = Mapa.getNodo(i, j+1);
			
			nodoAtual.setNodoDireita(n);
			nodoAtual.getNodoDireita().setNodoEsquerda(nodoAtual);
			adjacentes+= ";Direita"+ n.getNome();;
			
			
		}
		
		if(isCaminho(EnumDirecao.ESQUERDA, nodoAtual)) {
			
			Nodo n = Mapa.getNodo(i, j - 1);
			
			nodoAtual.setNodoEsquerda(n);
			nodoAtual.getNodoEsquerda().setNodoDireita(nodoAtual);
			adjacentes+= ";Esquerda"+ n.getNome();;
			
		}
		
		if(isCaminho(EnumDirecao.TRAZ, nodoAtual) && olharParaTraz) {
			
			Nodo n = Mapa.getNodo(i -1, j);
			
			nodoAtual.setNodoTraz(n);
			nodoAtual.getNodoTraz().setNodoFrente(nodoAtual);	
			adjacentes+= ";traz"+ n.getNome();;
			
		}
		
		if(isCaminho(EnumDirecao.FRENTE, nodoAtual)){
			
			Nodo n = Mapa.getNodo(i+1, j);
					
			nodoAtual.setNodoFrente(n);
			nodoAtual.getNodoFrente().setNodoTraz(nodoAtual);	
			adjacentes+= ";Frente-" + n.getNome();
		}	
		
		
		
		
		//System.out.println(nodoAtual.getNome() + adjacentes);
	
				
	}
	
			/**
			 * � caminho se n�o encontrou parede
			 * @param direcao
			 * @return
			 */
	@SuppressWarnings("static-access")
	private boolean isCaminho(EnumDirecao direcao, Nodo nodoAtual) {
		boolean iscaminho = false;
		// verifica se lugar esta dentro do mapa
		if(isDirecaoDentroMapSize(nodoAtual.getI(), nodoAtual.getJ(), direcao)) {
			//busca parade;
			robo.moveCabeca(direcao);
			iscaminho = ! robo.manager.encontrouParede();	
		}
			
		
		return iscaminho;
		
	}
	
	/**
	 * Verifica se a dire��o est� dentro do limite do mapa
	 */	
	private boolean isDirecaoDentroMapSize(int i, int j, EnumDirecao direcao) {
				
		//|        | traz |
		//|esquerfa|   x  |direita|
		//| 	   |frente|
		
		//FRENTE
		if(direcao == EnumDirecao.FRENTE) {
			return isDentroMapSize(i+1, j);
		}else if (direcao == EnumDirecao.DIREITA) {
			return isDentroMapSize(i, j+1);
		}else if (direcao == EnumDirecao.ESQUERDA) {
			return isDentroMapSize(i, j-1);
		}else if (direcao == EnumDirecao.TRAZ) {
			return isDentroMapSize(i-1, j);
		}else {
			return false;
		}
	}
	
	/**Verifica se o valor n�o est� fora do limite do mapa
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	
	private boolean isDentroMapSize(int i, int j) {
		boolean retorno = true;
		
		//verifica i
		if(i >= mapa.sizeI) {
			retorno = false;
			
		}else if(i < 0) {
		   retorno = false;	
		}
		//verifica J
		
		if(j >= mapa.sizeJ) {
			retorno = false;
			
		}else if(j < 0) {
		   retorno = false;	
		}
		
		return retorno;
	}	
	
	public static void addNodoEplorado(Nodo nodo){
		if(nodoListExplorados != null){
		nodoListExplorados.add(nodo);	
		}else{
			nodoListExplorados = new ArrayList<Nodo>();
		}
	
	}
	
	public boolean isTodoExplorado(){
		if(nodoListExplorados.size() == (mapa.sizeI * mapa.sizeJ)){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public static void procuraProduto(Nodo nodo){
		
		if(isProduto(robo.observaCor(), nodo)){
	
			//coletar produto
			if(produtoToColetar.contains(nodo.getProduto())) {
			addProdutList(nodo);
			}
		}
		
	}

	public static boolean isProduto(int[] cor, Nodo nodo){
		boolean isProduto = false;
		int r = cor[0];
		int g = cor[1];
		int b = cor[2];
		
		if(r > 225){
		
			if(g >  225){
				if(b > 225){					
					nodo.setProduto(EnumProduto.PRODUTO_1_PRETO);					
					isProduto = true;
				}
			}
		}
		return isProduto;
		
		
	}
	
	/**
	 * Calcula o Menor caminho para o Objetivo e alimenta a list caminhoProdutoList
	 * @param nodoOrigem
	 */
	
	public static void menorCaminhoAteprodutos(Nodo nodoOrigem){
		Dijkstra d = new Dijkstra(nodoListExplorados);
		int tamanhoTotal = 0;
		for(Nodo objetivo : produtoList){
			
			System.out.println("\nOrigem:"+ nodoOrigem.getNome() + ", Objetivo:" + objetivo.getNome());
			d.dijkstra(nodoOrigem, objetivo, false);
			
			System.out.println("Fila:" + d.imprimeFila());
			System.out.println("Tamanho:"+ d.getFila().size());
			tamanhoTotal += d.getFila().size() -1;
			
			caminhoProdutoList.add(d.getFila());
			
			///retorna caminho e addnuma lista;
			
			nodoOrigem = objetivo;
			
		}
		
		System.out.println("TamanhoTotal:"+ tamanhoTotal);
		
		
	}
	
	public static void menorCaminhoAteprodutos2(Nodo nodoOrigem, int tamanhoTotal){
		Dijkstra d = new Dijkstra(nodoListExplorados);
		ArrayList<Nodo> menorFila = null;
		Nodo menorObjetivo = null;
		int menorTamanho = Integer.MAX_VALUE;
		
		
		for(Nodo objetivo : produtoList){
			d.dijkstra(nodoOrigem, objetivo, false);
			if(d.getFila().size() < menorTamanho) {
				menorTamanho = d.getFila().size();
				menorFila = d.getFila();
				menorObjetivo = objetivo;
			}
		}
		
		produtoList.remove(menorObjetivo);
		tamanhoTotal += menorFila.size() -1;
		caminhoProdutoList.add(menorFila);
		
		System.out.println("\nOrigem:"+ nodoOrigem.getNome() + ", Objetivo:" + menorObjetivo.getNome());
		System.out.println("Fila:" + d.imprimeFila());
		System.out.println("Tamanho:"+ menorFila.size());		
		
		if(produtoList.size() > 0) {
			menorCaminhoAteprodutos2(menorObjetivo, tamanhoTotal);
		}
		
		System.out.println("TamanhoTotal:"+ tamanhoTotal);
		
	
	}	
	
	
	
	public static ArrayList<ArrayList<Nodo>> getCaminhoProdutoList(Nodo nodoOrigem) {
		int size = caminhoProdutoList.size() ;
		if(size <= 0) {
			//menorCaminhoAteprodutos(nodoOrigem);
			//System.err.println("ESCOLHER UM DOS CAMINHOS!");
			menorCaminhoAteprodutos2(nodoOrigem, 0);
		}
		
		return caminhoProdutoList;
	}



	public static void addProdutList(Nodo nodo){		
		produtoList.add(nodo);		
	}
				
	public static void setNodoListExplorados(ArrayList<Nodo> nodoListExplorados) {
		nodoListExplorados = nodoListExplorados;
	}



	public static ArrayList<EnumProduto> getProdutoToColetar() {
		return produtoToColetar;
	}



	public static void setProdutoToColetar(ArrayList<EnumProduto> produtoToColetar) {
		PotatoExplorer.produtoToColetar = produtoToColetar;
	}

}
