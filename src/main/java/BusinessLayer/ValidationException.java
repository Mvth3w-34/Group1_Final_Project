/* filename: ValidationException.java
 * date: Mar. 25th, 2025
 * updated by: Stephanie Prystupa-Maule
 * original authors: Stan Pieda (2015)
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Lab Assignment 2 - Java Servlet
 */
package BusinessLayer;

/**
 * Custom exception class for handling validation errors in the Authors Management System.
 * This exception is thrown when author data fails business rule validation.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 03/25/2025
 */

public class ValidationException extends Exception {
	
        /**
         * Constructs a ValidationException with a default message.
         */
	public ValidationException(){
		super("Data not in valid format");
	}
	
       /**
        * Constructs a ValidationException with a specified message.
        * 
        * @param message The detail message explaining the validation error
        */
	public ValidationException(String message){
		super(message);
	}
	
       /**
        * Constructs a ValidationException with a specified message and cause.
        * 
        * @param message The detail message explaining the validation error
        * @param throwable The cause of the validation error
        */
	public ValidationException(String message, Throwable throwable){
		super(message, throwable);
	}
	
       /**
        * Constructs a ValidationException with a specified cause.
        * 
        * @param throwable The cause of the validation error
        */
	public ValidationException(Throwable throwable){
		super(throwable);
	}
}
