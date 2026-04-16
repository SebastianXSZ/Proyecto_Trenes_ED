package edu.upb.client.view;

import edu.upb.client.model.ClientModel;
import edu.upb.client.observer.Observer;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class ClientView extends Observer {

  private JFrame frame;
  private JComboBox<String> originCombo;
  private JComboBox<String> destCombo;
  private JComboBox<String> categoryCombo;
  private JTextField nameField;
  private JTextField baggageField;
  private JButton purchaseButton;
  private JTextArea resultArea;

  private Consumer<SaleDTO> purchaseHandler;
  private Runnable loadStationsHandler;

  public ClientView(ClientModel model) {
    super(model);
    initComponents();
  }

  private void initComponents() {
    frame = new JFrame("Compra de Boletos - Trenes UPB");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);
    frame.setLayout(new BorderLayout(10, 10));

    // Panel de entrada
    JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    String[] categories = {"Premium", "Ejecutivo", "Estándar"};
    originCombo = new JComboBox<>();
    destCombo = new JComboBox<>();
    categoryCombo = new JComboBox<>(categories);
    nameField = new JTextField();
    baggageField = new JTextField("0");

    inputPanel.add(new JLabel("Origen:"));
    inputPanel.add(originCombo);
    inputPanel.add(new JLabel("Destino:"));
    inputPanel.add(destCombo);
    inputPanel.add(new JLabel("Categoría:"));
    inputPanel.add(categoryCombo);
    inputPanel.add(new JLabel("Nombre del pasajero:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("Peso equipaje (kg):"));
    inputPanel.add(baggageField);

    purchaseButton = new JButton("Comprar Boleto");
    purchaseButton.addActionListener(this::handlePurchase);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(purchaseButton);

    // Área de resultados
    resultArea = new JTextArea(8, 40);
    resultArea.setEditable(false);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado de la compra"));

    frame.add(inputPanel, BorderLayout.NORTH);
    frame.add(buttonPanel, BorderLayout.CENTER);
    frame.add(scrollPane, BorderLayout.SOUTH);

    frame.setLocationRelativeTo(null);
  }

  private void handlePurchase(ActionEvent e) {
    if (purchaseHandler == null) {
      JOptionPane.showMessageDialog(frame, "Error: Controlador no configurado.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String origin = (String) originCombo.getSelectedItem();
    String dest = (String) destCombo.getSelectedItem();
    String category = (String) categoryCombo.getSelectedItem();
    String passengerName = nameField.getText().trim();
    double baggageWeight;
    try {
      baggageWeight = Double.parseDouble(baggageField.getText().trim());
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(frame, "Peso de equipaje inválido.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (passengerName.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Debe ingresar el nombre del pasajero.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (origin == null || dest == null || origin.equals(dest)) {
      JOptionPane.showMessageDialog(frame, "Origen y destino deben ser diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    SaleDTO dto = new SaleDTO();
    dto.setOrigin(origin);
    dto.setDestination(dest);
    dto.setCategory(category);
    dto.setPassengerName(passengerName);
    dto.setBaggageWeight(baggageWeight);
    dto.setTrainId("TEMP");  // El servidor usará el tren de prueba

    purchaseHandler.accept(dto);
  }

  public void setStationNames(String[] stations) {
    SwingUtilities.invokeLater(() -> {
      originCombo.removeAllItems();
      destCombo.removeAllItems();
      for (String s : stations) {
        originCombo.addItem(s);
        destCombo.addItem(s);
      }
    });
  }

  public void setPurchaseHandler(Consumer<SaleDTO> handler) {
    this.purchaseHandler = handler;
  }

  public void displayTicket(Ticket ticket) {
    SwingUtilities.invokeLater(() -> {
      StringBuilder sb = new StringBuilder();
      sb.append("=== BOLETO EMITIDO ===\n");
      sb.append("ID: ").append(ticket.getRegistrationId()).append("\n");
      sb.append("Pasajero: ").append(ticket.getPassengerName()).append("\n");
      sb.append("Categoría: ").append(ticket.getCategory()).append("\n");
      sb.append("Asiento: ").append(ticket.getSeatNumber()).append("\n");
      sb.append("Valor: $").append(ticket.getFareValue()).append("\n");
      sb.append("========================");
      resultArea.setText(sb.toString());
    });
  }

  public void showError(String msg) {
    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE));
  }

  public void showMessage(String msg) {
    SwingUtilities.invokeLater(() -> resultArea.setText("Estado: " + msg));
  }

  public void setVisible(boolean visible) {
    frame.setVisible(visible);
  }

  @Override
  public void update() {
    ClientModel model = (ClientModel) subject;
    showMessage(model.getLogger());
  }
}