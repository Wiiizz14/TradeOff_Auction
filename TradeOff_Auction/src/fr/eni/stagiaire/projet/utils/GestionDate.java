package fr.eni.stagiaire.projet.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GestionDate {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// Conversion de la date (Java) en date (Sql)
	public static Date StringToDate (String dateSaisie) throws ParseException {
		
		java.util.Date maDate = sdf.parse(dateSaisie);
		return maDate;
	}
	
	// Conversion de la date (Sql) en date (Java)
	public static String DateToString (Date dateSaisie) {
		
		String maDate = sdf.format(dateSaisie);
		return maDate;
	}
}
