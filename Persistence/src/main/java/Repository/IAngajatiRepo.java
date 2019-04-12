package Repository;

import Utils.HasID;


public interface IAngajatiRepo<ID,E extends HasID<ID>> extends ICrudRepository<ID,E> {

    public Boolean verifyPassword(E entity);

}
