import java.util.*;
import java.math.*;
/*
*   The goal is to eavesdrop on some ElGamal communications between Alice and Bob. You
*   know Alice's public key, hack out her private key (or Bob's choice of random number) and
*   use it to decrypt the message being sent to her by Bob. Then output the message.
*/

public class ElGamalDecryption {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String[] aliceString = sc.nextLine().split(" ");
        String[] bobString = sc.nextLine().split(" ");
        int [] alice = new int[3];
        int [] bob = new int [2];

        for (int i = 0; i<aliceString.length; i++) {
            alice[i] = Integer.parseInt(aliceString[i]);
        }

        for (int i = 0; i<bobString.length; i++) {
            bob[i] = Integer.parseInt(bobString[i]);
        }

        BigInteger modNum = BigInteger.valueOf(alice[0]); // p
        BigInteger genNum = BigInteger.valueOf(alice[1]); // g
        BigInteger modResult1 = BigInteger.valueOf(alice[2]); // g^x % p

        BigInteger modResult2 = BigInteger.valueOf(bob[0]); // g^y % p
        BigInteger modResult3 = BigInteger.valueOf(bob[1]); // msg.g^x^y % p

        BigInteger x = findX(modNum, genNum, modResult1);
        BigInteger msg = decrypt(modResult2, modResult3, x, modNum);
        System.out.println(msg);

    }

    /* This method finds the value of x (Alice's secret key) by trying every possible BigInteger
    * value between 0 and modNum-1
    */

    static BigInteger findX(BigInteger modNum, BigInteger genNum, BigInteger modResult1) {

        for (BigInteger e = BigInteger.valueOf(0); e.intValue()<modNum.intValue(); e = e.add(BigInteger.valueOf(1))) {

            if (genNum.modPow(e, modNum).equals(modResult1)) {
                return e;
            }
        }

        return BigInteger.valueOf(-1);
    }

    /* This method decrypts Bob's message using his cipher and Alice's private key
     */

    static BigInteger decrypt(BigInteger c1, BigInteger c2, BigInteger x, BigInteger p) {
        BigInteger c = c1.modPow(x, p);
        return c2.multiply(c.modInverse(p)).mod(p);
    }



}
