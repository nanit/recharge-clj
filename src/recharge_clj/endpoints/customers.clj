(ns recharge-clj.endpoints.customers
  (:require [recharge-clj.core :refer [GET POST PUT DELETE]])
  (:refer-clojure :exclude [get list update]))

(defn get
  [id]
  (GET (format "/customers/%s" id) {:fetch-key :customer}))

(defn list
  ([] (list {}))
  ([filters] (GET "/customers" {:query-params filters :fetch-key :customers})))

(defn update
  [id changes]
  (PUT (format "/customers/%s" id) {:body changes :fetch-key :customer}))

(defn create
  [customer]
  (POST "/customers" {:body customer :fetch-key :customer}))
