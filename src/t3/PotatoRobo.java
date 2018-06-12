package t3;

import java.util.ArrayList;

import org.apache.bcel.generic.CALOAD;
import org.apache.bcel.generic.SWITCH;

public class PotatoRobo {
	//posicao global
	private static EnumDirecao direcaoRobo = EnumDirecao.FRENTE;
	//posicaoGlobal
	private static EnumDirecao direcaoCabeca = EnumDirecao.FRENTE;
	private static PotatoRobo instance = null;
	//public static PotatoManegerTeste manager = new  PotatoManegerTeste();//new PotatoManager();	
	public static PotatoManeger manager = new PotatoManeger();	
	public static Nodo nodoAtual;

	//private static NodoAtual;
	
	private PotatoRobo() {
		
	}
	public static PotatoRobo getInstance() {
		if(instance == null) {
			instance = new PotatoRobo();
		}
		return instance;
	}
	
	public static EnumDirecao getDirecaoRobo() {
		return direcaoRobo;
	}
	
	/**
	 * Define Direcao global do robo;
	 */
	public static void setDirecaoRobo(EnumDirecao direcaoRobo) {
		PotatoRobo.direcaoRobo = direcaoRobo;
	}
	public static EnumDirecao getDirecaoCabeca() {
		return direcaoCabeca;
	}
	/**
	 * Define a Direção global da cabeça.
	 * @param direcaoCabeca
	 */
	public static void setDirecaoCabeca(EnumDirecao direcaoCabeca) {
		PotatoRobo.direcaoCabeca = direcaoCabeca;
		
	}
	
	@SuppressWarnings("static-access")
	public static int[] observaCor(){		
		return manager.observaCor();
		
	}
	
	@SuppressWarnings("static-access")
	/**
	 * Move a cabeça do povo para 
	 * @param direcao
	 */
	public static void moveCabeca(EnumDirecao direcao) {		
		
		
		
		//direcaoCabeca = direcao;		
		int valorDirecional = direcao.valorDirecional(direcaoCabeca.valor);
		valorDirecional = modifica(valorDirecional, direcao);
		manager.viraDirecionadaCabeca4d(valorDirecional);		
		
		
	}
	
	/**ajuste de direcao.
	 * força que a rotação seja feita pelo o lado da frente do robo
	 * 
	 * @param valorDirecional
	 * @param direcao
	 * @return
	 */
	private static int modifica(int valorDirecional, EnumDirecao direcao) {
		if(direcaoCabeca == EnumDirecao.TRAZ && direcao == EnumDirecao.FRENTE && direcaoRobo == EnumDirecao.ESQUERDA) {
			if(valorDirecional > 0) {
			valorDirecional= - valorDirecional;
			//System.out.print("INVERTE1");
			}
		}else if(direcaoCabeca == EnumDirecao.FRENTE && direcao == EnumDirecao.TRAZ && direcaoRobo == EnumDirecao.DIREITA) {
			if(valorDirecional > 0) {
			valorDirecional= - valorDirecional;
			//System.out.print("INVERTE2");
			}
		}else if(direcaoCabeca == EnumDirecao.DIREITA && direcao == EnumDirecao.ESQUERDA && direcaoRobo == EnumDirecao.TRAZ) {
			if(valorDirecional > 0) {
			valorDirecional= - valorDirecional;
			//System.out.print("INVERTE3");
			}	
		}else if(direcaoCabeca == EnumDirecao.ESQUERDA && direcao == EnumDirecao.DIREITA && direcaoRobo == EnumDirecao.FRENTE) {
			if(valorDirecional < 0) {
			valorDirecional= - valorDirecional;
			//System.out.print("INVERTE4");
			}
		}
		
		return valorDirecional;
	}
	
	
	
	/**
	 * Em relação a matriz Global
	 * @return
	 */
	public static String desenhaRobo(){
		String robo = "";
		String cabeca ="";
		
		//DesenhaCorpo
	switch (direcaoRobo) {
		
		case FRENTE:
			 robo = "v";		
			break;
		case TRAZ:
			robo = "^";
			break;
			
		case DIREITA:
			robo = ">";
			break;
			
		case ESQUERDA:
			robo = "<";
			break;
				
		default:
			break;
		}
		//cabeca = desenhaCabeca();	
		switch (direcaoCabeca) {
		
		case FRENTE:
			 cabeca = "v";		
			break;
		case TRAZ:
			cabeca = "^";
			break;
			
		case DIREITA:
			cabeca = ">";
			break;
			
		case ESQUERDA:
			cabeca= "<";
			break;
			
		default:
			break;
		}
			
		
				
		return robo +"."+ cabeca;
	}
	
@SuppressWarnings("static-access")

/**
 * Movimentação Global do RObo, e movimenta a cabeça para frente Relativa do Robo
 * @param direcaoParaIr
 * @param direcaoRobo
 * @param distancia
 */
public static void Move4dDistancia(EnumDirecao direcaoParaIr, EnumDirecao direcaoRobo, int distancia){
		//PotatoRobo.direcaoRobo = direcaoParaIr;
		//EnumDirecao direcaoParaIrCabeca = manager.direcaoParaIrGlobal(direcaoCabeca , direcaoParaIr);
		//moveCabeca(direcaoRobo);
		
		//int valoDirecional  =  direcaoParaIr.valorDirecional(direcaoRobo.valor);
		//direcaoCabeca = giraDirecao(valoDirecional,  direcaoCabeca);
		//System.out.println(valoDirecional);
				
		//System.out.println(direcaoCabeca.valorDirecional(direcaoRobo.valor));
		manager.Move4dDistancia(direcaoParaIr, direcaoRobo, distancia);
		if(direcaoParaIr != direcaoRobo){
			manager.andarDistancia(5);
		}
		

		
	
	
		//viraDirecionada4d(valoDirecional);
	}
/**
 * Percorre um  Array de caminho. 
 * @param caminho Array com os nodos que compoem o caminho.Considera o objeto de Posição "0" como a posicao atual;
 * @param direcaoRobo
 * @param distanciaMapa
 * @param posicaoRoboI
 * @param posicaoRoboJ
 * @throws InterruptedException
 */
@SuppressWarnings("static-access")
public static void andaCaminho(ArrayList<Nodo> caminho, EnumDirecao direcao, int tamanhoQuadros, boolean inverso) throws InterruptedException {
	
	manager.andaCaminho(caminho, direcao, tamanhoQuadros, inverso);
}

public static EnumDirecao direcaoPorID(int direcaoId){

		switch (direcaoId) {
		case 0:
			return EnumDirecao.TRAZ;
		case 1:
			return EnumDirecao.DIREITA;
		case 2:
			return EnumDirecao.FRENTE;
		case 3:
			return EnumDirecao.ESQUERDA;
		default:
			return EnumDirecao.FRENTE;
		}
	
}

/***
 * GiraA direção relativa ao valor direcional executado 
 * @param valorDirecional
 * @param direcaoAtual
 * @return
 */

public static EnumDirecao giraDirecao(int valorDirecional, EnumDirecao direcaoAtual){
	
	//int valordirecional = direcaoAtual.valorDirecional(direcao.valor);
	
	int valor = direcaoAtual.valor;
	if(valorDirecional < 1){
	for(int i = 0;  i > valorDirecional; i--){
		
		
		valor--;
		if(valor <0){
			valor = 3;
		}
		
	}
	}else{
	
		for(int i = 0; i > valorDirecional; i++){			

			valor++;
			if(valor >3){
				valor = 0;
			}
			
		}
	}
	
	return direcaoPorID(valor);
}
	
	

}


