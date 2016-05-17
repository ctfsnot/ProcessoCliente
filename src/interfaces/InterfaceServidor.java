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
 * Nessa interface são definidos todos os métodos que poderão ser acessados via
 * RMI pelo cliente.
 */
public interface InterfaceServidor extends Remote {
    //método para a compra de passagens
    public boolean compraPassagem(int passagemId) throws RemoteException;
    //método para a compra de hospedagem
    public boolean compraHospedagem(int hospedagemId) throws RemoteException;
    //método para a resgistrar interesse em vôo
    public boolean registraInteresse(String cliente, String origem, String destino, float preco) throws RemoteException;
    //método para registrar interesse em hospedagem
    public boolean registraInteresse(String cliente, String local, int quartos, float preco) throws RemoteException;
    //método para listar as passagens
    public Object[] listaPassagens() throws RemoteException;
    //método para listar as hospedagens
    public Object[] listaHospedagens() throws RemoteException;
}
