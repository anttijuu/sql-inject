package org.anttijuustila.sqlinject.viewmodel;

import org.anttijuustila.sqlinject.model.User;

public interface UsersModelObserver {
	void selectionChanged(User user);
}
