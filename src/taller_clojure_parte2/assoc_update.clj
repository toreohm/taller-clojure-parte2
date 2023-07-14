(ns taller-clojure-parte2.assoc-update)

;ASSOC
(comment "La función assoc sirve para añadir nuevos elementos a un mapa.
Devuelve un nuevo mapa con los nuevos elementos.")

(assoc {} :a 100 :b 200)

;En caso de que una llave ya exista, te sobreescribe el valor.
(assoc {:a 200} :b 1000 :a 1)

(assoc {} :a {:b {:c 200}})

;UPDATE
(comment "La función update sirve para actualizar un valor de un mapa a
través de una función (ya existente o tu propia función).")

(update {:a 1000 :b 2000 :c 3000} :a inc)

;Si la key no existe, nil pasa como el primer argumento a la función.
;Esto dependiendo de la función en sí, puede devolver error o no.
(update {:a 1000 :b 2000 :c 3000} :key-incorrecto inc)      ;Devuelve error (inc nil)
(update {:a 1000 :b 2000 :c 3000} :key-incorrecto str)      ;Equivalente a (str nil)

;ASSOC-IN/UPDATE-IN

(comment "En el caso de assoc-in/update-in es lo mismo que assoc y update,
sólo que te permite añadir/actualizar valores en mapas anidados.")

(assoc-in {:a {:b 300}} [:a :new-key] "banana")

;En caso de no existir la llave, te la crea con su valor correspondiente
(assoc-in {:a {:b 300}} [:z :new-key] "apple")

(update-in {:a {:b 300}} [:a :b] + 100 1000)

(update-in {:a [1 2 3 4 5]} [:a] (fn [coll & demas]
                                   (println demas)
                                   (->> (map inc coll)
                                        (reduce +)
                                        str
                                        vector)) "casa" "juegos de mesa" "avion")