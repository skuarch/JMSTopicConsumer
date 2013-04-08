package util;

import javax.jms.JMSException;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

/**
 * Utilities for JMS
 *
 * @author skuarch
 */
public class JMSUtilities {

    //==========================================================================
    /**
     * this is a utilitie class and doesn't need a constructor
     */
    private JMSUtilities() {
    } // end JMSUtilities

    //==========================================================================
    /**
     * safely close topic session
     *
     * @param topicSession TopicSession
     */
    public static void closeTopicSession(TopicSession topicSession) {

        try {
            topicSession.close();
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }

    } // end closeTopicSession

    //==========================================================================
    /**
     * safely close topic subcriber.
     *
     * @param topicSubscriber
     */
    public static void closeTopicSubcriber(TopicSubscriber topicSubscriber) {

        try {
            topicSubscriber.close();
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }

    } // end closeTopicSession
} // end class
