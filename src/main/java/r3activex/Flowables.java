package r3activex;

import io.reactivex.Flowable;

public class Flowables {

   public static void main(String[] args) {
      Flowable.fromArray(1, 2, 3)
         .map(i -> i + 1)
         .subscribe(v ->
            System.out.println(v)
         );
   }

}
