/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.ejb;

import ch.hslu.d3s.enapp.common.SalesOrderJMS;
import ch.hslu.enappwebshop.entities.Customer;
import ch.hslu.enappwebshop.entities.Product;
import ch.hslu.enappwebshop.entities.Purchase;
import ch.hslu.enappwebshop.entities.Purchaseitem;
import ch.hslu.enappwebshop.mdb.SalesOrderMessage;
import ch.hslu.enappwebshop.payment.CreditCard;
import ch.hslu.enappwebshop.payment.NcResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zimmbi
 */
@Stateful
public class ProductSession implements ProductSessionLocal {

    @PersistenceContext(unitName = "EnappShop-ejbPU")
    private EntityManager em;
    private Purchase purchase = new Purchase();
    private Map<Product, Purchaseitem> cart = new HashMap<Product, Purchaseitem>();
    @Inject
    private PostFinanceBean postFinanceTestBean;
    @Inject
    private SalesOrderMessage salesOrderMessage;

    @Override
    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public List<Product> getProducts() {
        Query q = em.createNamedQuery("Product.findAll");
        return q.getResultList();
    }

    @Override
    public Product getProduct(Integer id) {
        Query q = em.createNamedQuery("Product.findById");
        q.setParameter("id", id);
        return (Product) q.getSingleResult();
    }

    @Override
    public Product mergeProduct(Product product) {
        return em.merge(product);
    }

    @Override
    public void addToCart(Product product) {
        if (!cart.containsKey(product)) {
            Purchaseitem item = new Purchaseitem();
            item.setProductid(product.getId());
            item.setDescription(product.getDescription());
            item.setQuantity(1L);
            item.setUnitprice(product.getUnitprice());
            cart.put(product, item);
        } else {
            increase(product);
        }
    }

    @Override
    public Map<Product, Purchaseitem> getCart() {
        return cart;
    }

    @Override
    public Purchase getPurchase() {
        return purchase;
    }

    @Override
    public void checkout(Customer customer) {

        long total = 0;
        SalesOrderJMS sojms = new SalesOrderJMS();

        purchase.setCustomerid(customer.getId());
        purchase.setDatetime(new Date());
        purchase.setStatus("coming soon");
        purchase = em.merge(purchase);
        em.flush();

        List<SalesOrderJMS.PurchaseItem> sojmsItems = new ArrayList<SalesOrderJMS.PurchaseItem>();

        for (Purchaseitem pi : cart.values()) {

            SalesOrderJMS.PurchaseItem item = sojms.new PurchaseItem(null, pi.getDescription(), pi.getQuantity().toString(), String.valueOf(pi.getUnitprice() * pi.getQuantity()));
            sojmsItems.add(item);

            total += pi.getUnitprice() * pi.getQuantity();
            pi.setPurchaseid(purchase.getId());
            em.merge(pi);
        }

        CreditCard c = new CreditCard();
        c.setCardNo("4111111111111111");
        c.setCustomerName("martin");
        c.setCvc("123");
        c.setExpiryDate("12/12");


        System.out.println("ID" + purchase.getId());
        System.out.println("TOTAL" + total);
        System.out.println("CC" + c);
        NcResponse response = postFinanceTestBean.makePayment(purchase.getId(), total, c);


        SalesOrderJMS.PurchaseCustomer purchaseCustomer = sojms.new PurchaseCustomer(null, customer.getName(), customer.getAddress(), "1234", "Luzern", customer.getId().toString(), customer.getUsername());

        sojms.setPayId(response.getPayId());
        sojms.setPurchaseCustomer(purchaseCustomer);
        sojms.setPurchaseDate(purchase.getDatetime());
        sojms.setPurchaseId(purchase.getId().toString());
        sojms.setPurchaseItemList(sojmsItems);
        sojms.setStudent("tezimmer");
        sojms.setTotalPrice(String.valueOf(total));


        salesOrderMessage.salesOrderMessageSender(sojms);


        purchase = new Purchase();
        cart.clear();
    }

    @Override
    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    @Override
    public void increase(Product product) {
        Purchaseitem p = cart.get(product);
        p.setQuantity(p.getQuantity() + 1L);
    }

    @Override
    public void decrease(Product product) {
        Purchaseitem p = cart.get(product);
        p.setQuantity(p.getQuantity() - 1L);
        if (p.getQuantity() < 1) {
            cart.remove(product);
        }
    }
}
