/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.ejb;

import ch.hslu.enapp.salesorder.Item;
import ch.hslu.enapp.salesorder.ItemList;
import ch.hslu.enappwebshop.entities.Product;
import ch.hslu.enappwebshop.nav.DynNav;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zimmbi
 */
@Singleton
public class FetchProducts {

    @PersistenceContext(unitName = "EnappShop-ejbPU")
    private EntityManager em;
    private DynNav dynNav = new DynNav();

    public List<ItemList> getDynNavItems() {
        return (List<ItemList>) dynNav.getNavItems();
    }

    public ItemList getNavItems() {
        return dynNav.getNavItems();
    }

    @Schedule(minute = "*/1", hour = "*")
    public void fetch() {
        Product product;
        Query query;

        for (Item item : dynNav.getNavItems().getItem()) {
//            String s = item.getNo();
//            s = s.substring(4, item.getNo().length());
//            int dynNavId = Integer.parseInt(s);
            query = em.createNamedQuery("Product.findByReference");
            query.setParameter("reference", item.getNo());

            try {
                try {
                    product = (Product) query.getSingleResult();
                } catch (Exception ex) {
                    product = new Product();
                }
                product.setName(item.getDescription());
                product.setReference(item.getNo());
                product.setMediapath(item.getMediafileName());
                product.setDescription(item.getDescription());
                product.setUnitprice(item.getUnitPrice().longValue());
                em.merge(product);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
