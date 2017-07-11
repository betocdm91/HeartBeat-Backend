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

import java.io.*;
import java.net.*;

public class Cliente{
	
    Socket clientSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String mensaje;
    String ip="127.0.0.1";
    int puerto=2004;
    
    Cliente(){}
    
    void run()
    {
        try{
            //1. socket para conectar con el servidor
            clientSocket = new Socket(ip, puerto); 
            System.out.println("conectado con ip: " + ip + " puerto: " + puerto);
            //2. Entrada/salida
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
            //3: Comunicarse con el servidor
            do{
                try{
                    mensaje = (String)in.readObject();
                    System.out.println("servidor>" + mensaje);
                    enviarMensaje("Hola");
                    mensaje = "adios";
                    enviarMensaje(mensaje);
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("datos recibidos en formato desconodido");
                }
            }while(!mensaje.equals("adios"));
        }
        catch(UnknownHostException unknownHost){
            System.err.println("Conectado a host desconocido!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Cerrar conexion
            try{
                in.close();
                out.close();
                clientSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    void enviarMensaje(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
            System.out.println("cliente>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    public static void main(String args[])
    {
        Cliente client = new Cliente();
        client.run();
    }
}
