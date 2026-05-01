package edu.upb.client.controller;

import edu.upb.client.model.ClientModel;
import edu.upb.client.view.LoginView;
import edu.upb.client.view.PurchaseView;
import edu.upb.client.view.AdminView;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

/**
 * Controlador del cliente en la arquitectura MVC.
 * Maneja el flujo de navegación entre Login, Compra y Administración según el
 * rol del usuario.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class ClientController {
  private ClientModel model;
  private LoginView loginView;
  private PurchaseView purchaseView;
  private AdminView adminView;

  public ClientController(ClientModel model) {
    this.model = model;
  }

  public void init() {
    boolean connected = model.connect();
    if (!connected) {
      javax.swing.JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor.");
      return;
    }
    loginView = new LoginView(model);
    loginView.setHandlers(
        (dto, callback) -> {
          boolean valid = model.login(dto.getUsername(), dto.getPassword());
          callback.accept(valid);
        },
        (data, callback) -> {
          boolean success = model.registerUser((String) data[0], (String) data[1], (String) data[2], (String) data[3],
              (String) data[4], (String) data[5]);
          callback.accept(success);
        });
    loginView.setOnLoginSuccess(username -> {
      loginView.dispose();
      String role = model.getUserRole(username);
      if ("ADMIN".equals(role)) {
        showAdminView(username);
      } else {
        showPurchaseView(username);
      }
    });
    loginView.setVisible(true);
  }

  private void showAdminView(String username) {
    adminView = new AdminView(model);
    adminView.setOnLogout(unused -> logout());
    addProfileMenu(adminView, username);
    adminView.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        logout();
      }
    });
    adminView.setVisible(true);
  }

  private void showPurchaseView(String username) {
    purchaseView = new PurchaseView();
    purchaseView.setStationNames(model.getStationNames());
    purchaseView.setPurchaseHandler(this::handlePurchase);
    addProfileMenu(purchaseView, username);
    purchaseView.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        logout();
      }
    });
    purchaseView.setVisible(true);
  }

  private void addProfileMenu(javax.swing.JFrame frame, String username) {
    javax.swing.JMenuBar menuBar = frame.getJMenuBar();
    if (menuBar == null)
      menuBar = new javax.swing.JMenuBar();

    edu.upb.model.User currentUser = model.getUser(username);
    String role = currentUser != null ? currentUser.getRole() : "UNKNOWN";

    javax.swing.JMenu menu = new javax.swing.JMenu("Perfil (" + username + " - " + role + ")");

    javax.swing.JMenuItem itemEditProfile = new javax.swing.JMenuItem("Editar Perfil");
    itemEditProfile.addActionListener(e -> {
      edu.upb.model.User user = model.getUser(username);
      if (user == null)
        return;

      javax.swing.JTextField txtName = new javax.swing.JTextField(user.getName());
      javax.swing.JTextField txtLastName = new javax.swing.JTextField(user.getLastName());
      javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
      panel.add(new javax.swing.JLabel("Nombre:"));
      panel.add(txtName);
      panel.add(new javax.swing.JLabel("Apellido:"));
      panel.add(txtLastName);

      int res = javax.swing.JOptionPane.showConfirmDialog(frame, panel, "Editar Perfil",
          javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
      if (res == javax.swing.JOptionPane.OK_OPTION) {
        user.setName(txtName.getText().trim());
        user.setLastName(txtLastName.getText().trim());
        if (model.updateUser(user)) {
          javax.swing.JOptionPane.showMessageDialog(frame, "Perfil actualizado.");
        } else {
          javax.swing.JOptionPane.showMessageDialog(frame, "Error al actualizar perfil.");
        }
      }
    });

    javax.swing.JMenuItem itemChangePass = new javax.swing.JMenuItem("Cambiar Contraseña");
    itemChangePass.addActionListener(e -> {
      javax.swing.JPasswordField txtOld = new javax.swing.JPasswordField();
      javax.swing.JPasswordField txtNew = new javax.swing.JPasswordField();
      javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
      panel.add(new javax.swing.JLabel("Contraseña Actual:"));
      panel.add(txtOld);
      panel.add(new javax.swing.JLabel("Nueva Contraseña:"));
      panel.add(txtNew);
      int res = javax.swing.JOptionPane.showConfirmDialog(frame, panel, "Cambiar Contraseña",
          javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
      if (res == javax.swing.JOptionPane.OK_OPTION) {
        boolean success = model.changePassword(username, new String(txtOld.getPassword()),
            new String(txtNew.getPassword()));
        if (success) {
          javax.swing.JOptionPane.showMessageDialog(frame, "Contraseña actualizada exitosamente.");
        } else {
          javax.swing.JOptionPane.showMessageDialog(frame, "Error al cambiar contraseña. Verifique sus datos.");
        }
      }
    });
    menu.add(itemEditProfile);
    menu.add(itemChangePass);
    menuBar.add(menu);
    frame.setJMenuBar(menuBar);
  }

  private void handlePurchase(SaleDTO dto) {
    try {
      Ticket ticket = model.purchaseTicket(dto);
      if (ticket != null) {
        purchaseView.showTicket(ticket);
      } else {
        javax.swing.JOptionPane.showMessageDialog(purchaseView, "Compra fallida. Verifique disponibilidad.");
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(purchaseView, "Error: " + e.getMessage());
    }
  }

  private void logout() {
    if (adminView != null)
      adminView.dispose();
    if (purchaseView != null)
      purchaseView.dispose();
    init();
  }
}