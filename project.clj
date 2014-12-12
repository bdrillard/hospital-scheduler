(defproject hospital-scheduler "0.1.0-SNAPSHOT"
  :description "REST API backend for 448 final project"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-time "0.6.0"]
                 [ring "1.3.0"]
                 [ring/ring-json "0.3.1"]
                 [jumblerg/ring.middleware.cors "1.0.1"]
                 [compojure "1.1.8"]
                 [yesql "0.4.0"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [mysql/mysql-connector-java "5.1.6"]
                 [environ "1.0.0"]]
  :plugins [[lein-ring "0.8.11"]
            [lein-environ "1.0.0"]]
  :source-paths ["src/clj"]
  :main hospital-scheduler.handler
  :ring {:handler hospital-scheduler.handler/app}
  :profiles {:dev {:env {:db-url "localhost" ; localhost
                         :db-port 3306
                         :db-name "scheduler"
                         :db-user "root"
                         :db-pass "eecs448!"}}}) ; password
