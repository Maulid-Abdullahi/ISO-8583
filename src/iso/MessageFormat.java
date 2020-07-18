package iso;

import java.util.HashMap;

public class MessageFormat {
     HashMap<Integer, String> version = new HashMap<>();

     HashMap<Integer, String> messageClass = new HashMap<>();

     HashMap<Integer, String> messageFunction = new HashMap<>();

     HashMap<Integer, String> messageOrigin = new HashMap<>();

     MessageFormat () {
        // version
        version.put(0, "ISO 8583:1987");
        version.put(1, "ISO 8583:1993");
        version.put(2, "ISO 8583:2003");
        version.put(3, "Reserved by ISO");
        version.put(4, "Reserved by ISO");
        version.put(5, "Reserved by ISO");
        version.put(6, "Reserved by ISO");
        version.put(7, "Reserved by ISO");
        version.put(8, "National use");
        version.put(9, "Private use");

        // message class
        messageClass.put(0,"Reserved by ISO");
        messageClass.put(1,"Authorization message");
        messageClass.put(2,"Financial messages");
        messageClass.put(3,"File actions message");
        messageClass.put(4,"Reversal and chargeback messages");
        messageClass.put(5,"Reconciliation message");
        messageClass.put(6,"Administrative message");
        messageClass.put(7,"Fee collection messages");
        messageClass.put(8,"Network management message");
        messageClass.put(9,"Reserved by ISO");
        
        // message function
        messageFunction.put(0,"Request");
        messageFunction.put(1,"Request response");
        messageFunction.put(2,"Advice");
        messageFunction.put(3,"Advice response");
        messageFunction.put(4,"Notification");
        messageFunction.put(5,"Notification acknowledgement");
        messageFunction.put(6,"Instruction");
        messageFunction.put(7,"Instruction acknowledgement");
        messageFunction.put(8,"Reserved for ISO use");
        messageFunction.put(9,"Reserved for ISO use");

        // message origin
        messageOrigin.put(0,"Acquirer");
        messageOrigin.put(1,"Acquirer repeat");
        messageOrigin.put(2,"Issuer");
        messageOrigin.put(3,"Issuer repeat");
        messageOrigin.put(4,"Other");
        messageOrigin.put(5,"Other repeat");
        messageOrigin.put(6,"Reserved by ISO");
        messageOrigin.put(7,"Reserved by ISO");
        messageOrigin.put(8,"Reserved by ISO");
        messageOrigin.put(9,"Reserved by ISO");

    }
}
