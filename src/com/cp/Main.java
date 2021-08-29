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
        String str = "5F3Z-2e-9-w";

        String result = s.licenseKeyFormatting(str, 4);
        System.out.println(result);
    }
}