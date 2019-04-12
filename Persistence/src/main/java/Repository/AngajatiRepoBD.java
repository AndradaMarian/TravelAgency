package Repository;

import Domain.Angajat;
import Utils.BDUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AngajatiRepoBD implements IAngajatiRepo<String,Angajat> {
    BDUtils bdUtils;
    public static final Logger logger= LogManager.getLogger();

    public AngajatiRepoBD(Properties properties) {
        bdUtils=new BDUtils(properties);
    }

    @Override
    public void save(Angajat entity) {

    }

    @Override
    public void delete(Angajat entity) {

    }

    @Override
    public Iterable<Angajat> findAll() {
        return null;
    }

    @Override
    public Angajat findOne() {
        return null;
    }


    @Override
    public Boolean verifyPassword(Angajat entity) {
        logger.traceEntry();
        Connection con=bdUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select AES_DECRYPT(Parola,'secret') as Parola from agentie.angajati where Nume=?;")){
            preparedStatement.setString(1,entity.getNume());
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            String parola=resultSet.getString("Parola");
            return entity.getParola().equals(parola);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error db "+e);
        }
        logger.traceExit();
        return false;
    }
}
