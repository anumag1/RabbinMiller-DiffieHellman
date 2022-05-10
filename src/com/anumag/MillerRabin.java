package com.anumag;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabin {
    public boolean isPrime(long n, int iteration) {

        if (n == 0 || n == 1) {
            return false;
        }
        if (n == 2) {
            return false;
        }
        if (n % 2 == 0) {

            return false;
        }
        long s = n - 1;
        while (s % 2 == 0) {
            s /= 2;
        }
        Random rand = new Random();
        for (int i = 0; i < iteration; i++) {
            long r = Math.abs(rand.nextLong());
            long a = r % (n - 1) + 1;
            long temp = s;
            long mod = modPow(a, temp, n);
            while (temp != n - 1 && mod != 1 && mod != n - 1) {
                mod = mulMod(mod, mod, n);
                temp *= 2;
            }
            if (mod != n - 1 && temp % 2 == 0) {
                return false;
            }
        }
        return true;
    }

    public static long modPow(long a, long b, long c) {
        long res = 1;
        for (int i = 0; i < b; i++) {
            res *= a;
            res %= c;
        }
        return res % c;
    }

    public static long mulMod(long a, long b, long mod) {
        return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValue();
    }
}
