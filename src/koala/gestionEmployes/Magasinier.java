package koala.gestionEmployes;

import koala.db.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Magasinier extends Employe {
    private int idMagasinier;

    public static Connection connection = ConnectionManager.getInstance().getConnection();

    public static List<Employe> loadTableToList() throws SQLException {
        return EmployeManager.loadTableToList("magasinier");
    }

    @Override
    public int getIdSpecial() {
        return idMagasinier;
    }

    @Override
    public void setIdSpecial(int idSpecial) {
        this.idMagasinier = idSpecial;
    }

    public int getIdMagasinier() {
        return idMagasinier;
    }

    public void setIdMagasinier(int idMagasinier) {
        this.idMagasinier = idMagasinier;
    }

    public static class Notification {


        private int idNotification;
        private int idMagasinier;
        private int idIngredient;
        private int idCuisinier;

        private boolean read;
        private boolean ignore;


        public Notification() {
        }

        public Notification(int idNotification, int idMagasinier, int idIngredient, int idCuisinier, boolean read, boolean ignore) {
            this.idNotification = idNotification;
            this.idMagasinier = idMagasinier;
            this.idIngredient = idIngredient;
            this.idCuisinier = idCuisinier;
            this.read = read;
            this.ignore = ignore;
        }

        public int getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(int idIngredient) {
            this.idIngredient = idIngredient;
        }

        public int getIdCuisinier() {
            return idCuisinier;
        }

        public void setIdCuisinier(int idCuisinier) {
            this.idCuisinier = idCuisinier;
        }

        public int getIdNotification() {
            return idNotification;
        }

        public void setIdNotification(int idNotification) {
            this.idNotification = idNotification;
        }

        public int getIdMagasinier() {
            return idMagasinier;
        }

        public void setIdMagasinier(int idMagasinier) {
            this.idMagasinier = idMagasinier;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("UPDATE magasinierNotification as mN set mN.read = "+ read+ " WHERE idmagasinierNotification = "+ idNotification);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.read = read;
        }

        public boolean isIgnore() {
            return ignore;
        }

        public void setIgnore(boolean ignore) {
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("UPDATE magasinierNotification as mN set mN.ignore = "+ ignore+ " WHERE idmagasinierNotification = "+ idNotification);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.ignore = ignore;
        }

    }

    public static class NotificationManager {
        static String sql_loadNReadNIgnoreTableToList = "SELECT * FROM magasinierNotification as mN WHERE mN.read = FALSE AND mN.ignore = FALSE ";
        static String sql_loadNReadIgnoreTableToList = "SELECT * FROM magasinierNotification as mN WHERE mN.read = FALSE";
        static String sql_loadReadTableToList = "SELECT * FROM magasinierNotification as mN WHERE mN.read = TRUE";


        public static void loadObjectToRow(Notification notification) {
            try (
                    Statement stmt = connection.createStatement();
            ){
                stmt.executeUpdate("INSERT INTO magasiniernotification (idMagasinier, idIngredient, idCuisiner, magasiniernotification.read, magasiniernotification.ignore) VALUES(" + notification.idMagasinier + ", "+ notification.idIngredient + ", "+ notification.idCuisinier + ", "+ notification.isRead() + ", "+ notification.isIgnore() +")");



            } catch(Exception ex){
                ex.printStackTrace();
            }

        }

        public static List<Notification> loadNReadNIgnoreTableToList() throws SQLException {
            return NotificationManager.loadTableToList(sql_loadNReadNIgnoreTableToList);
        }

        public static List<Notification> loadNReadIgnoreTableToList() throws SQLException {
            return NotificationManager.loadTableToList(sql_loadNReadIgnoreTableToList);
        }

        public static List<Notification> loadReadTableToList() throws SQLException {
            return NotificationManager.loadTableToList(sql_loadReadTableToList);
        }

        private static Notification loadCurrentRowToObject(ResultSet rs) throws SQLException {
            Notification notification = new Notification();

            notification.setIdNotification(rs.getInt("idmagasinierNotification"));
            notification.setIdMagasinier(rs.getInt("idMagasinier"));
            notification.setIdIngredient(rs.getInt("idIngredient"));
            notification.setIdCuisinier(rs.getInt("idCuisiner"));
            notification.setRead(rs.getBoolean("read"));
            notification.setIgnore(rs.getBoolean("ignore"));

            return notification;
        }

        public static List<Notification> loadTableToList(String sql) throws SQLException {
            List<Notification> list = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                list.add(loadCurrentRowToObject(rs));
            }
            statement.close();
            rs.close();

            return list;
        }

        public static void remove(int idNotification) {
            try (
                    Statement stmt = connection.createStatement();
            ){
                stmt.executeUpdate("DELETE FROM magasinierNotification WHERE idmagasiniernotification = " + idNotification);



            } catch(Exception ex){
                ex.printStackTrace();
            }
        }

        public static Notification getLatestNot() {
            return null;
        }
    }




}