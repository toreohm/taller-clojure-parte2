(ns taller-clojure-parte2.core-functions
  (:gen-class))

(comment
  "Las Core Functions nos permiten trabajar de manera eficiente con las estructuras de datos
   también conocidas cómo colecciones (coll). Con las core functions podremos procesar datos con
   los mapas, vectores, listas y sets.
   Las core functions pertenecen al namespace clojure.core")

(comment
  "Algunos ejemplos de lo que veremos: map, reduce, filter, conj, concat, apply, seq,
  take, take-while, drop, drop-while, sort, sort-by")

;Empecemos con seq;
;Probar en el REPL cada una de las operaciones.
(seq [1 2 3])
(seq [])
(seq '(1 2 3))
(seq '())
(seq #{1 2 3})
(seq #{})
(seq {:a 1 :b 2 :c 3})
(seq {})

(comment "Ahora vamos a ver la función map y explicaremos su relación con seq, de igual manera esta relación
con seq aplicaría a las demás funciones core de clojure: filter, reduce, some, first, last...")

(comment
  "map devuelve una lista del resultado de aplicar una función a cada elemento de una coll.")

(map inc [1 2 3 4 5 6])

(map (fn [coll]
       (apply + coll))
     [[1 2 3] [4 5 6] [7 8 9] [10 11 12]])

;El ejemplo anterior es equivalente a esta alternativa.
(map #(apply + %)
     [[1 2 3] [4 5 6] [7 8 9] [10 11 12]])

(map :cuenta [{:abono 1 :cargo 100 :cuenta "123456789"}
              {:abono 2 :cargo 200 :cuenta "000000000000"}
              {:abono 3 :cargo 300 :cuenta "11111111111"}
              {:abono 4 :cargo 400 :cuenta "999999999999"}])

(map :llave-incorrecta [{:abono 1 :cargo 100 :cuenta "123456789"}
                        {:abono 2 :cargo 200 :cuenta "000000000000"}
                        {:abono 3 :cargo 300 :cuenta "11111111111"}
                        {:abono 4 :cargo 400 :cuenta "999999999999"}])

(map #(:llave-incorrecta % "Valor default") [{:abono 1 :cargo 100 :cuenta "123456789"}
                                             {:abono 2 :cargo 200 :cuenta "000000000000"}
                                             {:abono 3 :cargo 300 :cuenta "11111111111"}
                                             {:abono 4 :cargo 400 :cuenta "999999999999"}])

(map (fn [mapa]
       (inc (:a mapa)))
     [{:a 1} {:a 2} {:a 3} {:a 4}])

(map (fn [mapa]
       (hash-map :a (inc (:a mapa))))
     [{:a 1} {:a 2} {:a 3} {:a 4}])

(map (fn [mapa]
       {:a                (inc (:a mapa))
        :cuenta-bancaria  "1234567890"
        :mas-info-aun     "banana"
        :permiso-cobranza (> (:a mapa) 2)})
     [{:a 1} {:a 2} {:a 3} {:a 4}])

;¿Pero qué pasa si le pasamos un mapa literal a map?
(comment "A todas las estructuras de datos (mapas, vectores, listas, sets), implicitamente clojure les aplica
la función seq al ser pasado como argumento a una función core: map filter, reduce, some, first, last")

;Si hacemos esto
(map identity {:a 1 :b 2})
;Implícitamente estaría pasando esto
(map identity (seq {:a 1 :b 2}))
;Lo cual sería equivalente a esto:
(map identity '([:a 1] [:b 2]))

;Lo mismo pasa con first
(first {:a 1 :b 2})
(last {:a 1 :b 2})

;BONUS => Puedes convertir el seq de vuelta a un mapa de la siguiente manera:
(into {} (seq {:a 1 :b 2 :c 3}))
(into {} [[:a 1] [:b 2] [:c 3]])

;Más ejemplitos para despertar la curiosidad
(apply conj {} [[:a 1] [:b 2] [:c 3]])
(conj {} [:a 1])
(apply assoc {} [:a 1])  ; Más adelante veremos que es assoc.
(map #(apply assoc {} %) [[:a 1] [:a 2] [:a 3]])

;REDUCE
(comment "reduce procesa cada elemento en una coll para devolver un valor.
La diferencia con map, es que mientras map siempre devuelve una lista de valores
(aun así sea '(nil nil nil nil)) Reduce puede devolver cualquier valor:
Un numero, una lista de mapas, un set, un vector de numeros, un string,etc")

;Probar en el REPL
(reduce + [1 2 3 4 5])
(apply + [1 2 3 4 5])

(reduce * [1 2 3 4 5])
(reduce * 100 [1 2 3 4 5])

(reduce str [1 2 3 4 5])

(reduce #(str % (:a %2))
        "Concatenacion: "
        [{:a "casa "} {:a "silla "} {:a "mesa "} {:a "puerta"}])

;Analizar el siguiente ejemplo en el repl
(str (first (seq "hola"))) ;"h"

;Ahora vemos un ejemplo en donde recibimos un string (seq string) y devolvemos un mapa
(reduce (fn [inicial valor]
          (let [key (keyword (gensym "a"))
                valor (str valor)]
            (conj inicial [key valor])))
        {}
        "HOLA")

;Ejemplo, en donde le pasamos un mapa y queremos devolver el mismo mapa actualizado
(reduce (fn [inicial un-vector]
          (let [llave (first un-vector)
                valor (second un-vector)
                valor (inc valor)]
            (conj inicial [llave valor])))
        {}
        {:a 1 :b 2 :c 3})

