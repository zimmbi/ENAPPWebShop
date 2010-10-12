/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.ejb;

import ch.hslu.enappwebshop.entities.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zimmbi
 */
@Stateless
public class ProductSession implements ProductSessionLocal {

    @PersistenceContext(unitName = "EnappShop-ejbPU")
    private EntityManager em;

    @Override
    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public List<Product> getProducts() {
        Query q = em.createNamedQuery("Product.findAll");
        return q.getResultList();
    }

    
    @Override
    public Product getProduct(Integer id) {
        Query q = em.createNamedQuery("Product.findById");
        q.setParameter("id", id);
        return (Product) q.getSingleResult();
    }

    @Override
    public Product mergeProduct(Product product) {
        return em.merge(product);
    }

}
