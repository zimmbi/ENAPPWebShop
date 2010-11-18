/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.enappwebshop.ejb;

import ch.hslu.d3s.enapp.common.Util;
import ch.hslu.enappwebshop.payment.CreditCard;
import ch.hslu.enappwebshop.payment.NcResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author zimmbi
 */
@Stateless
public class PostFinanceBean {

    public NcResponse makePayment(Integer id, long totalPrice, CreditCard creditCard) {

        String orderID = Integer.toString(id);
        String amount = Long.toString(totalPrice * 100);
        String currency = "CHF";

        String stringToHash = orderID + amount + currency + creditCard.getCardNo() + Util.PSPID + Util.SHA1PWDIN;
        try {
            String encoded = Util.createSHA1(stringToHash);
            MultivaluedMap formData = new MultivaluedMapImpl();
            formData.add("PSPID", Util.PSPID);
            formData.add("USERID", Util.USERID);
            formData.add("PSWD", Util.PSWD);
            formData.add("OrderId", orderID);
            formData.add("amount", amount);
            formData.add("currency", currency);
            formData.add("CARDNO", creditCard.getCardNo());
            formData.add("CVC", creditCard.getCvc());
            formData.add("ED", creditCard.getExpiryDate());
            formData.add("CN", creditCard.getCustomerName());
//            formData.add("operation", "SAL");
            formData.add("SHASign", encoded);

            Client client = Client.create();

            WebResource webResource = client.resource("https://e-payment.postfinance.ch/ncol/test/orderdirect.asp");
            ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, formData);

            JAXBContext jc = JAXBContext.newInstance(ch.hslu.enappwebshop.payment.NcResponse.class);
            Unmarshaller u = jc.createUnmarshaller();
            NcResponse ncResponse = (NcResponse) u.unmarshal(response.getEntityInputStream());
            
            System.out.println(ncResponse);

            return ncResponse;


        } catch (JAXBException ex) {
            Logger.getLogger(PostFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PostFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PostFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;



    }
}
