package exception;

import javax.sound.midi.Soundbank;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: fnbory
 * @Date: 6/6/2021 下午 7:41
 * @Description: 子线程抛出异常后主线程无法感知到
 */
public class SubThread {
    public static void main(String[] args) {
        ExecutorService executor= Executors.newFixedThreadPool(10);
        int count=0;
        AtomicBoolean t=new AtomicBoolean(false);
        for(int i=0;i<10;i++){
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    t.set(true);
//                    throw new RuntimeException("wrong");
//                }
//            });
            Future<String> future=executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        int a=1/0;
                    } catch (Exception e) {
                        t.set(true);
                    }
                }
            },"success");


            // 主线程catch不到子线程异常
//            try {
//                System.out.println(future.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            catch (RuntimeException e){
//                System.out.println("eee");
//            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(t.get()) System.out.println("get wrong");

        System.out.println("over");
    }
}
