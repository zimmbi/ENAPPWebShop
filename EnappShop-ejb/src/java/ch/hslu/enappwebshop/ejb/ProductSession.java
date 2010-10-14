/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.ejb;

import ch.hslu.enappwebshop.entities.Product;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zimmbi
 */
@Stateful
public class ProductSession implements ProductSessionLocal {

    @PersistenceContext(unitName = "EnappShop-ejbPU")
    private EntityManager em;
    private Purchase purchase;
    private Map<Product, Purchaseitem> cart = new HashMap<Product, Purchaseitem>();

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

    @Override
    public void addToCart(Product product) {
        if (!cart.containsKey(product)) {
            Purchaseitem item = new Purchaseitem();
            item.setProductid(product.getId());
            cart.put(product, item);
        }
    }

    @Override
    public Map<Product, Purchaseitem> getCart() {
        return cart;
    }

    @Override
    public Purchase getPurchase() {
        return purchase;
    }

    @Override
    public void checkout() {
        persist(purchase);
        for (Purchaseitem pi : cart.values()) {
            persist(pi);
        }
    }

    @Override
    public void removeFromCart(Product product) {
        cart.remove(product);
    }
}
