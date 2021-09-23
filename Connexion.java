/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.noswing.messagerie;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thbogusz
 */
public class Connexion {

    public Connexion() throws SQLException {
// Création de l'objet jdbc permettant d'administrer les utilisateurs
        JdbcUsers jdbc = new JdbcUsers();
        String[] champ = {"Identifiant", "Mot de passe"};
        ResultSet rs;

        while (true) {
            String[] reponse = GForm.show("CONNEXION", champ, null);

            if (reponse == null) {
                continue; // on reste dans la boucle sans examiner le reste
            }

            rs = jdbc.getUtilisateur(reponse[0]);

            if (rs.next() == false) {
                GForm.message("Identifiant inconnu...");
                continue;
            }

            if (!rs.getString("MotDePasse").equals(reponse[1])) {
                GForm.message("Mot de passe incorrect...");
                continue;
            }

            break; //on sort de la boucle car tout est ok

        }

        //--- identifiant et mot de passe correct : accéder à la messagerie
        try {
            if (rs.getString("role").equals("admin")) {
                new GestionUsers(rs.getString("identifiant"));
            } else {
                new GestionMessage(rs.getString("identifiant"));
            }
        } catch (SQLException e) {
            System.err.print(e);
        }

    }

}
