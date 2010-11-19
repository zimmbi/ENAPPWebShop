/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.web;

import ch.hslu.enappwebshop.ejb.CheckOrderStatusBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author zimmbi
 */
@Named(value = "checkOrderStatus")
@RequestScoped
public class CheckOrderStatusMBean {

    @EJB
    private CheckOrderStatusBean checkOrderStatusBean;

    public String theStatus(String correlationId) {
        if (correlationId == null) {
            return "unknown";
        }

        String status = checkOrderStatusBean.getStatus(correlationId);

        if (status == null) {
            return "unknown";
        }

        int postfinance = Integer.valueOf(status.substring(0, 1));
        int orderstatus = Integer.valueOf(status.substring(2, 3));

        switch (postfinance) {
            case 0:
                switch (orderstatus) {
                    case 0:
                        return "Open";
                    case 1:
                        return "Released";
                    case 2:
                        return "Shipped";
                    case 3:
                        return "Invoiced";
                    case 4:
                        return "finished";
                    case 9:
                        return "unknown";
                }
                break;
            case 1:
                return "payment rejected";
            case 2:
                return "payment hold";
            case 9:
                return "unknown";
        }
        return "n/a";
    }
}
