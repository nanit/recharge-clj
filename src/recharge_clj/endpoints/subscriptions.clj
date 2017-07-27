(ns recharge-clj.endpoints.subscriptions
  (:require [recharge-clj.core :refer [GET POST PUT DELETE]])
  (:refer-clojure :exclude [get list update]))

(defn get
  [id]
  (GET (format "/subscriptions/%s" id) {:fetch-key :subscription}))

(defn list
  ([] (list {}))
  ([filters] (GET "/subscriptions" {:query-params filters :fetch-key :subscriptions})))

(defn cancel [id reason] 
  (POST (format "/subscriptions/%s/cancel" id) {:body {:cancellation_reason reason} :fetch-key :subscription}))

(defn activate [id] 
  (POST (format "/subscriptions/%s/activate" id) {:body {} :fetch-key :subscription}))

(defn set-next-charge-date
  [id date]
  (POST (format "/subscriptions/%s/set_next_charge_date" id) {:body {:date date} :fetch-key :subscription}))

(defn update
  [id changes]
  (PUT (format "/subscriptions/%s" id) {:body changes :fetch-key :subscription}))

(defn create
  [address_id subscription]
  (POST "/subscriptions" {:body (assoc subscription :address_id address_id) :fetch-key :subscription}))
