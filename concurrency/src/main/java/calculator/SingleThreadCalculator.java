package calculator;

public class SingleThreadCalculator
{
    private long from;
    private long to;

    public SingleThreadCalculator(long from, long to)
    {
        this.from = from;
        this.to = to;
    }

    private long calculate()
    {
        long sum = 0;
        for (long i = from; i <= to; i++)
        {
            sum += i;
        }
        return sum;
    }

    public long getResult()
    {
        return calculate();
    }
}
