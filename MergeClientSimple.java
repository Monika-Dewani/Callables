package Callables;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeClientSimple {
    public static void main(String[] args) throws Exception {
        List<Integer> arrayToSort = List.of(7,1,9,7,7,7,5,9,3);

        ExecutorService executorService = Executors.newCachedThreadPool();
        MergeSorterSimple mergeSorter= new MergeSorterSimple(arrayToSort);
       // List<Integer> finalSortedArray= mergeSorter.call();

        Future<List<Integer>> finalSortedArray= executorService.submit(mergeSorter);
        // the above line is showing error due to the absence of FUTUre
        //reason is explained in book;
        System.out.println(finalSortedArray.get()); // get() is important when we use Future;
        executorService.shutdown();
    }
}
