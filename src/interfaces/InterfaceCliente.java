/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author a1144847
 * 
 * O único método que pode ser invocado pelo servidor é notificaInteresse, que
 * recebe uma String como parametro e é definido abaixo
 */
public interface InterfaceCliente extends Remote {
    public String notificaInteresse(String mensagem) throws RemoteException;
}
