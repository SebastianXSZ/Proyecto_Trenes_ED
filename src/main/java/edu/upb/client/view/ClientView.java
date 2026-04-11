package edu.upb.client.view;

import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.upb.client.model.ClientModel;
import edu.upb.client.observer.Observer;

public class ClientView extends Observer {

  private JFrame jFrame;
  private JButton jButton1;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JMenu jMenu1;
  private JMenu jMenu2;
  private JMenuBar jMenuBar1;
  private JMenuItem jMenuItem1;
  private JPanel jPanel1;
  private JTextField jTextField1;
  private Function<String, Void> registerTicket;

  public ClientView(ClientModel model) {
    super(model);
    this.jFrame = new JFrame();
    this.jPanel1 = new javax.swing.JPanel();
    this.jLabel1 = new javax.swing.JLabel();
    this.jTextField1 = new javax.swing.JTextField();
    this.jButton1 = new javax.swing.JButton();
    this.jLabel2 = new javax.swing.JLabel();
    this.jMenuBar1 = new javax.swing.JMenuBar();
    this.jMenu1 = new javax.swing.JMenu();
    this.jMenuItem1 = new javax.swing.JMenuItem();
    this.jMenu2 = new javax.swing.JMenu();
  }

  public void initComponents(Function<String, Void> registerTicket) {
    this.registerTicket = registerTicket;
    this.jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    jLabel1.setText("Nombres:");
    jTextField1.setName("names");
    jButton1.setText("Registrar");
    jButton1.setName("register");
    jButton1.addActionListener(this::jButton1ActionPerformed);
    jLabel2.setName("console");
    jLabel2.setText("Consola:");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap()));
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE)));

    jMenuItem1.setText("Salir");
    jMenu1.add(jMenuItem1);
    jMenuBar1.add(jMenu1);
    jMenu2.setText("Edit");
    jMenuBar1.add(jMenu2);
    this.jFrame.setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.jFrame.getContentPane());
    this.jFrame.getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));
    this.jFrame.pack();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    this.registerTicket.apply(jTextField1.getText());
  }

  public void setMessage(String message) {
    jLabel2.setText(message);
  }

  public void update() {
    ClientModel model = (ClientModel) this.subject;
    this.setMessage(model.getLogger());
  }

  public void setVisible(boolean visible) {
    this.jFrame.setVisible(visible);
  }
}