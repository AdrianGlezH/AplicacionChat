package cliente2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author EVEN
 */
public class MetodosCliente {
    
    private InterfazCliente interfaz;
    private EventosCliente eventos;
    private Socket cliente;
    private BufferedReader in;
    private PrintWriter out;
    
    public MetodosCliente(Socket cliente, InterfazCliente interfaz, EventosCliente eventos) throws IOException {
        this.cliente = cliente;
        this.interfaz = interfaz;
        this.eventos = eventos;
        in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        out = new PrintWriter(cliente.getOutputStream(), true);
    }
    
    public String recibirMSG() throws IOException {
        String msg = null;
        do {
            msg = in.readLine();
        } while(msg==null);
        return msg;
    }
    
    public void enviarMSG(String cadena) {
        out.println(cadena );
    }
    
    public void cerrarConexion() {
        try {
            in.close();
            out.close();
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void messageHandler() {
        try {
            String msg = recibirMSG();
            switch(msg.trim()){
                case "/bye":
                    eventos.salir();
                    interfaz.añadirTexto("Se ha apagado el servidor");
                    break;
                default: 
                    interfaz.añadirTexto(msg);
                    break;
            }
        } catch (IOException e) {
            eventos.salir();
            interfaz.añadirTexto("Te has deconectado del servidor.");
        }
    }
}