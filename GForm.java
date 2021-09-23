/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.noswing.messagerie;

import java.util.Arrays;
import plum.console.Clavier;

/**
 *
 * @author thbogusz
 */
public class GForm {

    public static String[] show(String leTitre, String[] champ, String data[]) {
        String espace;
        String[] reponse = new String[champ.length];
        
        int i;

        String titre = "MESSAGERIE -> " + leTitre;
        espace = "";
        if (titre.length() < 40) {
            espace = new String(new char[40 - titre.length()]).replace("\0", " ");
        }
        titre = Color.ANSI_BLUE_BACKGROUND + " "
                + Color.ANSI_BLUE_BACKGROUND + Color.ANSI_WHITE
                + titre
                + espace
                + Color.ANSI_BLUE_BACKGROUND + " ";
        System.out.println(titre);

        for (i = 0; i < champ.length; i++) {
            String d = "";
            if (data != null) {
                d = "(" + data[i] + ")";
            }
            String item = "  " + champ[i] + d + " ? ";
            espace = "";
            if (item.length() < 30) {
                espace = new String(new char[30 - item.length()]).replace("\0", " ");
            }
            item += espace;
            item = Color.ANSI_BLUE_BACKGROUND + " "
                    + Color.ANSI_WHITE_BACKGROUND + Color.ANSI_BLUE + item;

            reponse[i] = Clavier.lireTexte(item);
            
            if ( data!=null & reponse[i].equals("")){
                reponse[i]=data[i]; // car aucun changement
            }
        }

        String fermetureMenu = Color.ANSI_BLUE_BACKGROUND + " "
                + Color.ANSI_GREEN + Color.ANSI_BLUE_BACKGROUND
                + new String(new char[40]).replace("\0", " ")
                + Color.ANSI_BLUE_BACKGROUND + " ";

        String rep = "";
        if (champ.length > 1) {
            while (!rep.equals("N") & !rep.equals("O")) {
                rep = Clavier.lireTexte(Color.ANSI_BLUE_BACKGROUND
                        + Color.ANSI_WHITE
                        + "VALIDER (O/N) ? "
                        + Color.ANSI_RESET);
            }
        }

        if (rep.equals("N")) {
            // Aucune réponse à retourner
            return null;
        }
        
        return reponse;

    }

    public static void message(String message) {
        String messagef = Color.ANSI_BLUE_BACKGROUND + "!!"
                + Color.ANSI_WHITE + Color.ANSI_YELLOW_BACKGROUND
                + message
                + Color.ANSI_BLUE_BACKGROUND + "!!";
        
        System.out.println(messagef);
    }
}
