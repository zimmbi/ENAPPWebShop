/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.enappwebshop.ejb.CustomerSessionLocal;
import ch.hslu.enappwebshop.ejb.PostFinanceTestBean;
import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import ch.hslu.enappwebshop.payment.CreditCard;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
    @EJB
    private PostFinanceTestBean postFinanceTestBean;
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
        if (login.getCustomer() == null) {
            FacesContext.getCurrentInstance().addMessage("login:username", new FacesMessage(FacesMessage.SEVERITY_INFO, "Wrong username/password", "Wrong username/password"));
            return null;
        }
        return "Login?faces-redirect=true";
    }

    public String saveCustomer() {
        if (tempPw.length() > 0) {
            login.getCustomer().setPassword(tempPw);
            FacesContext.getCurrentInstance().addMessage("edit:password", new FacesMessage(FacesMessage.SEVERITY_INFO, "Password Changed", "Password Changed"));
        }
        login.setCustomer(customerSession.saveCustomer(login.getCustomer()));
//        return "Login?faces-redirect=true";
        return null;
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

    public float fetchTotal(int id) {
        return customerSession.getTotal(id);
    }

    public String testPayment() {
        CreditCard c = new CreditCard();
        c.setCardNo("234234234");
        c.setCustomerName("martin");
        c.setCvc("123");
        c.setExpiryDate("22-02-2012");
        postFinanceTestBean.makePayment(313371234, 20, c);
        return "Test?faces-redirect=true";
    }

}
