import java.util.Scanner;
public class FO2018400201 {
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		//take lines from user
		String line1= console.nextLine();
		String line2= console.nextLine();
		String line3= console.nextLine();
		String line4= console.nextLine();
		String line5="";
		//if there are no parentheses,add parentheses
		if(line4.charAt(0)!='(') {
			    line4='('+line4;
			for(int i=0;i<line4.length()-1;i++) {
				line5+=line4.charAt(i);
			}
			line5=line5+");";
		    line4=line5;
		}
		//replace variables into fourth line
		line4=replacement(line4,line1);
		line4=replacement(line4,line2);
		line4=replacement(line4,line3);
		//count how many parentheses fourth line have got 
		int paranthesescount=0;
		for(int i=0;i<line4.length();i++) {
			if(line4.charAt(i)=='(') {
				paranthesescount+=1;
			}
		}
		//calculate inside of the parentheses and replace them
		for(int i=0;i<paranthesescount;i++) {
			line4=noblank(line4).replace(parantheses(noblank(line4)), inside(parantheses(noblank(line4))));
		}
		//delete the semicolon at the end
		String newline4="";
		for(int i=0;i<line4.length()-1;i++) {
			newline4+=line4.charAt(i);
		}
		System.out.println(newline4);
    }
	public static String parantheses(String str){
		//return the parentheses which has highest order of precedence
		String k="";
		for(int i=0; i<=str.indexOf(')')+1; i++) {
			k+=str.charAt(i);
		}
		String firstp=k.substring(k.lastIndexOf('('),k.indexOf(')')+1);
		return firstp;
	}
	public static String inside(String str){
		//return the inside of parentheses
		String newstr="";
		String numb1="";
    	String numb2="";
    	int j=1;
    	int k=1;
    	String allprocess="";
    	//if there are any multiplication or division,
    	//find the numbers before symbol and after symbol
	    for(int i=0; i<str.length(); i++) {
	    	if(str.charAt(i)=='*' || str.charAt(i)=='/') {
	    		while(!(str.charAt(i-j)=='+' || str.charAt(i-j)=='-' || str.charAt(i-j)=='(')) {
	    			j++;
	    		}
	    		numb1=str.substring(i-j+1,i);	    	
	    		while(!(str.charAt(i+k)=='+'|| str.charAt(i+k)=='-' || str.charAt(i+k)==')' || str.charAt(i+k)=='*' || str.charAt(i+k)=='/')) {
	    			k++;
	    		}
	    		numb2=str.substring(i+1,i+k);
	    		allprocess=str.substring(i-j+1,i+k);
	    		j=1;
	    		k=1;
	    		double dval1=0;
	    		double dval2=0;
	    		int ival1=0;
	    		int ival2=0;
	    		//if it is multiplication, do it
	    		if(str.charAt(i)=='*') {
	    		    if(numb1.contains(".") || numb2.contains(".")) {
	    			    dval1=Double.parseDouble(numb1);
	    			    dval2=Double.parseDouble(numb2);
	    			    str=str.replace(allprocess, Double.toString(dval1*dval2));
	    			    i=0;
	    		    }else {
	    			    ival1=Integer.parseInt(numb1);
	    			    ival2=Integer.parseInt(numb2);
	    			    str=str.replace(allprocess, Integer.toString(ival1*ival2));
	    			    i=0;
	    		    }
	    	    }else {
	    	    	//if it is division, do it
	    	    	if(numb1.contains(".") || numb2.contains(".")) {
	    			    dval1=Double.parseDouble(numb1);
	    			    dval2=Double.parseDouble(numb2);
	    			    str=str.replace(allprocess, Double.toString(dval1/dval2));
	    			    i=0;
	    		    }else {
	    			    ival1=Integer.parseInt(numb1);
	    			    ival2=Integer.parseInt(numb2);
	    			    str=str.replace(allprocess, Integer.toString(ival1/ival2));
	    			    i=0;
	    		    }
	    	    }
	        }
	    }
	    j=1;
	    k=1;
	    //if there are any subtraction or addition,
	    //find the numbers before symbols and after symbols 
	    for(int i=0; i<str.length(); i++) {
	    	if(str.charAt(i)=='+' || str.charAt(i)=='-') {
	    		while(!(str.charAt(i-j)=='(')) {
	    			j++;
	    		}
	    		numb1=str.substring(i-j+1,i);	    	
	    		while(!(str.charAt(i+k)=='+'|| str.charAt(i+k)=='-' || str.charAt(i+k)==')')) {
	    			k++;
	    		}
	    		numb2=str.substring(i+1,i+k);
	    		allprocess=str.substring(i-j+1,i+k);
	    		j=1;
	    		k=1;
	    		double dval1=0;
	    		double dval2=0;
	    		int ival1=0;
	    		int ival2=0;
	    		//if there are addition, do it
	    		if(str.charAt(i)=='+') {
	    		    if(numb1.contains(".") || numb2.contains(".")) {
	    			    dval1=Double.parseDouble(numb1);
	    			    dval2=Double.parseDouble(numb2);
	    			    str=str.replace(allprocess, Double.toString(dval1+dval2));
	    			    i=0;
	    		    }else {
	    			    ival1=Integer.parseInt(numb1);
	    			    ival2=Integer.parseInt(numb2);
	    			    str=str.replace(allprocess, Integer.toString(ival1+ival2));
	    			    i=0;
	    		    }
	    	    }else {
	    	    	//if there are subtraction, do it
	    	    	if(numb1.contains(".") || numb2.contains(".")) {
	    			    dval1=Double.parseDouble(numb1);
	    			    dval2=Double.parseDouble(numb2);
	    			    str=str.replace(allprocess, Double.toString(dval1-dval2));
	    			    i=0;
	    		    }else {
	    			    ival1=Integer.parseInt(numb1);
	    			    ival2=Integer.parseInt(numb2);
	    			    str=str.replace(allprocess, Integer.toString(ival1-ival2));
	    			    i=0;
	    		    }
	    	    }
	        }
	    }
	    //delete the parentheses
	    for(int i=0; i<str.length(); i++) {
	    	if(!(str.charAt(i)=='(' || str.charAt(i)==')')) {
	    		newstr+=str.charAt(i);
	    	}
	    }
	    return newstr;
	}
	public static String noblank(String str){
		//if there are any blanks, delete them
		String noblank="";
		for(int i=0;i<=str.length()-1;i++) {
			if(str.charAt(i)==' ') {
				noblank=noblank;
			}else {
				noblank=noblank+str.charAt(i);
			}
		}
		return noblank;
	}
	public static String value(String str) {
		//return the value of variables if they are integer
		String value=str.substring(str.indexOf('=')+1,str.indexOf(';'));
		return value;
	}
	public static String value2(String str) {
		//return the value of variables if they are double
		String value="";
		if(str.contains(".")) {
			value=str.substring(str.indexOf('=')+1,str.indexOf(';'));
		}else {
			value=str.substring(str.indexOf('=')+1,str.indexOf(';'))+".0";
		}
		return value;
	}
	public static String valuename(String str) {
		//return the variables' name
		String s="";
		if(str.contains("int")) {
			s=str.substring(str.indexOf('t')+1,str.indexOf('='));
		}else {
			s=str.substring(str.indexOf('e')+1,str.indexOf('='));
		}
		return s;
	}
	public static String replacement(String str1,String str2) {
		//replace values on the value names in the fourth line
		if(str2.charAt(0)=='i') {
		    str1=str1.replace(valuename(noblank(str2)), value(noblank(str2)));
		}else {
			str1=str1.replace(valuename(noblank(str2)), value2(noblank(str2)));
		}
		return str1;
	}
}

