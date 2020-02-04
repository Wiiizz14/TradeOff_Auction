/**
 * 
 */
package fr.eni.stagiaire.projet.dal;

/**
 * @author yledouge2018
 *
 */
public class DALException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DALException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DALException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public DALException(String arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("couche dal - ");
		sb.append(super.getMessage());
		return sb.toString();
	}
	
}
