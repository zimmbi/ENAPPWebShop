/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.ejb;

import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author zimmbi
 */
@Local
public interface CustomerSessionLocal {

    public void persist(java.lang.Object object);

    public List<Customer> getCustomers();
    public Customer getCustomer(Integer id);
    public List<Purchaseitem> getPurchaseItems(Purchase purchase);
    public List<Purchase> getPurchases(Customer customer);
    
    
}
