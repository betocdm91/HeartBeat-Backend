/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn;

/**
 *
 * @author CARLOS OSORIO
 */


public class BackEnd {
	
	static Servidor servidor;
	static Cliente cliente; 
	
	
	
	public static void main(String [] args){
		servidor = new Servidor();
		cliente = new Cliente();
		
		while(true){
			servidor.run();
			cliente.run();
		}
		
		
	}

}
