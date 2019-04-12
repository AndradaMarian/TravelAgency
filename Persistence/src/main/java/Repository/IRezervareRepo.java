package Repository;

import Utils.HasID;

public interface IRezervareRepo<ID, E extends HasID<ID>> extends ICrudRepository<ID, E> {

}
