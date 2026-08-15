import { defineConfig } from "deepsec/config";

export default defineConfig({
  projects: [
    {
      id: "rowtool",
      root: "..",
      promptAppend:
        "Prioritize backup import validation and rollback, local database integrity, exported Android components, file and URI handling, and sensitive logging.",
      priorityPaths: [
        "app/src/main/AndroidManifest.xml",
        "app/src/main/res/xml/",
        "app/src/main/java/com/finnvek/rowtool/data/repository/",
        "app/src/main/java/com/finnvek/rowtool/data/local/",
        "app/src/main/java/com/finnvek/rowtool/ui/screens/settings/"
      ]
    }
  ]
});
