package top.icdat.juicer.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SkyJourney
 */
@ConfigurationProperties(prefix = "juicer.data")
public class JuicerProperties {
    private boolean standalonePool = false;
    private int poolThread = Runtime.getRuntime().availableProcessors();
    private boolean interruptSave = false;
    private boolean persistence = false;
    private String[] scanPackages = {};
    private String savePath = "./JuicerSaveData.jdt";

    public boolean isStandalonePool() {
        return standalonePool;
    }

    public void setStandalonePool(boolean standalonePool) {
        this.standalonePool = standalonePool;
    }

    public boolean isInterruptSave() {
        return interruptSave;
    }

    public void setInterruptSave(boolean interruptSave) {
        this.interruptSave = interruptSave;
    }

    public boolean isPersistence() {
        return persistence;
    }

    public void setPersistence(boolean persistence) {
        this.persistence = persistence;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(String[] scanPackages) {
        this.scanPackages = scanPackages;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getPoolThread() {
        return poolThread;
    }

    public void setPoolThread(int poolThread) {
        this.poolThread = poolThread;
    }
}
