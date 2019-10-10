package com.study.sort;

public class MyQuickSort {

	public static int partion(long[] arr,int left ,int right ,long pivot){
		int l = left -1;
		int r = right;
		while(l<r){
			while(l<r && arr[++l] < pivot);
			while(l<r && arr[--r] > pivot);
			long tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
		}
		long tmp = arr[l];
		arr[l] = pivot;
		arr[right] = tmp;
		
		return l;
	}
	
	public static void displayArr(long[] arr) {
		System.out.print("[");
		for(long num : arr) {
			System.out.print(num + " ");
		}
		System.out.print("]");
		System.out.println();
	}
	
	public static void sort(long[] arr,int left,int right){
		if(left >= right){
			return;
		}else{
			//设置关键字
			long point = arr[right];
			int  partition = partion(arr, left, right, point);
			sort(arr,left,partition-1);
			sort(arr,partition+1,right);
		}
		
	}

    public static void main(String[] args) {
        long[] arr = {2,4,5,6,1,2,4,5,9};
        sort(arr,0,arr.length-1);
        displayArr(arr);
    }
}