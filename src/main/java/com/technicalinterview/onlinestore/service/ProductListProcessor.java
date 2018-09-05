package com.technicalinterview.onlinestore.service;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.technicalinterview.onlinestore.domain.BackPack;
import com.technicalinterview.onlinestore.domain.Product;
import com.technicalinterview.onlinestore.domain.SmartPhone;

/**
 * Implement the function productListToString that does conversion of provided list of products to text representation
 * of this list in this way.
 * - Result string is \n - separated string. E.g.
 *
 *   product 1 details
 *   product 2 details
 *   ...
 *   product 3 details
 *
 * - Each line contains details of one product
 * - Product description line should look like this:
 *   Product name (product GUID), product attribute 1 name:product attribute1 value; ... product attribute n name:product attributen value;
 *   For example. List contains BackPack definition:
 *   BackPack {
 *       guid: 111-222-333
 *       name: Cool Backpack
 *       maxContentWeight: 15
 *       color: "Black"
 *       capacity: 20
 *   }
 *   This becka pakc description string should look like this:
 *   Cool Backpack (111-222-333), maxContentWeight: 15, color: "Black", capacity: 20
 *
 * Keep in mind these requirements:
 * - String reorientation can be modified in future.
 * - There is a possibility to support multiply ways to convert list of products to string. E.g. it it is possible that in future
 *   you will need to implement support of different formats of outpust string (e.g. json instead of \n-separated string).
 * The basic idea is to make your current implementation flexible and modifiable in future.
 *
 * You can use any build system to build the sources (maven, gralde, ant).
 * You can use any 3rd party libs in your application.
 * Any java version (>=8).
 * Code must be tested (framework is up to you).
 *
 * If you are familiar with Git, please do work in a separate branch and create a pull request with your changes.
 */
public class ProductListProcessor {

    /**
     * Make String representation of providd product list.
     * @param products list of the products that needs to be converted to String
     * @return String representation of the provided list.
     */
    public static String productListToString(List<Product> products) {
    	StringBuilder sb = new StringBuilder();
    	for (int i=0;i<products.size();i++){
    		if(i>0)
    			sb.append("; ");
    		if (products.get(i) instanceof BackPack){
    			BackPack b = (BackPack)products.get(i);
    			sb.append(b.getName());
    			sb.append("(");
    			sb.append(b.getGuid());
    			sb.append("), ");
    			for (int j=0;j<products.get(i).getClass().getDeclaredFields().length;j++){
    				Field field = products.get(i).getClass().getDeclaredFields()[j];
    				field.setAccessible(true);
    				sb.append(field.getName());
    				sb.append(": ");
    				try {
    					if (field.getName().equalsIgnoreCase("maxContentWeight"))
							sb.append(b.getMaxContentWeight());
    					else if (field.getName().equalsIgnoreCase("color"))
    						sb.append(b.getColor());
    					else if (field.getName().equalsIgnoreCase("capacity"))
    						sb.append(b.getCapacity());
    					if(j<products.get(i).getClass().getDeclaredFields().length-1)
    						sb.append(", ");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
    			}
    		}else if (products.get(i) instanceof SmartPhone){
    			SmartPhone b = (SmartPhone)products.get(i);
    			sb.append(b.getName());
    			sb.append("(");
    			sb.append(b.getGuid());
    			sb.append("), ");
    			for (int j=0;j<products.get(i).getClass().getDeclaredFields().length;j++){
    				Field field = products.get(i).getClass().getDeclaredFields()[j];
    				field.setAccessible(true);
    				sb.append(field.getName());
    				sb.append(": ");
    				try {
    					if (field.getName().equalsIgnoreCase("manufacturer"))
							sb.append(b.getManufacturer());
    					else if (field.getName().equalsIgnoreCase("color"))
    						sb.append(b.getColor());
    					else if (field.getName().equalsIgnoreCase("numberOfCPUs"))
    						sb.append(b.getNumberOfCPUs());
    					else if (field.getName().equalsIgnoreCase("ramSize"))
    						sb.append(b.getRamSize());
    					else if (field.getName().equalsIgnoreCase("screenResolution"))
    						sb.append(b.getScreenResolution());
    					if(j<products.get(i).getClass().getDeclaredFields().length-1)
    						sb.append(", ");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
    			}
    		}
    	}
    	return sb.toString();
    }
    
    /**
     * Used to test the core method with different inputs
     * 
     * @param args
     */
    public static void main(String[] args){
    	BackPack back = new BackPack("110-100", "Cool");
    	back.setCapacity(15);
    	back.setColor("Black");
    	back.setMaxContentWeight(20);    	
    	SmartPhone smart = new SmartPhone("123-456", "Apple");
    	smart.setColor("Grey");
    	smart.setManufacturer("Apple");
    	smart.setNumberOfCPUs(2);
    	smart.setRamSize(4);
    	smart.setScreenResolution("1068");    	
    	List<Product> prds = new ArrayList<Product>();
    	prds.add(back);
    	prds.add(smart);    	
    	System.out.println(productListToString(prds));
    }
}