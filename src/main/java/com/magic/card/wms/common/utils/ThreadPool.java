package com.magic.card.wms.common.utils;

import com.sun.jmx.snmp.tasks.ThreadService;
import jdk.nashorn.internal.objects.annotations.Function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * com.magic.card.wms.common.utils
 *
 * @author : Mr.Zhang
 * @e.mail : mr.zy883@gmail.com
 * @date : 2019/8/3 10:14
 * @since : 1.0.0
 */
public class ThreadPool {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Function
    public static void excutor(Runnable runnable) {
        executorService.execute(runnable);
    }
}
