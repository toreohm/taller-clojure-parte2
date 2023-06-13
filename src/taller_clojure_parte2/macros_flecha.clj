(ns taller-clojure-parte2.macros-flecha
  (:require [clojure.string :as s]))

;La macro ->
(comment "La macro -> [x & forms]. Acepta x como un valor o una expresión que devuelve un valor (el que sea).
Despues este valor pasa como el segundo item en la siguiente forma. Una vez devuelto el valor de la forma, este vuelve
a pasar como el segundo item de la siguiente forma y así sucesivamente hasta terminar y devolver el resultado.")

(def persona
  {:informacion {:level1 {:level2 {:level3 "banana"}}}})

;Primer ejemplo
(first (vector (str (+ 200 100))))

(-> 100
    (+ 200)
    str
    vector
    first)

;Segundo ejemplo
(get-in persona [:informacion :level1 :level2 :level3])

(-> persona
    :informacion
    :level1
    :level2
    :level3)

;La macro ->>
(comment "La macro ->> [x & forms]. Muy similar al macro ->
Acepta x como un valor o una expresión que devuelve un valor (el que sea).
Despues este valor pasa como el ultimo item en la siguiente forma. Una vez devuelto el valor de la forma, este vuelve
a pasar como el ultimo item de la siguiente forma y así sucesivamente hasta terminar y devolver el resultado.")

;Primer ejemplo
((comp #(hash-map :cadena %) str #(reduce + %) vector) 1 2 3 4 5)
(->> (vector 1 2 3 4 5)
     (reduce +)
     str
     (hash-map :cadena))

;Segundo ejemplo
(->> (range)
     (map #(* % %))
     (filter even?)
     (take 10)
     (reduce +))

