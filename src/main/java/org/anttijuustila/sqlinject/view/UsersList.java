package org.anttijuustila.sqlinject.view;

import javax.swing.JList;
import javax.swing.JPanel;

import org.anttijuustila.sqlinject.viewmodel.UsersListModel;

public class UsersList extends JPanel {
	
	private JList usersList;
	
	public UsersList(UsersListModel model) {
		super();
		usersList = new JList<>(model);
	}
}
