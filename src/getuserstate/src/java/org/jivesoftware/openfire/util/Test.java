package org.jivesoftware.openfire.util;

import java.util.HashSet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String str="fu@localhost/Spark";
		//String[] info=str.split("@");
		//System.out.println(info[1]);
		
		HashSet<String> userLineHs = new HashSet<String>();//保存用户发送
		userLineHs.add("0");
		userLineHs.add("55");
		//System.out.println(userLineHs.size());
		for(String str:userLineHs){
			System.out.println(userLineHs.size());
			if((str.equals("55"))){
				System.out.println("有");
				userLineHs.remove("55");
			}
		}
		
		for(String str:userLineHs){
			
			
				System.out.println("sss="+str);
			
			
		}
		

	}

}
