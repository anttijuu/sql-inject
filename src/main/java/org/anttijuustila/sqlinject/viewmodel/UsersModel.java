package org.anttijuustila.sqlinject.viewmodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.anttijuustila.sqlinject.model.DatabaseInterface;
import org.anttijuustila.sqlinject.model.User;

public class UsersModel extends AbstractListModel<User> implements ListSelectionListener {

	private transient DatabaseInterface database;
	private transient List<User> users;
	private transient User selectedUser = null;

	private transient List<UsersModelObserver> observers;
	
	public UsersModel(DatabaseInterface database) {
		super();
		this.database = database;
		observers = new ArrayList<>();
		refresh();
	}

	public void add(User user) throws SQLException {
		database.addUser(user);
		refresh();
	}

	public void update(User user) throws SQLException {
		database.saveUser(user);
		refresh();
	}

	public boolean verifyUser(String name, String password) throws SQLException {
		return database.isRegisteredUser(name, password);
	}

	@Override
	public int getSize() {
		return users.size();
	}

	@Override
	public User getElementAt(int index) {
		return users.get(index);
	}

	public void refresh() {
		users = database.getAllUsers();
		fireContentsChanged(this, 0, getSize());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			JList<User> source = (JList<User>)e.getSource();
			int selectedIndex = source.getSelectedIndex();
			if (selectedIndex >= 0 && selectedIndex < users.size()) {
				selectedUser = users.get(selectedIndex);
			} else {
				selectedUser = null;
			}
			notifyObservers();	
		}
	}

	public void addObserver(UsersModelObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(UsersModelObserver observer) {
		observers.remove(observer);
	}

	private void notifyObservers() {
		for (UsersModelObserver observer : observers) {
			observer.selectionChanged(selectedUser);
		}
	}
	
}
