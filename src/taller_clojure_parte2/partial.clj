(ns taller-clojure-parte2.partial
  (:require [clojure.string :as s]))

;PARTIAL
(comment "Toma una función f (ya definida) y un número menor que los argumentos normales para f, y
  devuelve un fn que toma un número variable de argumentos adicionales. Cuando se ejecuta,
  la función devuelta llama a f con argumentos + argumentos adicionales.")

(def sumar-cinco (partial + 5))
(sumar-cinco 5) ;10
(sumar-cinco 5 10 20) ;40

;Otro ejemplo un poco más practico.
;Probar en REPL
(defn hacer-algo [k & valores]
  (cond
    (= k :string) (s/join " <--> " valores)
    (= k :suma) (apply + valores)
    (= k :multiplicacion) (reduce * valores)
    (= k :resta) (apply - valores)
    :else :keyword-incorrecto))

(def convertir-string (partial hacer-algo :string))
(def sumar-numeros (partial hacer-algo :suma))
(def restar-numeros (partial hacer-algo :resta))
(def volar-como-superman (partial hacer-algo :volar))

(convertir-string 1 2 3 4 5)
(sumar-numeros 1 2 3 4 5)
(restar-numeros 1 2 3 4 5)
(volar-como-superman 1 2 3 4 5)

