package control;

public class SerialConnectionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SerialConnectionException(String str){
        super(str);
    }

    public SerialConnectionException(){
        super();
    }
}
