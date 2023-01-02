package callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import runnable.Target;

import java.util.concurrent.Callable;

public class CallableTarget implements Callable<Integer>
{
    private static final Logger log = LoggerFactory.getLogger(CallableTarget.class);

    @Override
    public Integer call() throws Exception
    {
        log.debug("Message from CallableTarget");
        return 1024;
    }
}
