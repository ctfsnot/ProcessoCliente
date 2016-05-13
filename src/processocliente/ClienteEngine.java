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

/**
 *
 * @author Pepo
 */
public class ClienteEngine extends UnicastRemoteObject implements InterfaceCliente{
    
    
    static ArrayList<String> voosComprados = new ArrayList();
    static ArrayList<String> hospedagensCompradas = new ArrayList();
    
    public ClienteEngine() throws RemoteException{
        
    }

    @Override
    public synchronized String notificaInteresse(String mensagem) throws RemoteException {
        System.out.println(mensagem);
        return "";
    }
    
}
