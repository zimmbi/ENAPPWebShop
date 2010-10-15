/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.web;

import ch.hslu.enappwebshop.entities.Customer;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author zimmbi
 */
@Named
@SessionScoped
public class CreateAccount implements Serializable {

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

    public void create() {
        System.out.println("create!!!");
    }



}
