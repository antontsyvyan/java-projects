/*
 * GenericClass.java
 *
 */

package com.foursoft.gpa.utils;

import java.lang.reflect.Method;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * This class provides methods to dynamically load a class and call a certain method
 * in this class.
 */
public class GenericClass {
    
	private Logger log = Logger.getLogger(this.getClass().toString());
    /** Creates a new instance of GenericClass */
    public GenericClass() {
    }
    
    /**
     * Creates a temporary new instance of the specified classname. If a new instance could be
     * created, it will try to call the specified method by using the incoming parameters.
     * @param className Name of the class including the full package name (ie.: java.util.Vector)
     * @param callMethod Method name to invoke, without parameters and () signs, just the name.
     * @param methodParameters Parameters which are used to call the method. Should be put into the correct sequence in the vector.
     * @return a boolean which tells the calling class whether the call succeeded.
     */
    public boolean callGenericClassMethod(String className, String callMethod, Vector<?> methodParameters){
        
        Class<?> c=null;
        boolean found=false;
        boolean error=false;
        try{
            c = Class.forName(className);
            Object q=c.newInstance();
            Method[] methods=c.getMethods();
            for(int i=0;i<methods.length;i++){
                if(methods[i].getName().equals(callMethod)){
                	found=true;
                    try{
                        methods[i].invoke(q,methodParameters.toArray());
                    }catch(IllegalArgumentException argex){
                        String parameters = "";
                        for(int x=0;x<methods[i].getParameterTypes().length;x++){
                            Class<?>[] tempclass=methods[i].getParameterTypes();
                            if(x==(methods[i].getParameterTypes().length-1)){
                                parameters=parameters+tempclass[x].getName();
                            }else{
                                parameters=parameters+tempclass[x].getName()+", ";
                            }
                        }
                        log.severe("Types of arguments in do not match: "+methods[i].getName()+"("+parameters+")"+" found in "+className);                       
                        error=true;
                    }catch(Exception exerror){
                    	log.severe("Error ocured in the method: "+callMethod+"! Reason: "+exerror);
                    	exerror.printStackTrace();
                        error=true;
                    }
                }
            }
            if(!found){
            	log.severe("Error: Method  "+callMethod+" in "+className+" could not be found!");
                error=true;
            }
        }catch(Exception e){
        	log.severe("Error: "+e);
            error=true;
        }
        return error;
    }
    
    /**
     * Retreives all of the available methods for the classname specified.
     * @param className Name of the class including the full package name (ie.: java.util.Vector)
     * @return a method object which contains all available method names.
     */
    public Method[] getClassMethods(String className){
        Method[] methods=null;
        try{
            Class<?> c = Class.forName(className);
            methods=c.getDeclaredMethods();
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        return methods;
    }
}
