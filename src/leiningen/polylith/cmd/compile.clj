(ns leiningen.polylith.cmd.compile
  (:require [leiningen.polylith.cmd.changes :as changes]
            [leiningen.polylith.cmd.shared :as shared]))


(defn find-changes [ws-path top-dir args]
  (let [changed-components (changes/changes ws-path top-dir "c" args)
        changed-bases (changes/changes ws-path top-dir "b" args)
        changed-systems (changes/changes ws-path top-dir "s" args)]
    (println)
    (apply println "Changed components:" changed-components)
    (apply println "Changed bases:" changed-bases)
    (apply println "Changed systems:" changed-systems)
    (println)
    [changed-components changed-bases changed-systems]))

(defn compile-it [ws-path dir changes]
  (doseq [change changes]
    (println "Compiling" (str dir "/" change))
    (println (shared/sh "lein" "compile" :dir (str ws-path "/" dir "/" change)))))

(defn compile-changes [ws-path components bases systems]
  (println "Compiling interfaces")
  (println (shared/sh "lein" "install" :dir (str ws-path "/interfaces")))
  (compile-it ws-path "components" components)
  (compile-it ws-path "bases" bases)
  (compile-it ws-path "systems" systems))

(defn execute [ws-path top-dir args]
  (let [[changed-components
         changed-bases
         changed-systems] (find-changes ws-path top-dir args)]
    (compile-changes ws-path changed-components changed-bases changed-systems)))