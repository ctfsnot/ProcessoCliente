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
 */
public interface InterfaceServidor extends Remote {
    public void compraPassagem() throws RemoteException;
    public void compraHospedagem() throws RemoteException;
    public void registraInteresse(String cliente, String tipo_interesse) throws RemoteException;
    public Object[] listaPassagens() throws RemoteException;
}
