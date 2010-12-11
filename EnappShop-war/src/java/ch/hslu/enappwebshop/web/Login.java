/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.d3s.enapp.common.Util;
import ch.hslu.enappwebshop.ejb.CustomerSessionLocal;
import ch.hslu.enappwebshop.entities.Customer;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author zimmbi
 */
@Named("loginBean")
@SessionScoped
public class Login implements Serializable {

    private String username;
    private String password;
    private Customer customer;
    @EJB
    private CustomerSessionLocal customerSession;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(username, password);
            System.out.println("logged in " + username);
            try {
                customer = customerSession.verifyLogin(username, Util.createSHA1(password));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.addMessage(null, new FacesMessage("Login successful!"));

            return "ProductList?faces-redirect=true";
        } catch (ServletException e) {
            try {
                request.logout();
            } catch (ServletException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Handle unknown username/password in request.login().
            System.out.println("EXCEPTION: " + e.getMessage());
            context.addMessage(null, new FacesMessage("Unknown login"));
        }
        return null;
    }

    public boolean isLoggedIn() {
        return customer == null ? false : true;
    }

    public String logout() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.logout();
            customer = null;
        } catch (ServletException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Main?faces-redirect=true";
    }
}
