package tu.berkebile.ui;

import tu.berkebile.science.*;


public class UserInterface {
	
	/**
	 * The main method: serves to collect measurements and indexes needed to calculate fire danger indexes.
	 *
	 * @param args args
	 * @see Firedanger
	 * @see SpreadIndexVector
	 * @see HerbState
	 */
	public static void main(String[] args) {
		
		/** user data needed for fire danger determination */
		double dryBulbTemperature = 60.0;  //deg Fahrenheit
		double wetBulbTemperature = 40.;
		boolean snowOnTheGround = false;
		double precipitation = 0.2;  // inches
		double windSpeed = 20.;  // miles per hour
		HerbState herbState = HerbState.TRANSITION; // CURED, TRANSITION or GREEN
		double buildupIndexYesterday = 10.;
				
		
		Firedanger	today = new Firedanger();
		
		SpreadIndexVector index = today.getFireDangerIndexVector(dryBulbTemperature, wetBulbTemperature, snowOnTheGround, precipitation, 
				windSpeed, herbState, buildupIndexYesterday);
		
		/** print out fire danger indexes */
		index.printIndexes();

	}

}
