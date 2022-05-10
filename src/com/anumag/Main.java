package com.anumag;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long[] numbers = new long[3];
        int t;
        int k;
        long g = 0;
        long n = 0;
        ArrayList<Long> X = new ArrayList<>();

        MillerRabin mr = new MillerRabin();

        System.out.println("Введіть t:");
        t = scan.nextInt();
        System.out.println("Введіть k:");
        k = scan.nextInt();

        System.out.println("Згенеровані числа:");
        for (int i = 0; i < 3; i++) {
            long time = System.currentTimeMillis();
            while (true) {
                long temp;
                int multiply = 1;

                if (k >= 9 && k <= 13) {
                    multiply = 100_000;
                }
                if (k >= 14 && k <= 19) {
                    multiply = 1_000_000;
                }
                if (k >= 20 && k <= 24) {
                    multiply = 10_000_000;
                }

                temp = (long) (Math.random() * multiply);
                if (mr.isPrime(temp, t)) {
                    String binary = Long.toBinaryString(temp);
                    if (binary.length() == k) {
                        numbers[i] = temp;
                        System.out.println(numbers[i]);
                        break;
                    }
                }
            }
            System.out.println("Час генерації: " + (System.currentTimeMillis() - time) + " мілісекунд(и)");
        }
        for (int i = 0; i < 3; i++) {
            if (numbers[i] > n) {
                n = numbers[i];
            }
        }
        for (int i = 0; i < 3; i++) {
            if (numbers[i] != n) {
                X.add(numbers[i]);
            }
        }

        System.out.println("Випадкове велике просте число: " + n);
        g = findPrimitive((int) n);
        System.out.println("Первісний корінь за модулем n: " + g);

        System.out.println("Великі прості секретні числа(Xa та Xb): [" + X.get(0) + ", " + X.get(1) + "]");

        long Ya = calculateY(g, X.get(0), n);
        long Yb = calculateY(g, X.get(1), n);
        long K1 = calculateKey(Yb, X.get(0), n);
        long K2 = calculateKey(Ya, X.get(1), n);

        System.out.println("Ya=" + Ya + "\nYb=" + Yb + "\nK1=" + K1 + "\nK2=" + K2);
    }

    public static long calculateY(long g, long X, long n) {
        BigInteger G = BigInteger.valueOf(g);
        BigInteger y = G.pow((int) X);
        BigInteger N = BigInteger.valueOf(n);
        y = y.mod(N);
        return y.longValue();
    }

    public static long calculateKey(long Y, long X, long n) {
        BigInteger y = BigInteger.valueOf(Y);
        BigInteger K = y.pow((int) X);
        BigInteger N = BigInteger.valueOf(n);
        K = K.mod(N);
        return K.longValue();
    }

    static int power(int x, int y, int p) {
        int res = 1;

        x = x % p;

        while (y > 0) {
            if (y % 2 == 1) {
                res = (res * x) % p;
            }
            y = y >> 1;
            x = (x * x) % p;
        }
        return res;
    }

    static void findPrimeFactors(HashSet<Integer> s, int n) {
        while (n % 2 == 0) {
            s.add(2);
            n = n / 2;
        }
        for (int i = 3; i <= Math.sqrt(n); i = i + 2) {
            while (n % i == 0) {
                s.add(i);
                n = n / i;
            }
        }
        if (n > 2) {
            s.add(n);
        }
    }

    static int findPrimitive(int n) {
        HashSet<Integer> s = new HashSet<>();
        MillerRabin mr = new MillerRabin();
        if (!mr.isPrime(n, 2)) {
            return -1;
        }

        int phi = n - 1;
        findPrimeFactors(s, phi);
        for (int r = 2; r <= phi; r++) {
            boolean flag = false;
            for (Integer a : s) {
                if (power(r, phi / (a), n) == 1) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return r;
            }
        }
        return -1;
    }
}
