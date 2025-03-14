import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PhonebookUI {
    private JFrame frame;
    private JTextField nameField, phoneField, emailField, addressField;
    private JTable table;
    private DefaultTableModel tableModel;

    public PhonebookUI() {
        frame = new JFrame("📞 Phonebook App");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        // Input Panel
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = createStyledTextField();
        phoneField = createStyledTextField();
        emailField = createStyledTextField();
        addressField = createStyledTextField();

        panel.add(createStyledLabel("Name:"));
        panel.add(nameField);
        panel.add(createStyledLabel("Phone:"));
        panel.add(phoneField);
        panel.add(createStyledLabel("Email:"));
        panel.add(emailField);
        panel.add(createStyledLabel("Address:"));
        panel.add(addressField);

        frame.add(panel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = createStyledButton("Add", "➕", e -> addContact());
        JButton updateButton = createStyledButton("Update", "✏️", e -> updateContact());
        JButton deleteButton = createStyledButton("Delete", "❌", e -> deleteContact());
        JButton viewButton = createStyledButton("View", "📄", e -> loadContacts());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Email", "Address"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        loadContacts(); 

        frame.setVisible(true);
    }

    private void loadContacts() {
        ContactController.loadContacts(tableModel);
    }

    private void addContact() {
        if (ContactController.addContact(nameField.getText(), phoneField.getText(), emailField.getText(), addressField.getText())) {
            showSuccessDialog("Contact added successfully!");
            loadContacts();
        } else {
            showErrorDialog("Failed to add contact. Name and Phone cannot be empty!");
        }
    }

    private void updateContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showErrorDialog("Select a contact to update!");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        if (ContactController.updateContact(id, nameField.getText(), phoneField.getText(), emailField.getText(), addressField.getText())) {
            showSuccessDialog("Contact updated successfully!");
            loadContacts();
        } else {
            showErrorDialog("Failed to update contact. Name and Phone cannot be empty!");
        }
    }

    private void deleteContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showErrorDialog("Select a contact to delete!");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        if (ContactController.deleteContact(id)) {
            showSuccessDialog("Contact deleted successfully!");
            loadContacts();
        } else {
            showErrorDialog("Failed to delete contact.");
        }
    }

    private JLabel createStyledLabel(String text) { return new JLabel(text); }
    private JTextField createStyledTextField() { return new JTextField(); }
    private JButton createStyledButton(String text, String emoji, ActionListener action) {
        JButton button = new JButton(emoji + " " + text);
        button.addActionListener(action);
        return button;
    }

    private void showErrorDialog(String message) { JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE); }
    private void showSuccessDialog(String message) { JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE); }
}
