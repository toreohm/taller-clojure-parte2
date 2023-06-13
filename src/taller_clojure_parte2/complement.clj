(ns taller-clojure-parte2.complement)

;COMPLEMENT
(comment "Toma una función y los mismos argumentos que recibe la función.
Tiene los mismos efectos si es que los hay y devuelve el opuesto truth value")

;Ejecutar en el REPL
(def some?-segunda-edicion (complement nil?))
(nil? nil)
(some?-segunda-edicion nil)
(some?-segunda-edicion false)
(some?-segunda-edicion 100)

(def every?-opuesto (complement every?))
(every? #(> % 3) [4 5 6])
(every?-opuesto #(> % 3) [4 5 6])

(every?-opuesto #(> % 3) [1 4 5 6])

;Ejemplos de pure functions

#(+ 4 %)
#(str "Valor: " %)

