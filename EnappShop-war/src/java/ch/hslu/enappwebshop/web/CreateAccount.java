/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.d3s.enapp.common.Util;
import ch.hslu.enappwebshop.ejb.CustomerSessionLocal;
import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.CustomerGroup;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import javax.inject.Named;

/**
 *
 * @author zimmbi
 */
@Named
@SessionScoped
public class CreateAccount implements Serializable {

    @EJB
    private CustomerSessionLocal customerSession;
    private Customer customer;
    private String tempPw;
    @Inject
    private Login login;

    /** Creates a new instance of CreateAccount */
    public CreateAccount() {
    }

    public Customer getCustomer() {
        if (customer == null) {
            customer = new Customer();
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String create() {
        login.setUsername(customer.getUsername());
        login.setPassword(customer.getPassword());

        try {
            customer.setPassword(Util.createSHA1(customer.getPassword()));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CreateAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        customer = customerSession.saveCustomer(customer);

        CustomerGroup group = new CustomerGroup(customer.getUsername(), "USER");
        customerSession.addGroup(group);

        login.login();

        return "CREATED";
    }

    public void validatePw(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (component.getId().equals("password")) {
            tempPw = (String) value;
            return;
        }
        if (((String) value).equals(tempPw)) {
            return;
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwort not matching", "Passwort not matching"));
    }
}
