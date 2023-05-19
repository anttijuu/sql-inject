package org.anttijuustila.sqlinject;

import javax.swing.JFrame;

import org.anttijuustila.sqlinject.model.Database;
import org.anttijuustila.sqlinject.model.DatabaseInterface;

/**
 * Hello world!
 *
 */
public class App 
{
    private JFrame mainFrame;
    DatabaseInterface database;
    
    public static void main( String[] args )
    {
        new App().run();
    }

    private void run() {
        database = new Database();

    }
}
