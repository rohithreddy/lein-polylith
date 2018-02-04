(ns leiningen.polylith.cmd.help
  (:require [leiningen.polylith.version :as v]))

(defn help [sha1 sha2]
  (println (str "Polylith " v/version " (" v/date ") - https://github.com/tengstrand/polylith"))
  (println)
  (println "  lein polylith cmd [args]  - where cmd [args] are:")
  (println)
  (println "    changes x s1 s2      List changed components, bases or systems between two Git hashes")
  (println "    create x n [ns [d]]  Creates component or workspace 'n'")
  (println "    delete c n           Deletes component 'n'")
  (println "    deps                 List all dependencies")
  (println "    diff s1 s2           List all changes between two Git hashes")
  (println "    info [x] [s1 s2]     List interfaces, components, bases and systems")
  (println "    settings             The polylith settings in current project.clj")
  (println "    test x [s1 s2]       Execute or show tests")
  (println)
  (println "  lein polylith [help]        Show this help")
  (println "  lein polylith help cmd      Show help for a specific command")
  (println "  lein polylith help project  Show valid project.clj settings")
  (println)
  (println "  Examples:")
  (println "    lein polylith build" sha1 sha2)
  (println "    lein polylith changes c" sha1 sha2)
  (println "    lein polylith create c mycomponent")
  (println "    lein polylith create w myworkspace com.my.company")
  (println "    lein polylith delete c mycomponent")
  (println "    lein polylith deps")
  (println "    lein polylith deps f")
  (println "    lein polylith diff" sha1 sha2)
  (println "    lein polylith help info")
  (println "    lein polylith info")
  (println "    lein polylith info a")
  (println "    lein polylith info" sha1 sha2)
  (println "    lein polylith info a" sha1 sha2)
  (println "    lein polylith info c" sha1 sha2)
  (println "    lein polylith settings")
  (println "    lein polylith test i")
  (println "    lein polylith test u-")
  (println "    lein polylith test ui+" sha1 sha2))

(defn changes [sha1 sha2]
  (println "  Show what has changed between two Git hashes.")
  (println "")
  (println "  lein polylith changes x s1 s2")
  (println "    x = i -> show changed interfaces")
  (println "        c -> show changed components")
  (println "        b -> show changed bases")
  (println "        s -> show changed systems")
  (println "    s1 = last (successful) Git sha1")
  (println "    s2 = current Git sha1")
  (println)
  (println "  example:")
  (println "    lein polylith changes i" sha1 sha2)
  (println "    lein polylith changes c" sha1 sha2)
  (println "    lein polylith changes b" sha1 sha2)
  (println "    lein polylith changes s" sha1 sha2))

(defn create []
  (println "  Create a workspace or component.")
  (println)
  (println "    Create a component with the name 'n':")
  (println "      lein polylith create c n")
  (println)
  (println "    Create a workspace with the name 'n' using namespace 'ns':")
  (println "      lein polylith create w n ns [top-dir]")
  (println)
  (println "  If left out, the top directory will correspond to the package, e.g.:")
  (println "  'com/my/company' if package is 'com.my.company'.")
  (println "  Set to blank if package name only exists in the system project.clj files,")
  (println "  but not as a package structure under src, e.g.:")
  (println "    (defproject com.my.comp/development \"1.0\"")
  (println "      ...)")
  (println)
  (println "  example:")
  (println "    lein polylith create c mycomponent")
  (println "    lein polylith create w myworkspace com.my.company")
  (println "    lein polylith create w myworkspace com.my.company \"\""))

(defn delete []
  (println "  Deletes a component")
  (println)
  (println "  lein polylith delete c n")
  (println "    deletes component 'n'")
  (println)
  (println "  example:")
  (println "    lein polylith delete c mycomponent"))

(defn deps []
  (println "  List dependencies to interfaces")
  (println)
  (println "  lein polylith deps     list dependencies to interfaces")
  (println "  lein polylith deps f   list dependencies to functions"))

(defn diff [sha1 sha2]
  (println "  List all files and directories that has changed between two Git sha1's")
  (println)
  (println "  lein polylith diff s1 s2")
  (println "    s1 = last (successful) Git sha1")
  (println "    s2 = current Git sha1")
  (println)
  (println "  example:")
  (println "    lein polylith diff" sha1 sha2))

(defn build [sha1 sha2]
  (println "  Compile, test, and build components, bases and systems between two Git hashes.")
  (println "")
  (println "  lein polylith build s1 s2")
  (println "    s1 = last (successful) Git sha1")
  (println "    s2 = current Git sha1")
  (println)
  (println "  example:")
  (println "    lein polylith build" sha1 sha2))

(defn info [sha1 sha2]
  (println "  Show the content of a Polylith workspace and optionally its changes")
  (println "  (each row is followed by an * if something is changed)")
  (println)
  (println "  lein polylith info [x] [s1 s2]")
  (println "    x = a -> show all interfaces, components, bases and systems")
  (println "        c -> show changed interfaces, components, bases and systems")
  (println "        u -> show unchanged interfaces, components, bases and systems")
  (println "        (omitted) -> show all components, bases, systems (and interfaces if changed)")
  (println "    s1 = last (successful) Git sha1")
  (println "    s2 = current Git sha1")
  (println)
  (println "  example:")
  (println "    lein polylith info")
  (println "    lein polylith info a")
  (println "    lein polylith info" sha1 sha2)
  (println "    lein polylith info a" sha1 sha2)
  (println "    lein polylith info c" sha1 sha2))

(defn project []
  (println "  These are the valid settings of the :polylith section in the developments")
  (println "  project.clj file (the main development project if having more than one):")
  (println)
  (println "    :vcs x                \"git\" is the only valid value of x for the moment.")
  (println)
  (println "    :build-tool x         \"leiningen\" is the only valid value of x for the moment.")
  (println)
  (println "    :top-ns x             x is the name of the top namespace. This ns is added")
  (println "                          to the interfaces project.clj and each component project.clj")
  (println "                          file (to add the correct Maven artifact namespace).")
  (println)
  (println "    :top-dir x            x is the directory path of the top namespace. If empty")
  (println "                          then each component name will be the top namespace.")
  (println "                          Do not put a '/' at the end of the path.")
  (println)
  (println "    :development-dirs x   x is a vector of development directories.")
  (println)
  (println "    :ignored-tests x      x is a vector of component and base tests that should be ignored.")
  (println)
  (println "    :clojure-version x    x is the version of clojure used when creating components.")
  (println)
  (println "    :example-hash1 x      x is 'previous (successful) build SHA1' used in the examples.")
  (println "    :example-hash2 x      x is 'current build SHA1' used in the examples.")
  (println)
  (println "  Example of :ignored-tests:")
  (println "    [\"comp*\"] = ignore all 'comp' tests including all underlying namespaces")
  (println "    [\"comp.x*\"] = ignore all 'comp.x' tests including all underlying namespaces")
  (println "    [\"comp.x\" \"sys.y\"] = ignore all 'comp.x' and 'sys.y' tests")
  (println)
  (println "  Example of project.clj:")
  (println "    (defproject ...")
  (println "      ...")
  (println "      :polylith {:vcs \"git\"")
  (println "                 :build-tool \"leiningen\"")
  (println "                 :top-ns \"com.mycompany\"")
  (println "                 :top-dir \"com/mycompany\"")
  (println "                 :development-dirs [\"development\"]")
  (println "                 :ignored-tests [\"migration*\" \"backend.mock\"]")
  (println "                 :clojure-version \"1.9.0\"")
  (println "                 :exmple-sha1 \"2c851f3c6e7a5114cecf6bdd6e1c8c8aec8b32c1\"")
  (println "                 :exmple-sha2 \"58cd8b3106c942f372a40616fe9155c9d2efd122\"}")
  (println "      ...")
  (println "    )"))

(defn settings []
  (println "  Shows the {:polylith ....} settings in the project.clj file")
  (println "  + the root directory of the Polylith workspace")
  (println)
  (println "  lein polylith settings"))

(defn test-cmd [sha1 sha2]
  (println "  Execute or show tests")
  (println)
  (println "  lein polylith test x [s1 s2]")
  (println "    x = u   -> execute unit tests")
  (println "        i   -> execute integration tests")
  (println "        ui  -> execute unit + integration tests")
  (println "        u-  -> print Leiningen test statement (unit tests)")
  (println "        i-  -> print Leiningen test statement (integration tests)")
  (println "        ui- -> print Leiningen test statement (unit+integration tests)")
  (println "        u+  -> print unit tests")
  (println "        i+  -> print integration tests")
  (println "        ui+ -> print unit + integration tests")
  (println)
  (println "    s1: last (successful) Git sha1")
  (println "    s2: current Git sha1")
  (println)
  (println "    if s1 and s2 are given:")
  (println "      include tests from changed components and bases")
  (println "    if s1 and s2 are omitted:")
  (println "      include tests from all components and bases")
  (println)
  (println "    Tests can also be ignored, see the 'project' help.")
  (println)
  (println "  examples:")
  (println "    lein polylith test i")
  (println "    lein polylith test u-")
  (println "    lein polylith test ui+" sha1 sha2))

(defn execute [sha1 sha2 [cmd]]
  (condp = cmd
    "changes" (changes sha1 sha2)
    "create" (create)
    "delete" (delete)
    "deps" (deps)
    "diff" (diff sha1 sha2)
    "info" (info sha1 sha2)
    "project" (project)
    "settings" (settings)
    "test" (test-cmd sha1 sha2)
    (help sha1 sha2)))
