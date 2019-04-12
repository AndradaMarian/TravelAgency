package Repository;

import Domain.Excursie;
import Utils.BDUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExcursieRepoBD implements IExcursieRepo<String,Excursie> {
    BDUtils bdUtils;
    public static final Logger logger= LogManager.getLogger();
    Map<String, Excursie> list=new HashMap<>();
    public ExcursieRepoBD(Properties properties) {
    this.bdUtils=new BDUtils(properties);
    }


    @Override
    public void save(Excursie entity) {

    }

    @Override
    public void delete(Excursie entity) {

    }

    @Override
    public Iterable<Excursie> findAll() {
        return null;
    }


    @Override
    public Excursie findOne() {
        return null;
    }

    @Override
    public Iterable<Excursie> findObiectivInterval(String obiectiv, LocalTime start, LocalTime plecare) {
        logger.traceEntry();
        Connection con= bdUtils.getConnection();
        List<Excursie>excursii=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from agentie.excursie where ObiectivTuristic=? and OraPlecare>=? and OraPlecare<=?")) {
            preStmt.setString(1,obiectiv);
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("HH:mm:ss");
            preStmt.setTime(2, Time.valueOf(start.format(formatter)));
            preStmt.setTime(3, Time.valueOf(plecare.format(formatter)));

            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String firma=result.getString("FirmaTransport");
                    LocalTime ora=result.getTime("OraPlecare").toLocalTime();
                    double pret =result.getDouble("Pret");
                    int nrLocuri=result.getInt("NrLocuri");
                    excursii.add(new Excursie(String.valueOf(id),obiectiv,firma,ora,pret,nrLocuri));
                }
            }
            con.close();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(excursii);
        return excursii;
    }
}
