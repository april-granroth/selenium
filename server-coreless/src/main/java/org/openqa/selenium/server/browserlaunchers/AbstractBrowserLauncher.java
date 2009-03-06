package org.openqa.selenium.server.browserlaunchers;


import org.openqa.selenium.server.BrowserConfigurationOptions;
import org.openqa.selenium.server.RemoteControlConfiguration;

/**
 * Runs the specified command path to start the browser, and kills the process to quit.
 *
 * @author Paul Hammant
 * @version $Revision: 189 $
 */
public abstract class AbstractBrowserLauncher implements BrowserLauncher {

    protected String sessionId;
    private RemoteControlConfiguration configuration;
    protected BrowserConfigurationOptions browserConfigurationOptions;

    public AbstractBrowserLauncher(String sessionId, RemoteControlConfiguration configuration, BrowserConfigurationOptions browserOptions) {
        this.sessionId = sessionId;
        this.configuration = configuration;
        this.browserConfigurationOptions = browserOptions;
    }

    public void launchHTMLSuite(String suiteUrl, String browserURL) {
        launch(LauncherUtils.getDefaultHTMLSuiteUrl(browserURL, suiteUrl, browserConfigurationOptions.isMultiWindow(), 0));
    }

    public void launchRemoteSession(String browserURL) {
        boolean browserSideLog = browserConfigurationOptions.is("browserSideLog");
        if (browserSideLog) {
            configuration.getSeleniumServer().generateSSLCertsForLoggingHosts();
        }
        launch(LauncherUtils.getDefaultRemoteSessionUrl(browserURL, sessionId, browserConfigurationOptions.isMultiWindow(), 0, browserSideLog));
    }

    protected abstract void launch(String url);
    
    public RemoteControlConfiguration getConfiguration() {
        return configuration;
    }

    public int getPort() {
        return configuration.getPortDriversShouldContact();
    }

}
