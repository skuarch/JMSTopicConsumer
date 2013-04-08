package net.jms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.PropertieUtilities;

/**
 * this class make a topic connection. if you create a instance of this class
 * using only the name of topic connection factory and the name of topic you
 * have to used the method setUpPropertiesFile(). If you create a instance of
 * this class using the other constructor you must use setUpDefaultProperties().
 *
 *
 * @author skuarch
 */
public class JMSTopicConnection {

    private String jmsServer = null;
    private String jmsPort = null;
    private String jmsTopicConnectionFactoryName = null;
    private String jndiTopic = null;
    private InitialContext initialContext;
    private Properties properties;
    private TopicConnectionFactory topicConnectionFactory;
    private TopicConnection topicConnection;
    private TopicSession topicSession = null;
    private Topic topic;
    private TopicSubscriber topicSubscriber = null;
    private MessageProducer messageProducer = null;

    //==========================================================================
    /**
     * create a instance.
     *
     * @param jmsServer String ip address, hostname or domain.
     * @param jmsPort String number of port
     * @param jmsTopicConnectionFactoryName String name of
     * TopicConnectionFactory
     * @param jndiTopic String topic in glassfish
     */
    public JMSTopicConnection(String jmsServer, String jmsPort, String jmsTopicConnectionFactoryName, String jndiTopic) {

        this.jmsServer = jmsServer;
        this.jmsPort = jmsPort;
        this.jmsTopicConnectionFactoryName = jmsTopicConnectionFactoryName;
        this.jndiTopic = jndiTopic;

    } // end JMSTopicConnection

    //==========================================================================
    /**
     * create a instance.
     */
    public JMSTopicConnection(String jmsTopicConnectionFactoryName, String jndiTopic) {

        this.jmsTopicConnectionFactoryName = jmsTopicConnectionFactoryName;
        this.jndiTopic = jndiTopic;

    } // end JMSTopicConnection

    //==========================================================================
    /**
     * set up properties using a file (jndi.properties).
     *
     * @param file String path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void setUpPropertiesFile(String file) throws FileNotFoundException, IOException {
        
        if (file == null || file.length() < 1) {
            throw new NullPointerException("file is null or empty");
        }

        properties = PropertieUtilities.getProperties(file);                

    } // end setUpPropertiesFile

    //==========================================================================
    /**
     * set up default properties in order to used JMS.
     */
    public void setUpDefaultProperties() {

        properties = new Properties();
        properties.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        properties.put("java.naming.provider.url", "iiop://" + jmsServer + ":" + jmsPort);

    } // end setUpDefaultProperties

    //==========================================================================
    /**
     * create a initial context.
     */
    public void createInitialContext() throws NamingException, FileNotFoundException, IOException {

        if (properties == null) {
            throw new NullPointerException("properties is null");
        }

        initialContext = new InitialContext(properties);

    } // end createInitialContext

    //==========================================================================
    /**
     * create topic connection factory.
     */
    public void createTopicConnectionFactory() throws NamingException {

        if (initialContext == null) {
            throw new NullPointerException("initialContext is null");
        }

        if (jmsTopicConnectionFactoryName == null) {
            throw new NullPointerException("jmsTopicConnectionFactoryName is null");
        }

        topicConnectionFactory = (TopicConnectionFactory) initialContext.lookup(jmsTopicConnectionFactoryName);

    } // end createTopicConnection

    //==========================================================================
    /**
     * create a topic connection and start it.
     */
    public void createTopicConnection() throws JMSException {

        if (topicConnectionFactory == null) {
            throw new NullPointerException("topicConnectionFactory is null");
        } 
 
        topicConnection = topicConnectionFactory.createTopicConnection();
        topicConnection.start();

    } // end createTopicConnection

    //==========================================================================
    /**
     * create a topic
     *
     * @throws NamingException
     */
    public void createTopic() throws NamingException {

        if (initialContext == null) {
            throw new NullPointerException("initialContext is null");
        }

        if (jndiTopic == null) {
            throw new NullPointerException("jndiTopic is null");
        }

        topic = (Topic) initialContext.lookup(jndiTopic);

    } // end createTopic

    //==========================================================================
    /**
     * create session
     */
    public void createSession() throws JMSException {

        if (topicConnection == null) {
            throw new NullPointerException("topicConnection is null");
        }

        topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);

    } // end createSession

    //==========================================================================
    /**
     * create subscriber
     */
    public void createSubscriber() throws JMSException {

        if (topicSession == null) {
            throw new NullPointerException("topicSession is null");
        }

        if (topic == null) {
            throw new NullPointerException("topic is null");
        }

        topicSubscriber = topicSession.createSubscriber(topic);

    } // end createSubscriber

    //==========================================================================
    /**
     * create a message producer
     *
     * @throws JMSException
     */
    public void createMessageProducer() throws JMSException {

        if (topic == null) {
            throw new NullPointerException("topic is null");
        }

        messageProducer = topicSession.createProducer(topic);

    } // end createMessageProducer

    //==========================================================================
    /**
     * close initial context
     *
     * @throws NamingException
     */
    public void closeInitialContext() throws NamingException {

        if (initialContext != null) {
            initialContext.close();
        } else {
            throw new NullPointerException("initialContext is null");
        }

    } // end closeInitialContext

    //==========================================================================
    /**
     * close topic connection.
     *
     * @throws JMSException
     */
    public void closeTopicConnection() throws JMSException {

        if (topicConnection != null) {
            topicConnection.close();
        } else {
            throw new NullPointerException("topicConnection is null");
        }

    } // end closeTopicConnection

    //==========================================================================
    /**
     * close topic session
     *
     * @throws JMSException
     */
    public void closeTopicSession() throws JMSException {

        if (topicSession != null) {
            topicSession.close();
        } else {
            throw new NullPointerException("topicSession is null");
        }

    } // end closeTopicSession

    //==========================================================================
    /**
     * close topic subscriber.
     *
     * @throws JMSException
     */
    public void closeTopicSubscriber() throws JMSException {

        if (topicSubscriber != null) {
            topicSubscriber.close();
        } else {
            throw new NullPointerException("topicSubscriber is null");
        }

    } // end closeTopicSubscriber

    //==========================================================================
    /**
     * close message producer
     *
     * @throws JMSException
     */
    public void closeMessageProducer() throws JMSException {

        if (messageProducer != null) {
            messageProducer.close();
        } else {
            throw new NullPointerException("messageProducer is null");
        }

    } // end closeTopicMessageProducer

    //==========================================================================
    /**
     * get InitialContext
     *
     * @return InitialContext object
     */
    public InitialContext getInitialContext() {
        return initialContext;
    }

    //==========================================================================
    /**
     * set InitialContext
     *
     * @param initialContext InitialContext
     */
    public void setInitialContext(InitialContext initialContext) {
        this.initialContext = initialContext;
    }

    //==========================================================================
    /**
     * get properties
     *
     * @return Properties object
     */
    public Properties getProperties() {
        return properties;
    }

    //==========================================================================
    /**
     * set properties
     *
     * @param properties Properties
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    //==========================================================================
    /**
     * get topic connection factory
     *
     * @return TopicConnectionFactory object
     */
    public TopicConnectionFactory getTopicConnectionFactory() {
        return topicConnectionFactory;
    }

    //==========================================================================
    /**
     * set topic connection factory
     *
     * @param topicConnectionFactory TopicConnectionFactory
     */
    public void setTopicConnectionFactory(TopicConnectionFactory topicConnectionFactory) {
        this.topicConnectionFactory = topicConnectionFactory;
    }

    //==========================================================================
    /**
     * get topic connection
     *
     * @return TopicConnection object
     */
    public TopicConnection getTopicConnection() {
        return topicConnection;
    }

    //==========================================================================
    /**
     * set topic connection
     *
     * @param topicConnection TopicConnection
     */
    public void setTopicConnection(TopicConnection topicConnection) {
        this.topicConnection = topicConnection;
    }

    //==========================================================================
    /**
     * get topic session
     *
     * @return TopicSession object
     */
    public TopicSession getTopicSession() {
        return topicSession;
    }

    //==========================================================================
    /**
     * set topic session
     *
     * @param topicSession TopicSession
     */
    public void setTopicSession(TopicSession topicSession) {
        this.topicSession = topicSession;
    }

    //==========================================================================
    /**
     * get topic
     *
     * @return Topic object
     */
    public Topic getTopic() {
        return topic;
    }

    //==========================================================================
    /**
     * set topic
     *
     * @param topic Topic
     */
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    //==========================================================================
    /**
     * get topic subscriber
     *
     * @return
     */
    public TopicSubscriber getTopicSubscriber() {
        return topicSubscriber;
    }

    //==========================================================================
    /**
     * set topic subcriber
     *
     * @param topicSubscriber
     */
    public void setTopicSubscriber(TopicSubscriber topicSubscriber) {
        this.topicSubscriber = topicSubscriber;
    }

    //==========================================================================
    /**
     * get Message producer
     *
     * @return
     */
    public MessageProducer getMessageProducer() {
        return messageProducer;
    }

    //==========================================================================
    /**
     * set Message producer
     *
     * @param messageProducer
     */
    public void setMessageProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    //==========================================================================
    /**
     * get jmsServer
     *
     * @return String
     */
    public String getJmsServer() {
        return jmsServer;
    }

    //==========================================================================
    /**
     * set jmsServer
     *
     * @param jmsServer String
     */
    public void setJmsServer(String jmsServer) {
        this.jmsServer = jmsServer;
    }

    //==========================================================================
    /**
     * get jmsPort
     *
     * @return String
     */
    public String getJmsPort() {
        return jmsPort;
    }

    //==========================================================================
    /**
     * set jmsPort
     *
     * @param jmsPort String
     */
    public void setJmsPort(String jmsPort) {
        this.jmsPort = jmsPort;
    }

    //==========================================================================
    /**
     * get jmsTopicConnectionFactory
     *
     * @return String
     */
    public String getJmsTopicConnectionFactoryName() {
        return jmsTopicConnectionFactoryName;
    }

    //==========================================================================
    /**
     * set jmsTopicConnectionFactoryName
     *
     * @param jmsTopicConnectionFactoryName String
     */
    public void setJmsTopicConnectionFactoryName(String jmsTopicConnectionFactoryName) {
        this.jmsTopicConnectionFactoryName = jmsTopicConnectionFactoryName;
    }

    //==========================================================================
    /**
     * get jndiTopic
     *
     * @return String
     */
    public String getJndiTopic() {
        return jndiTopic;
    }

    //==========================================================================
    /**
     * set jndiTopic
     *
     * @param jndiTopic
     */
    public void setJndiTopic(String jndiTopic) {
        this.jndiTopic = jndiTopic;
    }
} // end class
