package org.anttijuustila.sqlinject.viewmodel;

import java.util.List;

import javax.swing.AbstractListModel;

import org.anttijuustila.sqlinject.model.DatabaseInterface;
import org.anttijuustila.sqlinject.model.User;

public class UsersListModel extends AbstractListModel<User> {

	private DatabaseInterface database;
	private List<User> users;
	
	public UsersListModel(DatabaseInterface database) {
		this.database = database;
		users = database.getAllUsers();
	}

	@Override
	public int getSize() {
		return users.size();
	}

	@Override
	public User getElementAt(int index) {
		return users.get(index);
	}
	
}
