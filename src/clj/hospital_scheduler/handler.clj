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
  (GET "/events-summary" [] (response rs/read-summary))
  (PUT "/create-event" {:params params} (rs/create-event params))
  (POST "/update-event/:id" {:params params} (rs/update-event id params))
  (DELETE "/delete-event/:id" [] (rs/delete-event id)))
