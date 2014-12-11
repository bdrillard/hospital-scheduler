(ns hospital-scheduler.handler
  "Server routes handler"
  (:gen-class)
  (:use compojure.core
        ring.adapter.jetty
        environ.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.params :as wrap-params]
            [ring.middleware.json :as middleware]
            [ring.middleware.cors :as cors]
            [hospital-scheduler.routes :as rs]))

;; HTTP routes
(defroutes app-routes
  (GET "/events-summary" [] (response rs/read-events))
  (PUT "/create-event" {params :params} (rs/create-event params))
  (POST "/update-event/:id" {id :id params :params} (rs/update-event id params))
  (DELETE "/delete-event/:id" [id] (rs/delete-event [id])))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (cors/wrap-cors (re-pattern (str "^http://"
                                       (env :db-url) ":" (env :db-port)
                                       "/events-summary$")))))

(defn -main [& args]
  (run-jetty #'app {:port 3000}))
