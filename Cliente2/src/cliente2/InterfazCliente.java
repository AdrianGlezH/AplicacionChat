package cliente2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author EVEN
 */

public class InterfazCliente extends JPanel {
    private JPanel mainPanel;
    private JPanel messagesPanel;
    private JTextArea texto;
    private JTextField barraTexto;
    private JButton botonEnviar;
    private JFrame frame;
    WindowListener exitListener;

    public InterfazCliente(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        mainPanel = new JPanel(new BorderLayout());
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BorderLayout());

        texto = new JTextArea();
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setPreferredSize(new Dimension(400, 500));
        JScrollPane scrollPane = new JScrollPane(texto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        messagesPanel.add(scrollPane, BorderLayout.CENTER);

        barraTexto = new JTextField();
        barraTexto.setPreferredSize(new Dimension(400, 30)); // Set preferred size

        botonEnviar = new JButton("Send");

        // Panel to hold the input text field and send button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(barraTexto, BorderLayout.CENTER);
        inputPanel.add(botonEnviar, BorderLayout.EAST);

        // Add the messages panel, input panel, and server status label to the main panel
        mainPanel.add(messagesPanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add the main panel to the client interface
        add(mainPanel);
    }
    
    public String getBarra() {
        return barraTexto.getText().toString();
    }
    
    public void vaciaBarra() {
        barraTexto.setText("");
    }
    
    public void añadirTexto(String linea) {
        texto.append(linea+"\n");
    }	
	
    public void setEnabled(boolean activado) {
        barraTexto.setEnabled(activado);
        texto.setEnabled(activado);
        botonEnviar.setEnabled(activado);
    }
    
    public void setEvento(EventosCliente l) {
        botonEnviar.setActionCommand("enviar");
        barraTexto.setActionCommand("enviar");
        
        botonEnviar.addActionListener(l);
        barraTexto.addActionListener(l);
        
        exitListener = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                l.salir();
                System.exit(0);
            }
        };
        frame.addWindowListener(exitListener);
    }	
}