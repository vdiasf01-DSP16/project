package app.model.dataSet;

/**
 * List of all the known mathematical operators that can be used 
 * in the transform Functions.
 * 
 * @author Vasco
 *
 */
public enum MathOperator {
	/**
	 * Adds given number to instantiated one .
	 */
	ADD, 

	/**
     * Subtracts given number from instantiated one.
     */
	SUB,
	
	/**
	 * Divide given number from instantiated one.
	 */
	DIV, 

	/**
	 * Multiply given number from instantiated one.
	 */
	MUL,
	
	/**
	 * Invert bitwise: 0 is false and anything else is true.
	 */
    INV, 

    /**
     * Ensure binary values: 0 = 0.0 anything else is 1.0.
     */
    BIN, 
}
