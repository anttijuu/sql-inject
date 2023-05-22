package org.anttijuustila.sqlinject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.anttijuustila.sqlinject.model.GoodDatabase;
import org.anttijuustila.sqlinject.model.BadDatabase;
import org.anttijuustila.sqlinject.model.DatabaseInterface;
import org.anttijuustila.sqlinject.view.UserView;
import org.anttijuustila.sqlinject.view.UsersListView;
import org.anttijuustila.sqlinject.viewmodel.UsersModel;

/**
 * Hello world!
 *
 */
public class SQLInjectApp 
{
    DatabaseInterface database;
    
    public static void main( String[] args )
    {
        new SQLInjectApp().run();
    }

    private void run() {
        try {
            database = GoodDatabase.getInstance();
            database.open("users-good.sqlite");
            // database = BadDatabase.getInstance();
            // database.open("users-bad.sqlite");
            JFrame mainFrame = new JFrame("SQL Injection Demo");
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            Container container = mainFrame.getContentPane();
    
            UsersModel usersModel = new UsersModel(database);

            JPanel rootPanel = new JPanel(new BorderLayout(2, 2));
            UsersListView list = new UsersListView(usersModel);
            list.setMinimumSize(new Dimension(320, 320));
            rootPanel.add(list, BorderLayout.LINE_START);
            UserView userView = new UserView(usersModel);
            usersModel.addObserver(userView);
            rootPanel.add(userView);
            container.add(rootPanel, BorderLayout.CENTER);

            mainFrame.pack();
            mainFrame.setVisible(true);                
        } catch (SQLException e) {
            System.out.println("*** SQL ERROR: " + e.getMessage());
        }
    }
}
