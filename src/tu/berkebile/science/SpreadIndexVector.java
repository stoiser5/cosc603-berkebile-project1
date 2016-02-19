package tu.berkebile.science;

public class SpreadIndexVector {
	
	//instance variables
	private double fineFuelMoisture;
	private double adjustedFuelMoisture;
	private double dryingFactor;
	private double buildUpIndex;
	private double fineFuelSpread;
	private double timberSpreadIndex;
	private double fireLoadIndex;
	
	//constructor
	

	//mutators
	public double getFineFuelMoisture() {
		return fineFuelMoisture;
	}

	public SpreadIndexVector(double fineFuelMoisture,
			double adjustedFuelMoisture, double dryingFactor,
			double buildUpIndex, double fineFuelSpread,
			double timberSpreadIndex, double fireLoadIndex) {
		super();
		
		this.fineFuelMoisture = fineFuelMoisture;
		this.adjustedFuelMoisture = adjustedFuelMoisture;
		this.dryingFactor = dryingFactor;
		this.buildUpIndex = buildUpIndex;
		this.fineFuelSpread = fineFuelSpread;
		this.timberSpreadIndex = timberSpreadIndex;
		this.fireLoadIndex = fireLoadIndex;
	}
	

	public void setFineFuelMoisture(double fineFuelMoisture) {
		this.fineFuelMoisture = fineFuelMoisture;
	}

	public double getAdjustedFuelMoisture() {
		return adjustedFuelMoisture;
	}

	public void setAdjustedFuelMoisture(double adjustedFuelMoisture) {
		this.adjustedFuelMoisture = adjustedFuelMoisture;
	}

	public double getBuildUpIndex() {
		return buildUpIndex;
	}

	public void setBuildUpIndex(double buildUpIndex) {
		this.buildUpIndex = buildUpIndex;
	}
	
	public double getDryingFactor() {
		return dryingFactor;
	}
	
	public void setDryingFactor(double dryingFactor) {
		this.dryingFactor = dryingFactor;
	}

	public double getFineFuelSpread() {
		return fineFuelSpread;
	}

	public void setFineFuelSpread(double fineFuelSpread) {
		this.fineFuelSpread = fineFuelSpread;
	}

	public double getTimberSpreadIndex() {
		return timberSpreadIndex;
	}

	public void setTimberSpreadIndex(double timberSpreadIndex) {
		this.timberSpreadIndex = timberSpreadIndex;
	}

	public double getFireLoadIndex() {
		return fireLoadIndex;
	}

	public void setFireLoadIndex(double fireLoadIndex) {
		this.fireLoadIndex = fireLoadIndex;
	}

	public void printIndexes() {
		
		System.out.println("The fire danger index values are: " + "\n\n" +
				"Fine fuel moisture: " + fineFuelMoisture + "\n" +
				"Adjusted fuel moisture: " + adjustedFuelMoisture + "\n" +
				"Drying factor: " + dryingFactor + "\n" +
				"Buildup index: " + buildUpIndex + "\n" +
				"Fine fuel spread: " + fineFuelSpread + "\n" +
				"Timber spread index: " + timberSpreadIndex + "\n" +
				"Fire load index: " + fireLoadIndex);
		
	}


}
