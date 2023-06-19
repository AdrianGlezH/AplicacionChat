package server2;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author EVEN
 */
public class Server2 {
    
    private static JFrame frame;
    private static ListaClientes lista;
    private static InterfazServer interfaz;
    private static ServerSocket server;
    private static EventosServer eventos;

    public static void main(String[] args) {
        crearInterfaz();
         try {
		    iniciarServer();
		    do {
	    		clientHandler();
		    }while(!server.isClosed());
        } catch (BindException e) {
			interfaz.añadirTexto("Servidor: el servidor ya está abierto");
		} catch(IOException e) {
        	interfaz.añadirTexto("Servidor: Error, imposible iniciar");
        }
        
        while(true) {}
    }
    
    
    private static void crearInterfaz(){
        interfaz = new InterfazServer();
        eventos = new EventosServer(interfaz);
        interfaz.setControlador(eventos);
        frame  = new JFrame("Servidor Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(interfaz);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void iniciarServer() throws IOException {
        server = new ServerSocket(1234);
        lista = new ListaClientes();
        eventos.setServidor(server);
        server.getInetAddress();
        interfaz.añadirTexto("Servidor: se ha iniciado el servidor en la ip "+InetAddress.getLocalHost().getHostAddress());
    }
    
    public static ListaClientes getClientes() {
        return lista;
    }
    
    public static void meterCliente(ServerLoop thread) {
        lista.add(thread.getNombre(), thread);
    	lista.actualizarConectados();
    	interfaz.setClientesConectados(lista.getClientesConectados());
    }
    
    public static void sacarCliente(String nombre) {
    	lista.remove(nombre);
    	lista.actualizarConectados();
    	interfaz.setClientesConectados(lista.getClientesConectados());
    }
    
    private static void clientHandler(){
        try {
            Socket cliente = server.accept();
            ServerLoop thread = new ServerLoop(interfaz, cliente);
            thread.start();
            if(lista.getClientesConectados() >= 10) {
                thread.enviarMSG("lleno");
                cliente.close();
                thread = null;
            }else
                thread.enviarMSG("aceptado");
        }catch(IOException e) {  }
    }
    
    public static void mandarTexto(String msg) {
        interfaz.añadirTexto(msg);
    }
    
    public static void mandarTodos(String msg) {
        mandarTexto(msg);
        lista.broadcastALL(msg);
    }
    
}
