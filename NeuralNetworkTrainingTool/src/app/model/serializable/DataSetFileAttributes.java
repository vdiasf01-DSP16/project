package app.model.serializable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import app.core.dataSet.FileParser;
import app.model.file.DataSetFileGUIReader;

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
public class DataSetFileAttributes implements FileAttributes, FileParser, DataSetFileGUIReader {

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

    /**
     * The default number of data rows to be displayed when requested
     * by the end user.
     */
    private final int DEFAULT_DATA_ROWS = 10;

    /**
     * Calculating dynamically the maximum number of columns to show.
     */
    private int headerColumns;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeaderRows() {
        return headerRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeaderRows(int headerRows) {
        this.headerRows = headerRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFooterRows() {
        return footerRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFooterRows(int footerRows) {
        this.footerRows = footerRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename() {
        return filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSeparator() {
        return separator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTestingRangeIndex(int testingStartIndex, int testingEndIndex) {
        this.testingStartIndex = testingStartIndex;
        this.testingEndIndex = testingEndIndex;
        this.setHasTestingRange(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTestingStartIndex() {
        return testingStartIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTestingEndIndex() {
        return testingEndIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTrainingRangeIndex(int trainingStartIndex, int trainingEndIndex) {
        this.trainingStartIndex = trainingStartIndex;
        this.trainingEndIndex = trainingEndIndex;
        this.setHasTrainingRange(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTrainingStartIndex() {
        return trainingStartIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTrainingEndIndex() {
        return trainingEndIndex;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public boolean isHasTrainingRange() {
        return hasTrainingRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHasTrainingRange(boolean hasTrainingRange) {
        this.hasTrainingRange = hasTrainingRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHasTestingRange() {
        return hasTestingRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHasTestingRange(boolean hasTestingRange) {
        this.hasTestingRange = hasTestingRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<String>> getHeaderDataRows() {
        headerColumns = 0;
        List<List<String>> headerList = new LinkedList<>();
        if ( headerRows == 0 ) {
            return headerList;
        }
        // Get the buffer handler ready..
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int currentRowPosition = 0;
        try {
            do {
                String line;
                line = bufferedReader.readLine();
                // Premature end of file is a sign of something going wrong somewhere.
                if ( line == null ) 
                    throw new ArrayIndexOutOfBoundsException("File seems empty.");

                List<String> row = getParsedRow(line);
                
                headerList.add(row);
                currentRowPosition++;
            } while ( headerRows > currentRowPosition );

        } catch (IOException e) {
            e.printStackTrace();
        }  
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headerList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<String>> getFirstDataRows(int dataRows) {
        // Default the number of dataRows to set default data rows.
        if ( dataRows == 0 ) dataRows = DEFAULT_DATA_ROWS;

        List<List<String>> firstDataRow = new LinkedList<>();

        // Get the buffer handler ready..
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int currentRowPosition = 0;

        try {
            do {
                String line = bufferedReader.readLine();

                if ( currentRowPosition < headerRows ) {
                    currentRowPosition++;
                    continue;
                }

                if ( line == null ) break;
                
                List<String> row = getParsedRow(line);

                firstDataRow.add(row);
                currentRowPosition++;
            } while ( dataRows > ( currentRowPosition - headerRows ) );

        } catch (IOException e) {
            e.printStackTrace();
        } 
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return firstDataRow;
    }

    /**
     * Takes one line, splits into columns as per supplied separator.
     * 
     * @param rowLine String
     * @return List String
     */
    private List<String> getParsedRow(String rowLine) {
        List<String> parsedRow = new LinkedList<>();
        

        String[] splitRow = rowLine.split(separator);
        
        if ( headerColumns < splitRow.length ) headerColumns = splitRow.length;

        for( int i = 0; i < headerColumns; i++ ) {
            if ( splitRow.length > i ) {
              parsedRow.add(splitRow[i]);
            }
            else {
                parsedRow.add("");
            }
        }

        return parsedRow;
    }
}
