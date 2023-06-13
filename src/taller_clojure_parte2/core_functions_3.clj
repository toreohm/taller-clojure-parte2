(ns taller-clojure-parte2.core-functions-3)

;TAKE
(comment "La función take toma los primeros n elementos que se le especifique de una coll, y los devuelve en una lista.
Tomar en cuenta, que así como en las demás funciones (map, reduce, filter): La función take, primero transforma la coll
proporcionada aplicandole (seq coll). Esto se aclara en los siguiente ejemplos
")

;Probar los siguientes ejemplos en el REPL
(take 2 [1 2 3 4 5 6 7])
(take 2 '(1 [{:a 1}] "b" "c" 3))
(take 1 #{1 2 3 4})
(take 2 {:a 1 :b 2 :c 3 :d 4})
(seq {:a 1 :b 2 :c 3 :d 4})
(take 2 [{:a 1} {:a 2} {:a 3} {:a 4}])

(take 1 [(fn [a] (str (+ a 2))) {:a 1} 11 "cadena"])

;DROP
(comment "La función drop es lo contrario a take; quita los primero n elementos que le especifiques de una coll.")

;Ver los siguientes ejemplos en el REPL
(drop 4 [1 2 3 4 5 6])
(drop 0 [1 2 3 4])
(drop 1 {:a 1 :b 2 :c 3})
(into {} (drop 1 {:a 1 :b 2 :c 3}))
(apply conj {} (drop 1 {:a 1 :b 2 :c 3}))

;TAKE-WHILE
(comment "La función take-while, regresa elementos de una coll \"while\" (pred item) retorne logical true.
Pred debe estar libre de side-effects (efectos secundarios). Es decir, debe ser una función pura.

BONUS: Para que una función sea pura, debe cumplir con 2 requisitos:

1) Si siempre le pasas los mismos argumentos, siempre debe darte el mismo resultado, ejemplo:
(fn [a b] (+ a b))

2) Tu función no debe tener side effects, es decir, modificar algo externo a tu función.
Ejemplo: La función de abajo, no es pura debido al println.
Y la razón a esto: https://stackoverflow.com/questions/47562045/how-is-the-function-println-not-pure-clojure
\"Since println writes to STDOUT, it is altering the state of the stdout. Therefore it has a side effect.\"
(fn [a b]
(println \"holaaa!!!\")
(+ a b))")

(take-while #(>= % 5) [1 2 3 4 5 6 7 8 9 10])
(take-while #(<= % 5) [1 2 3 4 5 6 7 8 9 10])
(take-while #(<= (:a %) 5) [{:a 1} {:a 2} {:a 3} {:a 4} {:a 5} {:a 6} {:a 7}])
(vec (take-while #(<= (:a %) 5) [{:a 1} {:a 2} {:a 3} {:a 4} {:a 5} {:a 6} {:a 7}]))
(into [] (take-while #(<= (:a %) 5) [{:a 1} {:a 2} {:a 3} {:a 4} {:a 5} {:a 6} {:a 7}]))

;DROP-WHILE

(comment "La función drop-while es lo contrario a take-while. Segun la documentación.
Regresa elementos de una coll mientras (pred item) retorne logical false.
Pero sinceramente yo prefiero verlo de esta manera:
Quita elementos de una coll \"while\" (pred item) retorne logical true.")

(drop-while neg? [-1 -2 -6 -7 1 2 3 4 -5 -6 0 1])
(drop-while #(<= % 5) [1 2 3 4 5 6 7 8 9 10 1])
(drop-while #(>= % 5) [1 2 3 4 5 6 7 8 9 10])
(take-while #(>= % 5) [1 2 3 4 5 6 7 8 9 10]) ;Compara el comportamiento con drop-while

;Una vez que la condición ya no se cumpla, regresa el resto de coll sin importar que los demas elementos tampoco
;cumplan con la condicion
(drop-while :a [{:a "a"} {:a 1} {:a 5} {:b 3} {:a "Esto si se muestra!!!!"} {:b "Yo tambien me muestro"}])

;SORT
(comment "La función sort regresa una sequencia ordenada de los elementos en coll.
Si no se usa algo que compare a los elementos, clojure usaría a compare por default.")
(sort [3 1 2 4])
(sort ["casa" "ventana" "sala" "cocina"])
(sort #(compare %2 %1) '(:a :b :c :d))

;SORT-BY
(comment "La función sort-by regresa una sequencia ordenada de los elementos en coll, el cual te permite
aplicar una función para determinar el como ordenar los elementos de la función. A veces a la función se le llama
key function.")

(sort-by count ["aaa" "bb" "c"])
(sort-by second {:a 100 :b 1 :c 25 :d 14})
(map #(second %) (sort-by second {:a 100 :b 1 :c 25 :d 14}))
(sort-by :a [{:a 100} {:a 1} {:a 25} {:a 14}])
(map :a (sort-by :a [{:a 100} {:a 1} {:a 25} {:a 14}]))