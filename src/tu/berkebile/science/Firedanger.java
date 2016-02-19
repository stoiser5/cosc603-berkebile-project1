package tu.berkebile.science;

// drying factor ranges between 1 and 7
//timberspread index and grass spread index: upper limit 99
// 
public class Firedanger {

	private static final double[][] constantsFineFuelMoisture = {{30.0, 19.2, 13.8, 22.5},{-1.859, -0.859, -0.05966, -.77373}};
	private static final double[] diffTemperature = {4.5, 12.5, 27.5};  // degrees Fahrenheit
	private static final double[] intervalDryingFactor = {16., 10., 7., 5., 4., 3.}; //lookup table for drying factor
	private static final double maximumWindSpeed = 14; // miles per hour
	private static final double limitPrecipitation = 0.1; //inches
	private static final double limitAdfm = 33.;
	
	
	public Firedanger() {
		super();
	}


	public SpreadIndexVector getFireDangerIndexVector(double dryBulbTemperature, double wetBulbTemperature, boolean snowOnTheGround, double precipitation, 
			double windSpeed, HerbState herbState, double buildupIndexYesterday) {
		
		double fineFuelMoisture=0.;
		double adjustedFuelMoisture=0;
		double buildupIndex=buildupIndexYesterday;
		double timberSpreadIndex=0;
		double grassSpreadIndex=0;
		double dryingFactor=0;
		double fireLoadIndex=0;
		

		// if snow is on the ground, fire danger is zero, otherwise proceed
		if (snowOnTheGround == false){

			fineFuelMoisture = calculateFineFuelMoisture(dryBulbTemperature, wetBulbTemperature, herbState);

			dryingFactor = calculateDryingFactor(fineFuelMoisture); 

			if (precipitation > limitPrecipitation){
				buildupIndex = calculateBuildupIndex(buildupIndexYesterday, precipitation) + dryingFactor;
			}
			else{
				buildupIndex = buildupIndexYesterday + dryingFactor;
			}

			adjustedFuelMoisture = calculateAdjustedFuelMoisture (fineFuelMoisture, buildupIndex);

			grassSpreadIndex = calculateGrassSpreadIndex(fineFuelMoisture, adjustedFuelMoisture, windSpeed);

			timberSpreadIndex = calculateTimberSpreadIndex(adjustedFuelMoisture, windSpeed);	

			if ((timberSpreadIndex > 0.) && (buildupIndex > 0.)){
				fireLoadIndex = calculateFireLoadIndex(timberSpreadIndex, buildupIndex);
			}
		}
		
		// update buildupIndex if it has rained significantly
		else if (precipitation > limitPrecipitation){
			buildupIndex = calculateBuildupIndex(buildupIndexYesterday, precipitation); 		
			
		}

		return new SpreadIndexVector(fineFuelMoisture, adjustedFuelMoisture, dryingFactor, 
				buildupIndex, grassSpreadIndex, timberSpreadIndex, fireLoadIndex);
	}

	
	private double calculateFireLoadIndex(double timberSpreadIndex,
			double buildupIndex) {

		double logFireLoadIndex = 1.75*Math.log10(timberSpreadIndex) + .32*Math.log10(buildupIndex) - 1.64;

		return Math.pow(10., logFireLoadIndex);
	}


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


	private double calculateAdjustedFuelMoisture(double fineFuelMoisture,
			double buildupIndex) {

		return 0.9 * fineFuelMoisture + 0.5 + 9.5 * Math.exp(-1.* buildupIndex/50.);
	}


	private double calculateBuildupIndex(double buildupIndexYesterday,
			double precipitation) {
		
	    double factor1 =  1.-(1. - Math.exp(-1.*buildupIndexYesterday/50.));
		double factor2 = Math.exp(-1.175 * (precipitation - limitPrecipitation));
		double buildUpIndex = -50. * Math.log(factor1 * factor2);
		
		return  (buildUpIndex >= 0.) ? buildUpIndex : 0.;
	}


	private double calculateDryingFactor(double fineFuelMoisture) {

		int i;
		for (i=1; i<=6; i++){
			if (fineFuelMoisture > intervalDryingFactor[i-1]){
				break;
			}
		}

		return i;
	}


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

		// if ffm is smaller than one, set it to one
		if (ffm < 1.){
			ffm = 1.;
		}

		// adjust fine fuel moisture according to herb stage
		if (herbstate == HerbState.TRANSITION){
			ffm = ffm + 5.;
		}
		else if (herbstate == HerbState.GREEN){
			ffm = ffm + 10.;
		}

		return ffm;
	}

}
