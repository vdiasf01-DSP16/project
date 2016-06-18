package app.model.serializable;

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
public class DataSetFileAttributes implements FileAttributes {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 510506102823934905L;

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

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getHeaderRows()
	 */
	@Override
	public int getHeaderRows() {
		return headerRows;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setHeaderRows(int)
	 */
	@Override
	public void setHeaderRows(int headerRows) {
		this.headerRows = headerRows;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getFooterRows()
	 */
	@Override
	public int getFooterRows() {
		return footerRows;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setFooterRows(int)
	 */
	@Override
	public void setFooterRows(int footerRows) {
		this.footerRows = footerRows;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getFilename()
	 */
	@Override
	public String getFilename() {
		return filename;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setFilename(java.lang.String)
	 */
	@Override
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getSeparator()
	 */
	@Override
	public String getSeparator() {
		return separator;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setSeparator(java.lang.String)
	 */
	@Override
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setTestingRangeIndex(int, int)
	 */
	@Override
	public void setTestingRangeIndex(int testingStartIndex, int testingEndIndex) {
		this.testingStartIndex = testingStartIndex;
		this.testingEndIndex = testingEndIndex;
		this.setHasTestingRange(true);
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getTestingStartIndex()
	 */
	@Override
	public int getTestingStartIndex() {
		return testingStartIndex;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getTestingEndIndex()
	 */
	@Override
	public int getTestingEndIndex() {
		return testingEndIndex;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setTrainingRangeIndex(int, int)
	 */
	@Override
	public void setTrainingRangeIndex(int trainingStartIndex, int trainingEndIndex) {
		this.trainingStartIndex = trainingStartIndex;
		this.trainingEndIndex = trainingEndIndex;
		this.setHasTrainingRange(true);
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getTrainingStartIndex()
	 */
	@Override
	public int getTrainingStartIndex() {
		return trainingStartIndex;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#getTrainingEndIndex()
	 */
	@Override
	public int getTrainingEndIndex() {
		return trainingEndIndex;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#toString()
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

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#isHasTrainingRange()
	 */
	@Override
	public boolean isHasTrainingRange() {
		return hasTrainingRange;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setHasTrainingRange(boolean)
	 */
	@Override
	public void setHasTrainingRange(boolean hasTrainingRange) {
		this.hasTrainingRange = hasTrainingRange;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#isHasTestingRange()
	 */
	@Override
	public boolean isHasTestingRange() {
		return hasTestingRange;
	}

	/* (non-Javadoc)
	 * @see app.model.dataSet.FileAttributes#setHasTestingRange(boolean)
	 */
	@Override
	public void setHasTestingRange(boolean hasTestingRange) {
		this.hasTestingRange = hasTestingRange;
	}
}
