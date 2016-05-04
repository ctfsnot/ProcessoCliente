/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processocliente;

import interfaces.InterfaceServidor;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author a1144847
 */
public class ProcessoCliente {

    static Registry servidorNomes;
    static InterfaceServidor server;
    static String pid;
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        
        pid = ManagementFactory.getRuntimeMXBean().getName();
        servidorNomes = LocateRegistry.getRegistry("localhost", 8888);
        server = (InterfaceServidor)servidorNomes.lookup("ServerEngine");
        
    }
    
}
