(ns leiningen.polylith.cmd.create-workspace-test
  (:require [clojure.test :refer :all]
            [leiningen.polylith :as polylith]
            [leiningen.polylith.file :as file]
            [leiningen.polylith.cmd.test-helper :as helper]
            [leiningen.polylith.version :as v]))

(use-fixtures :each helper/test-setup-and-tear-down)

(defn interfaces-project-content [ns-name]
  [['defproject ns-name "1.0"
     :description "Component interfaces"
     :dependencies [['org.clojure/clojure "1.9.0"]]
     :aot
     :all]])

(defn workspace-project-content [project-ns top-ns]
  [['defproject project-ns "1.0"
    :description "A Polylith workspace."
    :plugins [['polylith/lein-polylith v/version]]
    :polylith {:clojure-version      "1.9.0"
               :top-namespace        top-ns}]])

(defn development-project-content [ns-name]
  [['defproject ns-name "1.0"
    :description "The main development environment"
    :dependencies [['org.clojure/clojure "1.9.0"]]]])

(def gitignore-content
  ['**/target
   '**/pom.xml
   '**/.idea
   '*.iml
   '.nrepl-port
   '.lein-env
   'crash.log
   '.polylith/time.local.edn])

(deftest polylith-create--missing-namespace--show-error-message
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [output (with-out-str
                   (polylith/polylith nil "create" "w" "ws1"))]
      (is (= "Missing namespace name.\n"
             output)))))

(deftest polylith-create--create-workspace--creates-a-workspace-with-namespace
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [ws-dir (str @helper/root-dir "/ws1")]
      (polylith/polylith nil "create" "w" "ws1" "my.company")

      (is (= #{".gitignore"
               ".polylith"
               ".polylith/time.local.edn"
               "Readme.md"
               "logo.png"
               "interfaces/src/my/company"
               "interfaces/src/my"
               "interfaces/src"
               "interfaces/project.clj"
               "interfaces"
               "systems"
               "components"
               "bases"
               "environments/development/src/my/company"
               "environments/development/src/my"
               "environments/development/src"
               "environments/development/interfaces/my/company"
               "environments/development/interfaces/my"
               "environments/development/interfaces"
               "environments/development/docs"
               "environments/development/project-files/workspace-project.clj"
               "environments/development/project-files/interfaces-project.clj"
               "environments/development/project-files/systems"
               "environments/development/project-files/components"
               "environments/development/project-files/bases"
               "environments/development/project-files"
               "environments/development/resources"
               "environments/development/resources/.keep"
               "environments/development/test/my/company"
               "environments/development/test/my"
               "environments/development/test"
               "environments/development/project.clj"
               "environments/development"
               "environments"
               "project.clj"}
             (set (file/relative-paths ws-dir))))

      (is (= (interfaces-project-content 'my.company/interfaces)
             (helper/content ws-dir "interfaces/project.clj")))

      (is (= (workspace-project-content 'my.company/development "my.company")
             (helper/content ws-dir "environments/development/project-files/workspace-project.clj")))

      (is (= (interfaces-project-content 'my.company/interfaces)
             (helper/content ws-dir "environments/development/project-files/interfaces-project.clj")))

      (is (= (development-project-content 'my.company/development)
             (helper/content ws-dir "environments/development/project.clj")))

      (is (= gitignore-content
             (helper/content ws-dir ".gitignore"))))))

(deftest polylith-create--create-workspace--creates-a-workspace-without-namespace
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [ws-dir (str @helper/root-dir "/ws1")]
      (polylith/polylith nil "create" "w" "ws1" "")

      (is (= #{".gitignore"
               ".polylith"
               ".polylith/time.local.edn"
               "Readme.md"
               "logo.png"
               "interfaces/src"
               "interfaces/project.clj"
               "interfaces"
               "systems"
               "components"
               "bases"
               "environments/development/src"
               "environments/development/interfaces"
               "environments/development/docs"
               "environments/development/project-files/workspace-project.clj"
               "environments/development/project-files/interfaces-project.clj"
               "environments/development/project-files/systems"
               "environments/development/project-files/components"
               "environments/development/project-files/bases"
               "environments/development/project-files"
               "environments/development/resources"
               "environments/development/resources/.keep"
               "environments/development/test"
               "environments/development/project.clj"
               "environments/development"
               "environments"
               "project.clj"}
             (set (file/relative-paths ws-dir))))

      (is (= (interfaces-project-content 'interfaces)
             (helper/content ws-dir "interfaces/project.clj")))

      (is (= (workspace-project-content 'development "")
             (helper/content ws-dir "environments/development/project-files/workspace-project.clj")))

      (is (= (interfaces-project-content 'interfaces)
             (helper/content ws-dir "environments/development/project-files/interfaces-project.clj")))

      (is (= (development-project-content 'development)
             (helper/content ws-dir "environments/development/project.clj")))

      (is (= (slurp (clojure.java.io/resource "Readme.md"))
             (slurp (str ws-dir "/Readme.md"))))

      (is (= (slurp (clojure.java.io/resource "logo.png"))
             (slurp (str ws-dir "/logo.png"))))

      (is (= gitignore-content
             (helper/content ws-dir ".gitignore"))))))
