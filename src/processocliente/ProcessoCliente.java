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
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author a1144847
 */
public class ProcessoCliente {

    static Registry servidorNomes;
    static InterfaceServidor server;
    static String pid;
    static ArrayList<String> voosComprados = new ArrayList();
    static ArrayList<String> hospedagensCompradas = new ArrayList();
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        
        pid = ManagementFactory.getRuntimeMXBean().getName();
        servidorNomes = LocateRegistry.getRegistry("localhost", 8888);
        server = (InterfaceServidor)servidorNomes.lookup("ServerEngine");
        
        if(server != null){
            menu();
        }
        else
            System.out.println("Não foi possível localizar servidor.");
        
        
    }
    public static void menu() throws RemoteException{
        
        Scanner scanner = new Scanner(System.in);
                
        while(true){
            String MENU1 = "";
            MENU1 += "Escolha a opção desejada: \n\n";
            MENU1 += "1. Comprar passagem\n";
            MENU1 += "2. Comprar hospedagem\n";
            MENU1 += "3. Listar passagens adquiridas\n";
            MENU1 += "4. Listar passagens adquiridas\n";
            MENU1 += "Opção: ";
            System.out.print(MENU1);

            int opt1, opt3, opt5;
            String opt2, opt4, compra;
            
            switch (scanner.nextLine()){
                
                case "1":
                    String passagem = "";
                    for (int i = 0; i < server.listaPassagens().length; i++){
                        passagem = (String)server.listaPassagens()[i];
                        System.out.println((i+1) + ". " + passagem );
                    }
                    System.out.print("\n\nSelecione a passagem ou 0 para sair: ");
                    opt1 = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (opt1 == 0)
                        break;
                    else opt1--;    //corrige valor de opt pra bater com indice de array
                    
                    passagem = (String) server.listaPassagens()[opt1];
                    
                    System.out.print("\nSomente ida? [s/n]: ");
//                    scanner.nextLine();
                    opt2 = scanner.nextLine();
                    String dIda = "";
                    String dVolta = "";
                    boolean somenteIda = true;
                    System.out.print("Data de ida [dd/MM/yyyy]: ");
                    dIda = scanner.nextLine();
                    
                    if(opt2.equals("n")){
                        somenteIda = false;
                        System.out.print("Data de volta [dd/MM/yyyy]: ");
                        dVolta = scanner.nextLine();
                    }
                        
                    System.out.print("\nDigite o número de passageiros: [1-9]: ");
//                    scanner.nextLine();
                    opt3 = scanner.nextInt();
                    
                    int []idades = new int[opt3];
                    for(int c = 0; c < opt3; c++){
                        System.out.print("\nIdade do passageiro "+c+": ");
                        idades[c] = scanner.nextInt();
                    }
                    scanner.nextLine();
                    System.out.print("\nDigite os 16 digitos do cartão: ");
                    opt4 = scanner.nextLine();
                    
                    compra = passagem + "\n" 
                            + "Saída: " + dIda + "\n"
                            + "Retorno: " + dVolta + "\n"
                            + "Qtde. Passageiros: " + opt3 + "\n"
                            + "Dados do Cartão : " + opt4 + "\n\n";
                    
                    if(server.compraPassagem(opt1)){
                        System.out.println("\n*************** Dados da compra ***************\n");
                        System.out.print(compra);
                        System.out.println("\n***********************************************\n");
                        voosComprados.add(compra);
                    } else {
                        System.out.println("\n\nNão foi possivel efetuar a compra. Tente novamente.\n");
                    }
                    
                    break;
                case "2":
                    String hospedagem = "";
                    String quartos = "";
                    
                    for (int i = 0; i < server.listaHospedagens().length; i++){
                        hospedagem = (String)server.listaHospedagens()[i];
                        System.out.println((i+1) + ". " + hospedagem );
                    }
                    System.out.print("\n\nSelecione a hospedagem ou 0 para sair: ");
                    opt1 = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (opt1 == 0)
                        break;
                    else opt1--;    //corrige pra bater com indice do array
                    
                    hospedagem = (String) server.listaHospedagens()[opt1];
                    int index = hospedagem.indexOf(", com");
                    quartos = hospedagem.substring(index+6, index+7);
                    
                    String dEntrada = "";
                    String dSaida = "";
                    
                    System.out.print("Check-in [dd/MM/yyyy]: ");
                    dEntrada = scanner.nextLine();
                    
                    System.out.print("Check-out [dd/MM/yyyy]: ");
                    dVolta = scanner.nextLine();
                        
                    System.out.print("\nDigite o número de quartos: [1-"+quartos+"]: ");
//                    scanner.nextLine();
                    opt3 = scanner.nextInt();
                    
                    System.out.print("\nDigite o número de hospedes: [1-"+ 2*opt3 +"]: ");
                    opt5 = scanner.nextInt();
                    
                    int []hospedes = new int[opt5];
                    for(int c = 0; c < opt5; c++){
                        System.out.print("\nIdade do hospede "+c+": ");
                        hospedes[c] = scanner.nextInt();
                    }
                    scanner.nextLine();
                    System.out.print("\nDigite os 16 digitos do cartão: ");
                    opt2 = scanner.nextLine();
                    
                    compra = hospedagem + "\n" 
                            + "Entrada: " + dEntrada + "\n"
                            + "Saída: " + dSaida + "\n"
                            + "N. de Quartos: " + opt3 + "\n"
                            + "Qtde. hospedes: " + opt5 + "\n"
                            + "Dados do Cartão : " + opt2 + "\n\n";
                    
                    if(server.compraHospedagem(opt1)){
                        System.out.println("\n*************** Dados da compra ***************\n");
                        System.out.print(compra);
                        System.out.println("\n***********************************************\n");
                        hospedagensCompradas.add(compra);
                    } else {
                        System.out.println("\n\nNão foi possivel efetuar a compra. Tente novamente.\n");
                    }
                    break;
                case "3":
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }
}
