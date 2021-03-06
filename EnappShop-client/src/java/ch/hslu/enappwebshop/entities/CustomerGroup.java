/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author zimmbi
 */
@Entity
@Table(name = "customer_group")
@NamedQueries({
    @NamedQuery(name = "CustomerGroup.findAll", query = "SELECT c FROM CustomerGroup c"),
    @NamedQuery(name = "CustomerGroup.findByUsername", query = "SELECT c FROM CustomerGroup c WHERE c.customerGroupPK.username = :username"),
    @NamedQuery(name = "CustomerGroup.findByGroupname", query = "SELECT c FROM CustomerGroup c WHERE c.customerGroupPK.groupname = :groupname")})
public class CustomerGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CustomerGroupPK customerGroupPK;

    public CustomerGroup() {
    }

    public CustomerGroup(CustomerGroupPK customerGroupPK) {
        this.customerGroupPK = customerGroupPK;
    }

    public CustomerGroup(String username, String groupname) {
        this.customerGroupPK = new CustomerGroupPK(username, groupname);
    }

    public CustomerGroupPK getCustomerGroupPK() {
        return customerGroupPK;
    }

    public void setCustomerGroupPK(CustomerGroupPK customerGroupPK) {
        this.customerGroupPK = customerGroupPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerGroupPK != null ? customerGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerGroup)) {
            return false;
        }
        CustomerGroup other = (CustomerGroup) object;
        if ((this.customerGroupPK == null && other.customerGroupPK != null) || (this.customerGroupPK != null && !this.customerGroupPK.equals(other.customerGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.hslu.enappwebshop.entities.CustomerGroup[customerGroupPK=" + customerGroupPK + "]";
    }

}
