(defproject com.nomistech/serdes "0.1.0-SNAPSHOT"
  :url         "https://github.com/simon-katz/com-nomistech-serdes"
  :description "EDN-like serdes, but with \"nomis-edn: \" prepended"
  :license     {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
                :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure            "1.10.0"]
                 [org.apache.kafka/kafka-clients "2.8.0"]]

  :aot [com.nomistech.serdes.nomis-edn]

  :repl-options {:init-ns dev}

  :profiles {:dev     {:source-paths ["dev"]}
             :uberjar {:aot :all}})
