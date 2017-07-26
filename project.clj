(defproject recharge-clj "0.1.0"
  :description "Clojure client for Recharge API"
  :url "https://github.com/nanit/recharge-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [cheshire "5.6.3"]
                 [http-kit "2.2.0"]]
  :repl-options {:init-ns dev}
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.3"]]}})
