(ns recharge-clj.endpoints.orders
  (:require [recharge-clj.core :refer [GET POST PUT DELETE]])
  (:refer-clojure :exclude [get list update]))

(defn get
  [id]
  (GET (format "/orders/%s" id) {:fetch-key :order}))

(defn list
  ([] (list {}))
  ([filters] (GET "/orders" {:query-params filters :fetch-key :orders})))
