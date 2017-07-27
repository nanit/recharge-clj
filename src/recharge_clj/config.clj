(ns recharge-clj.config)

(def ^:private api-token (atom nil))
(def ^:private request-timeout (atom nil))

(defn- set-api-token! 
  "Sets the API token for Recharge API" 
  [token]
  (reset! api-token token))

(defn fetch-api-token!
  "Fetches api token or sets it to the RECHARGE_API_TOKEN environment variable" 
  []
  (cond 
    @api-token @api-token
    (System/getenv "RECHARGE_API_TOKEN") (do (set-api-token! (System/getenv "RECHARGE_API_TOKEN"))
                                             @api-token)
    :else (throw (Exception. "ReCharge API token must be set before making API requests"))))

(defn- set-request-timeout!
  "Sets the API request timeout" 
  [to]
  (reset! request-timeout to))

(defn fetch-request-timeout!
  "Fetches request timeout or uses RECHARGE_REQUEST_TIMEOUT environment variable" 
  []
  (cond 
    @request-timeout @request-timeout
    (System/getenv "RECHARGE_REQUEST_TIMEOUT") (do (set-request-timeout! (Integer. (System/getenv "RECHARGE_REQUEST_TIMEOUT")))
                                                   @request-timeout)
    :else nil))

(defn configure
  [opts]
  (when-let [t (:api-token opts)] (set-api-token! t))
  (when-let [t (:request-timeout opts)] (set-request-timeout! t)))
