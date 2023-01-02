package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiThreadCalculator
{
    private long from;
    private long to;
    private long interval;

    public MultiThreadCalculator(long from, long to, long interval)
    {
        this.from = from;
        this.to = to;
        this.interval = interval;
    }

    private long calculate()
    {
        long sum = 0;
        List<FutureTask<Long>> tasks = new ArrayList<>();
        long count;
        if (to < interval) count = 1;
        else if ((to - from + 1) % interval != 0) count = (to - from + 1) / interval + 1;
        else count = (to - from + 1) / interval;

        for (long i = 1; i <= count; i++)
        {
            long lower, upper;
            lower = from + interval * (i - 1);
            if (to - lower < interval) upper = to;
            else upper = interval * i;

            AccumulatorCallable accumulator = new AccumulatorCallable(lower, upper);
            FutureTask<Long> task = new FutureTask<>(accumulator);
            tasks.add(task);
            new Thread(task).start();
        }

        for (FutureTask<Long> task : tasks)
        {
            try
            {
                sum += task.get();
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        return sum;
    }

    public long getResult()
    {
        return this.calculate();
    }

    static class AccumulatorCallable implements Callable<Long>
    {
        private Long from;
        private Long to;

        public AccumulatorCallable(Long from, Long to)
        {
            this.from = from;
            this.to = to;
        }

        @Override
        public Long call()
        {
            return calculate();
        }

        private Long calculate()
        {
            long sum = 0;
            for (long i = from; i <= to; i++)
            {
                sum += i;
            }
            return sum;
        }
    }
}