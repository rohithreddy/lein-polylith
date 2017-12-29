(ns leiningen.polylith.cmd
  (:require [clojure.pprint :as p]
            [clojure.string :as str]
            [leiningen.polylith.cmd.create :as create-cmd]
            [leiningen.polylith.core :as core]
            [leiningen.polylith.file :as file]
            [leiningen.polylith.help :as help]
            [leiningen.polylith.info :as info]
            [leiningen.polylith.match :as match]
            [leiningen.polylith.validate :as validate]
            [leiningen.polylith.version :as v]))

(defn help [[cmd]]
  (condp = cmd
    "changes" (help/changes)
    "create" (help/create)
    "delete" (help/delete)
    "deps" (help/deps)
    "diff" (help/diff)
    "info" (help/info)
    "project" (help/project)
    "settings" (help/settings)
    "tests" (help/tests)
    (help/help)))

(defn changes [root-path [cmd last-success-sha1 current-sha1]]
  (if (nil? current-sha1)
    (do
      (println "Missing parameters.")
      (help/changes))
    (doseq [dir (core/changes root-path cmd last-success-sha1 current-sha1)]
      (println (str " " dir)))))

(defn delete [root-dir dev-dirs [cmd name]]
  (let [[ok? msg] (validate/delete root-dir cmd name)]
    (if ok?
      (condp = cmd
        "c" (core/delete root-dir dev-dirs name))
      (do
        (println msg)
        (help/delete)))))

(defn ns->component [nspace]
  (first (str/split (namespace nspace) #"\.")))

(defn print-component-deps [dependencies]
  (doseq [component (keys dependencies)]
    (println (str component ":"))
    (let [apis (sort (set (map ns->component (dependencies component))))]
      (doseq [api apis]
        (println " " api)))))

(defn print-api-deps [dependencies]
  (doseq [component (keys dependencies)]
    (println (str component ":"))
    (doseq [nspace (dependencies component)]
      (println " " nspace))))

(defn deps [root-dir [arg]]
  (let [dependencies (core/all-dependencies root-dir)]
    (if (= "f" arg)
      (print-api-deps dependencies)
      (print-component-deps dependencies))))

(defn diff [root-dir [last-success-sha1 current-sha1]]
  (if (or (nil? current-sha1)
          (nil? last-success-sha1))
    (do
      (println "Missing parameters.")
      (help/diff))
    (let [paths (core/diff root-dir last-success-sha1 current-sha1)]
      (doseq [path paths]
        (println " " path)))))

(defn info [root-dir args]
  (let [cmd (first args)
        a? (= "a" cmd)
        filter? (= 1 (count cmd))
        [show-changed?
         show-unchanged?
         show-apis?] (if filter?
                            [(or a? (= "c" cmd))
                             (or a? (= "u" cmd))
                             a?]
                            [true true false])
        [last-success-sha1
         current-sha1] (if filter? (rest args) args)
        data (if (and last-success-sha1 current-sha1)
               (core/info root-dir last-success-sha1 current-sha1)
               (core/info root-dir))]
    (info/print-info data show-changed? show-unchanged? show-apis?)))

(defn ->dir [ws-ns top-dir]
  (or top-dir
    (str/replace ws-ns #"\." "/")))

(defn create [root-dir top-dir top-ns dev-dirs [cmd name ws-ns ws-top-dir]]
  (let [[ok? msg] (validate/create root-dir top-ns cmd name ws-ns)]
    (if ok?
      (condp = cmd
        "c" (create-cmd/create-component root-dir top-dir top-ns dev-dirs name)
        "w" (create-cmd/create-workspace (file/current-path) name ws-ns (->dir ws-ns ws-top-dir)))
      (do
        (println msg)
        (help/create)))))

(defn settings [root-dir settings]
  (println "root-dir:")
  (println " " root-dir)
  (println "settings:")
  (doseq [[k d] settings]
    (println " " k d)))

(defn tests [root-dir ignore-tests [cmd last-success-sha1 current-sha1]]
  (if (nil? cmd)
    (do
      (println "Missing parameters.")
      (help/tests))
    (let [u? (str/includes? cmd "u")
          i? (str/includes? cmd "i")
          single-line-statement? (str/includes? cmd "-")
          execute? (str/includes? cmd "+")
          tests (match/filter-tests
                  (if (and last-success-sha1 current-sha1)
                    (core/tests root-dir [u? i?] [last-success-sha1 current-sha1])
                    (core/tests root-dir [u? i?]))
                  ignore-tests)]
      (if execute?
        (core/run-tests tests single-line-statement?)
        (core/show-tests tests single-line-statement?)))))

(defn task-not-found [subtask]
  (println "Subtask" subtask "not found.")
  (help/help))
