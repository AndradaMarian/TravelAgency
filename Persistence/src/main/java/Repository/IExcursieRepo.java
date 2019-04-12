package Repository;



import Utils.HasID;

import java.time.LocalTime;

public interface IExcursieRepo<ID,E extends HasID<ID>> extends ICrudRepository<ID,E> {

    Iterable<E> findObiectivInterval(String obiectiv, LocalTime start, LocalTime plecare);

}
