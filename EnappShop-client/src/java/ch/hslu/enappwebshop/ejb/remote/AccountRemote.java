/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.ejb.remote;

import ch.hslu.enappwebshop.entities.Customer;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author zimmbi
 */
@Remote
public interface AccountRemote {

    public List<Customer> list();
    public Customer update(Customer customer);
    
}
