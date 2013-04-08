package wrappers;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TopicSubscriber;
import javax.naming.NamingException;
import net.jms.JMSTopicConnection;

/**
 *
 * @author skuarch
 */
public class JMSListener extends JMSTopicConnection {

    private TopicSubscriber topicSubscriber = null;
    private Message message = null;

    //==========================================================================
    /**
     * create a instance
     */
    public JMSListener() {
        super("jms_topic_connection_factory", "jndi_jms_topic");
    } // end JMSListener

    //==========================================================================
    public void listenMessages() throws JMSException, FileNotFoundException, IOException, NamingException {

        try {
            
            setUpPropertiesFile("configuration/jndi.properties");
            createInitialContext();
            createTopicConnectionFactory();
            createTopicConnection();
            createTopic();
            createSession();
            createSubscriber();

            topicSubscriber = getTopicSubscriber();

            System.out.println("ready to receive messages");
            while (true) {

                message = topicSubscriber.receive();

                if (message != null) {
                    System.out.println(message);                    
                }

            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (NamingException ne) {
            throw ne;
        } catch (JMSException jmse) {
            throw jmse;
        } finally {
            closeInitialContext();
            closeTopicConnection();
            closeTopicSession();
            closeTopicSubscriber();
        }

    }
} // end class
