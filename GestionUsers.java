/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.noswing.messagerie;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import plum.console.Clavier;

/**
 *
 * @author thbogusz
 */
public class GestionUsers {

    /**
     * CONSTUCTEUR
     */
    public GestionUsers(String identifiant) throws SQLException {
        // Création de l'objet jdbc permettant d'administrer les utilisateurs
        JdbcUsers jdbc = new JdbcUsers();

        // pour les formulaires Ajouter et Modifier
        String champ[] = {"Identifiant", "Mot de passe", "Service", "Nom", "Prénom", "Rôle(admin/user)"};

        String titre = "UTILISATEURS";
        String[] menu = {"Liste des utilisateurs", "Ajouter un Utilisateur",
            "Modifier un utilisateur", "Supprimer un utilisateur", "Messages"};

        String[] reponse;
        int rep;
        do {
            rep = GMenu.show(titre, menu);

            switch (rep) {
                case 1:
                    ResultSet rs = jdbc.getAllUtilisateurs();
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nom = rs.getString("nom");
                        String prenom = rs.getString("prenom");
                        Date dateCreated = rs.getDate("date_created");
                        String role = rs.getString("role");
                        String identifiantu = rs.getString("identifiant");
                        String service = rs.getString("service");

                        // print the results
                        System.out.format("%s, %s, %s, %s, %s, %s, %s\n",
                                id, nom, prenom, dateCreated, identifiantu, role, service);
                    }

                    break;

                case 2:
                    reponse = GForm.show("AJOUTER UN UTILISATEUR", champ, null);

                    if (reponse != null) {
                        jdbc.insertUtilisateur(reponse);
                    }
                    break;

                case 3:
                    String[] champModif = {"Identifiant"};
                    reponse = GForm.show("MODIFIER UN UTILISATEUR -> Saisir l'identifiant", champModif, null);

                    ResultSet rsm = jdbc.getUtilisateur(reponse[0]);

                    if (rsm.next() == false) {
                        GForm.message("Identifiant inconnu...");
                        break;
                    }

                    String data[] = {rsm.getString("identifiant"),
                        rsm.getString("motDePasse"),
                        rsm.getString("service"),
                        rsm.getString("nom"),
                        rsm.getString("prenom"),
                        rsm.getString("role")
                    };

                    reponse = GForm.show("MODIFIER UN UTILISATEUR -> modifier les données", champ, data);

                    if (reponse != null) {
                        int id = rsm.getInt("id");
                        jdbc.updateUtilisateur(id, reponse);
                    }
                    break;

                case 4:
                    String[] champSup = {"Identifiant"};
                    String[] usersup = GForm.show("SUPPRIMER UN UTILISATEUR -> Saisir l'identifiant", champSup, null);

                    ResultSet rss = jdbc.getUtilisateur(usersup[0]);

                    if (rss.next() == false) {
                        GForm.message("Identifiant inconnu...");
                        break;
                    }

                    String confirm[] = {"CONFIRMEZ LA SUPPRESSION (Tapez OUI)"};

                    reponse = GForm.show("SUPPRIMER UN UTILISATEUR -> CONFIRMATION", confirm, null);

                    if (reponse != null && reponse[0].equals("OUI")) {
                        jdbc.deleteUtilisateur(rss.getInt("id"));
                        GForm.message(usersup[0] + " a été supprimé");
                    } else {
                        GForm.message("AUCUNE SUPPRESSION...");
                    }
                    break;

                case 5: //Accès à la messagerie
                    new GestionMessage(identifiant);
                    break;

            }
        } while (rep != 0);

    }
}
