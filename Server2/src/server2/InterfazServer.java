package server2;

/**
 *
 * @author EVEN
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InterfazServer extends JPanel {
    
    private JPanel mainPanel;
    private JPanel panelCliente;
    private JLabel labelClientes;
    private JTextArea texto;
    private JButton botonApagar;
    
    
    public InterfazServer() {
        setLayout(new BorderLayout());
        
        mainPanel = new JPanel(new BorderLayout());
        panelCliente = new JPanel();
        panelCliente.setLayout(new BoxLayout(panelCliente, BoxLayout.Y_AXIS));
        JPanel panelBajo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        texto = new JTextArea();
        texto.setPreferredSize(new Dimension(400, 500));
        texto.setEditable(false);
        texto.setLineWrap(true);
        panelCliente.add(texto);
        
        botonApagar = new JButton("Apagar");
        labelClientes = new JLabel("Clientes conectados: 0/10");
        panelBajo.add(botonApagar);
        panelBajo.add(labelClientes);

        mainPanel.add(panelCliente, BorderLayout.CENTER);
        mainPanel.add(panelBajo, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public void setControlador(ActionListener l) {
        botonApagar.setActionCommand("apagar");
        botonApagar.addActionListener(l);
    }
    
    public void añadirTexto(String msg) {
        texto.append(msg + "\n");
    }
    
    public void setClientesConectados(int clientesConectados) {
        labelClientes.setText("Clientes conectados: " + clientesConectados+"/10");
    }
    
    
    public void apagar() {
        botonApagar.setEnabled(false);
        texto.setEnabled(false);
        labelClientes.setText("El Servidor se ha apagado.");
    }
    
}