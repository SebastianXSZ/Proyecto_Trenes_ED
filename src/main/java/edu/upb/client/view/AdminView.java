/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.upb.client.view;

import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.List;
import edu.upb.client.model.ClientModel;
import edu.upb.model.ArnoldTrain;
import edu.upb.model.MercedesBenzTrain;
import edu.upb.model.Train;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import edu.upb.common.observer.Observer;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Ventana de administración para gestionar trenes y ver el orden de abordaje.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class AdminView extends javax.swing.JFrame implements Observer {

    private transient ClientModel model;
    private transient Consumer<String> onLogout;
    private Train selectedTrain = null;
    private transient List<Train> cachedTrains = null;
    private String filterQuery = "";
    private int currentPage = 0;
    private final int pageSize = 5;

    /**
     * Creates new form AdminView
     */
    public AdminView() {
        initComponents();
    }

    public AdminView(ClientModel model) {
        this.model = model;
        this.model.attach(this);
        initComponents();
        setupMenu();
        loadTrainsToTable();
        pack();
        setLocationRelativeTo(null);
        setupTableSelectionListener();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                model.detach(AdminView.this);
            }
        });
    }

    private void setupMenu() {
        javax.swing.JMenuBar menuBar = getJMenuBar();
        if (menuBar == null)
            menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu menu = new javax.swing.JMenu("Opciones");
        javax.swing.JMenuItem mnuSearch = new javax.swing.JMenuItem("Buscar Tren");
        mnuSearch.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(this, "ID o Nombre a buscar:", filterQuery);
            if (q != null) {
                filterQuery = q;
                currentPage = 0;
                loadTrainsToTable();
            }
        });
        menu.add(mnuSearch);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void setupTableSelectionListener() {
        tblTrains.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblTrains.getSelectedRow();
                if (selectedRow != -1) {
                    String id = tblTrains.getValueAt(selectedRow, 0).toString();
                    txtId.setText(id);
                    txtName.setText(tblTrains.getValueAt(selectedRow, 1).toString());
                    cmbType.setSelectedItem(tblTrains.getValueAt(selectedRow, 2).toString());
                    txtCapacity.setText(tblTrains.getValueAt(selectedRow, 3).toString());
                    txtMileage.setText(tblTrains.getValueAt(selectedRow, 4).toString());
                    selectedTrain = findInCache(id);
                }
            }
        });
    }

    private Train findInCache(String id) {
        if (cachedTrains == null)
            return null;
        Iterator<Train> it = cachedTrains.iterator();
        while (it.hasNext()) {
            Train t = it.next();
            if (t.getId().equals(id))
                return t;
        }
        return null;
    }

    public void setOnLogout(Consumer<String> handler) {
        this.onLogout = handler;
    }

    /**
     * Carga la lista de trenes en la tabla aplicando filtros y paginación.
     */
    private void loadTrainsToTable() {
        try {
            cachedTrains = model.getAllTrains();
            System.out.println(
                    "DEBUG loadTrainsToTable: fetched " + (cachedTrains != null ? cachedTrains.size() : 0) + " trains");

            DefaultTableModel tableModel = (DefaultTableModel) tblTrains.getModel();
            tableModel.setRowCount(0);
            System.out.println("DEBUG filterQuery='" + filterQuery + "' length="
                    + (filterQuery != null ? filterQuery.length() : 0));

            // Filtrar
            SinglyLinkedList<Train> filtered = new SinglyLinkedList<>();
            if (cachedTrains != null) {
                Iterator<Train> it = cachedTrains.iterator();
                while (it.hasNext()) {
                    Train t = it.next();
                    if (matchesFilter(t)) {
                        filtered.add(t);
                    }
                }
            }

            System.out.println("DEBUG loadTrainsToTable: filtered size=" + filtered.size());

            // Paginación
            int total = filtered.size();
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, total);
            System.out.println("DEBUG loadTrainsToTable: total=" + total + ", start=" + start + ", end=" + end);

            Iterator<Train> it = filtered.iterator();
            int index = 0;
            while (it.hasNext() && index < end) {
                Train t = it.next();
                System.out.println("DEBUG: processing index=" + index + ", train id=" + t.getId());
                if (index >= start) {
                    String type = (t instanceof edu.upb.model.MercedesBenzTrain) ? "Mercedes-Benz" : "Arnold";
                    tableModel.addRow(new Object[] {
                            t.getId(), t.getName(), type,
                            t.getLoadCapacity(), t.getMileage()
                    });
                    System.out.println("DEBUG: Added row for train " + t.getId());
                }
                index++;
            }

            String info = "Página " + (currentPage + 1) + " (Total: " + total + ")";
            if (filterQuery != null && !filterQuery.isEmpty()) {
                info += " (Filtrado)";
            }
            lblMessage.setText(info);
            btnPrev.setEnabled(currentPage > 0);
            btnNext.setEnabled(end < total);

        } catch (Exception e) {
            lblMessage.setText("Error al cargar trenes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la vista ante cambios en el modelo (Observer).
     * 
     * @param event El nombre del evento de actualización.
     */
    @Override
    public void update(String event) {
        if (event.startsWith("TRAIN_") || event.equals("ROUTE_DELETED")) {
            loadTrainsToTable();
        } else if (event.equals("ERROR")) {
            lblMessage.setText(model.getLogger());
        }
    }

    private boolean matchesFilter(Train t) {
        if (filterQuery == null || filterQuery.isEmpty())
            return true;
        if (t == null)
            return false;
        String q = filterQuery.toLowerCase();
        String name = (t.getName() != null) ? t.getName().toLowerCase() : "";
        String id = (t.getId() != null) ? t.getId().toLowerCase() : "";
        boolean result = name.contains(q) || id.contains(q);
        System.out.println("DEBUG matchesFilter: q=" + q + " name=" + name + " id=" + id + " result=" + result);
        return result;
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        cmbType.setSelectedIndex(0);
        txtCapacity.setText("");
        txtMileage.setText("");
        selectedTrain = null;
        tblTrains.clearSelection();
    }

    private boolean validateForm() {
        if (txtName.getText().trim().isEmpty()) {
            lblMessage.setText("El nombre es obligatorio.");
            return false;
        }
        try {
            Double.parseDouble(txtCapacity.getText().trim());
            Integer.parseInt(txtMileage.getText().trim());
        } catch (NumberFormatException _) {
            lblMessage.setText("Capacidad y Kilometraje deben ser números.");
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCapacity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMileage = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbType = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTrains = new javax.swing.JTable();
        lblMessage = new javax.swing.JLabel();
        btnBoarding = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnRoutes = new javax.swing.JButton();
        btnEmployees = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton("< Anterior");
        btnNext = new javax.swing.JButton("Siguiente >");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administración de trenes - UPB");

        jLabel1.setText("ID:");
        jLabel1.setToolTipText("");
        jLabel1.setName("lblId"); // NOI18N

        txtId.setName("txtId"); // NOI18N

        jLabel2.setText("Nombre:");
        jLabel2.setToolTipText("");
        jLabel2.setName("lblName"); // NOI18N

        txtName.setName("txtName"); // NOI18N

        jLabel3.setText("Capacidad (vagones):");
        jLabel3.setToolTipText("");
        jLabel3.setName("lblCapacity"); // NOI18N

        txtCapacity.setName("txtCapacity"); // NOI18N

        jLabel4.setText("Kilometraje:");
        jLabel4.setToolTipText("");
        jLabel4.setName("lblMileage"); // NOI18N

        txtMileage.setName("txtMileage"); // NOI18N

        jLabel5.setText("Tipo:");
        jLabel5.setName("lblType"); // NOI18N

        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mercedes-Benz", "Arnold" }));
        cmbType.setSelectedIndex(-1);
        cmbType.setName("cmbType"); // NOI18N

        btnUpdate.setText("Actualizar");
        btnUpdate.setName("btnUpdate"); // NOI18N
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("Eliminar");
        btnDelete.setName("btnDelete"); // NOI18N
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnAdd.setText("Agregar tren");
        btnAdd.setName("btnAdd"); // NOI18N
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnRefresh.setText("Refrescar lista");
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        jScrollPane2.setName("scrollPane"); // NOI18N

        tblTrains.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null }
                },
                new String[] {
                        "ID", "Nombre", "Tipo", "Capacidad", "Kilometraje"
                }));
        tblTrains.setName("tblTrains"); // NOI18N
        jScrollPane2.setViewportView(tblTrains);

        lblMessage.setName("lblMessage"); // NOI18N

        btnBoarding.setText("Ver abordaje");
        btnBoarding.addActionListener(this::btnBoardingActionPerformed);

        btnLogout.setText("Cerrar sesión");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        btnRoutes.setText("Gestión de rutas");
        btnRoutes.addActionListener(this::btnRoutesActionPerformed);

        btnEmployees.setText("Gestión de empleados");
        btnEmployees.addActionListener(this::btnEmployeesActionPerformed);

        btnPrev.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadTrainsToTable();
            }
        });
        btnNext.addActionListener(e -> {
            currentPage++;
            loadTrainsToTable();
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(23, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
                                                Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                125,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel4))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtMileage,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                71,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(txtCapacity,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                71,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel5,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 37,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(cmbType,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 116,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                37,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                57,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(45, 45, 45)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtName,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                100,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(txtId,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                100,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40,
                                                        Short.MAX_VALUE)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnRefresh)
                                                        .addComponent(btnDelete)
                                                        .addComponent(btnUpdate)
                                                        .addComponent(btnAdd))
                                                .addGap(12, 12, 12))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnPrev)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblMessage, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNext))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnBoarding)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addComponent(btnEmployees)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnRoutes)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnLogout)))
                                .addGap(20, 20, 20)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAdd)
                                                .addGap(21, 21, 21)
                                                .addComponent(btnUpdate)
                                                .addGap(21, 21, 21)
                                                .addComponent(btnDelete)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnRefresh))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1))
                                                .addGap(12, 12, 12)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(txtCapacity,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(txtMileage,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addComponent(jLabel5))
                                                        .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53,
                                        Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPrev)
                                        .addComponent(lblMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNext))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBoarding)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnLogout)
                                        .addComponent(btnRoutes)
                                        .addComponent(btnEmployees))
                                .addGap(0, 5, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddActionPerformed
        if (!validateForm())
            return;
        System.out.println("DEBUG: Formulario validado. ID=" + txtId.getText().trim());
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        double capacity = Double.parseDouble(txtCapacity.getText().trim());
        int mileage = Integer.parseInt(txtMileage.getText().trim());
        String type = (String) cmbType.getSelectedItem();
        if (type == null) {
            lblMessage.setText("Seleccione el tipo de tren.");
            return;
        }
        Train train;
        if ("Mercedes-Benz".equals(type)) {
            train = new MercedesBenzTrain(id, name, capacity, mileage);
        } else {
            train = new ArnoldTrain(id, name, capacity, mileage);
        }

        // Agregar vagones por defecto (RF-17: 1 carga por cada 2 pasajeros)
        edu.upb.model.PassengerWagon pw1 = new edu.upb.model.PassengerWagon(id + "-W1");
        edu.upb.model.PassengerWagon pw2 = new edu.upb.model.PassengerWagon(id + "-W2");
        edu.upb.model.CargoWagon cw1 = new edu.upb.model.CargoWagon(id + "-C1");
        train.addWagon(pw1);
        train.addWagon(pw2);
        train.addWagon(cw1);
        System.out.println("DEBUG: Tren creado: " + train.getId() + ", vagones: " + train.getWagons().size());
        try {
            boolean result = model.addTrain(train);
            System.out.println("DEBUG: model.addTrain devolvió: " + result);
            if (result) {
                lblMessage.setText("Tren agregado.");
                currentPage = 0;
                loadTrainsToTable();
                tblTrains.revalidate();
                tblTrains.repaint();
                System.out.println("DEBUG: Tabla refrescada después de agregar.");
                clearForm();
            } else {
                lblMessage.setText("Error al agregar el tren. Revisa la consola del servidor.");
            }
        } catch (Exception e) {
            lblMessage.setText("Excepción: " + e.getMessage());
            e.printStackTrace();
        }
    }// GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedTrain == null) {
            lblMessage.setText("Seleccione un tren para actualizar.");
            return;
        }
        if (!validateForm())
            return;

        selectedTrain.setName(txtName.getText().trim());
        selectedTrain.setLoadCapacity(Double.parseDouble(txtCapacity.getText().trim()));
        selectedTrain.setMileage(Integer.parseInt(txtMileage.getText().trim()));

        try {
            if (model.updateTrain(selectedTrain)) {
                lblMessage.setText("Tren actualizado.");
                currentPage = 0;
                loadTrainsToTable();
                clearForm();
            } else {
                lblMessage.setText("Error al actualizar.");
            }
        } catch (Exception e) {
            lblMessage.setText("Error: " + e.getMessage());
        }
    }// GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedTrain == null) {
            lblMessage.setText("Seleccione un tren para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el tren '" + selectedTrain.getName() + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        try {
            if (model.deleteTrain(selectedTrain.getId())) {
                lblMessage.setText("Tren eliminado.");
                currentPage = 0;
                loadTrainsToTable();
                clearForm();
            } else {
                lblMessage.setText("Error al eliminar.");
            }
        } catch (Exception e) {
            lblMessage.setText("Error: " + e.getMessage());
        }
    }// GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRefreshActionPerformed
        filterQuery = "";
        currentPage = 0;
        cachedTrains = null;
        loadTrainsToTable();
        clearForm();
        lblMessage.setText("Lista actualizada.");
    }// GEN-LAST:event_btnRefreshActionPerformed

    private void btnBoardingActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBoardingActionPerformed
        if (selectedTrain == null) {
            lblMessage.setText("Seleccione un tren en la tabla.");
            return;
        }
        BoardingView view = new BoardingView(model);
        view.loadBoardingOrder(selectedTrain.getId());
        view.setVisible(true);
    }// GEN-LAST:event_btnBoardingActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLogoutActionPerformed
        model.detach(this);
        if (onLogout != null)
            onLogout.accept("");
    }// GEN-LAST:event_btnLogoutActionPerformed

    private void btnRoutesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRoutesActionPerformed
        RouteView view = new RouteView(model);
        view.setStationNames(model.getStationNames());
        view.setVisible(true);
    }// GEN-LAST:event_btnRoutesActionPerformed

    private void btnEmployeesActionPerformed(java.awt.event.ActionEvent evt) {
        EmployeeView view = new EmployeeView(model);
        view.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBoarding;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEmployees;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRoutes;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JTable tblTrains;
    private javax.swing.JTextField txtCapacity;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMileage;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}