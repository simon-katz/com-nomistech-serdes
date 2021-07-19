(ns com.nomistech.serdes
  (:require [com.nomistech.serdes.nomis-edn :as nomis-edn])
  (:import (org.apache.kafka.common.serialization Serdes)))

;;;; Based on `jackdaw.serdes`.

(set! *warn-on-reflection* true)

(defn nomis-edn-serde
  "Implements a Nomis EDN serde."
  [& [opts]]
  (Serdes/serdeFrom (nomis-edn/nomis-edn-serializer)
                    (nomis-edn/nomis-edn-deserializer opts)))
