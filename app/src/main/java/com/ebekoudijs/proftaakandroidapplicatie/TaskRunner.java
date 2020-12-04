package com.ebekoudijs.proftaakandroidapplicatie;

import android.os.Handler;
import android.os.Looper;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final static Executor executor = Executors.newSingleThreadExecutor();
    private final static Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onComplete(Optional<R> result);
    }

    public static <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        executor.execute(() -> {
            R result = null;

            try {
                result = callable.call();
            }catch (Exception ex){
                ex.printStackTrace();
            }

            R finalResult = result;
            handler.post(() -> {
                callback.onComplete(Optional.ofNullable(finalResult));
            });
        });
    }

    /* Example or usage

    IUserWrapper userWrapper = new UserWrapper();

    TaskRunner.executeAsync(() -> userWrapper.getUser("email", "password"), (user) -> {
        if (user.isPresent()){
            String userName = user.get().getName();
        }else{
            // Send error message
        }
    });

    */

}