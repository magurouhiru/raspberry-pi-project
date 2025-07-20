lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(UniversalPlugin)
  // .enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend
  .settings(
    name := """api-server""",
    organization := "com.example",
    version := "0.2.0",
    crossScalaVersions := Seq("2.13.16", "3.3.6"),
    scalaVersion := crossScalaVersions.value.head,
    // ファイル変更検知できないためポーリング
    PlayKeys.fileWatchService := play.dev.filewatch.FileWatchService.polling(500),
    libraryDependencies ++= Seq(
      guice,
      javaWs,
      jdbc,
      evolutions,
      "org.xerial" % "sqlite-jdbc" % "3.50.2.0",
      "io.getquill" %% "quill-jdbc" % "4.8.5",
      "org.typelevel" %% "cats-core" % "2.13.0",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test,
    ),
    scalacOptions ++= Seq("-feature", "-Werror"),
  )
