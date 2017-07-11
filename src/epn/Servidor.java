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


public class Servidor{
	
    ServerSocket serverSocket;
    Socket con = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String mensaje;
    
    Servidor(){}
    
    void run()
    {
        try{
            //1. crear socket para el servidor
            serverSocket = new ServerSocket(2004, 10);
            //2. escuchar con
            System.out.println("esperando conexion");
            con = serverSocket.accept();
            System.out.println("conexion recibida de:  " + con.getInetAddress().getHostName());
            //3. entrada/salida
            out = new ObjectOutputStream(con.getOutputStream());
            out.flush();
            in = new ObjectInputStream(con.getInputStream());
            enviarMensaje("conexion exitosa");
            //4. repetir conexiones I/O
            do{
                try{
                    mensaje = (String)in.readObject();
                    System.out.println("cliente>" + mensaje);
                    if (mensaje.equals("adios"))
                        enviarMensaje("adios");
                }
                catch(ClassNotFoundException classnot){
                    System.err.println("Datos recibidos en formato equivocado");
                }
            }while(!mensaje.equals("adios"));
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: cerrar conexion
            try{
                in.close();
                out.close();
                serverSocket.close();
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
            System.out.println("servidor>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    public static void main(String args[])
    {
        Servidor server = new Servidor();
        while(true){
            server.run();
        }
    }
}
