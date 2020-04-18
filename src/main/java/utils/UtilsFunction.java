package utils;



public class UtilsFunction {

    private static int right(int i){
      return (2*i+2) ;
    }
    private static int left(int i){
     return (2*i+1) ;
    }

    private static int[] swap(int[] harr, int i, int j) {
        int tmp=harr[i];
        harr[i]=harr[j];
        harr[j]=tmp;
        return harr;
    }

    public static int buildHeap(int harr[], int heapSize, String heapType){

     if (heapSize==0)
          return 0;
     int root = harr[0];
     if(heapSize>1){
       harr[0]=harr[heapSize-1];
       harr[heapSize-1]=root;

       if(heapType=="min")
           minHeapify(harr,0,heapSize-1);
       else
           maxHeapify(harr,0,heapSize-1);
     }
        return buildHeap(harr,heapSize-1,heapType);
 }

    private static void maxHeapify(int[] harr, int i, int heapSize) {
        int r=right(i);
        int l=left(i);
        int largest=i;

        if(r<heapSize && harr[r]<harr[largest])
            largest=r;

        if(l<heapSize && harr[l]<harr[largest])
            largest=l;

        if(largest!=i){
            harr=swap(harr,i,largest);
            maxHeapify(harr,largest,heapSize);
        }
    }



    private static void minHeapify(int[] harr, int i, int heapSize) {
        int r=right(i);
        int l=left(i);
        int smallest=i;

        if(r<heapSize && harr[r]>harr[smallest])
            smallest=r;
        if(l<heapSize && harr[l]>harr[smallest])
            smallest=l;

        if(smallest!=i){
           harr=swap(harr,smallest,heapSize);
           minHeapify(harr, smallest,heapSize);
        }
    }

}
