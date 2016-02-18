package tu.berkebile.science;

public class SpreadIndexVector {
	
	//instance variables
	private double fineFuelMoisture;
	private double adjustedFuelMoisture;
	private double buildUpIndex;
	private double fineFuelSpread;
	private double timberSpreadIndex;
	private double fireLoadIndex;
	
	//constructor
	public SpreadIndexVector(double fineFuelMoisture,
			double adjustedFuelMoisture, double buildUpIndex,
			double fineFuelSpread, double timberSpreadIndex,
			double fireLoadIndex) {
		super();
		this.fineFuelMoisture = fineFuelMoisture;
		this.adjustedFuelMoisture = adjustedFuelMoisture;
		this.buildUpIndex = buildUpIndex;
		this.fineFuelSpread = fineFuelSpread;
		this.timberSpreadIndex = timberSpreadIndex;
		this.fireLoadIndex = fireLoadIndex;
	}

	//mutators
	public double getFineFuelMoisture() {
		return fineFuelMoisture;
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


}
