package no.westerdals.lobfre13.lms.dao.location;

import no.westerdals.lobfre13.lms.dao.event.JPALocation;
import no.westerdals.lobfre13.lms.dao.interceptors.DAOInterceptor;
import no.westerdals.lobfre13.lms.dto.Location;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 23.11.2015.
 */
@JPALocation
@Stateless
@Interceptors(DAOInterceptor.class)
public class JPALocationDao implements LocationDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public JPALocationDao() {
    }

    public JPALocationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Location persist(Location location) {
        entityManager.persist(location);
        return location;
    }

    @Override
    public Location getLocation(int id) {
        return entityManager.find(Location.class, id);
    }

    @Override
    public List<Location> getAll() {
        return entityManager.createNamedQuery("Location.getAll", Location.class).getResultList();
    }

    @Override
    public Location update(Location location) {
        if(!entityManager.contains(location))
            location = entityManager.merge(location);
        return location;
    }

    @Override
    public boolean delete(Location location) {
        location = update(location);
        entityManager.remove(location);
        return true;
    }

    public void close(){
        entityManager.close();
    }
}
