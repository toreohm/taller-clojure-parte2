(ns taller-clojure-parte2.resumen-practico)

;EJERCICIOS, REPASO, DUDAS
(def permisos-validos #{:SIT/ADMIN :SIT/REPORTE :SIT/EJECUTIVO :SIT/EJECUTIVO-MIGRANTE :SIT/EJECUTIVO-RECUPERATE-YA :SIT-OPERACIONES})
(def usuarios [
{:nombre "Patricia Sanchez" :email "patys@pretmex.com" :usuario "patys" :clave "ddq2e92eujdiwqd" :permisos #{:SIT/REPORTE}}
{:nombre "Mario Jimenez" :email "marioj@pretmex.com" :usuario "marioj" :clave "ddq2e92euefesfiwqd" :permisos #{:SIT/ADMIN :SIT-OPERACIONES}}
{:nombre "Maria Perez" :email "mariap@pretmex.com" :usuario "mariap" :clave "1112e92eujdiwqd" :permisos #{:SIT/EJECUTIVO}}
{:nombre "Daniel Juarez" :email "danielj@pretmex.com" :usuario "danielj" :clave "3332e92eujdiwqd" :permisos #{:SIT/EJECUTIVO-RECUPERATE-YA}}
{:nombre "Intruso" :email "intruso@pretmex.com" :usuario "intruso1" :clave "3332e92eujdiwqd" :permisos #{:PERMISTO/PATITO}}
{:nombre "Intruso2" :email "intruso2@pretmex.com" :usuario "intruso2" :clave "4442e92eujdiwqd" :permisos #{:PERMISTO/PATITO2}}
{:nombre "Intruso3" :email "intruso3@pretmex.com" :usuario "intruso3" :clave "4442e92eujdiwqd" :permisos #{:PERMISTO/PATITO3}}])

;EJERCICIO
;1) Verificar que todos los usuarios tenga permisos validos.
;2) Verificar que el usuario sea ejecutivo general.

;¿Como verificar que los permisos de cada usuario sean permisos-validos?
;Ejemplo: Verificar que estos permiso(s) #{:SIT/ADMIN :SIT-OPERACIONES} sean válidos.

(defn hay-permisos-invalidos? [coll]
   (contains? (set (map (fn [permisos-usuario]
                          (every? #(% permisos-validos) permisos-usuario))
                        (map :permisos coll))) false))
(defn hay-permisos-invalidos-v2? [coll]
 (contains? (->> (map :permisos coll)
                 (map (fn [permisos-usuario]
                        (every? #(% permisos-validos) permisos-usuario)))
                 set)
            false))
(defn hay-permisos-invalidos-v3? []
  (contains? (set (reduce (fn [inicial set-de-permisos]
                            (let [permisos-validos? (every? #(% permisos-validos) set-de-permisos)]
                              (if permisos-validos?
                                (conj inicial true)
                                (conj inicial false))))
                          []
                          (map :permisos usuarios))) false))

;solución al ejercicio: 2) Verificar que el usuario sea ejecutivo general
(defn es-ejecutivo-general? [registro]
  (:SIT/EJECUTIVO (:permisos registro)))

;solucion1
(first
  (filter some?
          (map #(when (es-ejecutivo-general? %) %)
               usuarios)))

;solucion2
(->> usuarios
     (map #(when (es-ejecutivo-general? %) %))
     (filter some?)
     first)


(comment "Noten, los nombres de las funciones del ejemplo anterior. Terminan con signos de interrogación.
Por lo que se entiende como si fuera una función pred que puede o no usarse dentro de funciones como:
(map, some, every? filter, remove). Estas funciones como ya vimos, se evalua/analiza cierta información
(estructura de datos) y el resultado de la función se evalua a truthy a falsey")

;3) Verificar si hay intrusos y en caso de que sí, obtener los registros de los intrusos.

(defn obtener-intrusos
  "1) Se valida cada registro de la coll.
   2) Se obtiene los permisos de cada registro.
   3) Los permisos de cada registros se comparan con los permisos-validos y en base a eso se decide si dicho registro
   corresponde a un intruso o no."
  [coll]
  (filter some? (map (fn [registro]
                       (let [permisos (:permisos registro)
                             son-permisos-validos? (every? #(% permisos-validos) permisos)]
                         (when-not son-permisos-validos?
                           registro)))
                     coll)))

(defn obtener-intrusos-v2
  "1) Se valida cada registro de la coll.
   2) Se obtiene los permisos de cada registro.
   3) Los permisos de cada registros se comparan con los permisos-validos y en base a eso se decide si dicho registro
   corresponde a un intruso o no."
  [coll]
  (reduce (fn [ini rec]
            (let [permisos (:permisos rec)
                  son-permisos-validos? (every? #(% permisos-validos) permisos)]
              (if-not son-permisos-validos?
                (conj ini rec)
                ini)))
          []
          coll))

;MAP VS REDUCE
;En ocasiones, se podrá resolver un problema con map y con reduce.
;Recordemos que map siempre devuelve una lista con el resultado de aplicarle un fn a cada elemento de coll.
;Entonces si coll tiene 6 elementos, map devuelve una lista de 6 elementos, aun así sea nil.

(reduce (fn [ini rec]
          (if (:a rec)
            (conj ini (:a rec))
            ini))
        []
        [{:a 1} {} {:a 2} {} {:a 3}])

"reduce vs map"
(map :a [{:a 1} {} {:a 2} {} {:a 3}])
"El ejemplo de abajo sería equivalente a usar reduce"
(vec (filter some?
             (map :a [{:a 1} {} {:a 2} {} {:a 3}])))

;EJEMPLO REAL con partial del proyecto SIT
;Pero antes de explicar el ejemplo real, es necesario entender esto:
(some :a [{:a false} {:a nil} {:a "Primer valor"}]) ;Regresa un string
(some #(when (:a %) %)
      [{:a false} {:a nil} {:a "Primer valor"}]) ;Regresa el mapa que tiene el string

;Aquí empieza el ejemplo real
(defn obtener-del-subtotal
  "Obtener las observaciones/proveedor/algun otro dato del mapa que tenga el subtotal.
  Son dos mapas, uno tiene el IVA cómo importe y el otro tiene el subtotal cómo importe."
  [k col]
  (some #(when (-> (:id %)
                   (clojure.string/split #":")
                   last
                   (= "1"))
           (k %))
        col))
(def obtener-observaciones-subtotal (partial obtener-del-subtotal :observaciones))
(def obtener-proveedores-subtotal (partial obtener-del-subtotal :proveedor))
(def obtener-tipodegasto-subtotal (partial obtener-del-subtotal :tipodegasto))

;El mapa (registro) en la practica real tiene más propiedades/keys
(obtener-observaciones-subtotal
  (conj []
        {:id "archivo1.csv:4:1" :abono 1500 :cargo 0 :observaciones "Observación de prueba Subtotal" :proveedor "patito"}
        {:id "archivo1.csv:3:2" :abono 240 :cargo 0 :observaciones "Observación de prueba IVA"}))
