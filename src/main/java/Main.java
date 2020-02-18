import java.io.File;
import java.io.IOException;

public class Main
{
    public static final int newWidth = 400;

    public static void main(String[] args) throws IOException, InterruptedException {
        String srcFolder = "C:/Users/v.lesina/Desktop/src";
        String dstFolder = "C:/Users/v.lesina/Desktop/dst";

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        int threadsCount = CoresCount.getTotalLogicalCoresViaOshi();

        MultiThreadResizeCopyLauncher.resizedCopyLaunch(threadsCount, files, newWidth, dstFolder);




    } }

