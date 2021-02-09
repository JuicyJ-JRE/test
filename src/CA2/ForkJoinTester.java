package CA2;


/**
 *
 * @author seank This Java application allows the experimentation with the Java
 * Fork Join Framework
 */
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinTester {

    private static int[] testArray;
    private static String systemProperty = "java.util.concurrent.ForkJoinPool.common.parallelism";

    public static void main(String[] args) {

        int arraySize = 1000;
        testArray = new int[arraySize];

        System.setProperty(systemProperty, "2");


        //testArray = FindPrimesSequential(100);
        FindPrimesParallel(100);

        System.out.println(prime_array_tester(testArray));
        }

    private static int[] FindPrimesSequential(int num_of_primes) {

        long startPoint = System.nanoTime();
        int[] arr = new int[num_of_primes];
        int j = 0;

        for (int i = 0; j < arr.length; i++) {
            if (isPrime(i)){
                arr[j] = i;
//                System.out.print(arr[j]);
//                System.out.print(" ");
//                if(j%10==0) System.out.println();
                j++;
            }

        }

        long nanoRunTime = System.nanoTime() - startPoint;
        printOutcome("SEQUENTIAL        ", nanoRunTime, arr[j-1]);
        return arr;
    }

    private static double FindPrimesParallel(int num_of_primes) {

        long startPoint = System.nanoTime();
        int[] arr = new int[num_of_primes];
        FindPrimes s = new FindPrimes(arr, 0, arr.length);
        ForkJoinPool.commonPool().invoke(s);
        double sumFromArraySum = s.answer;
        long nanoRunTime = System.nanoTime() - startPoint;
        printOutcome("PARALLEL_FORK_JOIN", nanoRunTime, 69);
        return sumFromArraySum;
    }

    private static class FindPrimes extends RecursiveAction {

        static int THRESHOLD_SEQUENTIAL = 100;
        int high;
        int low;
        int[] arr;
        double answer = 0;
        int j = 0;

        FindPrimes(int[] a, int l, int h) {
            arr = a;
            high = h;
            low = l;
        }

        // We must override the abstract method compute()- - the main
        // computation performed by the task
        protected void compute() {
            if (high - low <= THRESHOLD_SEQUENTIAL) {

                int j = 0;

                for (int i = 2; j < high; i++) {
                    for (int k = 2; k <= i / 2; k++) {
                        if (isPrime(i)) {
                            arr[j] = i;
                            j++;
                        }
                    }
                }
            }
            else {
                FindPrimes left = new FindPrimes(arr, low, (high + low) / 2);
                FindPrimes right = new FindPrimes(arr, (high + low) / 2, high);
                left.fork();
                right.compute();
                left.join();

                answer = left.answer + right.answer;
            }
        }
    }

    // Function check whether a number
    // is prime or not
    private static boolean isPrime(int n){
        if(n==0 || n==1){
            return false;
        }

        int flag = 0;
        int m = n/2;

        for(int i=2;i<=m;i++){
            if(n%i==0){
                flag = 1;
                break;
            }
        }

        if(flag == 0){
            return true;
        }
        else{
            return false;
        }
    }


    private static void printOutcome(String label, long runTime, double sum) {

        System.out.printf(" \n %s process runtime was:  %8.3f milliseconds with final sum as: %8.5f \n\n", label, runTime / 1e6, sum);
    }

    private static boolean prime_array_tester(int[] arr){
        for (int elem: arr) {
            if (!isPrime(elem)){
                System.out.println(elem);
                return false;
            }
        }
        return true;
    }

}