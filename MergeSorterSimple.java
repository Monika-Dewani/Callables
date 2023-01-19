package Callables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSorterSimple implements Callable<List<Integer>> {
    private List<Integer> arrayToSort;

    public MergeSorterSimple(List<Integer> arrayToSort) {
        this.arrayToSort= arrayToSort;
    }

    @Override
    public List<Integer> call() throws Exception {
        if(arrayToSort.size()<=1)
            return arrayToSort;

        //Divide array
        int mid= arrayToSort.size()/2;
        List<Integer> leftArray= new ArrayList<>();
        List<Integer> rightArray= new ArrayList<>();

        for(int i=0;i<mid;i++){
            leftArray.add(arrayToSort.get(i));
        }
        for(int i=mid;i<arrayToSort.size();i++){
            rightArray.add(arrayToSort.get(i));
        }

        // Now the time to sort the array
        // by calling function call() and passing array to sort;
        // as we cant pass anything inside call how should we then
        // pass our array to sort
        //so call() can be called by creating an object of its class
        // i.e. mergerSorterSimple;

        ExecutorService executorService= Executors.newCachedThreadPool();
        MergeSorterSimple leftMergeSort = new MergeSorterSimple(leftArray);
        MergeSorterSimple rightMergeSort= new MergeSorterSimple(rightArray);


        /// Use Executor service here to run different call in different threads;

        Future<List<Integer>> leftSortedArrayFuture=executorService.submit(leftMergeSort);
        Future<List<Integer>> rightSortedArrayFuture=executorService.submit(rightMergeSort);
        List<Integer> leftSortedArray= leftSortedArrayFuture.get();
        List<Integer> rightSortedArray= rightSortedArrayFuture.get();
      //  List<Integer> leftSortedArray= leftMergeSort.call();
      //  List<Integer> rightSortedArray= rightMergeSort.call();

        // merge both sorted arrays;


        List<Integer> finalSortedArray = new ArrayList<>();
        int i=0;
        int j=0;
        while(i<leftSortedArray.size()&& j<rightSortedArray.size()){
            if(leftSortedArray.get(i)<rightSortedArray.get(j)){
                finalSortedArray.add(leftSortedArray.get(i));
                i++;
            }
            else{
                finalSortedArray.add(rightSortedArray.get(j));
                j++;
            }

        }

        while(i<leftSortedArray.size()){
            finalSortedArray.add(leftSortedArray.get(i));
            i++;
        }

        while(j<rightSortedArray.size()){
            finalSortedArray.add(rightSortedArray.get(j));
            j++;
        }

        executorService.shutdown();
        return finalSortedArray;
    }
}
