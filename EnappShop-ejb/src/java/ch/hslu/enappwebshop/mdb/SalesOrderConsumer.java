/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hslu.enappwebshop.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author zimmbi
 */
@MessageDriven(mappedName = "jms/enappreplyqueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "adressList", propertyValue = "mq://10.29.2.50:7676/jms")
})
public class SalesOrderConsumer implements MessageListener {


    public void onMessage(Message message)
    {
        try {
            ObjectMessage msg = (ObjectMessage) message;
            System.out.println(msg.getObject().toString());
            System.out.println("Received Message Done");
        } catch (JMSException jmse) {
            System.out.println("Received Message Fail: " + jmse.getMessage());
            jmse.printStackTrace();
        }
    }
    
}