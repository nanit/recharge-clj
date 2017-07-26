(ns recharge-clj.subscriptions
  (:require [recharge-clj.core :refer [GET POST PUT DELETE]])
  (:refer-clojure :exclude [get]))

(defn get
  [id]
  (GET (format "/subscriptions/%s" id)))
