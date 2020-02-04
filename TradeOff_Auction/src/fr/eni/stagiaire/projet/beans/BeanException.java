package fr.eni.stagiaire.projet.beans;
/**
 * 
 * @author dbondigu2018
 *
 */
public class BeanException extends Exception {

	
	private static final long serialVersionUID = 1L;

	
	public BeanException() {
		super();
	}
		
	/**
	 * @param arg0
	 * @param arg1
	 */
	public BeanException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BeanException(String arg0) {
		super(arg0);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("couche bean - ");
		sb.append(super.getMessage());
		return sb.toString();
	}
	
	
}

