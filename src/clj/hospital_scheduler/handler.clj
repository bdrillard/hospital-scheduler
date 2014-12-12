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
  (GET "/events-summary" [] (rs/read-events))
  (GET "/procedures" [] (rs/read-procedures))
  (GET "/doctors" [] (rs/read-doctors))
  (PUT "/create-event" {params :params} (rs/create-event params))
  (context "/:id" [id] (defroutes app-routes
    (POST "/update-event" {params :params} (rs/update-event id params))
    (DELETE "/delete-event" [] (rs/delete-event id)))))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (cors/wrap-cors identity)))

(defn -main [& args]
  (run-jetty #'app {:port 3000}))
