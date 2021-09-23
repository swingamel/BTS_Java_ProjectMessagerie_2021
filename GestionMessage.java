/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.noswing.messagerie;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thbogusz
 */
public class GestionMessage {

    public GestionMessage(String identifiant) throws SQLException {
        // Création de l'objet jdbc permettant d'administrer les utilisateurs
        JdbcMessage jdbc = new JdbcMessage();

        String titre = "MESSAGES";

        String champ[] = {"Quel est votre identifiant?", "Quel est le destinataire?(Tapez l'identifiant de la personne)", "Quel est l'objet du message?", "Quel est le message?", "Quel est la dateEnvoi?(AAAA-MM-JJ)"};

        String champMessage[] = {"message", "dateEnvoi"};

        String champReponse[] = {"Quel le numéro du message auquel vous voulez répondre"};

        String[] menu = {"Voir les messages", "Envoyer un message",
            "Supprimer un message", "Répondre à un message"};

        String[] reponse;
        int rep;
        do {
            rep = GMenu.show(titre, menu);

            switch (rep) {
                case 1:
                    ResultSet rs = jdbc.getAllMessage();
                    ResultSet rso = jdbc.getAllUtilisateurs();
                    String nom[] = new String[20];
                    int Id[] = new int[20];
                    int i = 0;
                    while (rso.next()) {
                        String NomUsers = rso.getString("identifiant");
                        int IdUsers = rso.getInt("id");
                        nom[i] = NomUsers;
                        Id[i] = IdUsers;
                        i++;
                    }

                    while (rs.next()) {
                        String oUsers = "";
                        String dUsers = "";
                        int id = rs.getInt("id");
                        int origineUsers = rs.getInt("origineUsers");
                        int destinataireUsers = rs.getInt("destinataireUsers");
                        String objet = rs.getString("objet");
                        String message = rs.getString("message");
                        Date dateEnvoi = rs.getDate("dateEnvoi");
                        String etat = rs.getString("etat");

                        for (i = 0; i < Id.length; i++) {
                            if (origineUsers == Id[i]) {
                                oUsers = nom[i];
                            }
                        }
                        for (i = 0; i < Id.length; i++) {
                            if (destinataireUsers == Id[i]) {
                                dUsers = nom[i];
                            }
                        }
                        System.out.format("%s\n%s\n%s\n%s\n%s\n%s\n\n",
                                "Objet: " + objet,
                                "Message de " + oUsers,
                                "À " + dUsers,
                                "Le " + dateEnvoi,
                                "Texte:",
                                message);
                    }
                    break;

                case 2:
                    reponse = GForm.show("AJOUTER UN MESSAGE", champ, null);
                    int ori = 0;
                    rs = jdbc.getUtilisateurbyId(identifiant);
                    while (rs.next()) {
                        ori = rs.getInt("id");
                    }
                    String s = String.valueOf(ori);
                    rs = jdbc.getUtilisateurbyId(reponse[0]);
                    int des = 0;
                    while (rs.next()) {
                        des = rs.getInt("id");
                    }
                    String p = String.valueOf(des);
                    if (reponse != null) {
                        String envoyer[] = {s, p, reponse[1], reponse[2], reponse[3], "non lu"};
                        jdbc.insertMessage(envoyer);
                    }
                    break;

                case 3:
                    rs = jdbc.getAllMessage();
                    rso = jdbc.getAllUtilisateurs();
                    String nom3[] = new String[20];
                    int Id3[] = new int[20];
                    i = 0;
                    while (rso.next()) {
                        String NomUsers = rso.getString("identifiant");
                        int IdUsers = rso.getInt("id");
                        nom3[i] = NomUsers;
                        Id3[i] = IdUsers;
                        i++;
                    }
                    int numero = 0;
                    while (rs.next()) {
                           String oUsers = "";
                        String dUsers = "";
                        numero++;
                        int id = rs.getInt("id");
                        int origineUsers = rs.getInt("origineUsers");
                        int destinataireUsers = rs.getInt("destinataireUsers");
                        String objet = rs.getString("objet");
                        String message = rs.getString("message");
                        Date dateEnvoi = rs.getDate("dateEnvoi");
                        String etat = rs.getString("etat");
                        // print the results

                        for (i = 0; i < Id3.length; i++) {
                            if (origineUsers == Id3[i]) {
                                oUsers = nom3[i];

                            }

                        }

                        for (i = 0; i < Id3.length; i++) {
                            if (destinataireUsers == Id3[i]) {
                                dUsers = nom3[i];

                            }

                        }
                        System.out.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n",
                                "Numéro:" + id,
                                "Objet: " + objet,
                                "Message de " + oUsers,
                                "À " + dUsers,
                                "Le " + dateEnvoi,
                                "Texte:",
                                message);

                    }
                    String[] champSup = {"Id"};
                    String[] usersup = GForm.show("SUPPRIMER UN MESSAGE -> Saisir le numero du message", champSup, null);

                    ResultSet rss = jdbc.getMessage(usersup[0]);

                    if (rss.next() == false) {
                        GForm.message("Numero inconnu...");
                        break;
                    }
                    String confirm[] = {"CONFIRMEZ LA SUPPRESSION (Tapez OUI)"};

                    reponse = GForm.show("SUPPRIMER UN MESSAGE -> CONFIRMATION", confirm, null);

                    if (reponse != null && reponse[0].equals("OUI")) {
                        jdbc.deleteMessage(rss.getInt("id"));
                        GForm.message("Le message a été supprimé");
                    } else {
                        GForm.message("AUCUNE SUPPRESSION...");
                    }
                    break;

                case 4:
                    rs = jdbc.getAllMessage();
                    rso = jdbc.getAllUtilisateurs();
                    String nom2[] = new String[20];
                    int Id2[] = new int[20];
                    i = 0;
                    while (rso.next()) {
                        String NomUsers = rso.getString("identifiant");
                        int IdUsers = rso.getInt("id");
                        nom2[i] = NomUsers;
                        Id2[i] = IdUsers;
                        i++;
                    }
                    numero = 0;
                    while (rs.next()) {
                        String oUsers = "";
                        String dUsers = "";
                        numero++;
                        int id = rs.getInt("id");
                        int origineUsers = rs.getInt("origineUsers");
                        int destinataireUsers = rs.getInt("destinataireUsers");
                        String objet = rs.getString("objet");
                        String message = rs.getString("message");
                        Date dateEnvoi = rs.getDate("dateEnvoi");
                        String etat = rs.getString("etat");
                        // print the results

                        for (i = 0; i < Id2.length; i++) {
                            if (origineUsers == Id2[i]) {
                                oUsers = nom2[i];

                            }

                        }

                        for (i = 0; i < Id2.length; i++) {
                            if (destinataireUsers == Id2[i]) {
                                dUsers = nom2[i];

                            }

                        }
                        System.out.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n\n",
                                "Numéro:" + id,
                                "Objet: " + objet,
                                "Message de " + oUsers,
                                "À " + dUsers,
                                "Le " + dateEnvoi,
                                "Texte:",
                                message);

                    }
                    reponse = GForm.show("REPONDRE A UN MESSAGE", champReponse, null);

                    ResultSet rsm = jdbc.getMessage(reponse[0]);

                    if (rsm.next() == false) {
                        GForm.message("Message inconnu...");
                        break;
                    }
                    String origine = rsm.getString("origineUsers");
                    String destinataire = rsm.getString("destinataireUsers");
                    String objet = rsm.getString("objet");
                    reponse = GForm.show("REPONDRE A UN MESSAGE", champMessage, null);

                    String ReMessage[] = {destinataire, origine, objet, reponse[0], reponse[1], "non lu"};
                    if (reponse != null) {
                        jdbc.ReponseMessage(ReMessage);
                    }
                    break;

            }
        } while (rep != 0);

    }
}
