package com.hss01248.plugindemo;

import android.util.Log;

public class ASMFerDemo {

    public void costTime() {
        long startTime = System.currentTimeMillis();
        // ......
        long duration = System.currentTimeMillis() - startTime;
        Log.d("asm","The cost time of this method is " + duration + " ms");
    }
}
