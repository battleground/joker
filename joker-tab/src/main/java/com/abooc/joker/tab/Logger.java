package com.abooc.joker.tab;

/**
 * Created by dayu on 2016/12/27.
 */

public class Logger {

    public static Out out;

    public static void build(Out o) {
        out = o;
    }


    static void out(String message) {
        if (out != null) {
            out.print(message);
        }
    }

    public interface Out {

        void print(String message);

    }
}
