package t3;

import java.util.ArrayList;
import java.util.Collection;

import com.intel.bluetooth.Utils;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/** * 
 * @author Francisca
 *
 */

public class PotatoManeger {
	
	
	public static void main(String[] args) {
		
		//defineVelocidade(100);
		//motorCabeca.setSpeed(70);
		//motorCabeca.lock(70);
		/*String cor ="";

		cor += ",R"+ sensorCor.getColor().getRed();
		cor += ",G"+ sensorCor.getColor().getGreen();
		cor += ",B"+ sensorCor.getColor().getBlue()+"\n";
		
		
		int redmin = 999;
		int gremin =999;
		int blumin = 999;
		
		int redmax = 999;
		int gremax =999;
		int blumax = 999;
		
		
		while(!Button.ENTER.isDown()){
		
		System.out.println(cor);
		
		}
		
		while(!Button.ENTER.isDown()){
			
		}*/
		
		for (int i = 1 ; i<7; i++){
		andarRotate(PotatoManeger.dintanceToMotorRotate(26));
		motorCabeca.setSpeed(100);
		PotatoManeger.viraCabeca(PotatoManeger.grausToCabecaMotorRotate(90),EnumDirecao.DIREITA);
		System.out.println("   "+sensorUltrasonico.getDistance());
		//PotatoManager.viraCabeca(PotatoManager.grausToCabecaMotorRotate(90),EnumDirecao.DIREITA);
		//PotatoManager.viraCabeca(PotatoManager.grausToCabecaMotorRotate(90),EnumDirecao.ESQUERDA);
		PotatoManeger.viraCabeca(PotatoManeger.grausToCabecaMotorRotate(90),EnumDirecao.ESQUERDA);
		System.out.println("   "+sensorUltrasonico.getDistance());
		PotatoManeger.viraCabeca(PotatoManeger.grausToCabecaMotorRotate(90),EnumDirecao.ESQUERDA);
		System.out.println("   "+sensorUltrasonico.getDistance());
		PotatoManeger.viraCabeca(PotatoManeger.grausToCabecaMotorRotate(90),EnumDirecao.DIREITA);
		System.out.println("   "+sensorUltrasonico.getDistance());
		}
		//para();
		//motorCabeca.rotate(10);
		//Motor.A.rotate(3000);
		
		
		//PotatoManager.viraCabeca(PotatoManager.grausToCabecaMotorRotate(90),EnumDirecao.DIREITA);
	}
	
	/**
	 * Parametros de calibragem baseados na rotacao do motor
	 * 
	 *
	 */	
	
	private static final int VELOCIDADE = 150 ;
	private static int grau_90_MotorRotate_100  = 190;
	private static int grau_90_CabecaMotorRotate =100 ;
	///private static int distancia_10_MotorRotate_100 = 2400; 
	private static int distancia_10_MotorRotate_100 = 265; 
	private static int rotate_Calibragem_100 = 100;	
	private static NXTRegulatedMotor motorRodaDireita = Motor.B;
	private static NXTRegulatedMotor motorRodaEsquerda = Motor.C;
	private static NXTRegulatedMotor motorCabeca = Motor.A;
	private static int distancia_paredeUltraSonico;
	
	public static ColorSensor sensorCor = new ColorSensor(SensorPort.S4);
	public static UltrasonicSensor sensorUltrasonico = new UltrasonicSensor(SensorPort.S3);	
	public static boolean modoTeste = false;
	public static PotatoRobo robo = PotatoRobo.getInstance(); 
	
	public static void calibragemTeste() throws InterruptedException {
					
		motorCabeca.setSpeed(70);
		defineVelocidade(PotatoManeger.VELOCIDADE);	
		

	}
	
	public PotatoManeger(){
		motorCabeca.setSpeed(70);
	}
	
	public static void  menuCalibragemDistanca(){
	Boolean sair = false;
	System.out.println("Distancia_10");
	System.out.println("Atuaal:"+ distancia_10_MotorRotate_100 );
	
		while(sair){
			
			
			int buttonPress =Button.readButtons();
			
			switch (buttonPress) {
			
			case Button.ID_RIGHT:
				
				distancia_10_MotorRotate_100  += 10;
				
			break;
			case Button.ID_LEFT:

				distancia_10_MotorRotate_100  -= 10;
				
			break;
					
			case Button.ID_ENTER:

				sair = true;
				
			break;
				

			default:
				break;
			}
		
		}
		
		
	}
	
	public static void  menuCalibragemGrau(){
		Boolean sair = false;
		System.out.println("Grau_90");
		System.out.println("Atual:"+ grau_90_MotorRotate_100 );
		
			while(sair){
				
				
				int buttonPress =Button.readButtons();
				
				switch (buttonPress) {
				
				case Button.ID_RIGHT:
					
					grau_90_MotorRotate_100  += 10;
					
				break;
				case Button.ID_LEFT:

					grau_90_MotorRotate_100  -= 10;
					
				break;
						
				case Button.ID_ENTER:

					sair = true;
					
				break;
					

				default:
					break;
				}
								
				
			}
			
			
		}	
	
	
	public static void defineVelocidade(int velocidade) {
		motorRodaDireita.setSpeed(velocidade);
		motorRodaEsquerda.setSpeed(velocidade);
		
	}

	/**
 	*  Motor do robo rotaciona o angulo do motor "motorRotade" para direita ou para esquerda.	
 	* @param motorRotate rotacoes do motor
 	* @param direcao
 	*/
	public static void viraRotate (int motorRotate, EnumDirecao direcao) {
			
		motorRodaDireita.stop(true);
		motorRodaEsquerda.stop(true);
		
		boolean isMoving = false;
		while (isMoving){	
		isMoving = motorRodaDireita.isMoving() ||  motorRodaEsquerda.isMoving();			
		}	
	
		
		
		switch (direcao) {
			case DIREITA:
			motorRodaDireita.rotate(motorRotate);			
			motorRodaEsquerda.rotate(-motorRotate);
			break;
			
			case ESQUERDA:
				motorRodaDireita.rotate(-motorRotate);
				motorRodaEsquerda.rotate(motorRotate);
			break;	

		default:
			break;
		}
				
	}
	
	
	public static void viraAngulo (int angulo, EnumDirecao direcao) {
		
		viraRotate(grausToMotorRotate(angulo),direcao);
						
	}
	
	public static void para(){
		//while (
		//}
		motorRodaDireita.stop(true);
		motorRodaEsquerda.stop(true);
	}
	
	/**
	 * Anda para frente 
	 * @param distancia cm
	 */
	public static void andarDistancia(int distancia){
		andarRotate(dintanceToMotorRotate(distancia));
	}
	

	/**
	 * Anda para frente  
	 * @param motorRotate rotacao do motor
	 */
	public static void andarRotate(int motorRotate) {		
			 motorRodaDireita.rotate(motorRotate,true);
			 motorRodaEsquerda.rotate(motorRotate);					
		
	}

	
	/**Tranforma o valor do angulo em valores de rotacoes do motor
	 * 
	 * @param grau quantidade de graus que deseja obter o valor em rotacoes do motor
	 * @return
	 */
	
	
	public static int grausToMotorRotate(int grau){
		int motorRotate = (grau_90_MotorRotate_100 * grau) /90;
				
		return  motorRotate;
	}
	
	/**Tranforma o valor do angulo em valores de rotacoes do motor
	 * 
	 * @param grau quantidade de graus que deseja obter o valor em rotacoes do motor
	 * @return
	 */
	
	
	public static int grausToCabecaMotorRotate(int grau){
		int motorRotate = (grau_90_CabecaMotorRotate * grau) /90;
				
		return  motorRotate;
	}
	
	/**Tranforma o valor do angulo em valores de rotacoes do motor
	 * 
	 * @param grau quantidade de graus que deseja obter o valor em rotacoes do motor
	 * @return
	 */
	
	public static int dintanceToMotorRotate(int distancia){
	
		int motorRotate = (distancia_10_MotorRotate_100 * distancia ) / 10;
		return motorRotate ;
	}
	

	
	/**
	 * Movimenta robo para tal dire��o especificada em rela��o ao corpo do robo.
	 * @param direcao
	 * @param distancia   Distancia em cm a ser percorrida.
	 */
	public static void Move4dDistancia(EnumDirecao direcao, int distancia) {
		
		switch (direcao) {
		
		case FRENTE:
			andarDistancia(distancia);			
			break;
		case TRAZ:
			viraAngulo(180, EnumDirecao.DIREITA);
			andarDistancia(distancia);
			break;
			
		case DIREITA:
			viraAngulo(90, EnumDirecao.DIREITA);
			andarDistancia(distancia);
			break;
			
		case ESQUERDA:
			viraAngulo(90, EnumDirecao.ESQUERDA );
			andarDistancia(distancia);
			break;

		default:
			break;
		}
		
	}
	
	/**
	 * Movimenta o Robo em uma das quatros dire��es baseado na Posicao atual, e Posicao objetiva do robo. 
	 * @param posicaoAtualI
	 * @param posicaoAtualJ
	 * @param posicaoProximaI
	 * @param posicaoProximaJ
	 * @param direcaoRobo Posicao atual do robo.
	 * @param distancia Distancia em cm a ser percorrida.
	 */
	
	public static void Move4dDistancia(int posicaoAtualI, int posicaoAtualJ, int posicaoProximaI, int posicaoProximaJ, EnumDirecao direcaoRobo, int distancia){
		
		EnumDirecao direcaoParaIr= direcaoParaIr(posicaoAtualI, posicaoAtualJ, posicaoProximaI, posicaoProximaJ);
		 Move4dDistancia(direcaoParaIr, direcaoRobo, distancia );
		 }
	
	
	/**
	 * Movimenta o Robo em uma das quatros dire��es baseado na direcao para ir e dire��o objetiva do robo.
	 * @param direcaoParaIr Dire��o que o robo deve Ir.
	 * @param direcaoRobo Posicao atual do robo.
	 * @param distancia Distancia em cm a ser percorrida.
	 */
	
	public static void Move4dDistancia(EnumDirecao direcaoParaIr, EnumDirecao direcaoRobo, int distancia){
		
		int valoDirecional  =  direcaoParaIr.valorDirecional(direcaoRobo.valor);	
		viraDirecionada4d(valoDirecional);
	}
	
	/**
	 * Defifine que direcao ele deve ir, baseado na posicao atual dele e na posicao do objetivo.
	 * @param posicaoAtualI
	 * @param posicaoAtualJ
	 * @param posicaoProximaI
	 * @param posicaoProximaJ
	 * @return
	 */
	 
	public static EnumDirecao direcaoParaIr(int posicaoAtualI, int posicaoAtualJ, int posicaoProximaI, int posicaoProximaJ){

		EnumDirecao direcaoParaIr = null;

		if(posicaoAtualI > posicaoProximaI){

			direcaoParaIr = EnumDirecao.TRAZ;

		}

		if(posicaoAtualI < posicaoProximaI){

			direcaoParaIr = EnumDirecao.FRENTE;

		}

		if(posicaoAtualJ > posicaoProximaJ){

			direcaoParaIr = EnumDirecao.ESQUERDA;

		}

		if(posicaoAtualJ < posicaoProximaJ){

			direcaoParaIr = EnumDirecao.DIREITA;

		}

		

		return direcaoParaIr;

	}
	

	
/**
 * Percorre um ArrayList de Int
 * @param caminho
 * @param direcaoRobo
 * @param distanciaMapa
 * @param posicaoRoboI
 * @param posicaoRoboJ
 * @throws InterruptedException
 */
	public static void andaCaminho(ArrayList<int[]> caminho,EnumDirecao direcaoRobo, int distanciaMapa, int posicaoRoboI, int posicaoRoboJ) throws InterruptedException{

		//direcaoAtual = EnumDirecao.FRENTE;
		////Adicionar posicao inical do robo no caminho

		//int posicaoAtualI = 0; 
		//int posicaoAtualJ = 0;
		int posicaoProximaI = 0;
	    int posicaoProximaJ = 0;
	    int valoDirecional = 0;
	    
	   System.out.println( posicaoRoboI +"," +  posicaoRoboJ + "-" + direcaoRobo);
	   
		for(int[] p : caminho) {

			
			posicaoProximaI = p[0];
			posicaoProximaJ = p[1];		
			
			EnumDirecao direcao= direcaoParaIr(posicaoRoboI, posicaoRoboJ, posicaoProximaI, posicaoProximaJ);
			
		
			valoDirecional  =  direcao.valorDirecional(direcaoRobo.valor);	
			viraDirecionada4d(valoDirecional);
			
			System.out.println( "Rotaciona:" + valoDirecional * 90);			

			andarDistancia(distanciaMapa);
			//System.out.println( "Andar:" + distanciaMapa );
			//System.out.println(  p[0] +"," + p[1]+ "-" + direcao);
			//Thread.sleep(1000);
			
			posicaoRoboI = posicaoProximaI;
			posicaoRoboJ = posicaoProximaJ;
			
			direcaoRobo = direcao;


		}

		

	}
	
	/**
	 * Percorre um  Array de caminho. 
	 * @param caminho Aray com os nodos que compoem o caminho.
	 * @param direcaoRobo
	 * @param distanciaMapa
	 * @param posicaoRoboI
	 * @param posicaoRoboJ
	 * @throws InterruptedException
	 */
	
	public static void andaCaminho(ArrayList<Nodo> caminho,EnumDirecao direcaoRobo, int distanciaMapa, Boolean inverso) throws InterruptedException{

		int posicaoRoboI = caminho.get(0).getI(); 
		int posicaoRoboJ = caminho.get(0).getJ();
		int posicaoProximaI = 0;
	    int posicaoProximaJ = 0;
	   // int valoDirecional = 0;	    
	  // System.out.println( posicaoRoboI +"," +  posicaoRoboJ + "-" + direcaoRobo);
	    if(!inverso) {    	    
	   
		for(Nodo p : caminho) {
			
			posicaoProximaI = p.getI();
			posicaoProximaJ = p.getJ();			
		
			EnumDirecao direcaoParaIr= direcaoParaIr(posicaoRoboI, posicaoRoboJ, posicaoProximaI, posicaoProximaJ);
			Move4dDistancia(direcaoParaIr, direcaoRobo, distanciaMapa);			
			
			posicaoRoboI = posicaoProximaI;
			posicaoRoboJ = posicaoProximaJ;			
			
			direcaoRobo = direcaoParaIr;

		}
	    }else {
		
		for(int i =  caminho.size(); i >= 0; i--)  {
			
			Nodo p = caminho.get(i);
			posicaoProximaI = p.getI();
			posicaoProximaJ = p.getJ();			
		
			EnumDirecao direcaoParaIr= direcaoParaIr(posicaoRoboI, posicaoRoboJ, posicaoProximaI, posicaoProximaJ);
			Move4dDistancia(direcaoParaIr, direcaoRobo, distanciaMapa);			
			
			posicaoRoboI = posicaoProximaI;
			posicaoRoboJ = posicaoProximaJ;			
			
			direcaoRobo = direcaoParaIr;

			}	
		}	    

	}
	
	
	/**
	 * Rotaciona o Robo 90 graus * valor direcional
	 * ex:/n
	 *  0, n�o rotaciona	 *  
	 *  1 , rotaciona 90 , 90 graus para direita
	 *  2 , rotaciona 180, 180 graus para direita
	 *  -1 , rotaciona -90 , 90 graus para esquerda
	 *  -2 , rotaciona -180, 180 graus para esquerda.
	 * @param valorDirecional
	 */
	
	public static void viraDirecionada4d(int valorDirecional) {		
		
		viraAngulo(90 * valorDirecional, EnumDirecao.DIREITA);		
		
	}
	
	public static void viraDirecionadaCabeca4d(int valorDirecional) {		
		
		viraCabeca(grausToCabecaMotorRotate(90 * valorDirecional), EnumDirecao.DIREITA);	
		
	}
	/**
	 *  rotaciona angulo da cabeca "motorRotade" para direita ou para esque�a. 
	 *  
	 * @param motorRotate quantidade de angulos do
	 * @param direcao
	 */

	public static void viraCabeca(int motorRotate, EnumDirecao direcao) {		
		
		if(direcao == EnumDirecao.DIREITA){
		motorCabeca.rotate(motorRotate);
		
		}else if(direcao == EnumDirecao.ESQUERDA){
			motorCabeca.rotate(-motorRotate);
		}				

	}
	
	public static Boolean encontrouParede() {
		//sensorUltrasonico.capture();
		if(sensorUltrasonico.getDistance()< distancia_paredeUltraSonico) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
	



}
