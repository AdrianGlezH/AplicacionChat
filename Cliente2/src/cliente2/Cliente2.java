package cliente2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author EVEN
 */
public class Cliente2 {
    
    private static JFrame frame;
    private static InterfazCliente interfaz;
    private static ServerSocket server;
    private static EventosCliente eventos;
    private static Socket cliente;
    private static MetodosCliente metodos;

    public static void main(String[] args) {
        crearInterfaz();
        try {
            iniciarCliente();
            while(!cliente.isClosed()) {
                metodos.messageHandler();
            }
            while(true) {}
        } catch (SocketTimeoutException e) {
            interfaz.setEnabled(false);
            JOptionPane.showMessageDialog(frame, "Se ha perdido la conexión con el servidor", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (SocketException e) {
            interfaz.setEnabled(false);
            JOptionPane.showMessageDialog(frame, "El servidor al que quiere acceder está apagado.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private static void crearInterfaz(){
        frame  = new JFrame("Cliente Chat");
        interfaz = new InterfazCliente(frame);
        eventos = new EventosCliente(interfaz);
        interfaz.setEvento(eventos);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(interfaz);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static void iniciarCliente() throws NumberFormatException, IOException {
        int puerto;
    	String ip = JOptionPane.showInputDialog(frame, "Introduzca la ip del server al que se quiere conectar.", "IP", JOptionPane.QUESTION_MESSAGE);
        do{
            try{
                puerto = Integer.parseInt(JOptionPane.showInputDialog(frame, "Introduzca el puerto del servidor al que se quiere conectar", "Puerto", JOptionPane.QUESTION_MESSAGE));
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(frame, "Introduce un puerto valido");
                puerto = 0;
            }
        }while(puerto == 0);
        
        String nombre = JOptionPane.showInputDialog(frame, "Introduzca el nombre que usará", "Datos necesarios", JOptionPane.QUESTION_MESSAGE);
    	
    	
    	if(ip.equals(""))
            ip = "localhost";
        try {
            
            if(nombre.equals(""))
                throw new IOException("Nickname no válido.");
            cliente = new Socket();
            cliente.connect(new InetSocketAddress(ip, puerto), 5000);
            metodos = new MetodosCliente(cliente, interfaz, eventos);
    		
    		// Sino está lleno entramos, si está lleno lanzaremos el error.
    		if(metodos.recibirMSG().trim().equals("aceptado")) {
    			iniciarChat(nombre);
    		}else {
    			metodos = null;
    			throw new IOException("Servidor lleno");
    		}
    	}catch(NumberFormatException e) {
    		JOptionPane.showMessageDialog(frame, "Debes introducir un número de puerto válido.", "Error de conexión", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    private static void iniciarChat(String nombre) throws IOException {
        eventos.setCliente(metodos);
        metodos.enviarMSG(nombre);
    }
}
