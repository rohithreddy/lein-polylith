(ns leiningen.polylith.cmd.delete-test
  (:require [clojure.test :refer :all]
            [leiningen.polylith.cmd.test-helper :as helper]
            [leiningen.polylith :as polylith]
            [leiningen.polylith.file :as file]
            [clojure.string :as str]))

(use-fixtures :each helper/test-setup-and-tear-down)

(deftest delete--interface-not-shared-with-another-component--delete-interface
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [ws-dir (str @helper/root-dir "/ws1")
          project (helper/settings ws-dir "my.company")
          output (with-out-str
                   (polylith/polylith nil "create" "w" "ws1" "my.company" "-git")
                   (polylith/polylith project "create" "s" "system-1")
                   (polylith/polylith project "create" "c" "comp-1")
                   (polylith/polylith project "create" "c" "comp-2" "interface-2")
                   (polylith/polylith project "create" "c" "comp-3")
                   (polylith/polylith project "add" "comp-2" "system-1")
                   (polylith/polylith project "add" "comp-3" "system-1")
                   (polylith/polylith project "delete" "c" "comp-2")
                   (polylith/polylith project "info"))]

      (is (= ["interfaces:"
              "  comp-1 *"
              "  comp-3 *"
              "components:"
              "  comp-1 *"
              "  comp-3 *"
              "bases:"
              "  system-1 *"
              "systems:"
              "  system-1 *"
              "    comp-3 *     -> component"
              "    system-1 *   -> base"
              "environments:"
              "  development"
              "    comp-1 *     -> component"
              "    comp-3 *     -> component"
              "    system-1 *   -> base"]
             (helper/split-lines output)))

      (is (= #{".gitignore"
               ".polylith"
               ".polylith/time.edn"
               "Readme.md"
               "bases"
               "bases/system-1"
               "bases/system-1/Readme.md"
               "bases/system-1/project.clj"
               "bases/system-1/resources"
               "bases/system-1/resources/.keep"
               "bases/system-1/resources/system-1"
               "bases/system-1/resources/system-1/.keep"
               "bases/system-1/src"
               "bases/system-1/src/my"
               "bases/system-1/src/my/company"
               "bases/system-1/src/my/company/system_1"
               "bases/system-1/src/my/company/system_1/core.clj"
               "bases/system-1/test"
               "bases/system-1/test/my"
               "bases/system-1/test/my/company"
               "bases/system-1/test/my/company/system_1"
               "bases/system-1/test/my/company/system_1/core_test.clj"
               "components"
               "components/comp-1"
               "components/comp-1/Readme.md"
               "components/comp-1/project.clj"
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
               "components/comp-3"
               "components/comp-3/Readme.md"
               "components/comp-3/project.clj"
               "components/comp-3/resources"
               "components/comp-3/resources/.keep"
               "components/comp-3/resources/comp-3"
               "components/comp-3/resources/comp-3/.keep"
               "components/comp-3/src"
               "components/comp-3/src/my"
               "components/comp-3/src/my/company"
               "components/comp-3/src/my/company/comp_3"
               "components/comp-3/src/my/company/comp_3/core.clj"
               "components/comp-3/src/my/company/comp_3/interface.clj"
               "components/comp-3/test"
               "components/comp-3/test/my"
               "components/comp-3/test/my/company"
               "components/comp-3/test/my/company/comp_3"
               "components/comp-3/test/my/company/comp_3/core_test.clj"
               "environments"
               "environments/development"
               "environments/development/docs"
               "environments/development/docs/comp-1-Readme.md"
               "environments/development/docs/comp-3-Readme.md"
               "environments/development/docs/system-1-Readme.md"
               "environments/development/interfaces"
               "environments/development/interfaces/my"
               "environments/development/interfaces/my/company"
               "environments/development/interfaces/my/company/comp_1"
               "environments/development/interfaces/my/company/comp_1/interface.clj"
               "environments/development/interfaces/my/company/comp_3"
               "environments/development/interfaces/my/company/comp_3/interface.clj"
               "environments/development/project-files"
               "environments/development/project-files/bases"
               "environments/development/project-files/bases/system-1-project.clj"
               "environments/development/project-files/components"
               "environments/development/project-files/components/comp-1-project.clj"
               "environments/development/project-files/components/comp-3-project.clj"
               "environments/development/project-files/interfaces-project.clj"
               "environments/development/project-files/systems"
               "environments/development/project-files/systems/system-1-project.clj"
               "environments/development/project-files/workspace-project.clj"
               "environments/development/project.clj"
               "environments/development/resources"
               "environments/development/resources/.keep"
               "environments/development/resources/comp-1"
               "environments/development/resources/comp-1/.keep"
               "environments/development/resources/comp-3"
               "environments/development/resources/comp-3/.keep"
               "environments/development/resources/system-1"
               "environments/development/resources/system-1/.keep"
               "environments/development/src"
               "environments/development/src/my"
               "environments/development/src/my/company"
               "environments/development/src/my/company/comp_1"
               "environments/development/src/my/company/comp_1/core.clj"
               "environments/development/src/my/company/comp_1/interface.clj"
               "environments/development/src/my/company/comp_3"
               "environments/development/src/my/company/comp_3/core.clj"
               "environments/development/src/my/company/comp_3/interface.clj"
               "environments/development/src/my/company/system_1"
               "environments/development/src/my/company/system_1/core.clj"
               "environments/development/test"
               "environments/development/test/my"
               "environments/development/test/my/company"
               "environments/development/test/my/company/comp_1"
               "environments/development/test/my/company/comp_1/core_test.clj"
               "environments/development/test/my/company/comp_3"
               "environments/development/test/my/company/comp_3/core_test.clj"
               "environments/development/test/my/company/system_1"
               "environments/development/test/my/company/system_1/core_test.clj"
               "interfaces"
               "interfaces/project.clj"
               "interfaces/src"
               "interfaces/src/my"
               "interfaces/src/my/company"
               "interfaces/src/my/company/comp_1"
               "interfaces/src/my/company/comp_1/interface.clj"
               "interfaces/src/my/company/comp_3"
               "interfaces/src/my/company/comp_3/interface.clj"
               "logo.png"
               "project.clj"
               "systems"
               "systems/system-1"
               "systems/system-1/Readme.md"
               "systems/system-1/build.sh"
               "systems/system-1/project.clj"
               "systems/system-1/resources"
               "systems/system-1/resources/.keep"
               "systems/system-1/resources/comp-3"
               "systems/system-1/resources/comp-3/.keep"
               "systems/system-1/resources/system-1"
               "systems/system-1/resources/system-1/.keep"
               "systems/system-1/src"
               "systems/system-1/src/my"
               "systems/system-1/src/my/company"
               "systems/system-1/src/my/company/comp_3"
               "systems/system-1/src/my/company/comp_3/core.clj"
               "systems/system-1/src/my/company/comp_3/interface.clj"
               "systems/system-1/src/my/company/system_1"
               "systems/system-1/src/my/company/system_1/core.clj"}
             (set (file/relative-paths ws-dir)))))))

(deftest delete--interface-shared-with-another-component--keep-interface
  (with-redefs [file/current-path (fn [] @helper/root-dir)]
    (let [ws-dir (str @helper/root-dir "/ws1")
          project (helper/settings ws-dir "my.company")
          output (with-out-str
                   (polylith/polylith nil "create" "w" "ws1" "my.company" "-git")
                   (polylith/polylith project "create" "s" "system-1")
                   (polylith/polylith project "create" "c" "comp-1")
                   (polylith/polylith project "create" "c" "comp-2" "interface-2")
                   (polylith/polylith project "create" "c" "comp-3" "interface-2")
                   (polylith/polylith project "add" "comp-2" "system-1")
                   (polylith/polylith project "delete" "c" "comp-2")
                   (polylith/polylith project "info"))]

      (is (= ["interfaces:"
              "  comp-1 *"
              "  interface-2 *"
              "components:"
              "  comp-1 *"
              "  comp-3 *   > interface-2"
              "bases:"
              "  system-1 *"
              "systems:"
              "  system-1 *"
              "    system-1 *   -> base"
              "environments:"
              "  development"
              "    comp-1 *     -> component"
              "    system-1 *   -> base"]
             (helper/split-lines output)))

      (is (= #{".gitignore"
               ".polylith"
               ".polylith/time.edn"
               "Readme.md"
               "bases"
               "bases/system-1"
               "bases/system-1/Readme.md"
               "bases/system-1/project.clj"
               "bases/system-1/resources"
               "bases/system-1/resources/.keep"
               "bases/system-1/resources/system-1"
               "bases/system-1/resources/system-1/.keep"
               "bases/system-1/src"
               "bases/system-1/src/my"
               "bases/system-1/src/my/company"
               "bases/system-1/src/my/company/system_1"
               "bases/system-1/src/my/company/system_1/core.clj"
               "bases/system-1/test"
               "bases/system-1/test/my"
               "bases/system-1/test/my/company"
               "bases/system-1/test/my/company/system_1"
               "bases/system-1/test/my/company/system_1/core_test.clj"
               "components"
               "components/comp-1"
               "components/comp-1/Readme.md"
               "components/comp-1/project.clj"
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
               "components/comp-3"
               "components/comp-3/Readme.md"
               "components/comp-3/project.clj"
               "components/comp-3/resources"
               "components/comp-3/resources/.keep"
               "components/comp-3/resources/comp-3"
               "components/comp-3/resources/comp-3/.keep"
               "components/comp-3/src"
               "components/comp-3/src/my"
               "components/comp-3/src/my/company"
               "components/comp-3/src/my/company/comp_3"
               "components/comp-3/src/my/company/comp_3/core.clj"
               "components/comp-3/src/my/company/interface_2"
               "components/comp-3/src/my/company/interface_2/interface.clj"
               "components/comp-3/test"
               "components/comp-3/test/my"
               "components/comp-3/test/my/company"
               "components/comp-3/test/my/company/comp_3"
               "components/comp-3/test/my/company/comp_3/core_test.clj"
               "environments"
               "environments/development"
               "environments/development/docs"
               "environments/development/docs/comp-1-Readme.md"
               "environments/development/docs/system-1-Readme.md"
               "environments/development/interfaces"
               "environments/development/interfaces/my"
               "environments/development/interfaces/my/company"
               "environments/development/interfaces/my/company/comp_1"
               "environments/development/interfaces/my/company/comp_1/interface.clj"
               "environments/development/interfaces/my/company/interface_2"
               "environments/development/interfaces/my/company/interface_2/interface.clj"
               "environments/development/project-files"
               "environments/development/project-files/bases"
               "environments/development/project-files/bases/system-1-project.clj"
               "environments/development/project-files/components"
               "environments/development/project-files/components/comp-1-project.clj"
               "environments/development/project-files/interfaces-project.clj"
               "environments/development/project-files/systems"
               "environments/development/project-files/systems/system-1-project.clj"
               "environments/development/project-files/workspace-project.clj"
               "environments/development/project.clj"
               "environments/development/resources"
               "environments/development/resources/.keep"
               "environments/development/resources/comp-1"
               "environments/development/resources/comp-1/.keep"
               "environments/development/resources/system-1"
               "environments/development/resources/system-1/.keep"
               "environments/development/src"
               "environments/development/src/my"
               "environments/development/src/my/company"
               "environments/development/src/my/company/comp_1"
               "environments/development/src/my/company/comp_1/core.clj"
               "environments/development/src/my/company/comp_1/interface.clj"
               "environments/development/src/my/company/system_1"
               "environments/development/src/my/company/system_1/core.clj"
               "environments/development/test"
               "environments/development/test/my"
               "environments/development/test/my/company"
               "environments/development/test/my/company/comp_1"
               "environments/development/test/my/company/comp_1/core_test.clj"
               "environments/development/test/my/company/system_1"
               "environments/development/test/my/company/system_1/core_test.clj"
               "interfaces"
               "interfaces/project.clj"
               "interfaces/src"
               "interfaces/src/my"
               "interfaces/src/my/company"
               "interfaces/src/my/company/comp_1"
               "interfaces/src/my/company/comp_1/interface.clj"
               "interfaces/src/my/company/interface_2"
               "interfaces/src/my/company/interface_2/interface.clj"
               "logo.png"
               "project.clj"
               "systems"
               "systems/system-1"
               "systems/system-1/Readme.md"
               "systems/system-1/build.sh"
               "systems/system-1/project.clj"
               "systems/system-1/resources"
               "systems/system-1/resources/.keep"
               "systems/system-1/resources/system-1"
               "systems/system-1/resources/system-1/.keep"
               "systems/system-1/src"
               "systems/system-1/src/my"
               "systems/system-1/src/my/company"
               "systems/system-1/src/my/company/system_1"
               "systems/system-1/src/my/company/system_1/core.clj"}
             (set (file/relative-paths ws-dir)))))))