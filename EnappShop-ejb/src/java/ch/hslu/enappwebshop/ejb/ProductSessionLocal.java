/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.ejb;

import ch.hslu.enappwebshop.entities.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author zimmbi
 */
@Local
public interface ProductSessionLocal {

    public List<Product> getProducts();
    
    public Product getProduct(Integer id);

    public void persist(java.lang.Object object);

    public Product mergeProduct(Product product);
    
}
