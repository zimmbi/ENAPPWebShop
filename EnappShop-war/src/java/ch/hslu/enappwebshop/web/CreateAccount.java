/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.enappwebshop.ejb.CustomerSessionLocal;
import ch.hslu.enappwebshop.entities.Customer;
import java.io.Serializable;
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
    @Inject
    private CustomerBean customerBean;
    private Customer customer;
    private String tempPw;

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
        customer = customerSession.saveCustomer(customer);
        customerBean.getLogin().setCustomer(customer);
        return "CREATED";
    }

    public void validatePw(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(component.getId().equals("password")) {
            tempPw = (String)value;
            return;
        }
        if (((String) value).equals(tempPw)) {
            return;
        }
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwort not matching", "Passwort not matching"));
    }
}
