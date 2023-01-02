import calculator.MultiThreadCalculator;
import calculator.SingleThreadCalculator;
import callable.CallableTarget;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import runnable.Target;
import thread.MyThread;
import utils.ThreadUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class TestThread
{
    private static final Logger log = LoggerFactory.getLogger(TestThread.class);

    @Test
    public void testMyThread()
    {
        // 通过继承 Thread 重写 run() 方法实现多线程
        log.debug("Statement before new thread starts");
        new MyThread().start();
        log.debug("Statement after new thread starts");
    }

    @Test
    public void testTarget()
    {
        // 通过实现 Runnable 接口实现多线程
        log.debug("Statement before new thread starts");
        new Thread(new Target()).start();
        log.debug("Statement after new thread starts");
    }

    @Test
    public void testLambda()
    {
        // Runnable 接口实际上是一个函数式接口, 所以可以通过 lambda 表达式创建匿名内部类
        log.debug("Statement before new thread starts");
        new Thread(() -> log.debug("Message from Lambda")).start();
        log.debug("Statement after new thread starts");
    }

    @Test
    public void testCallableTarget() throws ExecutionException, InterruptedException
    {
        // 通过实现 Callable 接口实现多线程
        CallableTarget target = new CallableTarget();

        // FutureTask 实现了 RunnableFuture, RunnableFuture 实现了 Runnable
        // FutureTask 的 run 方法会调用 Callable 的 call 方法, 并把 call 方法的返回值保存
        // 在 FutureTask 的 outcome 成员变量中
        FutureTask<Integer> task = new FutureTask<>(target);

        log.debug("Statement before new thread starts");
        new Thread(task).start();
        Integer returnValue = task.get();
        log.debug("The return value is [{}]", returnValue);
        log.debug("Statement after receiving the return value");
    }

    @Test
    public void testMultiThreadCalculator()
    {
        long startTime = System.currentTimeMillis();

        // 通过多线程计算累加和
        long from = 1, to = 1000000000, interval = 100000000;
        MultiThreadCalculator calculator = new MultiThreadCalculator(from, to, interval);
        long result = calculator.getResult();

        long endTime = System.currentTimeMillis();
        log.debug("The sum from {} to {} is {}, use [{}] ms", from, to, result, endTime - startTime);
    }

    @Test
    public void testSingleThreadCalculator()
    {
        long startTime = System.currentTimeMillis();

        long from = 1, to = 1000000000;
        SingleThreadCalculator calculator = new SingleThreadCalculator(from, to);
        long result = calculator.getResult();

        long endTime = System.currentTimeMillis();
        log.debug("The sum from {} to {} is {}, use [{}] ms", from, to, result, endTime - startTime);
    }

    @Test
    public void testJoin()
    {
        // 下面的例子，ti.join() 方法用于将 t1 的父线程（main）变为 waiting 状态，
        // 只有当 t1 线程死亡后，main 线程才会继续执行

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++)
            {
                System.out.println("线程 t1 ...");
                ThreadUtils.sleep(1000);
            }
        });

        t1.setName("t1");
        t1.start();
        ThreadUtils.join(t1);
        // t1 执行 join 方法后 main 线程被阻塞，一直处于 waiting 状态 ...

        for (int i = 0; i < 5; i++)
        {
            System.out.println("线程 main ...");
            ThreadUtils.sleep(1000);
        }
    }
}
