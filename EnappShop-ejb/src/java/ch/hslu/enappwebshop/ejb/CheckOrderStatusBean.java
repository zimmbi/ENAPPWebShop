/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.ejb;

import ch.hslu.d3s.enapp.common.SalesOrderRestful;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author zimmbi
 */
@Stateless
@LocalBean
public class CheckOrderStatusBean {

    public String getStatus(String correlationId) {

        System.out.println("In RESTful");
        SalesOrderRestful rb = null;

        Client client = Client.create();

        WebResource resource = client.resource("http://enappsrv01.icompany.intern:8080/DynNAVdaemon-war/resources/salesorder/" + correlationId + "/status");
        ClientResponse response = resource.type("application/x-www-form-urlencoded ").get(ClientResponse.class);

        if(200 != response.getStatus()) {
            System.out.println("Error get Status via REST! VIA REST!!! VIA REST!!!");
            return null;
        }

        try {
            JAXBContext context = JAXBContext.newInstance(SalesOrderRestful.class);
            Unmarshaller u = context.createUnmarshaller();
            rb = (SalesOrderRestful) u.unmarshal(response.getEntityInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Response " + response);
        System.out.println(rb.getCorrelationid());
        System.out.println(rb.getStatus());
        return rb.getStatus();
    }
}
