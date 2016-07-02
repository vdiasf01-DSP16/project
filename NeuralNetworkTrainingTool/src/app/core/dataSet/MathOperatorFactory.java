package app.core.dataSet;

import app.core.dataSet.mathOperator.AddMathOperator;
import app.core.dataSet.mathOperator.BinMathOperator;
import app.core.dataSet.mathOperator.DivMathOperator;
import app.core.dataSet.mathOperator.InvMathOperator;
import app.core.dataSet.mathOperator.MulMathOperator;
import app.core.dataSet.mathOperator.SubMathOperator;

/**
 * Abstract class to return Math Operator objects which will apply
 * transformations linked to the selected transformation operation name.
 * 
 * @author Vasco
 */
public abstract class MathOperatorFactory {
	
    /**
     * Get the matching Math Operation available.
     * Given no initial bias value was supplied, the operation will 
     * make no effect to all given values.
     * 
     * @param activationFunction ActivationFunctionKey
     * @return ActivationFunctionCore
     */
    public static MathOperatorCore<?> getMathOperation(MathOperatorKey mathOperator) {
        if ( MathOperatorKey.ADD.equals(mathOperator) ) return new AddMathOperator();
        if ( MathOperatorKey.SUB.equals(mathOperator) ) return new SubMathOperator();
        if ( MathOperatorKey.MUL.equals(mathOperator) ) return new MulMathOperator();
        if ( MathOperatorKey.DIV.equals(mathOperator) ) return new DivMathOperator();
        if ( MathOperatorKey.INV.equals(mathOperator) ) return new InvMathOperator();
        if ( MathOperatorKey.BIN.equals(mathOperator) ) return new BinMathOperator();
        return null;
    }

    /**
     * Get the matching Math Operation available.
     * The given initial bias value will be used against all 
     * online values given by the data set or neural network output.
     * 
     * @param mathOperator
     * @param value double bias value
     * @return MathOperatorCore
     */
    public static MathOperatorCore<?> getMathOperation(MathOperatorKey mathOperator, Double value) {
        if ( MathOperatorKey.ADD.equals(mathOperator) ) return new AddMathOperator(value);
        if ( MathOperatorKey.SUB.equals(mathOperator) ) return new SubMathOperator(value);
        if ( MathOperatorKey.MUL.equals(mathOperator) ) return new MulMathOperator(value);
        if ( MathOperatorKey.DIV.equals(mathOperator) ) return new DivMathOperator(value);
        if ( MathOperatorKey.INV.equals(mathOperator) ) return new InvMathOperator(value);
        if ( MathOperatorKey.BIN.equals(mathOperator) ) return new BinMathOperator(value);
        return null;
    }

    /**
     * The Description associated with the given math operator.
     * 
     * @param mathOperator
     * @return String
     */
    public static String getDescription(MathOperatorKey mathOperator) {
        return getMathOperation(mathOperator).getDescription();
    }
    
    /**
     * Returning the name of the math operator given.
     * 
     * @param mathOperator
     * @return String
     */
    public static String getName(MathOperatorKey mathOperator) {
        return getMathOperation(mathOperator).getName();
    }

}