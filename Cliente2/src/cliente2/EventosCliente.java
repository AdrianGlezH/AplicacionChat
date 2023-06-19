package cliente2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author EVEN
 */
public class EventosCliente implements ActionListener {

	private MetodosCliente metodos;
	private InterfazCliente interfaz;
	
	public EventosCliente(InterfazCliente interfaz) {
		this.interfaz = interfaz;
	}

	public void setCliente(MetodosCliente metodos) {
		this.metodos = metodos;
	}
	
	/**
	 * Interpreta las acciones realizadas sobre el cliente.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "salir":
                    salir();
                    break;
                case "enviar":
                    metodos.enviarMSG(interfaz.getBarra());
                    interfaz.vaciaBarra(); 
                    break;
                default:
                    break;
		}
	}
	
	/**
	 * Método que desconecta y apaga el cliente.
	 * @return
	 */
	public int salir() {
		metodos.enviarMSG("/bye");
		metodos.cerrarConexion();
		interfaz.añadirTexto("Has abandonado la sala del chat.");
		interfaz.setEnabled(false);
		return 0;
	}
	


}