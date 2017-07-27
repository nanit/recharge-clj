(ns recharge-clj.core
  (:require [org.httpkit.client :as http]
            [recharge-clj.config :as config]
            [cheshire.core :refer [generate-string parse-string]]))

(def ^:private base-url "https://api.rechargeapps.com")

(def configure config/configure)

(defn- options [opts]
  (-> opts
      (select-keys [:body :query-params])
      (update :body generate-string)
      (cond-> 
        (config/fetch-request-timeout!) (assoc :timeout (config/fetch-request-timeout!)))
      (merge 
        {:insecure? true
         :headers {"Accept" "application/json"
                   "X-Recharge-Access-Token" (config/fetch-api-token!)
                   "Content-Type" "application/json"}})))

(defn- url 
  "Composes url parts on top of base-url" 
  [path]
  (str base-url path))

(defn- parse-response
  [req fetch-key]
  (let [{:keys [status body error] :as response} @req]
    (if error
      response
      (let [parsed-body (parse-string body true)
            http-error (not= status 200)
            parsed-response {:status status :body parsed-body :error http-error}]
        (cond
          http-error parsed-response
          fetch-key (assoc parsed-response fetch-key (fetch-key parsed-body))
          :else parsed-response)))))

(defn- request 
  "Launches an HTTP request to Recharge's API" 
  [method path opts]
  (parse-response (method (url path) (options opts)) (:fetch-key opts)))

(defn POST 
  "Launches a POST request to Recharge's API. Body is serialized as JSON." 
  [path opts]
  (request http/post path opts))

(defn PUT 
  "Launches a PUT request to Recharge's API. Body is serialized as JSON." 
  [path opts]
  (request http/put path opts))

(defn GET 
  "Launches a GET request to Recharge's API" 
  [path opts] (request http/get path opts))

(defn DELETE 
  "Launches a DELETE request to Recharge's API" 
  ([path] (DELETE path {}))
  ([path query-params] (request http/delete path {:query-params query-params})))
