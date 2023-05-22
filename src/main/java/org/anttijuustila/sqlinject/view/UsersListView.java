package org.anttijuustila.sqlinject.view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.anttijuustila.sqlinject.model.User;
import org.anttijuustila.sqlinject.viewmodel.UsersModel;

public class UsersListView extends JPanel implements ListDataListener {
	
	private JList<User> usersList;
	
	public UsersListView(UsersModel model) {
		super();
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		usersList = new JList<>(model);
		usersList.addListSelectionListener(model);
		usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(usersList);
		scrollPane.setPreferredSize(new Dimension(256, 300));
		model.addListDataListener(this);
		add(scrollPane);
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		// Not used
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		// Not used
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		// Not used
	}
}
