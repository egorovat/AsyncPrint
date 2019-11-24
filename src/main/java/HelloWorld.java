import java.util.concurrent.*;

public class HelloWorld {

    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private static final ExecutorService executor =
            Executors.newFixedThreadPool(3);


    public static void main(String[] args) throws InterruptedException {

        HelloWorld helloWorld = new HelloWorld();
        final ScheduledFuture<?> helloHandler =
                scheduler.scheduleAtFixedRate(()->helloWorld.runAllAsync().run(), 10, 10, TimeUnit.SECONDS);

        scheduler.schedule((Runnable) () -> helloHandler.cancel(true), 60, TimeUnit.SECONDS);

    }

    private Runnable runAllAsync(){

        return () -> {
            executor.submit(()->CompletableFuture.runAsync(this::printHello).join());
            executor.submit(()->CompletableFuture.runAsync(this::printWorld).join());
        };
    };

    private void printHello(){
         System.out.print("Hello ");
    }

    private void printWorld(){
        System.out.print("World ");
    }
}
