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
import javax.faces.validator.Validator;
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
    private Customer customer = new Customer();

    /** Creates a new instance of CreateAccount */
    public CreateAccount() {
    }

    public Customer getCustomer() {
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
    private Validator myValidator = new Validator() {

        @Override
        public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
            System.out.println(customer.getPassword());
            if (((String) value).equals(customer.getPassword())) {
                return;
            }
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwort not matching", "Passwort not matching"));
        }
    };

    public Validator getMyValidator() {
        return myValidator;
    }
}
