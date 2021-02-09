package CA2;

import java.util.Scanner;

//Using Recursion to calcu;ate a Fibonacci Sequence

public class FibonacciCalculator{

    public static void main(String args[]) {

        Scanner user_input = new Scanner(System.in); // used to take in input from the user

        int endNumber = user_input.nextInt(); // user defines the fib number that the program will count to

        System.out.print("Fibonacci Series of "+endNumber+" numbers: ");
        for(int i = 0; i < endNumber; i++){
            System.out.print(recursiveFibonacci(i) +" ");
        }
    }


    public static int recursiveFibonacci(int num){
        if(num == 0){
            return 0;
        }
        if(num == 1 || num == 2){
            return 1;
        }
        return recursiveFibonacci(num-2) + recursiveFibonacci(num-1);
    }


}