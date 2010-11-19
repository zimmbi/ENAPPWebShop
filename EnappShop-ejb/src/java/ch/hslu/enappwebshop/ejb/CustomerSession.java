package ch.hslu.enappwebshop.ejb;

import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zimmbi
 */
@Stateful
public class CustomerSession implements CustomerSessionLocal {

    @PersistenceContext(unitName = "EnappShop-ejbPU")
    private EntityManager em;

    @Override
    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public List<Customer> getCustomers() {
        Query q = em.createNamedQuery("Customer.findAll");
        return q.getResultList();
    }

    @Override
    public Customer getCustomer(Integer id) {
        Query q = em.createNamedQuery("Customer.findById", Customer.class);
        q.setParameter("id", id);
        return (Customer) q.getSingleResult();
    }

    @Override
    public List<Purchaseitem> getPurchaseItems(int purchaseId) {
        Query q = em.createNamedQuery("Purchaseitem.findByPurchaseid");
        q.setParameter("purchaseid", purchaseId);
        return q.getResultList();
    }

    @Override
    public List<Purchase> getPurchases(Customer customer) {
        Query q = em.createNamedQuery("Purchase.findByCustomerid");
        q.setParameter("customerid", customer.getId());
        return q.getResultList();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return em.merge(customer);
    }

    @Override
    public Customer verifyLogin(String username, String password) {
        Query q = em.createNamedQuery("Customer.findByUsernameAndPassword");
        q.setParameter("username", username);
        q.setParameter("password", password);
        List<Customer> list = q.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public float getTotal(int purchaseId) {
        Query q = em.createNativeQuery("SELECT sum(unitprice * quantity) as total FROM purchaseitem where purchaseid = " + purchaseId);
        BigDecimal count = (BigDecimal) q.getSingleResult();
        return new Float(count.floatValue());
    }
}
