package ui;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JTable tableContacts;
    private JButton buttonNewContact;
    private JLabel labelContactCount;
    private JButton buttonRemove;

    private final ContactBusiness mContactBusiness;
    private String mName = "";
    private String mPhone = "";

    public MainForm() {

        mContactBusiness = new ContactBusiness();

        this.setContentPane(rootPanel);
        this.setSize(500, 250);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setEvents();

        this.loadContacts();
    }

    private void setEvents() {
        this.buttonNewContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new ContactForm();

                dispose();

            }
        });

        this.tableContacts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {

                    if (tableContacts.getSelectedRow() != -1) {
                        mName = tableContacts.getValueAt(tableContacts.getSelectedRow(), 0).toString();
                        mPhone = tableContacts.getValueAt(tableContacts.getSelectedRow(), 1).toString();
                    }

                }
            }
        });

        this.buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    mContactBusiness.delete(mName, mPhone);

                    mName = "";
                    mPhone = "";

                } catch (Exception excp) {
                    JOptionPane.showMessageDialog(new JFrame(), excp.getMessage(), "Erro ao remover", JOptionPane.ERROR_MESSAGE);
                } finally {

                    loadContacts();

                }
            }
        });
    }

    private void loadContacts() {
        List<ContactEntity> contactList = this.mContactBusiness.getList();

        String[] columnNames = {"Nome", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for (ContactEntity entity : contactList) {
            Object[] o = new Object[2];
            o[0] = entity.getName();
            o[1] = entity.getPhone();

            model.addRow(o);
        }

        this.tableContacts.clearSelection();
        this.tableContacts.setModel(model);

        labelContactCount.setText(mContactBusiness.getContactCountDescription());
    }
}