package r3activex;

import hu.akarnokd.rxjava2.interop.CompletableInterop;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.subjects.CompletableSubject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFuturesOk {

   public static void main(String[] args) throws Exception {
      Completable completable = CompletableInterop.fromFuture(createFuture1());
      Maybe<Integer> maybe = completable.andThen(
         (MaybeSource<Integer>) observer -> {
            System.out.println("Subscribe to maybe source...");
            createFuture2()
               .whenComplete(
                  (v, t) -> {
                     if (t != null)
                        observer.onError(t);
                     else if (v != null)
                        observer.onSuccess(v);
                     else
                        observer.onComplete();
                  }
            );
      });

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
