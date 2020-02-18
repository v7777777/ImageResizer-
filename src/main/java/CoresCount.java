
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class CoresCount {


    private static SystemInfo systemInfo = new SystemInfo();
    private static HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
    private static CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();

    public static int getTotalPhysicalCoresViaOshi() { return centralProcessor.getPhysicalProcessorCount();}

    public static int getTotalLogicalCoresViaOshi() { return centralProcessor.getLogicalProcessorCount();}

    public static int getAvailableLogicalCores () {return Runtime.getRuntime().availableProcessors();} }


