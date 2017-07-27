(ns recharge-clj.endpoints.webhooks
  (:require [recharge-clj.core :refer [GET POST PUT DELETE]])
  (:refer-clojure :exclude [get list update]))

(def all-topics ["subscription/created" "subscription/activated" "subscription/cancelled" "customer/created" "order/created"])

(defn list
  []
  (GET "/webhooks" {:fetch-key :webhooks}))

(defn create
  [topic address]
  (POST "/webhooks" {:body {:address address :topic topic} :fetch-key :webhook}))

(defn create-all
  [address]
  (map #(create % address) all-topics))

(defn delete
  [id]
  (DELETE (format "/webhooks/%s" id) {:fetch-key :webhook}))

(defn delete-all
  []
  (map delete (map :id (:webhooks (list)))))
