(ns taller-clojure-parte2.abstraction)

(comment
  Clojure defines map and reduce functions in terms of the sequence abstraction,
  not in terms of specific data structures.
  As long as a data structure responds to the core sequence operations
  (The functions first, rest, and cons), it will work with map, reduce, filter,
  and many of other sequence functions for free.)

(comment La razon por la que clojure aplica (seq coll) a las
         estructuras de datos que pasan como argumentos a las core functions o quiza "seq functions".
         Es para permitir que se le pueda aplicar first, rest y cons a la coll.)

;Ya vimos first y rest, cons es más complejo de entender.
(first [1 2 3 4])
(rest [1 2 3 4])
(cons 1 '(2 3 4 5 6))
(cons [1 2] [4 5 6])


(comment
  En el caso de cons:
  Useful for creating lazy sequences, because it does not need to realize the param "seq" ->
  it just appends (adjunta) the whole thing to param "x"
  cons es utilizado en conjunto con lazy-seq, permitiendo generar valores infinitos sin hacer que truene
  la aplicacion.
  "https://clojuredocs.org/clojure.core/lazy-seq"
  "https://www.javadoc.io/doc/org.clojure/clojure/1.8.0/clojure/lang/ISeq.html"
  pero no lo veremos a detalle en este curso.)

;Checar este en el REPL
(defn positive-numbers
  ([] (positive-numbers 1))
  ([n] (lazy-seq (cons n (positive-numbers (inc n))))))

(take 5 (positive-numbers))

(comment
  "The collection abstraction está muy relacionada con la sequence abstraction.
  Todas las estructuras de datos de Clojure (vectores, mapas, listas y sets)
  forman parte de ambas abstracciones.")

(comment "The sequence abstraction se trata de operar en elemento por elemento de manera individual
de una coll. Mientras la collection abstraction se trata de operar la estructura de datos
como un todo. Algunos ejemplos que aplican esta abstracción con las colls son:")

(count [1 2 3 4 5])
(count {:a 1 :b 2 :c 3})
(empty? [])
(empty? #{1 32})
(every? :a [{:a 1} {:a 200} {:a false} {:a true}])
(every? :a [{:a 1} {:a 200} {:a #(str "Tu valor es: " %)} {:a true}])




