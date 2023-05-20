package org.anttijuustila.sqlinject.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.anttijuustila.sqlinject.model.User;
import org.anttijuustila.sqlinject.viewmodel.UsersModel;
import org.anttijuustila.sqlinject.viewmodel.UsersModelObserver;

public class UserView extends JPanel implements ActionListener, UsersModelObserver {

	private JLabel uuidLabel;
	private JTextField nameText;
	private JTextField emailText;
	private JTextField passWordText;

	private JButton saveButton;
	private JButton newButton;

	private UsersModel model;
	private User user;

	public UserView(UsersModel model) {
		super();
		this.model = model;
		this.user = null;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel editorsPanel = new JPanel();
		editorsPanel.setLayout(new BoxLayout(editorsPanel, BoxLayout.Y_AXIS));
		uuidLabel = new JLabel("");
		editorsPanel.add(uuidLabel);
		nameText = new JTextField("");
		nameText.setPreferredSize(new Dimension(256, 20));
		editorsPanel.add(nameText);
		emailText = new JTextField("");
		emailText.setPreferredSize(new Dimension(256, 20));
		editorsPanel.add(emailText);
		passWordText = new JTextField("");
		passWordText.setPreferredSize(new Dimension(256, 20));
		editorsPanel.add(passWordText);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		saveButton = new JButton("Save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		buttonsPanel.add(saveButton);
		newButton = new JButton("New");
		newButton.setActionCommand("new");
		newButton.addActionListener(this);
		buttonsPanel.add(newButton);

		add(editorsPanel, BorderLayout.NORTH);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void referesh() {
		if (null != user) {
			uuidLabel.setText(user.getId());
			nameText.setText(user.getName());
			emailText.setText(user.getEmail());
			passWordText.setText(user.getPassword());
		} else {
			uuidLabel.setText("No user selected");
			nameText.setText("");
			emailText.setText("");
			passWordText.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("save")) {
			if (null != user) {
				final String name = nameText.getText();
				final String email = emailText.getText();
				final String passwd = passWordText.getText();
				user.setUsername(name);
				user.setEmail(email);
				user.setPassword(passwd);
				try {
					model.update(user);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "Something wrong with database: " + e1.getMessage(),
							"Could not update User",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getActionCommand().equals("new")) {
			if (null != user) {
				final String name = nameText.getText();
				final String email = emailText.getText();
				final String passwd = passWordText.getText();
				User newUser = new User(name, passwd, email);
				try {
					model.add(newUser);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "Something wrong with database: " + e1.getMessage(),
							"Could not add new User",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	@Override
	public void selectionChanged(User user) {
		this.user = user;
		referesh();
	}

}
