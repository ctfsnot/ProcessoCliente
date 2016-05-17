/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processocliente;

import interfaces.InterfaceCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class ClienteEngine extends UnicastRemoteObject implements InterfaceCliente{
    
    //array para guardar as passagens compradas
    static ArrayList<String> voosComprados = new ArrayList();
    //array para guardar as hospedagens compradas
    static ArrayList<String> hospedagensCompradas = new ArrayList();
    
    public ClienteEngine() throws RemoteException{
        
    }
    //método que servidor usa para notificar o cliente, informando que uma opção melhor
    //foi cadastrada
    @Override
    public synchronized String notificaInteresse(String mensagem) throws RemoteException {
        System.out.println(mensagem);
        return "";
    }
    
}
