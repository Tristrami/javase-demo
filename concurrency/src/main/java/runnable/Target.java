package runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Target implements Runnable
{
    private static final Logger log = LoggerFactory.getLogger(Target.class);

    @Override
    public void run()
    {
        log.debug("Message from Target");
    }
}
