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
//        this.customer = customer;
//        return null;
    }

    public String login() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        String username = context.getExternalContext().getRequestParameterMap().get("login:username");
//        String password = context.getExternalContext().getRequestParameterMap().get("login:password");
        login.setCustomer(customerSession.verifyLogin(login.getUsername(), login.getPassword()));

        return null;
    }

//    public Customer getDetails() {
//        return customer;
//    }
    public String saveCustomer() {
//        customer = customerSession.saveCustomer(customer);
//        return null;
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
