package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyThread extends Thread
{
    private static final Logger log = LoggerFactory.getLogger(MyThread.class);

    @Override
    public void run()
    {
        log.debug("Message from MyThread");
    }
}
