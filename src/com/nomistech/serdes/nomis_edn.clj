(ns com.nomistech.serdes.nomis-edn
  "Implements a hacked EDN serdes, with a prefix in the serialized data."
  (:require [clojure.edn]
            [jackdaw.serdes.fn :as jsfn])
  (:import (java.nio.charset StandardCharsets))
  (:gen-class
   :implements [org.apache.kafka.common.serialization.Serde]
   :name com.nomistech.serdes.NomisEdnSerde
   :prefix "NomisEdnSerde-"))

;;;; Based on `jackdaw.serdes.edn2`.

(set! *warn-on-reflection* true)

(defn to-bytes
  "Converts a string to a byte array."
  [data]
  (.getBytes ^String data StandardCharsets/UTF_8))

(defn- from-bytes
  "Converts a byte array to a string."
  [^bytes data]
  (String. data StandardCharsets/UTF_8))

(def ^:private nomis-edn-prefix "nomis-edn: ")

(defn nomis-edn-serializer
  "Return a Nomis EDN serializer."
  []
  (jsfn/new-serializer
   {:serialize (fn [_ _ data]
                 (when data
                   (let [s (str nomis-edn-prefix
                                (pr-str data))]
                     (to-bytes s))))}))

(defn nomis-edn-deserializer
  "Return a Nomis EDN deserializer."
  ([]
   (nomis-edn-deserializer {}))
  ([opts]
   (let [opts (into {} opts)]
     (jsfn/new-deserializer
      {:deserialize (fn [_ _ data]
                      (let [s (from-bytes data)]
                        (->> (subs s
                                   (count nomis-edn-prefix))
                             (clojure.edn/read-string opts))))}))))

(def NomisEdnSerde-configure
  (constantly nil))

(defn NomisEdnSerde-serializer
  [& _]
  (nomis-edn-serializer))

(defn NomisEdnSerde-deserializer
  [& _]
  (nomis-edn-deserializer))
