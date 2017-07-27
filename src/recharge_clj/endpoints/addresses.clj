(ns recharge-clj.endpoints.addresses
  (:require [recharge-clj.core :refer [GET POST PUT DELETE]])
  (:refer-clojure :exclude [get list update]))

(defn get
  [id]
  (GET (format "/addresses/%s" id) {:fetch-key :address}))

(defn list
  [customer-id] (GET (format "/customers/%s/addresses" customer-id) {:fetch-key :addresses}))

(defn update
  [id changes]
  (PUT (format "/addresses/%s" id) {:body changes :fetch-key :address}))

(defn create
  [customer-id address]
  (POST (format "/customers/%s/addresses" customer-id) {:body address :fetch-key :address}))
