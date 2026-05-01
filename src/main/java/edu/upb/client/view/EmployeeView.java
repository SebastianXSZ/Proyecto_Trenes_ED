package edu.upb.client.view;

import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.List;
import edu.upb.client.model.ClientModel;
import edu.upb.model.Employee;
import edu.upb.common.observer.Observer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeeView extends javax.swing.JFrame implements Observer {

    private transient ClientModel model;
    private Employee selectedEmployee = null;
    private transient List<Employee> cachedEmployees = null;

    public EmployeeView(ClientModel model) {
        this.model = model;
        this.model.attach(this);
        initComponents();
        loadEmployeesToTable();
        setLocationRelativeTo(null);
        setupTableSelectionListener();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                model.detach(EmployeeView.this);
            }
        });
    }

    private void setupTableSelectionListener() {
        tblEmployees.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblEmployees.getSelectedRow();
                if (selectedRow != -1) {
                    String id = tblEmployees.getValueAt(selectedRow, 0).toString();
                    txtId.setText(id);
                    txtName.setText(tblEmployees.getValueAt(selectedRow, 1).toString());
                    txtLastName.setText(tblEmployees.getValueAt(selectedRow, 2).toString());
                    cmbRole.setSelectedItem(tblEmployees.getValueAt(selectedRow, 3).toString());
                    txtTrainId.setText(tblEmployees.getValueAt(selectedRow, 4).toString());
                    selectedEmployee = findInCache(id);
                }
            }
        });
    }

    private Employee findInCache(String id) {
        if (cachedEmployees == null) return null;
        Iterator<Employee> it = cachedEmployees.iterator();
        while (it.hasNext()) {
            Employee e = it.next();
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    private void loadEmployeesToTable() {
        try {
            cachedEmployees = model.getAllEmployees();
            DefaultTableModel tableModel = (DefaultTableModel) tblEmployees.getModel();
            tableModel.setRowCount(0);
            if (cachedEmployees != null) {
                Iterator<Employee> it = cachedEmployees.iterator();
                while (it.hasNext()) {
                    Employee e = it.next();
                    tableModel.addRow(new Object[]{
                        e.getId(), e.getName(), e.getLastName(), e.getRole(), e.getAssignedTrainId()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
        }
    }

    @Override
    public void update(String event) {
        if (event.startsWith("EMPLOYEE_")) {
            loadEmployeesToTable();
        }
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel("ID:");
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel("Nombre:");
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel("Apellido:");
        txtLastName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel("Rol:");
        cmbRole = new javax.swing.JComboBox<>(new String[]{"Conductor", "Auxiliar", "Seguridad"});
        jLabel5 = new javax.swing.JLabel("ID Tren:");
        txtTrainId = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton("Agregar");
        btnUpdate = new javax.swing.JButton("Actualizar");
        btnDelete = new javax.swing.JButton("Eliminar");
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployees = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Empleados");

        tblEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nombre", "Apellido", "Rol", "ID Tren"}
        ));
        jScrollPane1.setViewportView(tblEmployees);

        btnAdd.addActionListener(evt -> {
            Employee emp = new Employee(txtId.getText(), txtName.getText(), txtLastName.getText(), cmbRole.getSelectedItem().toString(), txtTrainId.getText());
            model.addEmployee(emp);
        });

        btnUpdate.addActionListener(evt -> {
            if (selectedEmployee != null) {
                Employee emp = new Employee(txtId.getText(), txtName.getText(), txtLastName.getText(), cmbRole.getSelectedItem().toString(), txtTrainId.getText());
                model.updateEmployee(emp);
            }
        });

        btnDelete.addActionListener(evt -> {
            if (selectedEmployee != null) {
                model.deleteEmployee(selectedEmployee.getId());
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(txtId)
                            .addComponent(jLabel2)
                            .addComponent(txtName)
                            .addComponent(jLabel3)
                            .addComponent(txtLastName)
                            .addComponent(jLabel4)
                            .addComponent(cmbRole, 0, 150, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(txtTrainId))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTrainId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEmployees;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtTrainId;
}
