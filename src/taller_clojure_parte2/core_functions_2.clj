(ns taller-clojure-parte2.core-functions-2
  (:require [clojure.string :as s]))

;FILTER
(comment
  "Filter regresa una nueva coll para la cual (pred item) devuelve true.
  En este caso pred vendría siendo una función/función anónima cuyo valor de retorno se evalua a true o a false.
  E item sería un elemento de la coll, el cual se pasa como argumento a pred.
  Filter recorre toda la coll, elemento por elemento y regresa una nueva coll con los elementos
  que \"pasen la prueba\". Recibe dos argumentos [pred coll].

  Es decir, filter filtra los elementos de una coll, devolviendo una nueva coll con los elementos filtrados.")

;Probar en el REPL los siguientes ejemplos
(filter :a
        [{:a 5} {:a nil} {:a nil} {:a 7} {:a 10} {:a nil}])

(filter (fn [elemento]
          (when (some? (:a elemento))
            (> (:a elemento) 7)))
        [{:a 5} {:a nil} {:a nil} {:a 7} {:a 10} {:a nil}])

(filter #(s/starts-with? % "a")
        ["casa" "abuelo" "amigo" "cielo" "luna" "azul" "etc"])

;Si por alguna razón necesitas/prefieres un vector con los elementos filtrados...
;Hay dos formas de hacerlo
(vec (filter #(s/starts-with? % "a")
             ["casa" "abuelo" "amigo" "cielo" "luna" "azul" "etc"]))
(into [] (filter #(s/starts-with? % "a")
                 ["casa" "abuelo" "amigo" "cielo" "luna" "azul" "etc"]))

;Ejemplo bonus de vec y con into
(vec {:a 1 :b 2})
(into [] {:a 1 :b 2})

;REMOVE
(comment "la función remove es lo contrario a filter.
Es decir, remove regresa una nueva coll para la cual (pred item) devuelve false.
Se puede ver esto de dos maneras:
1) remove, remueve/quita los elementos de la coll proporcionada que SÍ pasen las prueba.
2) remove, es un filter inverso, es decir: filtra los elementos que NO pasen la prueba. (No se recomienda)")

;Probar los siguientes ejemplos en el REPL
(remove #(> % 10) [5 6 7 20 30])

(remove :a
        [{:a 5} {:a nil} {:a nil} {:a 7} {:a 10} {:a nil}])

(remove (fn [elemento]
          (when (some? (:a elemento))
            (> (:a elemento) 7)))
        [{:a 5} {:a nil} {:a nil} {:a 7} {:a 10} {:a nil}])

;CONJ
(comment "Regresa una nueva coll con los elementos añadidos de manera individual.")

;Checar los siguientes ejemplos en el REPL
(conj [1 2 3] 4 5)
(conj [1 2 3] [4 5])
(conj [1 2 3] {:a 4 :b 5})
(conj {:a 1} [:b 2] [:c 3])

;INTO
(comment "into por su parte, acepta una coll como segundo argumento para pasar sus elementos individuales
a la primera coll que se pasa como primer argumento.")

;Checar los siguientes ejemplos en el REPL
(into [1 2 3 4 5] [6 7])
(into #{:a :b :c} (list "a" "b" "c"))
(into {:a 1} [[:b 2] [:c 3]])

(comment "Se puede usar conj como si fuera into con la función apply")
(apply conj {:a 1} [[:b 2] [:c 3]])
(apply conj #{:a :b :c} (list "a" "b" "c"))

;CONCAT
(comment "La función concat, regresa la concatenación de los elementos de las colls proporcionadas.
Es similar a into con la diferencia que puedes pasar varias colls. Además regresa una lista de todos los
elementos concatenados porque se le aplica seq a todas las colls proporcionadas.")

(concat [1 2] [3 4] [5 6] [7 8])
(concat [1 2] {:a 3 :b 4} #{5 6})
(concat {:a 3 :b 4}  {:c 30 :d 40})

;APPLY
(comment "Recibe una función como arg y una coll como segundo arg.
apply ejecuta la función con los elementos de la coll de manera individual.")

(apply + [1 2 3 4])
(apply str "" ["Mi " "casa " "NO " "es " "tu " "casa. " "Saquese!!!"])
;Otra forma de hacerlo es con join
(s/join ["Mi " "casa " "NO " "es " "tu " "casa. " "Saquese!!!"])
