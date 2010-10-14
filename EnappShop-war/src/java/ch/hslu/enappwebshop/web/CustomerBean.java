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
@ManagedBean(name = "customer")
@SessionScoped
public class CustomerBean implements Serializable {

    @EJB
    private CustomerSessionLocal customerSession;
    private Login login = new Login();

    public Login getLogin() {
        return login;
    }


    /** Creates a new instance of CustomerBean */
    public CustomerBean() {
    }

    public void select(Customer customer) {
    }

    public String login() {
        login.setCustomer(customerSession.verifyLogin(login.getUsername(), login.getPassword()));
        return null;
    }

    public String saveCustomer() {
        return "TEST";
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
