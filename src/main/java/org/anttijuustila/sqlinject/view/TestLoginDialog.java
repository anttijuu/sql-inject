package org.anttijuustila.sqlinject.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.anttijuustila.sqlinject.viewmodel.UsersModel;

// Based on https://stackoverflow.com/a/20286605
class TestLoginDialog extends JDialog {

	private UsersModel model;

	private final JLabel labelUserName = new JLabel("Username");
	private final JLabel labelPassword = new JLabel("Password");

	private final JTextField textUserName = new JTextField(15);
	private final JPasswordField textPassword = new JPasswordField();

	private final JButton buttonLogin = new JButton("Test Login");
	private final JButton buttonCancel = new JButton("Close");

	private final JLabel labelStatus = new JLabel(" ");

	public TestLoginDialog(final Window parent, UsersModel model) {
		super(parent, ModalityType.APPLICATION_MODAL);
		this.model = model;

		JPanel p3 = new JPanel(new GridLayout(2, 1));
		p3.add(labelUserName);
		p3.add(labelPassword);

		JPanel p4 = new JPanel(new GridLayout(2, 1));
		p4.add(textUserName);
		p4.add(textPassword);

		JPanel p1 = new JPanel();
		p1.add(p3);
		p1.add(p4);

		JPanel p2 = new JPanel();
		p2.add(buttonLogin);
		p2.add(buttonCancel);

		JPanel p5 = new JPanel(new BorderLayout());
		p5.add(p2, BorderLayout.CENTER);
		p5.add(labelStatus, BorderLayout.NORTH);
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

		setLayout(new BorderLayout());
		add(p1, BorderLayout.CENTER);
		add(p5, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		buttonLogin.addActionListener(e -> {
			final String userName = textUserName.getText();
			final String password = String.valueOf(textPassword.getPassword());
			try {
				if (model.verifyUser(userName, password)) {
					labelStatus.setText("Correct username and password");
				} else {
					labelStatus.setText("Invalid username or password");
				}						
			} catch (SQLException ex) {
				labelStatus.setText("Database error: " + ex.getLocalizedMessage());
			}
		});
		buttonCancel.addActionListener(e -> dispose() );
	}
}