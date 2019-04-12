package Repository;

public interface ICrudRepository <ID,E>{

    public void save(E entity);
    public void delete(E entity);
    public Iterable<E> findAll();
    public E findOne();
}
