package edu.upb.client.view;

import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.upb.client.model.ClientModel;
import edu.upb.client.observer.Observer;

public class ClientView extends Observer {

  private JFrame jFrame;
  private JTextField jTextField1;
  private JButton jButton1;
  private JLabel jLabel2;

  private Function<String, Void> registerTicket;

  public ClientView(ClientModel model) {
    super(model);
    this.jFrame = new JFrame("Registro de Ticket");
    this.jTextField1 = new JTextField(20);
    this.jButton1 = new JButton("Registrar");
    this.jLabel2 = new JLabel("Consola:");
    initComponents();
  }

  private void initComponents() {
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel();
    panel.add(new JLabel("Nombres:"));
    panel.add(jTextField1);
    panel.add(jButton1);
    panel.add(jLabel2);
    jFrame.add(panel);
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);

    jButton1.addActionListener(e -> {
      if (registerTicket != null) {
        registerTicket.apply(jTextField1.getText());
      }
    });
  }

  public void setRegisterTicketHandler(Function<String, Void> handler) {
    this.registerTicket = handler;
  }

  public void setMessage(String msg) {
    jLabel2.setText(msg);
  }

  public void setVisible(boolean visible) {
    jFrame.setVisible(visible);
  }

  @Override
  public void update() {
    ClientModel model = (ClientModel) subject;
    setMessage(model.getLogger());
  }
}