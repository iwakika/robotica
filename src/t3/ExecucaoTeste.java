package t3;

import java.util.ArrayList;

import lejos.nxt.Button;

public class ExecucaoTeste {
	
	private static int posicaoRoboI =6;
	private static int posicaoRoboJ =0;

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
	
		//Button.ENTER.callListeners();
		
		PotatoExplorer pe = new PotatoExplorer(posicaoRoboI,posicaoRoboJ, EnumDirecao.DIREITA, false);		
		ArrayList<EnumProduto> produtoToColetar = new ArrayList<EnumProduto>();
		produtoToColetar.add(EnumProduto.PRODUTO_0_PADRAO);
		
		try {
			pe.explorerMapa(pe.robo.nodoAtual,produtoToColetar);		
		
		Thread.sleep(400);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pe.voltaInicio();
		pe.coletaProduto(Mapa.getNodo(posicaoRoboI, posicaoRoboJ));
		
		
	}

}
