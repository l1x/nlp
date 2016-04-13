(ns ^{  :doc      "com.sb.ann :: main"
        :author "Istvan Szukacs"  }
  ann.core
  (:require 
    [liberator.core                 :refer [resource defresource] ]
    [ring.middleware.params         :refer [wrap-params]          ]
    [compojure.core                 :refer [defroutes ANY]        ]
    [clojure.tools.logging          :as     log                   ]
    [clojure.data.csv               :as     csv                   ]
    [clojure.java.io                :as     io                    ]
    
    )
  (:import
    [java.util
     Properties                                               ]
    [edu.stanford.nlp.pipeline
      Annotation StanfordCoreNLP                              ]
    [edu.stanford.nlp.ling
     CoreAnnotations$SentencesAnnotation                      ]
    [edu.stanford.nlp.sentiment
     SentimentCoreAnnotations$SentimentAnnotatedTree          ]
    [edu.stanford.nlp.trees
     Tree                                                     ]
    [edu.stanford.nlp.util 
     CoreMap                                                  ]
    [edu.stanford.nlp.neural.rnn 
     RNNCoreAnnotations                                       ]
    )
    (:gen-class))

;; Init for starting up CoreNLP

(def state (atom {}))

(defn init [] 
  (let [ 
        props (doto (Properties.) (.setProperty "annotators", "tokenize, ssplit , parse, sentiment"))
        pipeline (StanfordCoreNLP. props)
        ]
    ;init done
    (swap! state assoc :ok pipeline)))

;; CoreNLP functions wrapped in Clojure

(defn get-annotation 
  [txt]
  (let  [ pipeline (:ok @state)
          annotation (.process pipeline txt) ]
    ;return
    annotation))

(defn get-sentences
  [ann] 
  ;return
  (.get ann CoreAnnotations$SentencesAnnotation))

(defn get-tree
  ;return
  [sen] (.get sen SentimentCoreAnnotations$SentimentAnnotatedTree))

(defn get-sentiment 
  [tree]
  (RNNCoreAnnotations/getPredictedClass tree))

(defn sentiment 
  [txt] 
  (let [  ann       (get-annotation txt)
          sentences (get-sentences ann)   ]
  ;  
  (for [sentence sentences]
    (get-sentiment (get-tree sentence)))))

(defn read-csv-lazy 
  [file]
  (with-open [in-file (io/reader file)] 
    (doall (csv/read-csv in-file))))

(defn process-entries 
  [entries]
  (for [  vect entries  ]
       (let [ sntm (sentiment (nth vect 6)) ]
    (conj vect
      (if (empty? sntm) -1 (apply min sntm))))))

(defn write-csv-lazy
  [entries file]
  (with-open [out-file (io/writer file)]
  (csv/write-csv out-file entries)))

(defresource parameter [txt]
    :available-media-types ["text/plain"]
    :handle-ok (fn [_] (str (apply min (sentiment txt)))))

(defroutes app
    (ANY "/bar/:txt" [txt] (parameter txt)))

(def handler
    (-> app 
    wrap-params))
