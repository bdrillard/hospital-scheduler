(ns hospital-scheduler.routes
  "Functions to translate REST calls to database operations"
  (:use clojure.java.jdbc
        environ.core)
  (:require [ring.util.response :refer [response]]
            [yesql.core :refer [defquery]]))

;;;; Routes served by database operations
;; database connector parameters
(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname (str "//" (env :db-url) ":" (env :db-port) "/" (env :db-name))
         :user (env :db-user)
         :password (env :db-pass)})

;; SQL queries and operations over event data
(defquery select-events "sql/select-events.sql")
(defquery select-event "sql/select-event.sql")
(defquery insert-event! "sql/insert-event.sql")
(defquery update-event! "sql/update-event.sql")
(defquery delete-event! "sql/delete-event.sql")
(defquery select-collisions "sql/select-collisions.sql")

(defn collision?
  "Determine if any events exist with a given doctor already scheduled at
  a given time"
  [id time doctor_id]
  (if (seq (select-collisions db id time doctor_id))
    true
    false))

(defn read-events 
  "Select a listing of registered events"
  []
  {:body (select-events db)})

(defn create-event
  "Register a new event.
  Disallow a doctor from being registered two different events at the same time"
  [{:keys [time patient doctor_id procedure_id descr]}]
  (if (collision? -1 time doctor_id)
    (response {:status 403
               :body {:error (str "An event at " time " was already scheduled for the selected doctor")}})
    (do
      (insert-event! db time patient doctor_id procedure_id descr)
      (response {:status 200
                 :body (str "Event scheduled")}))))

(defn merge-new
  "Returns an event dictionary where entries of the old event have been
  reassociated with updated entries"
  [old-event event-updates]
  (loop [event-keys (keys old-event) output-event {}]
    (if (seq event-keys)
      (let [key-to-check (first event-keys)]
        (if (and (contains? event-updates key-to-check) 
                 (not= (key-to-check old-event) (key-to-check event-updates)))
          (recur (rest event-keys) (assoc output-event key-to-check (key-to-check event-updates)))
          (recur (rest event-keys) (assoc output-event key-to-check (key-to-check old-event)))))
      output-event)))

(defn update-event
  "Update an event given its unique ID and a map of values to change"
  [id event-updates]
  (let [old-event (first (select-event db id)) ; select-event on unique id returns single element list
        {:keys [id time patient doctor_id procedure_id descr]} (merge-new old-event event-updates)]
    (if (collision? id time doctor_id)
      (response {:status 403
                 :body {:error (str "An event at " time " was already scheduled for the selected doctor")}})
      (do
        (update-event! db time patient doctor_id procedure_id descr id)
        (response {:status 200
                   :body (str "Event updated")})))))

(defn delete-event
  "Delete an event given its unique ID"
  [id]
  (do
    (delete-event! db id)
    (response {:status 200
	       :body (str "Event deleted")})))
