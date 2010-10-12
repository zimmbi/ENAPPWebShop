/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.web;

import ch.hslu.enappwebshop.ejb.CustomerSessionLocal;
import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author zimmbi
 */
@ManagedBean(name="customer")
@SessionScoped
public class CustomerBean implements Serializable {
    @EJB
    private CustomerSessionLocal customerSession;

    /** Creates a new instance of CustomerBean */
    public CustomerBean() {
    }


    public List<Customer> getCustomers() {
        return customerSession.getCustomers();
    }

    public List<Purchaseitem> getPurchaseItems(Purchase purchase) {
        return customerSession.getPurchaseItems(purchase);
    }

    public List<Purchase> getPurchases(Customer customer) {
        return customerSession.getPurchases(customer);
    } 

    

}
