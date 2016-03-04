package tu.berkebile.science;

import tu.berkebile.ui.Condition;

/**
 * Includes methods to determine seven indexes used in the past by the Forest Service (US Department of Agriculture) 
 * to characterize the danger of forest fire spread:  
 * <ul>
 *     <li>Fine fuel moisture: represents the moisture content of litter (forest material that can burn) measured in %.</li>
 *    
 *     <li>Adjusted fuel moisture: Fine fuel moisture adjusted for precipitation in the last day.</li>
 *     
 *     <li>Drying factor: ranges between 1 an 7 (inversely proportional to ffm)</li>
 *     
 *     <li>Buildup index: reflecting the combined cumulative effects of daily drying 
 *                        and precipitation in fuels (such as dead vegetation) with a 10 day time lag constant</li>
 *     
 *     <li>Fine fuel spread: fire spread danger for grass fuel environments (measured in %) </li>
 *     
 *     <li>Timber spread index: predicts the relative forward spread of fire burning in a comparatively closed
 *                              environment under a timber canopy (measured in %).</li>
 *     
 *     <li>Fire load index: rating of the maximum effort required to contain all probable fires 
 *                          (most fires occur when <code>fli ~= 70</code>).</li>
 * </ul>
 *  
 * <p>The only interface is the <code>getFireDangerIndexVector</code> method.</p>
 */
public class Firedanger {
    
	/** constants and limits needed for index calculation */
	private static final double[][] constantsFineFuelMoisture = {{30.0, 19.2, 13.8, 22.5},{-1.859, -0.859, -0.05966, -.77373}};
	/** lookup table for temp differences of dry and wet bulbs in degrees Fahrenheit */
	private static final double[] diffTemperature = {4.5, 12.5, 27.5};   
	/** lookup table for drying factor */
	private static final double[] intervalDryingFactor = {16., 10., 7., 5., 4., 3.}; 
	/** measured in miles per hour */
	private static final double maximumWindSpeed = 14; 
	/** measured in inches */
	static final double limitPrecipitation = 0.1; 
	private static final double limitAdfm = 33.; 


	/**
	 * Instantiates a new instance of Firedanger.
	 */
	public Firedanger() {
		super();
	}


	/**
	 * Gets the fire danger index vector depending on the current environmental conditions.
	 * 
	 * @param currentConditions current environmental conditions
	 * @return the fire danger index vector containing the seven fire danger indexes.
	 */
	public SpreadIndexVector getFireDangerIndexVector(Condition currentConditions) {

		double fineFuelMoisture=0.;
		double adjustedFuelMoisture=0;
		double buildupIndex=currentConditions.getBuildupIndexYesterday();
		double timberSpreadIndex=0;
		double grassSpreadIndex=0;
		double dryingFactor=0;
		double fireLoadIndex=0;


		/** fire danger is zero when snow on the ground */ 
		if (currentConditions.isSnowOnTheGround() == false){

			fineFuelMoisture = calculateFineFuelMoisture(currentConditions.getDryBulbTemperature(), 
					currentConditions.getWetBulbTemperature(), currentConditions.getHerbState());

			dryingFactor = calculateDryingFactor(fineFuelMoisture); 

			if (currentConditions.getPrecipitation() > limitPrecipitation){
				buildupIndex = adjustBuildupIndex(currentConditions.getBuildupIndexYesterday(),
						currentConditions.getPrecipitation()) + dryingFactor;
			}
			else{
				buildupIndex = currentConditions.getBuildupIndexYesterday() + dryingFactor;
			}

			adjustedFuelMoisture = calculateAdjustedFuelMoisture (fineFuelMoisture, buildupIndex);

			grassSpreadIndex = calculateGrassSpreadIndex(fineFuelMoisture, adjustedFuelMoisture, currentConditions.getWindSpeed());

			timberSpreadIndex = calculateTimberSpreadIndex(adjustedFuelMoisture, currentConditions.getWindSpeed());	

			fireLoadIndex = calculateFireLoadIndex(timberSpreadIndex, buildupIndex);
		}
		/** update buildupIndex if it has rained significantly */
		else if (currentConditions.getPrecipitation() > limitPrecipitation){
			buildupIndex = adjustBuildupIndex(currentConditions.getBuildupIndexYesterday(), currentConditions.getPrecipitation()); 		
		}

		return new SpreadIndexVector(fineFuelMoisture, adjustedFuelMoisture, dryingFactor, 
				buildupIndex, grassSpreadIndex, timberSpreadIndex, fireLoadIndex);
	}


	/**
	 * Calculate fire load index: rating of the maximum effort required to contain all probable fires.	 
	 *
	 * @param timberSpreadIndex 
	 * @param buildupIndex 
	 * @return double
	 */
	private double calculateFireLoadIndex(double timberSpreadIndex,
			double buildupIndex) {

		double logFireLoadIndex = 0;
		double fireLoadIndex = -1;

		if ((timberSpreadIndex > 0.) && (buildupIndex > 0.)){

			logFireLoadIndex = 1.75*Math.log10(timberSpreadIndex) + .32*Math.log10(buildupIndex) - 1.64;
			fireLoadIndex =  Math.pow(10., logFireLoadIndex);
		}
		return fireLoadIndex;
	}


	/**
	 * Calculate timber spread index: danger of fire spread in a forest (upper limit 99%).
	 *
	 * @param adjustedFuelMoisture 
	 * @param windSpeed current windspeed
	 * @return double 
	 */
	private double calculateTimberSpreadIndex(double adjustedFuelMoisture,
			double windSpeed) {

		double timberSpreadIndex=0;

		if (adjustedFuelMoisture > limitAdfm){
			return 1.;
		}
		else if (windSpeed >= maximumWindSpeed){
			
			timberSpreadIndex = .00918 * (windSpeed + maximumWindSpeed) * Math.pow((33. - adjustedFuelMoisture), 1.65) - 3.;

			return (timberSpreadIndex > 99.) ? 99. : timberSpreadIndex;
		}
		else{
			timberSpreadIndex = .01312 * (windSpeed + 6.) * Math.pow((33. - adjustedFuelMoisture), 1.65) - 3.;
		}

		return timberSpreadIndex;
	}


	/**
	 * Calculate grass spread index: danger of fire spreading in grass environments (upper limit 99%).
	 *
	 * @param fineFuelMoisture 
	 * @param adjustedFuelMoisture 
	 * @param windSpeed 
	 * @return double
	 */
	private double calculateGrassSpreadIndex(double fineFuelMoisture, double adjustedFuelMoisture, double windSpeed) {

		double grassSpreadIndex = 1.;

		if (adjustedFuelMoisture > limitAdfm){
			return 1.;
		}
		else if (windSpeed >= maximumWindSpeed){

			grassSpreadIndex = .00918 * (windSpeed + maximumWindSpeed) * Math.pow((33. - fineFuelMoisture), 1.65) - 3.;

			return (grassSpreadIndex > 99.) ? 99. : grassSpreadIndex;
		}
		else{
			grassSpreadIndex = .01312 * (windSpeed + 6.) * Math.pow((33. - fineFuelMoisture), 1.65) - 3.;
		}
		return grassSpreadIndex;
	}


	/**
	 * Calculate adjusted fuel moisture: fuel moisture adjusted for recent precipitation over 0.1 inches.
	 *
	 * @param fineFuelMoisture 
	 * @param buildupIndex 
	 * @return double
	 */
	private double calculateAdjustedFuelMoisture(double fineFuelMoisture,
			double buildupIndex) {

		return 0.9 * fineFuelMoisture + 0.5 + 9.5 * Math.exp(-1.* buildupIndex/50.);
	}


	/**
	 * Calculate the buildup index: reflects the combined cumulative effects of daily drying and precipitation
	 *  in fuels with a 10 day time lag constant. Typical values range in between 50 to 100.
	 *
	 * @param buildupIndexYesterday Yesterday's buildup index
	 * @param precipitation Precipitation in inches
	 * @return The buildup index, always greater than 0.
	 */
	private double adjustBuildupIndex(double buildupIndexYesterday,
			double precipitation) {

		double buildupIndex = buildupIndexYesterday;

		double factor1 =  1.-(1. - Math.exp(-1.*buildupIndexYesterday/50.));
		double factor2 = Math.exp(-1.175 * (precipitation - limitPrecipitation));
		buildupIndex = -50. * Math.log(factor1 * factor2);

		return  (buildupIndex >= 0.) ? buildupIndex : 0.;
	}


	/**
	 * Calculate drying factor: higher for smaller fine fuel moisture, determined from lookup table.
	 *
	 * @param fineFuelMoisture the fine fuel moisture
	 * @return the double
	 */
	private double calculateDryingFactor(double fineFuelMoisture) {

		int i;
		for (i=1; i<=6; i++){
			if (fineFuelMoisture > intervalDryingFactor[i-1]){
				break;
			}
		}

		return i;
	}


	/**
	 * Calculate fine fuel moisture: moisture of litter measured in %.
	 *
	 * @param dryBulbTemperature 
	 * @param wetBulbTemperature 
	 * @param herbstate 
	 * @return double
	 */
	private double calculateFineFuelMoisture(double dryBulbTemperature,
			double wetBulbTemperature, HerbState herbstate) {

		double deltaT = dryBulbTemperature - wetBulbTemperature;
		double ffm = 0.; // fine fuel moisture

		if (deltaT <= diffTemperature[0]){
			ffm = constantsFineFuelMoisture[0][0] * Math.exp(constantsFineFuelMoisture[1][0] * deltaT);
		}
		else if (deltaT <= diffTemperature[1]){
			ffm = constantsFineFuelMoisture[0][1] * Math.exp(constantsFineFuelMoisture[1][1] * deltaT);
		}
		else if (deltaT <= diffTemperature[2]){
			ffm = constantsFineFuelMoisture[0][2] * Math.exp(constantsFineFuelMoisture[1][2]* deltaT);
		}
		else{
			ffm = constantsFineFuelMoisture[0][3] * Math.exp(constantsFineFuelMoisture[1][3] * deltaT);
		}

		/** if ffm is smaller than one, set it to one */
		if (ffm < 1.){
			ffm = 1.;
		}

		/** adjust fine fuel moisture according to herb state */
		if (herbstate == HerbState.TRANSITION){
			ffm = ffm + 5.;
		}
		else if (herbstate == HerbState.GREEN){
			ffm = ffm + 10.;
		}

		return ffm;
	}

}
