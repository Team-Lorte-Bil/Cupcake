package domain.order;

public class NoOrderExists extends Exception{
    public NoOrderExists(){
        super("No orders exists yet.");
    }
}
