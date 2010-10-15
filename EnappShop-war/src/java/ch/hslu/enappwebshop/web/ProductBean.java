/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.enappwebshop.ejb.ProductSessionLocal;
import ch.hslu.enappwebshop.entities.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author zimmbi
 */
@ManagedBean(name = "product")
@SessionScoped
public class ProductBean implements Serializable {

    @EJB
    private ProductSessionLocal productSession;
    private Product product;

//    @ManagedProperty(name="customer", value="#{customer}")
//    private CustomerBean customer;

    /** Creates a new instance of ProductBean */
    public ProductBean() {
    }

    public List<Product> getProducts() {
        return productSession.getProducts();
    }

    public Product getProduct() {
        return null;
    }

    public Product getNewProduct() {
        Product p = new Product();
        this.product = p;
        return p;
    }

    public String saveProduct() {
        productSession.mergeProduct(product);
        return "SAVED";
    }

    public String select(Product p) {
        product = p;
        return "DETAILS";
    }

    public Product getDetails() {
        return product;
    }

    public List<Product> getCartProducts() {
        return new ArrayList(productSession.getCart().keySet());
    }

    public Long showQuantity(Product product) {
        return productSession.getCart().get(product).getQuantity();
    }

    public void addToCart(Product product) {
        productSession.addToCart(product);
    }

    public void removeFromCart(Product product) {
        productSession.removeFromCart(product);
    }

    public void checkout() {
        productSession.checkout();
    }


    
}
