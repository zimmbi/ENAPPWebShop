/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.d3s.enapp.common.Util;
import ch.hslu.enappwebshop.ejb.CustomerSessionLocal;
import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
    @Inject
    private Login login;
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

//    public String getTest() {
//        return test;
//    }
//
//    public void setTest(String test) {
//        this.test = test;
//    }
//    public Login getLogin() {
//        return login;
//    }
//    /** Creates a new instance of CustomerBean */
//    public CustomerBean() {
//    }
//
//    public void select(Customer customer) {
//    }
//    public String login() {
//        login.setCustomer(customerSession.verifyLogin(login.getUsername(), login.getPassword()));
//        if (login.getCustomer() == null) {
//            FacesContext.getCurrentInstance().addMessage("login:username", new FacesMessage(FacesMessage.SEVERITY_INFO, "Wrong username/password", "Wrong username/password"));
//            return null;
//        }
//        return "Login?faces-redirect=true";
//    }
//
    public String saveCustomer() {
        if (tempPw.length() > 0) {
            try {
                login.getCustomer().setPassword(Util.createSHA1(tempPw));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
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
//
//    public boolean isLoggedIn() {
//        return true;
////        return login.getCustomer() == null ? false : true;
//    }
//
//    public String logout() {
//        login.setCustomer(null);
//        return "Login?faces-redirect=true";
//    }
//

    public Customer getLoggedInCustomer() {
        return login.getCustomer();
    }

    public float fetchTotal(int id) {
        return customerSession.getTotal(id);
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
