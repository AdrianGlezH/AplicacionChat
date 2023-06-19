package server2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author EVEN
 */
public class EventosServer implements ActionListener{
    private InterfazServer interfaz;
    private ServerSocket server;
    
    
    public EventosServer(InterfazServer interfaz) {
        this.interfaz = interfaz;
    }
    
    public void setServidor(ServerSocket server) {
        this.server = server;
    }
    @Override
    public void actionPerformed(ActionEvent a) {
        switch(a.getActionCommand()){
            case "apagar":
                try {
                    do {
                        Server2.getClientes().desconectarTodos();
                        
                        server.close();
                        interfaz.añadirTexto("Server: se ha apagado el servidor.");
                        interfaz.apagar();
		
                    }while(!server.isClosed());
                } catch (IOException e) {interfaz.añadirTexto("El servidor ya estaba apagado."); }
                break;
            default:
                break;
        }
    }    
}
