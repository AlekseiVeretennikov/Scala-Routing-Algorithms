scalaVersion := "2.12.20"

val javafxVersion = "17.0.2"

// Упрощенная версия для Windows. Убрана сложная логика, вызывавшая сбой.
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "16.0.0-R24",

  "org.openjfx" % "javafx-base"     % javafxVersion classifier "win",
  "org.openjfx" % "javafx-controls" % javafxVersion classifier "win",
  "org.openjfx" % "javafx-graphics" % javafxVersion classifier "win",
  "org.openjfx" % "javafx-media"    % javafxVersion classifier "win",
  "org.openjfx" % "javafx-swing"    % javafxVersion classifier "win",
  "org.openjfx" % "javafx-web"      % javafxVersion classifier "win"
)