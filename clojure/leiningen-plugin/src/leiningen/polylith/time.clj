(ns leiningen.polylith.time
  (:require [clojure.pprint :as pp]
            [leiningen.polylith.file :as file])
  (:import (java.io FileNotFoundException)
           (java.util Date)
           (java.text SimpleDateFormat)))

(defn time-bookmarks [ws-path]
  (try
    (read-string (slurp (str ws-path "/time.edn")))
    (catch FileNotFoundException _ {})))

(defn last-successful-build-time [ws-path]
  (or (:last-successful-build (time-bookmarks ws-path))
      0))

(defn paths-except-time [ws-path]
  (filter #(not= (str %)
                 (str ws-path "/time.edn"))
          (file/paths ws-path)))

(defn set-last-successful-build! [ws-path]
  (let [paths (paths-except-time ws-path)
        latest-change (file/latest-modified paths)
        bookmarks (assoc (time-bookmarks ws-path)
                    :last-successful-build latest-change)
        file (str ws-path "/time.edn")]
    (pp/pprint bookmarks (clojure.java.io/writer file))))

(def formatter (SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss"))

(defn ->time [timestamp]
  (let [time (.format formatter (Date. timestamp))]
    (str (subs time 0 10) " "
         (subs time 11 16) ":"
         (subs time 17 19))))
