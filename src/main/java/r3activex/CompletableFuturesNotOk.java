package r3activex;

import hu.akarnokd.rxjava2.interop.CompletableInterop;
import hu.akarnokd.rxjava2.interop.MaybeInterop;
import io.reactivex.Completable;
import io.reactivex.Maybe;

import java.util.concurrent.CompletableFuture;

public class CompletableFuturesNotOk {

   public static void main(String[] args) throws Exception {
      Completable completable = CompletableInterop.fromFuture(createFuture1());
      Maybe<Integer> maybe = completable.andThen(MaybeInterop.fromFuture(createFuture2()));
      maybe.subscribe(
         v -> System.out.println("Final value: " + v)
         , t -> System.out.println("Error: " + t)
      );

      Thread.sleep(60000);
   }

   static CompletableFuture<?> createFuture1() {
      return CompletableFuture.supplyAsync(() -> {
         try {
            Thread.sleep(2000);
            System.out.println("Call future 1");
            return null;
         } catch (InterruptedException e) {
            e.printStackTrace();  // TODO: Customise this generated block
            return null;
         }
      });
   }

   static CompletableFuture<Integer> createFuture2() {
      return CompletableFuture.supplyAsync(() -> {
         System.out.println("Call future 2");
         return 1;
      });
   }

}
