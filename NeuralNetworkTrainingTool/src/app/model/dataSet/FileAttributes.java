package app.model.dataSet;

/**
 * File Attributes to be passed in whenever a file needs to be defined
 * in terms of filename, header rows, footer, or any other required attribute.
 * 
 * This allows the simplification of the FileDataSet object structure when
 * passed in at the construction time, allowing further adjustment of file 
 * header rows whenever these are known or need to be adjusted dynamically.
 * 
 * @author Vasco
 *
 */
public class FileAttributes {

	/**
	 * The number of header rows in file.
	 */
	private int headerRows;
	
	/**
	 * The number of footer rows in file.
	 */
	private int footerRows;
	
	/**
	 * The filename.
	 */
	private String filename;
	
	/**
	 * The separator used.
	 */
	private String separator;
	
	/**
	 * The testing start row index.
	 */
	private int testingStartIndex;
	
	/**
	 * The testing end row index.
	 */
	private int testingEndIndex;
	
	/**
	 * The training start row index.
	 */
	private int trainingStartIndex;
	
	/**
	 * The training end row index.
	 */
	private int trainingEndIndex;
	
	/**
	 * If set to false, the training range will be ignored.
	 */
	private boolean hasTrainingRange;
	
	/**
	 * If set to false, the testing range will be ignored.
	 */
	private boolean hasTestingRange;

	/**
	 * The Header rows.
	 * 
	 * @return int
	 */
	public int getHeaderRows() {
		return headerRows;
	}

	/**
	 * Set the number of file header rows.
	 * 
	 * @param headerRows 
	 */
	public void setHeaderRows(int headerRows) {
		this.headerRows = headerRows;
	}

	/**
	 * The Footer rows.
	 * 
	 * @return int
	 */
	public int getFooterRows() {
		return footerRows;
	}

	/**
	 * Set the number of file footer rows.
	 * 
	 * @param footerRows 
	 */
	public void setFooterRows(int footerRows) {
		this.footerRows = footerRows;
	}

	/**
	 * The file name.
	 * 
	 * @return String
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * The file name.
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * The file data separator.
	 * 
	 * @return String
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * The file data separator.
	 * 
	 * @param separator
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * Sets the start and end row index for the testing set.
	 * 
	 * @param testingStartIndex
	 * @param testingEndIndex
	 */
	public void setTestingRangeIndex(int testingStartIndex, int testingEndIndex) {
		this.testingStartIndex = testingStartIndex;
		this.testingEndIndex = testingEndIndex;
		this.setHasTestingRange(true);
	}

	/**
	 * The testing starting row index.
	 * 
	 * @return int
	 */
	public int getTestingStartIndex() {
		return testingStartIndex;
	}

	/**
	 * The testing end row index.
	 * 
	 * @return int
	 */
	public int getTestingEndIndex() {
		return testingEndIndex;
	}

	/**
	 * The training start and end row index.
	 *  
	 * @param trainingStartIndex
	 * @param trainingEndIndex
	 */
	public void setTrainingRangeIndex(int trainingStartIndex, int trainingEndIndex) {
		this.trainingStartIndex = trainingStartIndex;
		this.trainingEndIndex = trainingEndIndex;
		this.setHasTrainingRange(true);
	}

	/**
	 * The training start row index.
	 * 
	 * @return int
	 */
	public int getTrainingStartIndex() {
		return trainingStartIndex;
	}

	/**
	 * The training end row index.
	 * 
	 * @return int
	 */
	public int getTrainingEndIndex() {
		return trainingEndIndex;
	}

	/**
	 * The default print of all data set.
	 */
	@Override
	public String toString() {
		return "HeaderRows: "+headerRows+"\n"
				+ "FooterRows: "+footerRows+"\n"
				+ "FileName: "+filename+"\n"
				+ "Separator: '"+separator+"'\n"
                + "TestingRange: "+testingStartIndex+" to "+testingEndIndex+"\n"
				+ "TrainingRange: "+trainingStartIndex+" to "+trainingEndIndex+"\n";
	}

	/**
	 * If set to false, the Training range is to be ignored.
	 * 
	 * @return true if has valid Training range.
	 */
	public boolean isHasTrainingRange() {
		return hasTrainingRange;
	}

	/**
	 * Sets the Training range to be considered.
	 * 
	 * @param hasTrainingRange
	 */
	public void setHasTrainingRange(boolean hasTrainingRange) {
		this.hasTrainingRange = hasTrainingRange;
	}

	/**
	 * If set to false, the Testing range is to be ignored.
	 * 
	 * @return true if has valid Traing range.
	 */
	public boolean isHasTestingRange() {
		return hasTestingRange;
	}

	/**
	 * Sets the Testing range to be considered.
	 * 
	 * @param hasTestingRange
	 */
	public void setHasTestingRange(boolean hasTestingRange) {
		this.hasTestingRange = hasTestingRange;
	}
}
