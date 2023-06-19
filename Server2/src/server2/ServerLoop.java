package server2;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author EVEN
 */
public class ServerLoop extends Thread{
    
    private Socket cliente;
    private InterfazServer interfaz;
    private BufferedReader in;
    private PrintWriter out;
    private String nombre;
    
    public ServerLoop(InterfazServer interfaz, Socket cliente) throws IOException {
        this.interfaz = interfaz;
        this.cliente = cliente;
        this.cliente.setSoTimeout(5000);
        nombre = "";
        in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        out = new PrintWriter(cliente.getOutputStream(), true);
	}
    
    @Override
    public void run() {
        String msg;
        iniciarCliente();
        try {
            do {
                msg = recibirMSG();
                messageHandler(msg);
            }while(!msg.equals("/bye"));
            in.close();
            out.close();
            cliente.close();
        } catch(SocketTimeoutException e) {
            Server2.mandarTodos("Servidor: "+nombre+" ha tenido problemas de conexión.");
            Server2.sacarCliente(nombre);
        } catch(IOException e) { Server2.mandarTodos("Server: "+nombre+" desconectado debido a un error."); }
        interfaz.setClientesConectados(Server2.getClientes().getClientesConectados());
    }
    
    public void cerrarConexion() {
        enviarMSG("/bye");
    }
    
    public String recibirMSG() {
        String msg = null;
        do {
            try {
                msg = in.readLine();
            } catch (IOException e) { msg = null; }
        } while(msg == null);
        return msg;
    }
    
    public void enviarMSG(String msg) {
        out.println(msg );
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    private String nombreRepe(String msg) {
    	String nombre = msg; int i = 1;
    	while(Server2.getClientes().yaEsta(nombre)) { 
    		nombre = msg.concat(Integer.toString(i));
    		i++; 
    	}
        return nombre;
    }
    
    private void iniciarCliente(){
        nombre = nombreRepe(recibirMSG());
        Server2.getClientes().actualizarConectados();
        Server2.meterCliente(this);
        Server2.mandarTodos("Servidor : se ha conectado "+nombre+" a la sala");
    }
    
    private void messageHandler(String msg) {
        switch(msg.trim()) {
			case "/bye":
				
				Server2.mandarTodos("Server : "+ nombre+ " Ha abandonado la sala.");
				Server2.sacarCliente(nombre);	
			break;
			default:
				Server2.mandarTodos(nombre+": "+ msg);
			break;
		}
	}
}
