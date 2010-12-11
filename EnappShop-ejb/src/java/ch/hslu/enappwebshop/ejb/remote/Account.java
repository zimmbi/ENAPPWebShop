/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.ejb.remote;

import ch.hslu.enappwebshop.entities.Customer;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zimmbi
 */
@RolesAllowed("ADMIN")
@Stateless
public class Account implements AccountRemote {
    
    @PersistenceContext(unitName = "EnappShop-ejbPU")
    private EntityManager em;

    @Override
    public List<Customer> list() {
        Query q = em.createNamedQuery("Customer.findAll");
        return q.getResultList();
    }

    @Override
    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    
 
}
