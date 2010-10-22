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
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

/**
 *
 * @author zimmbi
 */
@Named("customer")
@SessionScoped
public class CustomerBean implements Serializable {

    @EJB
    private CustomerSessionLocal customerSession;
    private Login login = new Login();
    private String test;
    private int purchaseId;
    private String tempPw;

    public String getTempPw() {
        return tempPw;
    }

    public void setTempPw(String tempPw) {
        this.tempPw = tempPw;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

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
        return "Login?faces-redirect=true";
    }

    public String saveCustomer() {
        if (tempPw.length()>0) {
            login.getCustomer().setPassword(tempPw);
        }
        login.setCustomer(customerSession.saveCustomer(login.getCustomer()));
        return "Login?faces-redirect=true";
    }

    public List<Customer> getCustomers() {
        return customerSession.getCustomers();
    }

    public List<Purchaseitem> getPurchaseItems() {
        return customerSession.getPurchaseItems(purchaseId);
    }

    public List<Purchase> getPurchases() {
        return customerSession.getPurchases(login.getCustomer());
    }

    public boolean isLoggedIn() {
        return login.getCustomer() == null ? false : true;
    }

    public String logout() {
        login.setCustomer(null);
        return "Login?faces-redirect=true";
    }

    public Customer getLoggedInCustomer() {
        return login.getCustomer();
    }

}
