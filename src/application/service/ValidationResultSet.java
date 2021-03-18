package application.service;

import java.util.ArrayList;
import java.util.List;

public class ValidationResultSet {
	private List<ValidationResult> vrList = new ArrayList<ValidationResult>();
	private int negativesCounter = 0;
	private int zerosCounter = 0;		// It will be used in future improvements.
	private int positivesCounter = 0;	// It will be used in future improvements.
	private boolean verdict = false;

	public void add(ValidationResult vr) {
		this.vrList.add(vr);
	}

	public void remove(ValidationResult vr) {
		this.vrList.remove(vr);
	}

	public List<ValidationResult> getVRList() {
		return this.vrList;
	}

	private void analyseResults() {
		System.out.println("DEBUG-Logging [ValidationResultSet:analyseResults.");
		if (this.vrList.isEmpty()) {
			System.out.println("DEBUG-Logging [ValidationResultSet:analyseResults:vrList.isEmpty[veredict=false].");
			this.verdict = false;
		}
		else {
			System.out.println("DEBUG-Logging [ValidationResultSet:analyseResults[calculing-veredict].");
			for (ValidationResult forVR : vrList) {
				if (forVR.getCode() < 0)
					negativesCounter++;
				if (forVR.getCode() == 0)
					zerosCounter++;
				if (forVR.getCode() > 0)
					positivesCounter++;
			}
		}
		this.verdict = (negativesCounter > 0) ? false : true;
		System.out.println("DEBUG-Logging [ValidationResultSet:analyseResults[veredict="+this.verdict+"].");
	}

	public boolean getVerdict() {
		analyseResults();
		return this.verdict;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + negativesCounter;
		result = prime * result + positivesCounter;
		result = prime * result + (verdict ? 1231 : 1237);
		result = prime * result + ((vrList == null) ? 0 : vrList.hashCode());
		result = prime * result + zerosCounter;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationResultSet other = (ValidationResultSet) obj;
		if (negativesCounter != other.negativesCounter)
			return false;
		if (positivesCounter != other.positivesCounter)
			return false;
		if (verdict != other.verdict)
			return false;
		if (vrList == null) {
			if (other.vrList != null)
				return false;
		} else if (!vrList.equals(other.vrList))
			return false;
		if (zerosCounter != other.zerosCounter)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ValidationResultSet [vrList=" + vrList + ", negativesCounter=" + negativesCounter + ", zerosCounter="
				+ zerosCounter + ", positivesCounter=" + positivesCounter + ", verdict=" + verdict + "]";
	}
	
}
