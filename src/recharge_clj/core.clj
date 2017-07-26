(ns recharge-clj.core
  (:require [org.httpkit.client :as http]
            [cheshire.core :refer [generate-string parse-string]]))

(def api-token (atom nil))
(def ^:private base-url "https://api.rechargeapps.com")

(defn set-api-token! 
  "Sets the API token for Recharge API" 
  [token]
  (reset! api-token token))

(defn- fetch-api-token!
  "Fetches api token or sets it to the RECHARGE_API_TOKEN environment variable" 
  []
  (cond 
    @api-token @api-token
    (System/getenv "RECHARGE_API_TOKEN") (do (reset! api-token (System/getenv "RECHARGE_API_TOKEN"))
                                             @api-token)
    :else (throw (Exception. "ReCharge API api token must be set before making API requests"))))

(defn- options [opts]
  (merge 
    {:timeout 2000
     :headers {"Accept" "application/json"
               "X-Recharge-Access-Token" (fetch-api-token!)
               "Content-Type" "application/json"}}
    opts))

(defn- url 
  "Composes url parts on top of base-url" 
  [path]
  (str base-url path))

(defn- parse-response
  [req]
  (let [{:keys [status body]} @req]
    {:status status :body (parse-string body true)}))

(defn- request 
  "Launches an HTTP request to Recharge's API" 
  [method path opts]
  (parse-response (method (url path) (options opts))))

(defn POST 
  "Launches a POST request to Recharge's API. Body is serialized as JSON." 
  [path body]
  (request http/post path {:body (generate-string body)}))

(defn PUT 
  "Launches a PUT request to Recharge's API. Body is serialized as JSON." 
  [path body]
  (request http/put path {:body (generate-string body)}))

(defn GET 
  "Launches a GET request to Recharge's API" 
  ([path] (GET path {}))
  ([path query-params] (request http/get path {:query-params query-params})))

(defn DELETE 
  "Launches a DELETE request to Recharge's API" 
  ([path] (DELETE path {}))
  ([path query-params] (request http/delete path {:query-params query-params})))
