import java.util.Arrays;

public class MyThread {
    static final int SIZE = 10000000;
    static final int HALF_SIZE = SIZE/2;

    public static void main(String[] args) throws InterruptedException {
        float[] array1 = singleThread();
        float[] array2 = multiThread();
        System.out.println(Arrays.equals(array1, array2));
    }

    public static float[] singleThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5) * Math.cos(0.4f + (float) i / 2));
        }
        System.out.println("SingleTread Time = " + (System.currentTimeMillis() - startTime));
        return arr;
    }

    public static float[] multiThread() throws InterruptedException {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1.0f);
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];
        long startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, arr2, 0, HALF_SIZE);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5) * Math.cos(0.4f + (float) i / 2));
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + (float) (HALF_SIZE + i) / 5) * Math.cos(0.2f + (float) (HALF_SIZE + i) / 5) * Math.cos(0.4f + (float) (HALF_SIZE + i) / 2));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(arr1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, arr, HALF_SIZE, HALF_SIZE);
        System.out.println("MultiTread Time = " + (System.currentTimeMillis() - startTime));
        return arr;
    }
}
