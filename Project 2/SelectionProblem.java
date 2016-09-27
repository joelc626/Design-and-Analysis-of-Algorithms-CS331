/* Author: Joel Castro
 * Class: CS 331: Design and Analysis of Algorithms
 * Project 2 - Selection Problem
 *
 * Purpose: Given a list of n numbers, the Selection Problem is to find
 * the kth smallest element in the list.
 *
 * Algorithms: Merge Sort, Quick Sort by recursion,
 * Quick Sort by iterative, and the MM rule.
 */

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Stack;

public class SelectionProblem {
    public static void main(String[] args) {
        long seed = 11; //seed can be changed
        Random r;
        long startTime, endTime;
        long totalTime = 0;
        DecimalFormat df = new DecimalFormat("#,###");
        
        int n = 1000; //n = 10, 50, 100, 250, 500, 1000, ...
        int k = n; //k = 1, n/4, n/2, 3n/4, and n
        int[] nums = new int[n];
        
        for (int run = 0; run < 10; run++) {
            r = new Random(seed);
            //fill array with random values between 1 and 100
            for (int i = 0; i < n; i++)
                nums[i] = r.nextInt(100) + 1;
            
            startTime = System.nanoTime();
            mergeSort(nums, 0, n - 1, k);
            endTime = System.nanoTime();
            
            totalTime += (endTime - startTime);
        }
        totalTime /= 10;
        System.out.println("\nMergeSort Method: " + df.format(totalTime));
        
        totalTime = 0; //reset total time
        for (int run = 0; run < 10; run++) {
            r = new Random(seed);
            //fill array with random values between 1 and 100
            for (int i = 0; i < n; i++)
                nums[i] = r.nextInt(100) + 1;
            
            startTime = System.nanoTime();
            quickSort_Recursive(nums, 0, n - 1, k);
            endTime = System.nanoTime();
            
            totalTime += (endTime - startTime);
        }
        totalTime /= 10;
        System.out.println("\nQuickSort By Recursive Method: " + df.format(totalTime));
        
        totalTime = 0; //reset total time
        for (int run = 0; run < 10; run++) {
            r = new Random(seed);
            //fill array with random values between 1 and 100
            for (int i = 0; i < n; i++)
                nums[i] = r.nextInt(100) + 1;
            
            startTime = System.nanoTime();
            quickSort_Iterative(nums, k);
            endTime = System.nanoTime();
            
            totalTime += (endTime - startTime);
        }
        totalTime /= 10;
        System.out.println("\nQuickSort By Iterative Method: " + df.format(totalTime));
        
        totalTime = 0; //reset total time
        for (int run = 0; run < 10; run++) {
            r = new Random(seed);
            //fill array with random values between 1 and 100
            for (int i = 0; i < n; i++)
                nums[i] = r.nextInt(100) + 1;
            
            startTime = System.nanoTime();
            recQuickSort(nums, 0, n - 1, k);
            endTime = System.nanoTime();
            
            totalTime += (endTime - startTime);
        }
        totalTime /= 10;
        System.out.println("\nMM Rule Method: " + df.format(totalTime));
        
    }
    
    static void mergeSort(int[] a, int left, int right, int k) {
        if (left < right) {
            int mid = (left + right) / 2;
            if (a[mid] == k)
                return;
            mergeSort(a, left, mid, k);
            mergeSort(a, mid + 1, right, k);
            merge(a, left, mid, right);
        }
    }
    
    static void merge(int[] a, int p, int m, int q) {
        if(a[m] <= a[m + 1])
            return; //meaning a[p...q] is already sorted
        int left = p, right = m + 1, fresh = 0;
        int[] tempArr = new int[q + 1 - p];
        
        while(left <= m && right <= q) {
            if (a[left] <= a[right])
                tempArr[fresh++] = a[left++];
            if (a[left] >= a[right])
                tempArr[fresh++] = a[right++];
        }
        
        if (left <= m)
            System.arraycopy(a, left, a, left + q - m, m + 1 - left);
        
        System.arraycopy(tempArr, 0, a, p, fresh);
    }
    
    static void quickSort_Iterative(int[] arr, int k) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);
        stack.push(arr.length);
        while (!stack.isEmpty()) {
            int end = stack.pop();
            int start = stack.pop();
            if (end - start < 2)
                continue;
            int p = start + ((end - start) / 2);
            if (arr[p] == k) //meaning k
                return;
            p = partition_Iterative(arr, p, start, end);
            
            stack.push(p + 1);
            stack.push(end);
            
            stack.push(start);
            stack.push(p);
        }
    }
    
    static int partition_Iterative(int[] arr, int p, int start, int end) {
        int l = start;
        int h = end - 2;
        int piv = arr[p];
        swap(arr, p, end - 1);
        
        while (l < h) {
            if (arr[l] < piv)
                l++;
            else if (arr[h] >= piv)
                h--;
            else
                swap(arr, l, h);
        }
        int idx = h;
        if (arr[h] < piv)
            idx++;
        swap(arr, end - 1, idx);
        return idx;
    }
    
    static void quickSort_Recursive(int[] a, int p, int q, int k) {
        Random rand = new Random();
        
        if (p < q) {
            int pivotIndex = p + rand.nextInt(q + 1 - p);
            if (a[pivotIndex] == k)
                return;
            int pos = partition_Recursive(a, p, q, pivotIndex);
            quickSort_Recursive(a, p, pos - 1, k);
            quickSort_Recursive(a, pos + 1, q, k);
        }
    }
    
    static int partition_Recursive(int[] a, int left, int right, int pivotIndex) {
        int pivotElement = a[pivotIndex];
        
        //Move pivot to the end
        swap(a, pivotIndex, right);
        
        //storeIndex represents the boundary between small and large elements
        int storeIndex = left;
        for(int i = left; i < right; i++) {
            if(a[i] <= pivotElement)
                swap(a, storeIndex++, i);
        }
        
        //Move pivot to its final place
        swap(a, right, storeIndex);
        
        return storeIndex;
    }
    
    static void swap(int[] a, int pos1, int pos2) {
        int temp = a[pos1];
        a[pos1] = a[pos2];
        a[pos2] = temp;
    }
    
    
    static void recQuickSort(int[] data, int left, int right, int k) {
        int size = right - left + 1;
        if (size <= 3) // manual sort if small
            manualSort(data, left, right);
        else { // quicksort if large
            long median = medianOf3(data, left, right, k);
            if (median == -1) //meaning k
                return;
            int partition = partitionIt(data, left, right, median, k);
            recQuickSort(data, left, partition - 1, k);
            recQuickSort(data, partition + 1, right, k);
        }
    }
    
    static long medianOf3(int[] data, int left, int right, int k) {
        int center = (left + right) / 2;
        if (data[center] == k)
            return -1;
        // order left & center
        if (data[left] > data[center])
            swap(data, left, center);
        // order left & right
        if (data[left] > data[right])
            swap(data, left, right);
        // order center & right
        if (data[center] > data[right])
            swap(data, center, right);
        
        swap(data, center, right - 1); // put pivot on right
        return data[right - 1]; // return median value
    }
    
    static int partitionIt(int[] data, int left, int right, long pivot, int k) {
        int leftPtr = left; // right of first elem
        int rightPtr = right - 1; // left of pivot
        
        while (true) {
            //       Inc. to find bigger
            while (data[++leftPtr] < pivot)
                ;
            //       Dec. to find smaller
            while (data[--rightPtr] > pivot)
                ;
            if (leftPtr >= rightPtr) // if pointers cross, partition done
                break;
            else // not crossed, so
                swap(data, leftPtr, rightPtr); // swap elements
        }
        swap(data, leftPtr, right - 1); // restore pivot
        return leftPtr; // return pivot location
    }
    
    static void manualSort(int[] data, int left, int right) {
        int size = right - left + 1;
        if (size <= 1)
            return; // no sort necessary
        if (size == 2) { // 2-sort left and right
            if (data[left] > data[right])
                swap(data, left, right);
        } else {// size is 3
            // 3-sort left, center, & right
            if (data[left] > data[right - 1])
                swap(data, left, right - 1); // left, center
            if (data[left] > data[right])
                swap(data, left, right); // left, right
            if (data[right - 1] > data[right])
                swap(data, right - 1, right); // center, right
        }
    }
}