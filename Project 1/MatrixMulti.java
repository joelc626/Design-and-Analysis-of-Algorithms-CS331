/* Author: Joel Castro
 * Class: CS 331: Design and Analysis of Algorithms
 * Project 1 - Matrix Multiplication
 *
 * Purpose: Classical Matrix Multiplication, Divide-and-conquer
 *  Matrix Multiplication, and Strassen's Matrix Multiplication.
 *
 * In order to obtain more accurate results, the algorithms should be
 * tested with the same matrices of different sizes many times.
 * The total time spent is then divided by the number of times the algorithm
 * is performed to obtain the time taken to solve the given instance.
 */

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

public class MatrixMulti {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        Random rand = new Random();
        DecimalFormat df = new DecimalFormat("#,###");
        long startTime, endTime, totalTime;
        int x, size;
        
        System.out.print("Using n = 2^x, enter a value for x to cretae nxn matrices: ");
        x = kb.nextInt();
        
        size = (int)(Math.pow(2.0, x));
        
        int [][] A = new int[size][size];
        int [][] B = new int[size][size];
        int [][] C = new int[size][size];
        
        //Randomly fill in Matrix A with 0's and 1's
        for (int row = 0; row < size; row++)
            for (int column = 0; column < size; column++)
                A[row][column] = rand.nextInt(2) + 0;
        
        //Randomly fill in Matrix B with 0's and 1's
        for (int row = 0; row < size; row++)
            for (int column = 0; column < size; column++)
                B[row][column] = rand.nextInt(2) + 0;
        
        //Start timer to multiply Matric C
        startTime = System.nanoTime();
        
        //Classical Matrix Multiplication method
        C = multiply(A, B);
        
        //End timer to multiply Matric C
        endTime = System.nanoTime();
        
        //Print time
        totalTime = (endTime - startTime);
        System.out.println("\nTotal time for Classical Matrix Multiplication"
                           + " method: " + df.format(totalTime));
        
        //reset C
        C = new int [size][size];
        int divS = size / 2;
        
        //Divide-and-Conquer Matrix Multiplication method
        //Divide Matrix A into 4 submatrices
        int [][] a11 = new int[divS][divS];
        int [][] a12 = new int[divS][divS];
        int [][] a21 = new int[divS][divS];
        int [][] a22 = new int[divS][divS];
        
        //Divide Matrix B into 4 submatrices
        int [][] b11 = new int[divS][divS];
        int [][] b12 = new int[divS][divS];
        int [][] b21 = new int[divS][divS];
        int [][] b22 = new int[divS][divS];
        
        startTime = System.nanoTime();
        
        //fill Upper Left divided matrix partition
        for(int i = 0; i < divS; i++) //rows
            for(int j = 0; j < divS; j++) //columns
                a11[i][j] = A[i][j];
        
        //fill Upper Right divided matrix partition
        for(int i = 0; i < divS; i++)
            for(int j = divS; j < size; j++)
                a12[i][j - divS] = A[i][j];
        
        //fill Lower Left divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = 0; j < divS; j++)
                a21[i - divS][j] = A[i][j];
        
        //fill Lower Right divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = divS; j < size; j++)
                a22[i - divS][j - divS] = A[i][j];
        
        //fill Upper Left divided matrix partition
        for (int i = 0; i < divS; i++)
            for (int j = 0; j < divS; j++)
                b11[i][j] = B[i][j];
        
        //fill Upper Right divided matrix partition
        for (int i = 0; i < divS; i++)
            for (int j = divS; j < size; j++)
                b12[i][j - divS] = B[i][j];
        
        //fill Lower Left divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = 0; j < divS; j++)
                b21[i - divS][j] = B[i][j];
        
        //fill Lower Right divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = divS; j < size; j++)
                b22[i - divS][j - divS] = B[i][j];
        
        int [][] c11 = add(multiply(a11, b11), multiply(a12, b21));
        int [][] c12 = add(multiply(a11, b12), multiply(a12, b22));
        int [][] c21 = add(multiply(a21, b11), multiply(a22, b21));
        int [][] c22 = add(multiply(a21, b12), multiply(a22, b22));
        
        endTime = System.nanoTime();
        
        totalTime = (endTime - startTime);
        
        System.out.println("\nTotal time for Divide-and-Conquer Matrix"
                           + " Multiplication method: " + df.format(totalTime));
        
        //Strassen's Matrix Multiplication
        //reset C
        C = new int [size][size];
        
        //Divide Matrix A into 4 submatrices
        a11 = new int[divS][divS];
        a12 = new int[divS][divS];
        a21 = new int[divS][divS];
        a22 = new int[divS][divS];
        
        //Divide Matrix B into 4 submatrices
        b11 = new int[divS][divS];
        b12 = new int[divS][divS];
        b21 = new int[divS][divS];
        b22 = new int[divS][divS];
        
        startTime = System.nanoTime();
        
        //fill Upper Left divided matrix partition
        for(int i = 0; i < divS; i++) //rows
            for(int j = 0; j < divS; j++) //columns
                a11[i][j] = A[i][j];
        
        //fill Upper Right divided matrix partition
        for(int i = 0; i < divS; i++) //rows
            for(int j = divS; j < size; j++) //columns
                a12[i][j - divS] = A[i][j];
        
        //fill Lower Left divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = 0; j < divS; j++)
                a21[i - divS][j] = A[i][j];
        
        //fill Lower Right divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = divS; j < size; j++)
                a22[i - divS][j - divS] = A[i][j];
        
        //fill Upper Left divided matrix partition
        for (int i = 0; i < divS; i++)
            for (int j = 0; j < divS; j++)
                b11[i][j] = B[i][j];
        
        //fill Upper Right divided matrix partition
        for (int i = 0; i < divS; i++)
            for (int j = divS; j < size; j++)
                b12[i][j - divS] = B[i][j];
        
        //fill Lower Left divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = 0; j < divS; j++)
                b21[i - divS][j] = B[i][j];
        
        //fill Lower Right divided matrix partition
        for (int i = divS; i < size; i++)
            for (int j = divS; j < size; j++)
                b22[i - divS][j - divS] = B[i][j];
        
        int [][] P = multiply(add(a11, a22), add(b11, b22));
        int [][] Q = multiply(add(a21, a22), b11);
        int [][] R = multiply(a11, subtract(b12, b22));
        int [][] S = multiply(a22, subtract(b21, b11));
        int [][] T = multiply(add(a11, a12), b22);
        int [][] U = multiply(subtract(a21, a11), add(b11, b12));
        int [][] V = multiply(subtract(a12, a22), add(b21, b22));
        
        c11 = add(subtract(add(P, S), T), V); //P+S-T+V
        c12 = add(R, T); //R+T
        c21 = add(Q, S); //Q+S
        c22 = add(subtract(add(P, R), Q), U); //P+R-Q+U
        
        endTime = System.nanoTime();
        
        totalTime = (endTime - startTime);
        
        System.out.println("\nTotal time for Strassen's Matrix Multiplication: "
                           + df.format(totalTime));
    }
    
    public static int [][] add(int a[][], int[][] b) {
        int n = a.length;
        int[][] temp = new int[n][n];
        
        for (int i = 0; i < n; i++) // aRow
            for (int j = 0; j < n; j++) // bColumn
                temp[i][j] = a[i][j] + b[i][j];
        return temp;
    }
    
    public static int [][] subtract(int [][] a, int [][] b) {
        int n = a.length;
        int [][] temp = new int[n][n];
        
        for (int i = 0; i < n; i++) // aRow
            for (int j = 0; j < n; j++) // bColumn
                temp[i][j] = a[i][j] - b[i][j];
        return temp;
    }
    
    public static int [][] multiply(int [][] a, int [][] b) {
        int n = a.length;
        int [][] temp = new int[n][n];
        
        for (int i = 0; i < n; i++) // aRow
            for (int j = 0; j < n; j++) // bColumn
                for (int k = 0; k < n; k++) // aColumn
                    temp[i][j] += a[i][k] * b[k][j];
        return temp;
    }
}
