/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author csexton
 */
@Stateless(name="CategoryFacade")
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "ReadersParadisePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    public List<Category> findRandom(int limit) {
        return getEntityManager()
                .createNamedQuery("Category.findRandomByLimit")
                .setParameter(1, limit)
                .getResultList();
    }

    public Category findByName(String name) {
        try {
            return (Category) getEntityManager()
                    .createNamedQuery("Category.findByName")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NonUniqueResultException e) {
            // log e as multiple results exist in the database
            return (Category) getEntityManager()
                    .createNamedQuery("Category.findByName")
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            // log e as no results exist in the database
            return null;
        }
    }
}
