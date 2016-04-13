(defproject ann "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                  [org.clojure/clojure                "1.7.0"   ]
                  ;edu.stanford.nlp
                  [edu.stanford.nlp/stanford-corenlp  "3.6.0"   ]
                  [edu.stanford.nlp/stanford-corenlp  "3.6.0"   :classifier "models" ]
                  ;[edu.stanford.nlp/stanford-parser   "3.6.0"   ]
                  [liberator                          "0.14.1"  ]
                  [ring/ring-core                     "1.4.0"   ]
                  [compojure                          "1.5.0"   ]
                  [org.clojure/tools.cli              "0.3.3"   ]
                  [org.clojure/tools.logging          "0.3.1"   ]
                  [org.slf4j/slf4j-log4j12            "1.7.12"  ]
                  [log4j/log4j                        "1.2.17"  ]
                  [org.clojure/data.csv               "0.1.3"   ]
                 ]
  
  :plugins [[lein-ring "0.9.7"]]
  :ring {
         :init    ann.core/init
         :handler ann.core/handler}
  
  :repl-options {:init-ns ann.core}
  )
