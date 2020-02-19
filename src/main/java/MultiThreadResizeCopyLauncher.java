import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MultiThreadResizeCopyLauncher {


    public static void resizedCopyLaunch(int threadsCount, File[] files, int newWidth, String dstFolder) {

        ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue<>();

        for(File file: files) { queue.add(file);}

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++)  {

            String name = "thread # " + ((Integer) (i +1)).toString();
            long start =  System.currentTimeMillis();
            threads.add(new ImageResizer(queue, newWidth, dstFolder, start, name));
            threads.get(i).start();

        }


    }



}

