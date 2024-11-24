package com.xedox.paide.utils;

public class FastTask {
    public static void execute(Executor e) {
        new Thread(e::execute).start();
    }
    
    public static interface Executor {
        void execute();
    }
}
