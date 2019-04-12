package Repository;

import Domain.Rezervare;
import Utils.BDUtils;
import Utils.HasID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RezervariRepoBD implements IRezervareRepo<String, Rezervare> {
    BDUtils bdUtils;
    public static final Logger logger= LogManager.getLogger();


    public RezervariRepoBD(Properties properties) {
        this.bdUtils = new BDUtils(properties);
    }

    @Override
    public void save(Rezervare rezervare) {

        logger.traceEntry();
        Connection con= bdUtils.getConnection();
        List<HasID> excursii=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("insert into agentie.Rezervare(idExcursie,NumeClient,NumarTelefon,NumarLocuri) values (?,?,?,?);")) {
            preStmt.setInt(1,Integer.valueOf(rezervare.getExcursie().getID()));
            preStmt.setString(2,rezervare.getNumeClient());
            preStmt.setString(3,rezervare.getNumarTelefon());
            preStmt.setInt(4,rezervare.getNumarBilete());
            preStmt.executeUpdate() ;
                try(PreparedStatement preparedStatement=con.prepareStatement("update agentie.Excursie  set NrLocuri=? where id=?;")){

                    int locuri=rezervare.getExcursie().getNrLocuri()-rezervare.getNumarBilete();

                    preparedStatement.setInt(1,locuri);
                    preparedStatement.setInt(2,Integer.valueOf(rezervare.getExcursie().getID()));
                    preparedStatement.executeUpdate();

            }
            con.close();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }

        logger.traceExit(excursii);
    }

    @Override
    public void delete(Rezervare rezervare) {

    }

    @Override
    public Iterable<Rezervare> findAll() {
        return null;
    }

    @Override
    public Rezervare findOne() {
        return null;
    }
}
