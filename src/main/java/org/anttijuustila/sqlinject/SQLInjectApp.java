package org.anttijuustila.sqlinject;

import java.awt.BorderLayout;
import java.awt.Container;
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
    private JFrame mainFrame;
    DatabaseInterface database;
    
    public static void main( String[] args )
    {
        new SQLInjectApp().run();
    }

    private void run() {
        try {
            // database = GoodDatabase.getInstance();
            // database.open("users-good.sqlite");
            database = BadDatabase.getInstance();
            database.open("users-bad.sqlite");
            mainFrame = new JFrame("SQL Injection");
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            Container container = mainFrame.getContentPane();
    
            UsersModel usersModel = new UsersModel(database);
            UsersListView list = new UsersListView(usersModel);
            container.add(list, BorderLayout.CENTER);
            UserView userView = new UserView(usersModel);
            usersModel.addObserver(userView);
            container.add(userView, BorderLayout.LINE_END);

            mainFrame.pack();
            mainFrame.setVisible(true);                
        } catch (SQLException e) {
            System.out.println("*** SQL ERROR: " + e.getMessage());
        }
    }
}
