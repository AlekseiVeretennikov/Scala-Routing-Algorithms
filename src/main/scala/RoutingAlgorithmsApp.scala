import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle, Line}
import scalafx.scene.text.{Font, Text}

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Random, Success, Try}

/*
 * Лабораторная работа №1 по курсу "Программное обеспечение компьютерных сетей"
 *
 * Цель: Реализовать и сравнить алгоритмы поиска кратчайшего пути в графе.
 * - Реализованы алгоритмы: Дейкстры, Беллмана-Форда, Флойда-Уоршалла.
 * - Для интерфейса используется ScalaFX.
 * - Есть визуализация графа и найденного пути.
 */
object RoutingAlgorithmsApp extends JFXApp {

  // --- Основные переменные для графа ---
  type Vertex = Int
  type Weight = Int
  case class Edge(source: Vertex, destination: Vertex, weight: Weight)

  var graphEdges: List[Edge] = List.empty
  var numVertices: Int = 0
  val Infinity: Int = Int.MaxValue

  // --- Элементы интерфейса (GUI) ---
  private val canvas = new Pane { // Панель для отрисовки графа
    style = "-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1;"
    prefWidth = 800
    prefHeight = 650
  }
  private val logArea = new TextArea { // Область для вывода результатов
    editable = false
    font = Font("Monospaced", 12)
    promptText = "Результаты и метрики производительности будут здесь..."
  }
  private val verticesInput = new TextField {
    text = "105"
    promptText = "Кол-во вершин (>100)"
  }
  private val startInput = new TextField {
    promptText = "Старт"
  }
  private val endInput = new TextField {
    promptText = "Финиш"
  }

  // Функция для генерации случайного графа
  def generateGraph(vertices: Int): Unit = {
    numVertices = vertices
    val r = new Random
    val edges = mutable.ListBuffer[Edge]()

    // Сначала создаем связный граф, чтобы из любой вершины можно было добраться в любую
    for (i <- 0 until vertices - 1) {
      edges += Edge(i, i + 1, r.nextInt(20) + 1)
    }
    // Добавляем еще случайных ребер для сложности
    val extraEdges = vertices * 2
    for (_ <- 0 until extraEdges) {
      val u = r.nextInt(vertices)
      val v = r.nextInt(vertices)
      if (u != v && !edges.exists(e => e.source == u && e.destination == v)) {
        edges += Edge(u, v, r.nextInt(50) + 1)
      }
    }
    graphEdges = edges.toList
    drawGraph() // Перерисовываем
    logArea.text = s"Сгенерирован граф с $numVertices вершинами и ${graphEdges.size} ребрами."
  }

  // Главная функция отрисовки. Умеет подсвечивать путь.
  def drawGraph(path: List[Vertex] = List.empty): Unit = {
    canvas.children.clear() // Очищаем старый граф перед отрисовкой
    if (numVertices == 0) return

    val radius = 300
    val centerX = canvas.getPrefWidth / 2
    val centerY = canvas.getPrefHeight / 2

    // Рассчитываем координаты вершин по кругу
    val positions = (0 until numVertices).map { i =>
      val angle = 2 * Math.PI * i / numVertices
      (centerX + radius * Math.cos(angle), centerY + radius * Math.sin(angle))
    }.toVector

    // Сначала рисуем все ребра серым цветом
    graphEdges.foreach { edge =>
      val (startX, startY) = positions(edge.source)
      val (endX, endY) = positions(edge.destination)
      val line = new Line()
      line.startX = startX
      line.startY = startY
      line.endX = endX
      line.endY = endY
      line.stroke = LightGray
      line.strokeWidth = 1
      canvas.children.add(line.delegate)
    }

    // Если есть путь для подсветки - рисуем его красным поверх
    if (path.nonEmpty) {
      path.sliding(2).foreach {
        case List(u, v) =>
          val (startX, startY) = positions(u)
          val (endX, endY) = positions(v)
          val line = new Line()
          line.startX = startX
          line.startY = startY
          line.endX = endX
          line.endY = endY
          line.stroke = Red
          line.strokeWidth = 3
          canvas.children.add(line.delegate)
      }
    }

    // Рисуем сами вершины (круги с номерами)
    positions.zipWithIndex.foreach { case ((x, y), i) =>
      val isPathNode = path.contains(i)
      val circle = new Circle {
        centerX = x
        centerY = y
        radius = if (isPathNode) 9 else 6
        fill = if (isPathNode) OrangeRed else SteelBlue
      }
      
      val label = i.toString
      // Динамически вычисляем отступ, чтобы цифры были по центру
      val xOffset = label.length match {
        case 1 => 4
        case 2 => 8
        case _ => 12
      }
      val text = new Text(x - xOffset, y + 5, label) {
        font = Font("Arial", 11)
        fill = White
      }
      
      canvas.children.addAll(circle.delegate, text.delegate)
    }
  }

  // --- Сами алгоритмы поиска пути ---

  def dijkstra(start: Vertex, end: Vertex): (List[Vertex], Weight) = {
      val distances = Array.fill(numVertices)(Infinity)
      val previous = Array.fill[Option[Vertex]](numVertices)(None)
      val pq = mutable.PriorityQueue[(Weight, Vertex)]()(Ordering.by(-_._1))
      distances(start) = 0
      pq.enqueue((0, start))
      while (pq.nonEmpty) {
          val (_, u) = pq.dequeue()
          if (u == end) { // Оптимизация - останавливаемся, как только нашли конечную вершину
              val path = mutable.ListBuffer[Vertex]()
              var current = Option(end)
              while (current.isDefined) {
                  path.prepend(current.get)
                  current = previous(current.get)
              }
              return (path.toList, distances(end))
          }
          graphEdges.filter(_.source == u).foreach { edge =>
              val newDist = distances(u) + edge.weight
              if (newDist < distances(edge.destination)) {
                  distances(edge.destination) = newDist
                  previous(edge.destination) = Some(u)
                  pq.enqueue((newDist, edge.destination))
              }
          }
      }
      (List.empty, Infinity) // Если путь не найден
  }

  def bellmanFord(start: Vertex, end: Vertex): (List[Vertex], Weight) = {
      val distances = Array.fill(numVertices)(Infinity)
      val previous = Array.fill[Option[Vertex]](numVertices)(None)
      distances(start) = 0
      // Расслабление ребер V-1 раз
      for (_ <- 1 until numVertices) {
          graphEdges.foreach { edge =>
              if (distances(edge.source) != Infinity && distances(edge.source) + edge.weight < distances(edge.destination)) {
                  distances(edge.destination) = distances(edge.source) + edge.weight
                  previous(edge.destination) = Some(edge.source)
              }
          }
      }
      // Восстановление пути от конца к началу
      val path = mutable.ListBuffer[Vertex]()
      var current = Option(end)
      while(current.isDefined && current.get != start) {
          path.prepend(current.get)
          current = previous(current.get)
      }
      if (current.isDefined && current.get == start) {
          path.prepend(start)
          (path.toList, distances(end))
      } else (List.empty, Infinity)
  }

  def floydWarshall(start: Vertex, end: Vertex): (List[Vertex], Weight) = {
      val dist = Array.fill(numVertices, numVertices)(Infinity)
      val next = Array.fill[Option[Vertex]](numVertices, numVertices)(None)
      graphEdges.foreach { edge =>
          dist(edge.source)(edge.destination) = edge.weight
          next(edge.source)(edge.destination) = Some(edge.destination)
      }
      for (i <- 0 until numVertices) dist(i)(i) = 0

      for (k <- 0 until numVertices; i <- 0 until numVertices; j <- 0 until numVertices) {
          if (dist(i)(k) != Infinity && dist(k)(j) != Infinity && dist(i)(k) + dist(k)(j) < dist(i)(j)) {
              dist(i)(j) = dist(i)(k) + dist(k)(j)
              next(i)(j) = next(i)(k)
          }
      }

      if (dist(start)(end) == Infinity) return (List.empty, Infinity)
      
      val path = mutable.ListBuffer[Vertex]()
      var u = Option(start)
      while(u.get != end) {
          path += u.get
          u = next(u.get)(end)
          if(u.isEmpty) return (List.empty, Infinity) // На всякий случай
      }
      path += end
      (path.toList, dist(start)(end))
  }
  
  // Функция, которая запускается при нажатии на главную кнопку
  def runAlgorithms(): Unit = {
    // Безопасно пытаемся превратить текст из полей в числа
    val startOpt = Try(startInput.text.value.toInt).toOption
    val endOpt = Try(endInput.text.value.toInt).toOption

    if (startOpt.isEmpty || endOpt.isEmpty) {
      logArea.text = "Ошибка: введите корректные стартовую и конечную вершины."
      return
    }
    val start = startOpt.get
    val end = endOpt.get

    // Проверка, что числа не выходят за пределы количества вершин
    if (start < 0 || start >= numVertices || end < 0 || end >= numVertices) {
      logArea.text = "Ошибка: вершины вне диапазона графа."
      return
    }

    logArea.text = "Выполнение расчетов...\n"

    // Запускаем каждый алгоритм в своем потоке, чтобы не блокировать интерфейс
    val futureDijkstra = Future {
        val startTime = System.nanoTime()
        val (path, weight) = dijkstra(start, end)
        val duration = (System.nanoTime() - startTime) / 1e6
        (path, weight, duration)
    }
    val futureBellmanFord = Future {
        val startTime = System.nanoTime()
        val (path, weight) = bellmanFord(start, end)
        val duration = (System.nanoTime() - startTime) / 1e6
        (path, weight, duration)
    }
    val futureFloydWarshall = Future {
        val startTime = System.nanoTime()
        val (path, weight) = floydWarshall(start, end)
        val duration = (System.nanoTime() - startTime) / 1e6
        (path, weight, duration)
    }

    // Когда расчеты закончатся, выводим результаты в лог
    futureDijkstra.onComplete {
      case Success((path, weight, duration)) =>
        javafx.application.Platform.runLater(() => {
          logArea.appendText(f"[Дейкстра]\nВремя: $duration%.4f ms\nВес пути: $weight\nПуть: ${path.mkString(" -> ")}\n\n")
          drawGraph(path) // Перерисовываем граф с подсвеченным путем
        })
      case _ =>
    }
    futureBellmanFord.onComplete {
      case Success((_, weight, duration)) =>
        javafx.application.Platform.runLater(() =>
          logArea.appendText(f"[Беллман-Форд]\nВремя: $duration%.4f ms\nВес пути: $weight\n\n")
        )
      case _ =>
    }
    futureFloydWarshall.onComplete {
      case Success((_, weight, duration)) =>
        javafx.application.Platform.runLater(() =>
          logArea.appendText(f"[Флойд-Уоршалл]\nВремя: $duration%.4f ms\nВес пути: $weight\n\n")
        )
      case _ =>
    }
  }

  // --- Настройка главного окна приложения ---
  stage = new PrimaryStage {
    title = "Лабораторная работа 1: Алгоритмы маршрутизации"
    scene = new Scene(1200, 800) {

      val controls = new VBox(10) {
        padding = Insets(10)
        children = Seq(
          new Label("Настройки графа"),
          new HBox(10, new Label("Вершин:"), verticesInput),
          new Button("Сгенерировать граф") {
            onAction = _ => generateGraph(Try(verticesInput.text.value.toInt).getOrElse(100))
          },
          new Separator(),
          new Label("Поиск пути"),
          new HBox(10, new Label("От:"), startInput),
          new HBox(10, new Label("До:"), endInput),
          new Button("Найти кратчайший путь") {
            onAction = _ => runAlgorithms()
            style = "-fx-font-weight: bold;"
          }
        )
      }

      val rootLayout = new BorderPane {
        left = controls
        center = new VBox(10) {
          padding = Insets(10)
          children = Seq(
            new Label("Визуализация графа и найденного пути") {
              font = Font("Arial", 16)
            },
            canvas, // Используем нашу панель
            new Label("Вывод и сравнительный анализ") {
              font = Font("Arial", 16)
            },
            logArea // и наш лог
          )
          VBox.setVgrow(logArea, Priority.Always)
        }
      }

      root = rootLayout
    }
    generateGraph(105) // Генерируем граф при первом запуске
  }
}