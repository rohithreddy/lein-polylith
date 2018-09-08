(ns leiningen.polylith.cmd.remove-test
  (:require [clojure.test :refer :all]
            [leiningen.polylith :as polylith]
            [leiningen.polylith.cmd.test-helper :as helper]
            [leiningen.polylith.file :as file]))

(use-fixtures :each helper/test-setup-and-tear-down)

(deftest polylith-remove--remove-component-from-system-with-namespace--component-removed
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [ws-dir  (str @helper/root-dir "/ws1")
          project (helper/settings ws-dir "my.company")]
      (polylith/polylith nil "create" "w" "ws1" "my.company" "-git")
      (polylith/polylith project "create" "s" "sys-1" "base-1")
      (polylith/polylith project "create" "c" "comp-1")
      (polylith/polylith project "create" "c" "comp-2" "ifc-2")
      (polylith/polylith project "add" "comp-1" "sys-1")
      (polylith/polylith project "add" "comp-2" "sys-1")
      (polylith/polylith project "remove" "comp-2" "sys-1")

      (is (= #{".gitignore"
               ".polylith"
               ".polylith/time.edn"
               "bases"
               "bases/base-1"
               "bases/base-1/project.clj"
               "bases/base-1/readme.md"
               "bases/base-1/resources"
               "bases/base-1/resources/.keep"
               "bases/base-1/resources/base-1"
               "bases/base-1/resources/base-1/.keep"
               "bases/base-1/src"
               "bases/base-1/src/my"
               "bases/base-1/src/my/company"
               "bases/base-1/src/my/company/base_1"
               "bases/base-1/src/my/company/base_1/core.clj"
               "bases/base-1/test"
               "bases/base-1/test/my"
               "bases/base-1/test/my/company"
               "bases/base-1/test/my/company/base_1"
               "bases/base-1/test/my/company/base_1/core_test.clj"
               "components"
               "components/comp-1"
               "components/comp-1/project.clj"
               "components/comp-1/readme.md"
               "components/comp-1/resources"
               "components/comp-1/resources/.keep"
               "components/comp-1/resources/comp-1"
               "components/comp-1/resources/comp-1/.keep"
               "components/comp-1/src"
               "components/comp-1/src/my"
               "components/comp-1/src/my/company"
               "components/comp-1/src/my/company/comp_1"
               "components/comp-1/src/my/company/comp_1/core.clj"
               "components/comp-1/src/my/company/comp_1/interface.clj"
               "components/comp-1/test"
               "components/comp-1/test/my"
               "components/comp-1/test/my/company"
               "components/comp-1/test/my/company/comp_1"
               "components/comp-1/test/my/company/comp_1/core_test.clj"
               "components/comp-2"
               "components/comp-2/project.clj"
               "components/comp-2/readme.md"
               "components/comp-2/resources"
               "components/comp-2/resources/.keep"
               "components/comp-2/resources/comp-2"
               "components/comp-2/resources/comp-2/.keep"
               "components/comp-2/src"
               "components/comp-2/src/my"
               "components/comp-2/src/my/company"
               "components/comp-2/src/my/company/comp_2"
               "components/comp-2/src/my/company/comp_2/core.clj"
               "components/comp-2/src/my/company/ifc_2"
               "components/comp-2/src/my/company/ifc_2/interface.clj"
               "components/comp-2/test"
               "components/comp-2/test/my"
               "components/comp-2/test/my/company"
               "components/comp-2/test/my/company/comp_2"
               "components/comp-2/test/my/company/comp_2/core_test.clj"
               "doc"
               "doc/style.css"
               "environments"
               "environments/development"
               "environments/development/docs"
               "environments/development/docs/base-1-readme.md"
               "environments/development/docs/comp-1-readme.md"
               "environments/development/docs/comp-2-readme.md"
               "environments/development/docs/sys-1-readme.md"
               "environments/development/interfaces"
               "environments/development/interfaces/my"
               "environments/development/interfaces/my/company"
               "environments/development/interfaces/my/company/comp_1"
               "environments/development/interfaces/my/company/comp_1/interface.clj"
               "environments/development/interfaces/my/company/ifc_2"
               "environments/development/interfaces/my/company/ifc_2/interface.clj"
               "environments/development/project-files"
               "environments/development/project-files/bases"
               "environments/development/project-files/bases/base-1-project.clj"
               "environments/development/project-files/components"
               "environments/development/project-files/components/comp-1-project.clj"
               "environments/development/project-files/components/comp-2-project.clj"
               "environments/development/project-files/interfaces-project.clj"
               "environments/development/project-files/systems"
               "environments/development/project-files/systems/sys-1-project.clj"
               "environments/development/project-files/workspace-project.clj"
               "environments/development/project.clj"
               "environments/development/resources"
               "environments/development/resources/.keep"
               "environments/development/resources/base-1"
               "environments/development/resources/base-1/.keep"
               "environments/development/resources/comp-1"
               "environments/development/resources/comp-1/.keep"
               "environments/development/resources/comp-2"
               "environments/development/resources/comp-2/.keep"
               "environments/development/src"
               "environments/development/src/my"
               "environments/development/src/my/company"
               "environments/development/src/my/company/base_1"
               "environments/development/src/my/company/base_1/core.clj"
               "environments/development/src/my/company/comp_1"
               "environments/development/src/my/company/comp_1/core.clj"
               "environments/development/src/my/company/comp_1/interface.clj"
               "environments/development/src/my/company/comp_2"
               "environments/development/src/my/company/comp_2/core.clj"
               "environments/development/src/my/company/ifc_2"
               "environments/development/src/my/company/ifc_2/interface.clj"
               "environments/development/test"
               "environments/development/test/my"
               "environments/development/test/my/company"
               "environments/development/test/my/company/base_1"
               "environments/development/test/my/company/base_1/core_test.clj"
               "environments/development/test/my/company/comp_1"
               "environments/development/test/my/company/comp_1/core_test.clj"
               "environments/development/test/my/company/comp_2"
               "environments/development/test/my/company/comp_2/core_test.clj"
               "interfaces"
               "interfaces/project.clj"
               "interfaces/src"
               "interfaces/src/my"
               "interfaces/src/my/company"
               "interfaces/src/my/company/comp_1"
               "interfaces/src/my/company/comp_1/interface.clj"
               "interfaces/src/my/company/ifc_2"
               "interfaces/src/my/company/ifc_2/interface.clj"
               "logo.png"
               "project.clj"
               "readme.md"
               "systems"
               "systems/sys-1"
               "systems/sys-1/build.sh"
               "systems/sys-1/project.clj"
               "systems/sys-1/readme.md"
               "systems/sys-1/resources"
               "systems/sys-1/resources/.keep"
               "systems/sys-1/resources/base-1"
               "systems/sys-1/resources/base-1/.keep"
               "systems/sys-1/resources/comp-1"
               "systems/sys-1/resources/comp-1/.keep"
               "systems/sys-1/src"
               "systems/sys-1/src/my"
               "systems/sys-1/src/my/company"
               "systems/sys-1/src/my/company/base_1"
               "systems/sys-1/src/my/company/base_1/core.clj"
               "systems/sys-1/src/my/company/comp_1"
               "systems/sys-1/src/my/company/comp_1/core.clj"
               "systems/sys-1/src/my/company/comp_1/interface.clj"}
             (set (file/relative-paths ws-dir)))))))

(deftest polylith-remove--remove-component-from-system-without-namespace--component-removed
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [ws-dir  (str @helper/root-dir "/ws1")
          project (helper/settings ws-dir "")]
      (polylith/polylith nil "create" "w" "ws1" "-" "-git")
      (polylith/polylith project "create" "s" "sys1" "base1")
      (polylith/polylith project "create" "c" "comp1")
      (polylith/polylith project "create" "c" "comp2" "ifc2")
      (polylith/polylith project "add" "comp1" "sys1")
      (polylith/polylith project "add" "comp2" "sys1")
      (polylith/polylith project "remove" "comp2" "sys1")

      (is (= #{".gitignore"
               ".polylith"
               ".polylith/time.edn"
               "bases"
               "bases/base1"
               "bases/base1/project.clj"
               "bases/base1/readme.md"
               "bases/base1/resources"
               "bases/base1/resources/.keep"
               "bases/base1/resources/base1"
               "bases/base1/resources/base1/.keep"
               "bases/base1/src"
               "bases/base1/src/base1"
               "bases/base1/src/base1/core.clj"
               "bases/base1/test"
               "bases/base1/test/base1"
               "bases/base1/test/base1/core_test.clj"
               "components"
               "components/comp1"
               "components/comp1/project.clj"
               "components/comp1/readme.md"
               "components/comp1/resources"
               "components/comp1/resources/.keep"
               "components/comp1/resources/comp1"
               "components/comp1/resources/comp1/.keep"
               "components/comp1/src"
               "components/comp1/src/comp1"
               "components/comp1/src/comp1/core.clj"
               "components/comp1/src/comp1/interface.clj"
               "components/comp1/test"
               "components/comp1/test/comp1"
               "components/comp1/test/comp1/core_test.clj"
               "components/comp2"
               "components/comp2/project.clj"
               "components/comp2/readme.md"
               "components/comp2/resources"
               "components/comp2/resources/.keep"
               "components/comp2/resources/comp2"
               "components/comp2/resources/comp2/.keep"
               "components/comp2/src"
               "components/comp2/src/comp2"
               "components/comp2/src/comp2/core.clj"
               "components/comp2/src/ifc2"
               "components/comp2/src/ifc2/interface.clj"
               "components/comp2/test"
               "components/comp2/test/comp2"
               "components/comp2/test/comp2/core_test.clj"
               "doc"
               "doc/style.css"
               "environments"
               "environments/development"
               "environments/development/docs"
               "environments/development/docs/base1-readme.md"
               "environments/development/docs/comp1-readme.md"
               "environments/development/docs/comp2-readme.md"
               "environments/development/docs/sys1-readme.md"
               "environments/development/interfaces"
               "environments/development/interfaces/comp1"
               "environments/development/interfaces/comp1/interface.clj"
               "environments/development/interfaces/ifc2"
               "environments/development/interfaces/ifc2/interface.clj"
               "environments/development/project-files"
               "environments/development/project-files/bases"
               "environments/development/project-files/bases/base1-project.clj"
               "environments/development/project-files/components"
               "environments/development/project-files/components/comp1-project.clj"
               "environments/development/project-files/components/comp2-project.clj"
               "environments/development/project-files/interfaces-project.clj"
               "environments/development/project-files/systems"
               "environments/development/project-files/systems/sys1-project.clj"
               "environments/development/project-files/workspace-project.clj"
               "environments/development/project.clj"
               "environments/development/resources"
               "environments/development/resources/.keep"
               "environments/development/resources/base1"
               "environments/development/resources/base1/.keep"
               "environments/development/resources/comp1"
               "environments/development/resources/comp1/.keep"
               "environments/development/resources/comp2"
               "environments/development/resources/comp2/.keep"
               "environments/development/src"
               "environments/development/src/base1"
               "environments/development/src/base1/core.clj"
               "environments/development/src/comp1"
               "environments/development/src/comp1/core.clj"
               "environments/development/src/comp1/interface.clj"
               "environments/development/src/comp2"
               "environments/development/src/comp2/core.clj"
               "environments/development/src/ifc2"
               "environments/development/src/ifc2/interface.clj"
               "environments/development/test"
               "environments/development/test/base1"
               "environments/development/test/base1/core_test.clj"
               "environments/development/test/comp1"
               "environments/development/test/comp1/core_test.clj"
               "environments/development/test/comp2"
               "environments/development/test/comp2/core_test.clj"
               "interfaces"
               "interfaces/project.clj"
               "interfaces/src"
               "interfaces/src/comp1"
               "interfaces/src/comp1/interface.clj"
               "interfaces/src/ifc2"
               "interfaces/src/ifc2/interface.clj"
               "logo.png"
               "project.clj"
               "readme.md"
               "systems"
               "systems/sys1"
               "systems/sys1/build.sh"
               "systems/sys1/project.clj"
               "systems/sys1/readme.md"
               "systems/sys1/resources"
               "systems/sys1/resources/.keep"
               "systems/sys1/resources/base1"
               "systems/sys1/resources/base1/.keep"
               "systems/sys1/resources/comp1"
               "systems/sys1/resources/comp1/.keep"
               "systems/sys1/src"
               "systems/sys1/src/base1"
               "systems/sys1/src/base1/core.clj"
               "systems/sys1/src/comp1"
               "systems/sys1/src/comp1/core.clj"
               "systems/sys1/src/comp1/interface.clj"}
             (set (file/relative-paths ws-dir)))))))
