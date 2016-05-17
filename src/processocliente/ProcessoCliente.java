/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processocliente;

import interfaces.InterfaceCliente;
import interfaces.InterfaceServidor;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author a1144847
 * 
 * O ProcessoCliente engloba o menu do programa cliente, registra o cliente no
 * servidor de nomes e se comunica com o servidor para o uso remoto dos métodos
 */
public class ProcessoCliente{

    static Registry servidorNomes;
    static InterfaceServidor server;
    static String pid;
    static ClienteEngine clienteEngine;
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException, AlreadyBoundException {
        
        //se comunica com o servidor de nomes e adiciona o pid do cliente a ele
        pid = ManagementFactory.getRuntimeMXBean().getName();
        servidorNomes = LocateRegistry.getRegistry("localhost", 8888);
        server = (InterfaceServidor)servidorNomes.lookup("ServerEngine");
        
        clienteEngine = new ClienteEngine();
        
        //servidorNomes.bind(pid, clienteEngine);
        
        //se ele conseguir se conectar ao servidor o menu é carregado, caso contrário
        //uma mensagem de erro é exibida
        if(server != null){
            menu();
        }
        else
            System.out.println("Não foi possível localizar servidor.");
        
        
    }
    //método que imprime o menu e se comunica com o servidor para o acesso
    //remoto dos métodos
    public static void menu() throws RemoteException{
        
        Scanner scanner = new Scanner(System.in);
        //imprime o menu do processo cliente        
        while(true){
            String MENU1 = "";
            MENU1 += "Escolha a opção desejada: \n\n";
            MENU1 += "1. Comprar passagem\n";
            MENU1 += "2. Comprar hospedagem\n";
            MENU1 += "3. Listar passagens adquiridas\n";
            MENU1 += "4. Listar hospedagens adquiridas\n";
            MENU1 += "5. Registrar interesse\n";
            MENU1 += "\nOpção: ";
            System.out.print(MENU1);

            int opt1, opt3, opt5;
            String opt2, opt4, compra;
            
            switch (scanner.nextLine()){
                
                //o menu '1' serve para comprar novas passagens
                case "1":
                    String passagem = "";
                    System.out.print("\n");
                    //lista quais passagens estão disponíveis no servidor
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
                    
                    //a String 'passagem' guarda a passagem comprada
                    passagem = (String) server.listaPassagens()[opt1];
                    
                    //permite o cliente escolher se a passagem é só de ida ou também de volta
                    System.out.print("\nSomente ida? [s/n]: ");
                    opt2 = scanner.nextLine();
                    String dIda = "";
                    String dVolta = "";
                    //pergunta a data da ida
                    boolean somenteIda = true;
                    System.out.print("Data de ida [dd/MM/yyyy]: ");
                    dIda = scanner.nextLine();
                    //pergunta a data da volta, caso ela exista
                    if(opt2.equals("n")){
                        somenteIda = false;
                        System.out.print("Data de volta [dd/MM/yyyy]: ");
                        dVolta = scanner.nextLine();
                    }
                    
                    //permite o cliente informar quantos passageiros vão viajar
                    System.out.print("\nDigite o número de passageiros: [1-9]: ");
                    opt3 = scanner.nextInt();
                    //pergunta a idade de cada passageiro
                    int []idades = new int[opt3];
                    for(int c = 0; c < opt3; c++){
                        System.out.print("\nIdade do passageiro "+c+": ");
                        idades[c] = scanner.nextInt();
                    }
                    scanner.nextLine();
                    //requisita os dados do cartão de crédito ao cliente
                    System.out.print("\nDigite os 16 digitos do cartão: ");
                    opt4 = scanner.nextLine();
                    //requisita em quantas parcelas serão feitas a compra
                    System.out.print("\nParcelas[0-6x]: ");
                    String parcelas = scanner.nextLine();
                    
                    //grava em 'compra' os dados da passagem comprada
                    compra = passagem + "\n" 
                            + "Saída: " + dIda + "\n"
                            + "Retorno: " + dVolta + "\n"
                            + "Qtde. Passageiros: " + opt3 + "\n"
                            + "Dados do Cartão : " + opt4 + "\n"
                            + "Número de parcelas : "+ parcelas+ "\n\n";
                    //imprime os dados da compra
                    if(server.compraPassagem(opt1)){
                        System.out.println("\n*************** Dados da compra ***************\n");
                        System.out.print(compra);
                        System.out.println("***********************************************\n");
                        //adiciona a compra no vetor de voosComprados no clienteEngine
                        clienteEngine.voosComprados.add(compra);
                    } else {
                        System.out.println("\n\nNão foi possivel efetuar a compra. Tente novamente.\n");
                    }
                    
                    break;
                //o menu '2' serve para comprar novas hospedagens    
                case "2":
                    String hospedagem = "";
                    String quartos = "";
                    System.out.print("\n");
                    //lista as hospedagens disponíveis no servidor
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
                    //a String 'hospedagem' guarda a hospedagem comprada
                    hospedagem = (String) server.listaHospedagens()[opt1];
                    int index = hospedagem.indexOf(", com");
                    //a String 'quartos' guarda o numero de quartos da hospedagem comprada
                    quartos = hospedagem.substring(index+6, index+7);
                    
                    String dEntrada = "";
                    String dSaida = "";
                    //registra o check-in e o check-out
                    System.out.print("Check-in [dd/MM/yyyy]: ");
                    dEntrada = scanner.nextLine();
                    System.out.print("Check-out [dd/MM/yyyy]: ");
                    dVolta = scanner.nextLine();
                    //registra quantos quartos o cliente vai reservar, limitando pela quantidade disponível    
                    System.out.print("\nDigite o número de quartos: [1-"+quartos+"]: ");
                    opt3 = scanner.nextInt();
                    //registra o número de hospedes, limitando pela quantidade disponível
                    System.out.print("\nDigite o número de hospedes: [1-"+ 2*opt3 +"]: ");
                    opt5 = scanner.nextInt();
                    //registra a idade dos hospedes
                    int []hospedes = new int[opt5];
                    for(int c = 0; c < opt5; c++){
                        System.out.print("\nIdade do hospede "+c+": ");
                        hospedes[c] = scanner.nextInt();
                    }
                    scanner.nextLine();
                    //requisita os dados do cartão de crédito ao cliente
                    System.out.print("\nDigite os 16 digitos do cartão: ");
                    opt2 = scanner.nextLine();
                    //requisita em quantas parcelas serão feitas a compra
                    System.out.print("\nParcelas[0-6x]: ");
                    String parcel = scanner.nextLine();
                    //registra os dados da hospedagem comprada na String 'compra'
                    compra = hospedagem + "\n" 
                            + "Entrada: " + dEntrada + "\n"
                            + "Saída: " + dSaida + "\n"
                            + "N. de Quartos: " + opt3 + "\n"
                            + "Qtde. hospedes: " + opt5 + "\n"
                            + "Dados do Cartão : " + opt2 + "\n"
                            +"Número de parcelas : "+ parcel + "\n\n";
                    //imprime os dados da compra
                    if(server.compraHospedagem(opt1)){
                        System.out.println("\n*************** Dados da compra ***************\n");
                        System.out.print(compra);
                        System.out.println("***********************************************\n");
                        //adiciona a compra ao array de hospedagensCompradas no clienteEngine
                        clienteEngine.hospedagensCompradas.add(compra);
                    } else {
                        System.out.println("\n\nNão foi possivel efetuar a compra. Tente novamente.\n");
                    }
                    break;
                case "3":
                    //a opção '3' imprime a lista de passagens já compradas pelo cliente
                    System.out.println("\n\n****Passagens já adquiridas pelo cliente**** ");
                    //percorre o array de voosComprados no clienteEngine e imprime
                    for(int i=0; i<ClienteEngine.voosComprados.size(); i++){
                        System.out.println(ClienteEngine.voosComprados.get(i)+"\n\n");
                    }
                    System.out.print("\n");
                    break;
                case "4":
                    //a opção '4' imprime a lista de hospedagens já compradas pelo cliente
                    System.out.println("\n\n****Hospedagens já adquiridas pelo cliente**** ");
                    //percorre o array de hospedagensCompradas no clienteEngine e imprime
                    for(int i=0; i<ClienteEngine.hospedagensCompradas.size(); i++){
                        System.out.println(ClienteEngine.hospedagensCompradas.get(i)+"\n\n");
                    }
                    System.out.print("\n");
                    break;
                case "5":
                    //a opção '5' permite ao cliente registrar um interesse por uma passagem ou por
                    //uma hospedagem mais barata
                    String MENU2 = "";
                    //permite ao cliente escolher entre passagem ou hospedagem
                    MENU2 += "Selecione a oferta em que você tem interesse: \n\n";
                    MENU2 += "1. Oferta de passagem\n";
                    MENU2 += "2. Oferta de hospedagem\n";
                    MENU2 += "\nOpção: ";
                    System.out.print(MENU2);
                    
                    int opt_oferta1, opt_oferta2;
                    
                    switch (scanner.nextLine()){
                        case "1":
                            //caso o cliente queira registrar interesse em uma passagem
                            String passagem_int = "";
                            //lista as pasasgens dispoíveis no servidor
                            for (int i = 0; i < server.listaPassagens().length; i++){
                                passagem = (String)server.listaPassagens()[i];
                                System.out.println((i+1) + ". " + passagem );
                            }
                            System.out.print("\n\nSelecione a passagem que você tem interesse: ");
                            opt_oferta1 = scanner.nextInt();
                            scanner.nextLine();
                            if (opt_oferta1 == 0)
                                break;
                            else opt_oferta1--;    //corrige valor de opt pra bater com indice de array
                            //guarda em 'passagem' a passagem de interesse
                            passagem = (String) server.listaPassagens()[opt_oferta1];
                            
                            String origem;
                            String destino;
                            String preco;
                            
                            int pi, pf;
                            
                            //coleta a cidade de origem da String 'passagem'
                            pi = 3;
                            pf = passagem.indexOf(" Para");
                            origem = passagem.substring(pi, pf);
                            //coleta a cidade de destino da String 'passagem'
                            pi = passagem.indexOf("Para")+5;
                            pf = passagem.indexOf(" -");
                            destino = passagem.substring(pi, pf);
                            //coleta o preço da String 'passagem'
                            pi = passagem.indexOf("$:")+3;
                            pf = passagem.length();
                            preco = passagem.substring(pi, pf);
                            
                            server.registraInteresse(clienteEngine, origem, destino, Float.parseFloat(preco));
                            
                            break;
                        case "2":
                            //caso o cliente queira registrar interesse em uma hospedagem
                            //mostra quais hospedagens estão disponíveis no servidor
                            for (int i = 0; i < server.listaHospedagens().length; i++){
                                hospedagem = (String)server.listaHospedagens()[i];
                                System.out.println((i+1) + ". " + hospedagem );
                            }
                            System.out.print("\n\nSelecione a hospedagem ou 0 para sair: ");
                            opt_oferta2 = scanner.nextInt();
                            scanner.nextLine();
                            if (opt_oferta2 == 0)
                                break;
                            else opt_oferta2--;    //corrige pra bater com indice do array
                            //a String 'hospedagem' guarda a hospedagem escolhida
                            hospedagem = (String) server.listaHospedagens()[opt_oferta2];
                            
                            String local;
                            String quart;
                            String prec;
                            //coleta o local da String 'hospedagem'
                            pi = 3;
                            pf = hospedagem.indexOf(", com");
                            local = hospedagem.substring(pi, pf);
                            //coleta o número de quartos da String 'hospedagem'
                            pi = hospedagem.indexOf(" com ")+5;
                            pf = hospedagem.indexOf(" quartos");
                            quart = hospedagem.substring(pi, pf);
                            //coleta o preço da String 'hospedagem'
                            pi = hospedagem.indexOf("$:")+3;
                            pf = hospedagem.length();
                            prec = hospedagem.substring(pi, pf);
                            
                            server.registraInteresse(clienteEngine, local, Integer.parseInt(quart), Float.parseFloat(prec));
                            break;
                        default:
                            System.out.println("Opção inválida");
                            break;    
                    }
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

}
