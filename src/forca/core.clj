(ns forca.core
  (:gen-class))


(def total-de-vidas 6)
(def palavra-secreta "MELANCIA")
(def carros [1000.0, 2000.0, 3000.0])

(defn perdeu [] (println "Você perdeu"))
(defn ganhou [] (println "Você ganhou"))

(defn letras-faltantes [palavra acertos] 
  (remove (fn [letra] (contains? acertos (str letra))) palavra))

(defn acertou-a-palavra-toda? [palavra acertos] 
  (empty? (letras-faltantes palavra acertos)))

; função com ! muda o estado (efeito colateral)
(defn le-letra! [] (read-line))

; usando método contains da Classe String do Java
(defn acertou? [chute palavra] (.contains palavra chute) )

(defn imprime-forca [vidas palavra acertos]
  (println "Vidas " vidas)
  (doseq [letra (seq palavra)] 
      (if (contains? acertos (str letra)) 
          (print letra " ") 
          (print "_" " ")))
  (println))

(defn jogo [vidas palavra acertos]
  (imprime-forca vidas palavra acertos)
  (cond
     (= vidas 0) (perdeu)
      ; senão
      (acertou-a-palavra-toda? palavra acertos) (ganhou)
      :else    
      (let [chute (le-letra!)]
        (if (acertou? chute palavra)
          (do
            (println "Acertou a letra!") 
            ; recursão de cauda
            (recur vidas palavra (conj acertos chute)))
          (do
            (println "Errou a letra! Perdeu vida!")
            (recur (dec vidas) palavra acertos))))))

; processamento de lista em sequencia
(->> carros
  (map (fn [x] (* x 2)))
  (reduce (fn [acc n] (+ acc n)))
)

(defn comeca-o-jogo [] (jogo total-de-vidas palavra-secreta #{}))

; executado pelo comando: lein run
; lein uberjar empacota o programa em um JAR
(defn -main [& args]
  (comeca-o-jogo)
)