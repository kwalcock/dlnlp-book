package org.clulab.dlnlp_book.apps

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.SeqConverters

object LogisticRegressionApp extends App {

  // In [1]:
  val random = py.module("random")
  val np = py.module("numpy")
  val torch = py.module("torch")
  val tqdm = py.module("tqdm.notebook")

  // set this variable to a number to be used as the random seed
  // or to None if you don't want to set a random seed
  val seedOpt = Some(1234) // None

  seedOpt.foreach { seed =>
    random.seed(seed)
    np.random.seed(seed)
  }

  // In [4]:
  val glob = py.module("glob")
  val posFiles = glob.glob("e:/DocumentCollections/aclImdb/train/pos/*.txt")
  val negFiles = glob.glob("e:/DocumentCollections/aclImdb/train/neg/*.txt")

  val len = py.Dynamic.global.len

  println(s"number of positive reviews: ${len(posFiles)}")
  println(s"number of negative reviews: ${py.Dynamic.global.len(negFiles)}")

  // In [5]:
  val sklearn = py.module("sklearn.feature_extraction.text")
  val cv = sklearn.CountVectorizer(input="filename")
  val docTermMatrix = cv.fit_transform(posFiles + negFiles)
  // println(docTermMatrix)

  // In [6]:
  var xTrain = docTermMatrix.toarray()
  println(xTrain.shape)

  // In [7]:
  // Append 1s to the xs; this will allow us to multiply by the weights and
  // the bias in a single pass.
  // Make an array with a one for each row/data point
  val ones = np.ones(xTrain.shape.bracketAccess(0))
  // Concatenate these ones to existing feature vectors
  xTrain = np.column_stack((xTrain, ones))
  println(xTrain.shape)

  // In [8]:
  // training labels
  val yPos = np.ones(py.Dynamic.global.len(posFiles))
  val yNeg = np.zeros(py.Dynamic.global.len(negFiles))
  val yTrain = np.concatenate(py.Dynamic.global.list(Seq(yPos, yNeg).toPythonProxy))
  println(yTrain)

  println("Hello, world!")
}
