package com.xedox.paide.utils;

public class FastTask {
    public static void execute(ExecuteCode ec) {
        Runnable runnable = () -> ec.execute();
        new Thread(runnable).start();
    }

    public static void execute(ExecuteCode ec, Object obj) {
        synchronized (obj) {
            Runnable runnable = () -> ec.execute();
            new Thread(runnable).start();
        }
    }

    public static interface ExecuteCode {
        void execute();
    }
}
