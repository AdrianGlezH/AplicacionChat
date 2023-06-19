package server2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author EVEN
 */
public class ListaClientes {
    
    
    private static HashMap<String, ServerLoop> mapaClientes;
	
	public ListaClientes(){
		mapaClientes = new HashMap<String, ServerLoop>();
	}
	
	/* ======================== Métodos Básicos ========================== */
	
	public int getClientesConectados() {
		return mapaClientes.size();
	}
	
	public void add(String nombre, ServerLoop cliente) {
		mapaClientes.put(nombre, cliente);
	}
	
	public void remove(String nombre) {
		mapaClientes.remove(nombre);
	}
	
	/**
	 * Devuelve true si un cliente se encuentra en la lista.
	 */
	public boolean yaEsta(String nombre) {
		return mapaClientes.containsKey(nombre);
	}
	
	/* ======================== Métodos Avanzados ========================== */
	
	/**
	 * Devuelve un string con la lista de los nombres de los clientes.
	 * @return
	 */
	public String getListaClientes() {
		StringBuilder clientes = new StringBuilder(250);
		
		// Recorremos las claves (nombres) de los clientes
		Set<String> claves = mapaClientes.keySet();
		for (String clave : claves) {
		   clientes.append(clave + ", ");
		}
		
		// Al final quitamos la coma e imprimimos punto
		clientes.setLength(clientes.length()-2);
		clientes.append(".");
				
		return clientes.toString().trim();
	}
	
	/**
	 * Envía a todos los clientes actualización de número de clientes conectados.
	 */
	public void actualizarConectados() {
    	broadcastALL(getClientesConectados() + "/10");
    }
	
	/**
	 * Desconecta a todos los clientes del servidor (los echa).
	 */
	public void desconectarTodos() {
		Set<Map.Entry<String, ServerLoop>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
			((ServerLoop) entry.getValue()).cerrarConexion();
		}
	}
	

	public void broadcastALL(String msg) {
		Set<Map.Entry<String, ServerLoop>> set = mapaClientes.entrySet();
		for (@SuppressWarnings("rawtypes") Entry entry : set) {
		   ((ServerLoop) entry.getValue()).enviarMSG(msg);
		}
	}
}
