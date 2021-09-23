/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.noswing.messagerie;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author thbogusz
 */
public class JdbcUsers {

    private Connection conn;

    public JdbcUsers() {
        try {
            // create our mysql database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(myDriver);

            // déclaration URL base de données... règlage du TIMESTAMP
            String myUrl = "jdbc:mysql://localhost/bdmessage"
                    + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC";

            //Connection à la base bdmessage sous MYSQL
            conn = DriverManager.getConnection(myUrl, "root", "");

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public ResultSet getAllUtilisateurs() {
        String query = "SELECT * FROM users";
        ResultSet rs = null;
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            System.err.print(e);
        }

        return rs;
    }

    public ResultSet getUtilisateur(String identifiant) {
        String query = "SELECT * FROM users where identifiant='" + identifiant + "'";
        ResultSet rs = null;
        try {
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            System.err.print(e);
        }

        return rs;
    }

    public void updateUtilisateur(int id, String[] données) {
        String sql = "UPDATE users"
                + " SET identifiant=?,"
                + "motDePasse=?,"
                + "service=?,"
                + "nom=?,"
                + "prenom=?,"
                + "role=?"
                + "where id=?";

        try {
            conn.setAutoCommit(false);

            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, données[0]);
            prepare.setString(2, données[1]);
            prepare.setString(3, données[2]);
            prepare.setString(4, données[3]);
            prepare.setString(5, données[4]);
            prepare.setString(6, données[5]);
            prepare.setInt(7, id);

            int r = prepare.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            System.err.print(e);
        }
    }

    public void insertUtilisateur(String[] données) {
        String sql = "INSERT into users "
                + "(identifiant,"
                + "motDePasse,"
                + "service,"
                + "nom,"
                + "prenom,"
                + "role)"
                + " VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement prepare;
            prepare = conn.prepareStatement(sql);
            prepare.setString(1, données[0]);
            prepare.setString(2, données[1]);
            prepare.setString(3, données[2]);
            prepare.setString(4, données[3]);
            prepare.setString(5, données[4]);
            prepare.setString(6, données[5]);

            int r = prepare.executeUpdate();
            prepare.close();
        } catch (SQLException e) {
            System.err.println(e);
        }

    }
    
    public void deleteUtilisateur(int id) {
        String sql = "DELETE FROM users"
                + " where id=?";

        try {
            conn.setAutoCommit(false);

            PreparedStatement prepare = conn.prepareStatement(sql);
           
            prepare.setInt(1, id);

            int r = prepare.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            System.err.print(e);
        }
    }
}
