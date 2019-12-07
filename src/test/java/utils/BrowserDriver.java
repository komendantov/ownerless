package utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class BrowserDriver {
    private static EventFiringWebDriver mDriver;

    public synchronized static EventFiringWebDriver getCurrentDriver() {
        if (mDriver == null) {
            try {
                mDriver = new EventFiringWebDriver(new ChromeDriver());
            } finally {
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
        return mDriver;
    }

    public static void close() {
        try {
            getCurrentDriver().quit();
            mDriver = null;
            //  LOGGER.info("closing the browser");
        } catch (UnreachableBrowserException e) {
            //   LOGGER.info("cannot close browser: unreachable browser");
        }
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            //  LOGGER.info("Closing the browser");
            close();
        }
    }
}
