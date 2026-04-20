package edu.upb.client.controller;

import edu.upb.client.model.ClientModel;
import edu.upb.client.view.PurchaseView;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

public class ClientController {
    private ClientModel model;
    private PurchaseView view;

    public ClientController(ClientModel model, PurchaseView view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        boolean connected = model.connect();
        if (!connected) {
            javax.swing.JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor.");
            return;
        }
        view.setStationNames(model.getStationNames());
        view.setPurchaseHandler(this::handlePurchase);
        view.setVisible(true);
    }

    private void handlePurchase(SaleDTO dto) {
        try {
            Ticket ticket = model.purchaseTicket(dto);
            if (ticket != null) {
                view.showTicket(ticket);
            } else {
                javax.swing.JOptionPane.showMessageDialog(view, "Compra fallida. Verifique disponibilidad.");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(view, "Error: " + e.getMessage());
        }
    }
}