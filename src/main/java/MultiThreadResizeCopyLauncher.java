import java.io.File;

public class MultiThreadResizeCopyLauncher {


    public static void resizedCopyLaunch(int threadsCount, File[] files, int newWidth, String dstFolder) {

        long start =  System.currentTimeMillis();

        int filesDivideIndexes [] = new int [threadsCount]; // индексы разделения файлов, 4 потока = 4 индекса 0 1 2 3

        filesDivideIndexes [0] = 0;



        if (files.length % threadsCount == 0) {  // количество файлов кратно количеству потоков / ядер

            int step = files.length/threadsCount;  // шаг = сколько файлов копируется в 1 потоке

            for (int j = 1; j < filesDivideIndexes.length; j++) {  // заполнение индексов = файлы по которым делить потоки

                filesDivideIndexes[j] = filesDivideIndexes [j-1] + step;

            }

            for (int i = 0; i < filesDivideIndexes.length; i++)  {

                int currentSplitIndex = filesDivideIndexes[i];
                File[] filesPart = new File[step]; // размер части файлов = делим колич файлов на колич потоков
                System.arraycopy(files, currentSplitIndex, filesPart, 0, step);
                ImageResizer imageResizerPart = new ImageResizer(filesPart, newWidth, dstFolder, start);
                imageResizerPart.start();

            }

        }
        else if (files.length < threadsCount) {  // количество файлов меньше количества потоков / ядер

            int threads = files.length;

            for (int s = 0; s < files.length; s++ ) {

                File[] filesPart = new File[1]; // по 1 файлу на поток
                filesPart[0] = files [s];

                ImageResizer imageResizer = new ImageResizer(filesPart, newWidth, dstFolder, start);
                imageResizer.start(); }
        }
        else {   // количество файлов не кратно количеству потоков / ядер

            int step = files.length/threadsCount;  // количество файлов для всех потоков кроме последнего

            for (int j = 1; j < filesDivideIndexes.length; j++) {   // заполнение индексов = файлы по которым делить потоки

                filesDivideIndexes[j] = filesDivideIndexes [j-1] + step;

            }

            for (int i = 0; i < filesDivideIndexes.length - 1; i++)  {  //для последнего потока отдельный алгоритм

                int copyIndex = filesDivideIndexes[i];
                File[] filesPart = new File[step];
                System.arraycopy(files, copyIndex, filesPart, 0, step);
                ImageResizer imageResizerPart = new ImageResizer(filesPart, newWidth, dstFolder, start);
                imageResizerPart.start();

            }

            // алгоритм для последнего потока, колич файлов = шаг + остаток от деления или -->

            int restFilesCount = files.length - (threadsCount-1)*step;
            File[] filesLastPart = new File[restFilesCount];
            int lastCopyIndex = filesDivideIndexes [filesDivideIndexes.length-1];
            System.arraycopy(files, lastCopyIndex, filesLastPart, 0, restFilesCount);
            ImageResizer imageResizerPart = new ImageResizer(filesLastPart, newWidth, dstFolder, start);
            imageResizerPart.start();

        }




    }



}

