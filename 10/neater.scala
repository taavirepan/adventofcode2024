class Path(val path: List[(Int, Int)]) {
  def extend(x: Int, y: Int): Option[Path] = {
    if (path.contains((x, y))) {
      return None
    }
    return Some(new Path(path :+ (x, y)))
  }

  def position: (Int, Int) = {
    path.last
  }
}

class Grid(val size: Int, val data: Array[Int]) {
  def apply(x: Int, y: Int): Int = {
    if (x < 0 || y < 0 || x >= size || y >= size) {
      return -1;
    }
    data(x * size + y)
  }

  def forEach(f: (Int, Int, Int) => Unit): Unit = {
    for (i <- 0 until size; j <- 0 until size) {
      f(i, j, this(i, j))
    }
  }

  def paths[T](path: Path, f: (T, Int, Int) => T, init: T): T = {
    var queue = List(path)
    var ret = init
    while (queue.nonEmpty) {
      val current = queue.head
      queue = queue.tail
      val (x, y) = current.position
      if (this(x, y) == 9) {
        ret = f(ret, x, y)
      }
      for ((dx, dy) <- List((1, 0), (-1, 0), (0, 1), (0, -1)) if this(x + dx, y + dy) - this(x, y) == 1) {
        current.extend(x + dx, y + dy).foreach(queue :+= _)
      }
    }
    ret
  }
}

def readGrid(): Grid = {
  var size = 0
  var data = List[Int]()
  while (System.in.available() > 0) {
    val char = System.in.read()
    if (char == '\n') {
      size += 1
    } else {
      data = data :+ (char - '0')
    }
  }
  new Grid(size, data.toArray)
}

@main
def main(): Unit = {
  val grid = readGrid()
  var task1: Int = 0;
  var task2: Int = 0;
  grid.forEach((x, y, v) => {
    if (v == 0) {
      val path = new Path(List((x, y)))
      task1 += grid.paths(path, (ret: Set[(Int, Int)], x, y) => ret + ((x, y)), Set[(Int, Int)]()).size
      task2 += grid.paths(path, (ret: Int, x, y) => ret + 1, 0)
    }
  })
  println(task1);
  println(task2);
}
