package CA2;

import java.math.BigInteger;

public class IterativeFactorial {

    IterativeFactorial(){};


    public BigInteger Factorial(int number){
        int i = number;
        BigInteger result = BigInteger.valueOf(1);

        while(i>1){
            result = result.multiply(BigInteger.valueOf(i--));
        }

        return result;
    }
}
