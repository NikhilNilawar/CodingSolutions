package com.cp;

import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {

        Solution s = new Solution();

        //int repeat = s.repetitionsRequired("abc", "cbad");
        //int[] input = { 81, 2, 3, 4, 5, 6, 7, 1 };
//        Object[] array = s.getUnsortedList(input);
//
//        for (Object o : array) {
//            System.out.println(o);
//        }
        String first = "abcd";
        String second = "ikugi";

        int lcs = s.longestCommonSubString(first, second);
        System.out.println(lcs);
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            long id  = Thread.currentThread().getId();
            System.out.println("This is within thread : " + id);
        }
    }
}