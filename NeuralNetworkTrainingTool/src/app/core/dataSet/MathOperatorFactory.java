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
     * @throws IllegalStateException
     */
    public static MathOperatorCore<?> getMathOperation(MathOperatorKey mathOperator) 
            throws IllegalStateException {
        if ( MathOperatorKey.ADD.equals(mathOperator) ) return new AddMathOperator();
        if ( MathOperatorKey.SUB.equals(mathOperator) ) return new SubMathOperator();
        if ( MathOperatorKey.MUL.equals(mathOperator) ) return new MulMathOperator();
        if ( MathOperatorKey.DIV.equals(mathOperator) ) return new DivMathOperator();
        if ( MathOperatorKey.INV.equals(mathOperator) ) return new InvMathOperator();
        if ( MathOperatorKey.BIN.equals(mathOperator) ) return new BinMathOperator();
        throw new IllegalStateException("Unknown Math Operation "+mathOperator);
    }

    /**
     * Get the matching Math Operation available.
     * This converts a string that matches the Math Key into the
     * respective Math Operator object.
     * 
     * @param mathOperator String
     * @return ActivationFunctionCore
     * @throws IllegalStateException
     */
    public static MathOperatorCore<?> getMathOperation(String mathOperator) 
            throws IllegalStateException {
        if ( getMathOperation(MathOperatorKey.ADD).getName().equals(mathOperator) ) 
            return new AddMathOperator();
        if ( getMathOperation(MathOperatorKey.SUB).getName().equals(mathOperator) ) 
            return new SubMathOperator();
        if ( getMathOperation(MathOperatorKey.MUL).getName().equals(mathOperator) ) 
            return new MulMathOperator();
        if ( getMathOperation(MathOperatorKey.DIV).getName().equals(mathOperator) ) 
            return new DivMathOperator();
        if ( getMathOperation(MathOperatorKey.INV).getName().equals(mathOperator) ) 
            return new InvMathOperator();
        if ( getMathOperation(MathOperatorKey.BIN).getName().equals(mathOperator) ) 
            return new BinMathOperator();
        throw new IllegalStateException("Unknown Math Operation "+mathOperator);
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