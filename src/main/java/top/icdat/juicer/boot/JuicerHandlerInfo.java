package top.icdat.juicer.boot;

/**
 * @author SkyJourney
 */
public class JuicerHandlerInfo {

    private String handler;
    private String previous;

    public JuicerHandlerInfo(String handler, String previous) {
        this.handler = handler;
        this.previous = previous;
    }

    public JuicerHandlerInfo() {
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
