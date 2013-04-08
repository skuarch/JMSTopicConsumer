package application;

import wrappers.JMSListener;

/**
 *
 * @author skuarch
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().startProgram();
    } // end main
    
    //==========================================================================
    private void startProgram(){
    
        try {            
            new JMSListener().listenMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } // end startProgram
    
} // end class
