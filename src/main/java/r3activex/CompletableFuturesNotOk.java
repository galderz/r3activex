package r3activex;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.MaybeSubject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class CompletableFuturesNotOk {

   public static void main(String[] args) throws Exception {
      Completable completable = futureToCompletable(createFuture1());
      Maybe<Integer> maybe = completable.andThen(futureToMaybe(createFuture2()));
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

   static Completable futureToCompletable(CompletionStage<?> future) {
      CompletableSubject cs = CompletableSubject.create();

      future.whenComplete(
         (v, t) -> {
            if (t != null)
               cs.onError(t);
            else
               cs.onComplete();
         }
      );

      return cs;
   }

   static <T> Maybe<T> futureToMaybe(CompletionStage<T> future) {
      MaybeSubject<T> ms = MaybeSubject.create();

      future.whenComplete(
         (v, t) -> {
            if (t != null)
               ms.onError(t);
            else if (v != null)
               ms.onSuccess(v);
            else
               ms.onComplete();
         }
      );
      return ms;
   }



}
