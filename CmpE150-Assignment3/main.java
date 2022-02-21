import java.util.Scanner;
import java.io.*;
public class FO2018400201 {
	public static void main(String[] args)throws FileNotFoundException {
		int mode =Integer.parseInt(args[0]); //MODE FOR ARGUMENT
		String inputFile= args[1]; //NAME OF INPUT FILE
		File ppmfile = new File(inputFile);
		Scanner input=new Scanner(ppmfile);
		String ppmtype =input.next(); //P3
		int columns=input.nextInt(); //COLUMN NUMBER OF IMAGE
		int rows=input.nextInt(); //ROW NUMBER OF IMAGE
		int highestvalue=input.nextInt(); //HIGHEST VALUE IN PIXELS
		int ppmarray[][][]=new int[rows][columns][3]; //3D ARRAY
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				for(int k=0;k<=2;k++) {
					ppmarray[i][j][k]=input.nextInt(); //TAKING ALL VALUES INTO 3D ARRAY
				}
			}
		}
		boolean filledarray[][][]=new boolean[rows][columns][3]; //IT IS FOR ISFILLED METHOD
		if(mode==0) {
			outputppm(ppmtype,columns,rows,highestvalue,ppmarray); //FIRST PART OF PROJECT
		}else if(mode==1) {
			blackandwhiteppm(ppmtype,columns,rows,highestvalue,ppmarray);  //SECOND PART OF PROJECT
		}else if(mode==2) {
			String filter= args[2]; //FÝLTER NAME
			File filterfile=new File(filter);
			Scanner filterinput=new Scanner(filterfile);
			String dimensions=filterinput.next(); //COLUMNS AND ROWS OF FILTER
			int fcolumns=Integer.parseInt(dimensions.substring(0,dimensions.indexOf('x'))); //COLUMNS OF FILTER
			int frows=Integer.parseInt(dimensions.substring(dimensions.indexOf('x')+1)); //ROWS OF FILTER
			int filterarray[][]=new int[frows][fcolumns]; //2D FILTER ARRAY
			for(int i=0;i<frows;i++) {
				for(int j=0;j<fcolumns;j++) {
					filterarray[i][j]=filterinput.nextInt(); //FILTER VALUES
				}
			}
			convolutionppm(ppmtype,columns,rows,highestvalue,ppmarray,fcolumns,frows,filterarray); //THIRD PART OF PROJECT
		}else if(mode==3) {
			int range =Integer.parseInt(args[2]); //RANGE VALUE FOR QUANTIZATION
			for(int k=0;k<3;k++) {
				for(int i=0;i<rows;i++) {
					for(int j=0;j<columns;j++) {
						quantizedppm(columns,rows,ppmarray,range,i,j,k,filledarray); //LAST PART OF PROJECT
					}
				}
			}
			PrintStream output=new PrintStream(new File("quantized.ppm"));
			output.println(ppmtype);
			output.println(columns+" "+rows);
			output.println(highestvalue);
			for(int i=0;i<rows;i++) {
				for(int j=0;j<columns;j++) {
					for(int k=0;k<=2;k++) {
						output.print(ppmarray[i][j][k]+" "); //WRITING LAST PART INTO A FILE
					}
					output.print("\t");
				}
				output.println();
			}
		}
	}
	public static void outputppm(String ppmtype, int columns,int rows,int highestvalue,int ppmarray[][][])throws FileNotFoundException {
		PrintStream output = new PrintStream(new File("output.ppm"));
		output.println(ppmtype);
		output.println(columns+" "+rows);
		output.println(highestvalue);
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				for(int k=0;k<=2;k++) {
					output.print(ppmarray[i][j][k]+" "); //WRITING SAME ARRAY INTO A FILE
				}
				output.print("\t");
			}
			output.println();
		}
	}
	public static void blackandwhiteppm(String ppmtype, int columns,int rows,int highestvalue,int ppmarray[][][])throws FileNotFoundException{
		int ppmarray2[][][]=new int[rows][columns][3]; //FOR BLACK AND WHITE VERSION 3D ARRAY
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				for(int k=0;k<=2;k++) {
					ppmarray2[i][j][k]=(ppmarray[i][j][0]+ppmarray[i][j][1]+ppmarray[i][j][2])/3; //TAKING AVERAGE
				}
			}
		}
		PrintStream output = new PrintStream(new File("black-and-white.ppm"));
		output.println(ppmtype);
		output.println(columns+" "+rows);
		output.println(highestvalue);
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				for(int k=0;k<=2;k++) {
					output.print(ppmarray2[i][j][k]+" "); //WRITING NEW ARRAY INTO A FILE
				}
				output.print("\t");
			}
			output.println();
		}
	}
	public static void convolutionppm(String ppmtype,int columns,int rows,int  highestvalue,int ppmarray[][][],int fcolumns,int frows,int filterarray[][])throws FileNotFoundException {
		int ppmarray2[][][]=new int[rows-(frows+1)/2][columns-(fcolumns+1)/2][3]; //NEW 3D ARRAY FOR CONVOLUTION
		int ppmarray3[][][]=new int[rows-(frows+1)/2][columns-(fcolumns+1)/2][3]; //NEW 3D ARRAY FOR BLACK AND WHITE
		for(int i=0;i<rows-(frows+1)/2;i++) {
			for(int j=0;j<columns-(fcolumns+1)/2;j++) {
				for(int k=0;k<=2;k++) {
					for(int m=0;m<=(frows+1)/2;m++) {
						for(int n=0;n<=(fcolumns+1)/2;n++) {
							ppmarray2[i][j][k]+=ppmarray[i+m][j+n][k]*filterarray[m][n]; //MULTIPLYING FILTER VALUES FOR EACH PIXEL
						}
					}
				}
			}
		}
		for(int i=0;i<rows-(frows+1)/2;i++) {
			for(int j=0;j<columns-(fcolumns+1)/2;j++) {
				for(int k=0;k<=2;k++) {
					if(ppmarray2[i][j][k]<0) {
						ppmarray2[i][j][k]=0; //FOR NEGATIVE VALUES
					}
				}
			}
		}
		for(int i=0;i<rows-(frows+1)/2;i++) {
			for(int j=0;j<columns-(fcolumns+1)/2;j++) {
				for(int k=0;k<=2;k++) {
					if(ppmarray2[i][j][k]>highestvalue) {
						ppmarray2[i][j][k]=highestvalue; //FOR VALUES HIGHER THAN THE HIGHEST VALUE
					}
				}
			}
		}
		for(int i=0;i<rows-(frows+1)/2;i++) {
			for(int j=0;j<columns-(fcolumns+1)/2;j++) {
				for(int k=0;k<=2;k++) {
					ppmarray3[i][j][k]=(ppmarray2[i][j][0]+ppmarray2[i][j][1]+ppmarray2[i][j][2])/3; //TAKING AVERAGE FOR MAKING BLACK AND WHITE
				}
			}
		}
		PrintStream output = new PrintStream(new File("convolution.ppm"));
		output.println(ppmtype);
		output.println((columns-((fcolumns+1)/2))+" "+(rows-((frows+1)/2)));
		output.println(highestvalue);
		for(int i=0;i<rows-(frows+1)/2;i++) {
			for(int j=0;j<columns-(fcolumns+1)/2;j++) {
				for(int k=0;k<=2;k++) {
					output.print(ppmarray3[i][j][k]+" "); //WRITING NEW ARRAY INTO A FILE
				}
				output.print("\t");
			}
			output.println();
		}	
	}
	public static boolean isSafe(int i,int j,int k,int rows,int columns) {
		if(k<=2&&k>=0&&i>=0&&i<rows&&j>=0&&j<columns) { //FOR FINDING WHETHER THE VALUES ARE IN THE BORDER OR NOT
			return true;
		}else {
			return false;
		}
	}
	public static boolean isFilled(int i,int j,int k,boolean filledarray[][][]) {
		if(filledarray[i][j][k]==false) { //FOR FINDING WHETHER THE VALUES ARE CHANGED BEFORE OR NOT
			filledarray[i][j][k]=true;
			return true;
		}else {
			return false;
		}
	}
	public static void quantizedppm(int columns,int rows,int ppmarray[][][],int range,int i,int j,int k,boolean filledarray[][][])throws FileNotFoundException {
		if(isFilled(i,j,k,filledarray)) {
			if(isSafe(i+1,j,k,rows,columns)) {
				if(ppmarray[i+1][j][k]<=ppmarray[i][j][k]+range&&ppmarray[i+1][j][k]>=ppmarray[i][j][k]-range) { //RIGHT NEIGHBOR
					ppmarray[i+1][j][k]=ppmarray[i][j][k];
					quantizedppm(columns,rows,ppmarray,range,i+1,j,k,filledarray);
				}
			}
			if(isSafe(i-1,j,k,rows,columns)) {
				if(ppmarray[i-1][j][k]<=ppmarray[i][j][k]+range&&ppmarray[i-1][j][k]>=ppmarray[i][j][k]-range) { //LEFT NEIGHBOR
					ppmarray[i-1][j][k]=ppmarray[i][j][k];
					quantizedppm(columns,rows,ppmarray,range,i-1,j,k,filledarray);
				}
			}
			if(isSafe(i,j+1,k,rows,columns)) {
				if(ppmarray[i][j+1][k]<=ppmarray[i][j][k]+range&&ppmarray[i][j+1][k]>=ppmarray[i][j][k]-range) { //TOP NEIGHBOR
					ppmarray[i][j+1][k]=ppmarray[i][j][k];
					quantizedppm(columns,rows,ppmarray,range,i,j+1,k,filledarray);
				}
			}
			if(isSafe(i,j-1,k,rows,columns)) {
				if(ppmarray[i][j-1][k]<=ppmarray[i][j][k]+range&&ppmarray[i][j-1][k]>=ppmarray[i][j][k]-range) { //BOTTOM NEIGHBOR
					ppmarray[i][j-1][k]=ppmarray[i][j][k];
					quantizedppm(columns,rows,ppmarray,range,i,j-1,k,filledarray);
				}
			}
			if(isSafe(i,j,k+1,rows,columns)) {
				if(ppmarray[i][j][k+1]<=ppmarray[i][j][k]+range&&ppmarray[i][j][k+1]>=ppmarray[i][j][k]-range) { //FRONT NEIGHBOR
					ppmarray[i][j][k+1]=ppmarray[i][j][k];
					quantizedppm(columns,rows,ppmarray,range,i,j,k+1,filledarray);
				}
			}
			if(isSafe(i,j,k-1,rows,columns)) {
				if(ppmarray[i][j][k-1]<=ppmarray[i][j][k]+range&&ppmarray[i][j][k-1]>=ppmarray[i][j][k]-range) { //BACK NEIGHBOR
					ppmarray[i][j][k-1]=ppmarray[i][j][k];
					quantizedppm(columns,rows,ppmarray,range,i,j,k-1,filledarray);
				}
			}
		}
	}
}
