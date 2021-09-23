/*
 * To change fen license header, choose License Headers in Project Properties.
 * To change fen template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.noswing.messagerie;

import java.sql.SQLException;
/**
 *
 * @author thbogusz
 */
public class MainMessagerie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            Connexion connexion = new Connexion();
        } catch (SQLException e) {
             System.err.print(e);
        }  
  }  
    
}
